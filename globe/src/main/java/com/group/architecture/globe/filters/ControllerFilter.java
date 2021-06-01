package com.group.architecture.globe.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.architecture.globe.model.response.ContinentResponse;
import com.group.architecture.globe.service.cache.CacheService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class ControllerFilter extends OncePerRequestFilter {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private ObjectMapper mapper;

    private static final String HEADER_ETAG = "ETag";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String URI = httpServletRequest.getRequestURI();
        if(URI.contains("//")) {
            URI = URI.replaceAll("//", "/");
        }

        String requestMethod = httpServletRequest.getMethod();
        String etag = httpServletRequest.getHeader(HEADER_ETAG);
        if (requestMethod.equals(HttpMethod.GET) && URI.contains("continent") && etag != null) {
            try {
                ContinentResponse response = cacheService.getContinentByEtag("",etag);
                httpServletResponse.setHeader(HEADER_ETAG, etag);
                httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().print(mapper.writeValueAsBytes(response));
            } catch (NotFoundException e) {
                log.error("Failed to get continent from cache");
            }
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
        System.out.println("");
    }
}
