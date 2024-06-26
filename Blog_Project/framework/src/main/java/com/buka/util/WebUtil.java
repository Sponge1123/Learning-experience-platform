package com.buka.util;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Excle工具类
 */
public class WebUtil {
    public static void setDownLoadHeader(String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fname = URLEncoder.encode(filename,"utf-8");
        response.setHeader("Content-disposition","attachment;filename="+fname);
    }
}
