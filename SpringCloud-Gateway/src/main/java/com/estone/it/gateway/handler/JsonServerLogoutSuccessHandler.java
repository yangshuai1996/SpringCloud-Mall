package com.estone.it.gateway.handler;

import com.alibaba.fastjson.JSONObject;
import com.estone.it.gateway.common.AjaxResult;
import com.estone.it.gateway.constants.ApiResultCode;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;


import io.netty.util.CharsetUtil;
import reactor.core.publisher.Mono;

/**
 * @Author: pilsy
 * @Date: 2020/7/10 0010 15:05
 */
@Component
public class JsonServerLogoutSuccessHandler implements ServerLogoutSuccessHandler {
    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        ServerHttpResponse response = exchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        String result = JSONObject.toJSONString(AjaxResult.restResult("注销成功", ApiResultCode.SUCCESS));
        DataBuffer buffer = response.bufferFactory().wrap(result.getBytes(CharsetUtil.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}