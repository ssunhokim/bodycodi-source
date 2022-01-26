package com.bodycodi.test.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bodycodi.test.dto.TokenDto;
import com.bodycodi.test.dto.UserDto;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("Authorization");

        if (request.getAttribute("user") == null) {
            request.setAttribute("user", new UserDto());        
        }

        return true;
    }


}
