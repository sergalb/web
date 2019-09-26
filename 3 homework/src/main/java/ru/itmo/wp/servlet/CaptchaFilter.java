package ru.itmo.wp.servlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class CaptchaFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        String value = request.getParameter("number");

        if (request.getMethod().equalsIgnoreCase("GET") && value == null) {
            String htmlCode = getHtmlCode(setCaptcha(session));
            response.setContentType("text/html");
            response.getWriter().println(htmlCode);
            response.getWriter().flush();
        } else {
            chain.doFilter(request, response);
        }
    }
}