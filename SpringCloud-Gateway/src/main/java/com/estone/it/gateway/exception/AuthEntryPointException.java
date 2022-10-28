package com.estone.it.gateway.exception;

import com.alibaba.fastjson.JSONObject;
import io.netty.util.CharsetUtil;
import com.estone.it.gateway.common.AjaxResult;
import com.estone.it.gateway.constants.ApiResultCode;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 无访问权限的返回结果
 *
 * @author pilsy
 */
@Component
public class AuthEntryPointException implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        AjaxResult<String> ajaxResult = AjaxResult.restResult(e.getMessage(), ApiResultCode.FAILED);
        String body = JSONObject.toJSONString(ajaxResult);
        DataBuffer wrap = exchange.getResponse().bufferFactory().wrap(body.getBytes(CharsetUtil.UTF_8));
        return exchange.getResponse().writeWith(Flux.just(wrap));
    }
}