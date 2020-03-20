package com.henu.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henu.auth.config.RsaKeyProperties;
import com.henu.auth.domain.SysUser;
import com.henu.common.domain.Payload;
import com.henu.common.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class JwtVerifyFilter extends BasicAuthenticationFilter {
    private RsaKeyProperties prop;

    public JwtVerifyFilter(AuthenticationManager authenticationManager, RsaKeyProperties prop) {
        super(authenticationManager);
        this.prop = prop;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            try {
                //用户未登录
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                PrintWriter writer = response.getWriter();
                Map requestMap = new HashMap();
                requestMap.put("code", HttpServletResponse.SC_FORBIDDEN);
                requestMap.put("msg", "请登录");
                writer.write(new ObjectMapper().writeValueAsString(requestMap));
                writer.flush();
                writer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        }

        try {
            //用户登陆了验证token
            String token = header.replace("Bearer", "");
            //验证token
            Payload<SysUser> payload = JwtUtils.getInfoFromToken(token, prop.getPublicKey(), SysUser.class);
            SysUser user = payload.getUserInfo();
            UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authResult);
        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();
            return;
        }
        chain.doFilter(request, response);
    }
}
