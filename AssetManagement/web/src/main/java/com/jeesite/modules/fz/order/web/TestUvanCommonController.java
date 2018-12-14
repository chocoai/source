package com.jeesite.modules.fz.order.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author easter
 * @data 2018/12/10 9:28
 */
@RestController
public class TestUvanCommonController {

    @RequestMapping("/uvantest")
    public void test(HttpServletResponse response){
        try {
           String data = "中国优梵艺术";
            //String data = DateUtil.formatDate(new Date());
            //String data = ReturnCode.codeMap.get("15035");
            OutputStream stream = response.getOutputStream();
            response.setContentType("text/html;charset=utf-8");//设置字符流编码，而且还会添加content-Type响应头，这个头通知浏览器用utf-8解码。
            response.setHeader("content-type", "text/html;charset=UTF-8");//通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
            stream.write(data.getBytes("UTF-8"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
