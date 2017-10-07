package com.dayuanit.dymall.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class URIFilter implements Filter {

    private static final List<String> uriList = new ArrayList<String>();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        InputStream is = null;
        is = URIFilter.class.getClassLoader().getResourceAsStream("whiteURI.properties");
        Properties pro = new Properties();
        try {
            pro.load(is);
            String uris = pro.getProperty("white_uri");
            System.out.println(uris);
            String[] uriArr = uris.split(",");
            for(String uri : uriArr) {
                uriList.add(uri);
            }

        } catch (IOException e) {
            if(null == is ) {
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
       /* if(! uriList.contains(uri)){
            User loginUser = (User)req.getSession(false).getAttribute("loginUser");
            if(null == loginUser) {
                req.getRequestDispatcher("/user/toLogin.do").forward(req,resp);
                filterChain.doFilter(req,resp);
            }
            filterChain.doFilter(req,resp);
        }
*/
        filterChain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }

}
