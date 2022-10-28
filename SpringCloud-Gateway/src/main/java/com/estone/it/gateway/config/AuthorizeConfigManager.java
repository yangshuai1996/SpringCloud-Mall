package com.estone.it.gateway.config;

import com.alibaba.fastjson.JSONObject;
import com.estone.it.gateway.constants.ApiResultCode;
import lombok.extern.slf4j.Slf4j;
import com.estone.it.gateway.common.AjaxResult;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collection;

/**
 * API请求权限校验配置类
 *
 * @Author: pilsy
 * @Date: 2020/7/1 0001 18:27
 */
@Slf4j
@Component
public class AuthorizeConfigManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication,
                                             AuthorizationContext authorizationContext) {
        return authentication.map(auth -> {
            ServerWebExchange exchange = authorizationContext.getExchange();
            ServerHttpRequest request = exchange.getRequest();

            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                String authorityAuthority = authority.getAuthority();
                String path = request.getURI().getPath();
                if (antPathMatcher.match(authorityAuthority, path)) {
                    log.info(String.format("用户请求API校验通过，GrantedAuthority:{%s}  Path:{%s} ", authorityAuthority, path));
                    return new AuthorizationDecision(true);
                }
            }
            return new AuthorizationDecision(false);
        }).defaultIfEmpty(new AuthorizationDecision(false));
    }

    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return check(authentication, object)
                .filter(d -> d.isGranted())
                .switchIfEmpty(Mono.defer(() -> {
                    AjaxResult<String> ajaxResult = AjaxResult.restResult("当前用户没有访问权限! ", ApiResultCode.FAILED);
                    String body = JSONObject.toJSONString(ajaxResult);
                    return Mono.error(new AccessDeniedException(body));
                }))
                .flatMap(d -> Mono.empty());
    }
}