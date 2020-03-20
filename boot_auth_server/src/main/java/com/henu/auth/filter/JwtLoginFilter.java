package com.henu.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henu.auth.config.RsaKeyProperties;
import com.henu.auth.domain.SysRole;
import com.henu.auth.domain.SysUser;
import com.henu.common.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private RsaKeyProperties prop;

    public JwtLoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties prop) {
        this.authenticationManager = authenticationManager;
        this.prop = prop;
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            SysUser sysUser = new ObjectMapper().readValue(request.getInputStream(), SysUser.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword());
            return authenticationManager.authenticate(authRequest);
        } catch (Exception e) {
            try {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter writer = response.getWriter();
                Map requestMap = new HashMap();
                requestMap.put("code", HttpServletResponse.SC_UNAUTHORIZED);
                requestMap.put("msg", "用户名或密码错误");
                writer.write(new ObjectMapper().writeValueAsString(requestMap));
                writer.flush();
                writer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SysUser user = new SysUser();
        user.setUsername(authResult.getName());
        user.setRoles((List<SysRole>) authResult.getAuthorities());
        String token = JwtUtils.generateTokenExpireInMinutes(user, prop.getPrivateKey(), 24 * 60);
        response.addHeader("Authorization", "Bearer " + token);
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter writer = response.getWriter();
            Map requestMap = new HashMap();
            requestMap.put("code", HttpServletResponse.SC_OK);
            requestMap.put("msg", "认证通过");
            writer.write(new ObjectMapper().writeValueAsString(requestMap));
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
