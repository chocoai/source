package com.jeesite.modules.k3.report.filter.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @desc 修改HttpServletRequest参数
 * @author AlbertFeng
 * @date 2018-11-23 13:58
 */
public class FilteredRequest extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public FilteredRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public StringBuffer getRequestURL() {
        StringBuffer requestURL = super.getRequestURL();
        int startIndex = requestURL.lastIndexOf("e");
        String requestSubUrl = requestURL.substring (0,startIndex);
        requestURL = new StringBuffer(requestSubUrl + "listData");
        if (requestURL != null && !requestURL.toString().isEmpty()){
            return requestURL;
        }
        return super.getRequestURL();
    }
}
