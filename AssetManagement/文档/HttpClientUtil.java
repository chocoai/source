package com.richinfo.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.log4j.Logger;

public final class HttpClientUtil {
    private HttpClientUtil() {
    }

    public static String get(String url, String charset) throws IOException {
        return get(url, (Map)null, (String)charset);
    }

    public static String get(String url, Map<String, String> headers, String charset) throws IOException {
        HttpGet method = new HttpGet(url);
        return internalExecute(method, headers, charset);
    }

    public static String post(String url, String queryString, String charset) throws IOException {
        return post(url, (String)queryString, (Map)null, charset);
    }

    public static String post(String url, String queryString, Map<String, String> headers, String charset) throws IOException {
        Logger.getLogger("httpclientlog").debug("url: " + url + ", qs: " + queryString);
        HttpPost method = new HttpPost(url);
        StringEntity entity = new StringEntity(queryString, charset);
        entity.setContentType("application/x-www-form-urlencoded");
        method.setEntity(entity);
        return internalExecute(method, headers, charset);
    }

    public static String post(String url, Map<String, Object> fields, String charset) throws IOException {
        return post(url, (Map)fields, (Map)null, charset);
    }

    public static String post(String url, Map<String, Object> fields, Map<String, String> headers, String charset) throws IOException {
        Logger.getLogger("httpclientlog").debug("url: " + url);
        HttpPost method = new HttpPost(url);
        MultipartEntity entity = new MultipartEntity();
        Iterator it = fields.entrySet().iterator();

        while(true) {
            while(it.hasNext()) {
                Entry<String, Object> e = (Entry)it.next();
                if (e.getValue() != null && e.getValue() instanceof File) {
                    File file = (File)e.getValue();
                    entity.addPart((String)e.getKey(), new InputStreamBody(new FileInputStream(file), file.getName()));
                } else {
                    entity.addPart((String)e.getKey(), new StringBody("" + e.getValue()));
                }
            }

            method.setEntity(entity);
            return internalExecute(method, headers, charset);
        }
    }

    public static void post(String url, Map<String, Object> fields, Map<String, String> headers, String charset, HttpClientUtil.HttpClientCallback callback) throws IOException {
        Logger.getLogger("httpclientlog").debug("url: " + url);
        HttpPost method = new HttpPost(url);
        MultipartEntity entity = new MultipartEntity();
        Iterator it = fields.entrySet().iterator();

        while(true) {
            while(it.hasNext()) {
                Entry<String, Object> e = (Entry)it.next();
                if (e.getValue() != null && e.getValue() instanceof File) {
                    File file = (File)e.getValue();
                    entity.addPart((String)e.getKey(), new InputStreamBody(new FileInputStream(file), file.getName()));
                } else {
                    entity.addPart((String)e.getKey(), new StringBody("" + e.getValue()));
                }
            }

            method.setEntity(entity);
            internalExecute(method, headers, charset, callback);
            return;
        }
    }

    private static String internalExecute(HttpRequestBase method, Map<String, String> headers, String charset) throws IOException {
        final StringBuilder sb = new StringBuilder();
        HttpClientUtil.HttpClientCallback callback = new HttpClientUtil.HttpClientCallback() {
            public void process(HttpResponse response) throws IOException {
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                sb.append((String)responseHandler.handleResponse(response));
            }
        };
        internalExecute(method, headers, charset, callback);
        Logger.getLogger("httpclientlog").debug("Http execute: " + method.getMethod() + " " + method.getURI() + "\nresponse: " + sb.toString());
        return sb.toString();
    }

    private static void internalExecute(HttpRequestBase method, Map<String, String> headers, String charset, HttpClientUtil.HttpClientCallback callback) throws IOException {
        DefaultHttpClient client = null;

        try {
            client = new DefaultHttpClient();
            setClientParams(charset, method, headers, client);
            callback.process(client.execute(method));
        } catch (IOException var9) {
            Logger.getLogger("httpclientlog").error("Http execute: " + method.getMethod() + " " + method.getURI(), var9);
            throw var9;
        } finally {
            if (client != null) {
                client.getConnectionManager().shutdown();
            }

        }

    }

    public static void get(String url, String charset, HttpClientUtil.HttpClientCallback callback) throws IOException {
        HttpGet method = new HttpGet(url);
        DefaultHttpClient client = null;

        try {
            client = new DefaultHttpClient();
            setClientParams(charset, method, (Map)null, client);
            Logger.getLogger("httpclientlog").debug("Http execute: " + method.getMethod() + " " + method.getURI());
            callback.process(client.execute(method));
        } catch (IOException var9) {
            Logger.getLogger("httpclientlog").error("Http execute: " + method.getMethod() + " " + method.getURI(), var9);
            throw var9;
        } finally {
            if (client != null) {
                client.getConnectionManager().shutdown();
            }

        }

    }

    private static void setClientParams(String charset, HttpRequestBase method, Map<String, String> headers, DefaultHttpClient client) {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, charset);
        HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
        params.setParameter("http.socket.timeout", 15000);
        params.setParameter("http.connection.timeout", 15000);
        method.setParams(params);
        client.getParams().setParameter("http.socket.timeout", 15000);
        client.getParams().setParameter("http.connection.timeout", 15000);
        client.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
        if (headers != null) {
            Iterator it = headers.entrySet().iterator();

            while(it.hasNext()) {
                Entry<String, String> e = (Entry)it.next();
                method.setHeader((String)e.getKey(), (String)e.getValue());
            }
        }

    }

    public static void main(String[] args) {
        try {
            Map<String, String> headers = new HashMap();
            headers.put("User-Agent", "Nokia5310");
            System.out.println(get("http://218.206.69.209:8080/bo/s/index.do", (Map)headers, (String)"utf-8"));
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
        } catch (ClientProtocolException var3) {
            var3.printStackTrace();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public interface HttpClientCallback {
        void process(HttpResponse var1) throws IOException;
    }
}
