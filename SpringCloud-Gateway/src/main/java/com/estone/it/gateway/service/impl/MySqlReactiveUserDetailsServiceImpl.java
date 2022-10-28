package com.estone.it.gateway.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.estone.it.gateway.repository.AccountInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 身份认证类
 *
 * @Author: pilsy
 * @Date: 2020/6/29 0029 18:01
 */
@Slf4j
@Service
public class MySqlReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {

    private static final String USER_NOT_EXISTS = "用户不存在！";

    private final AccountInfoRepository accountInfoRepository;

    public MySqlReactiveUserDetailsServiceImpl(AccountInfoRepository accountInfoRepository) {
        this.accountInfoRepository = accountInfoRepository;
    }

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return accountInfoRepository.findByUsername(username)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException(USER_NOT_EXISTS))))
                .doOnNext(u -> log.info(
                        String.format("查询账号成功  user:%s password:%s", u.getUsername(), u.getPassword())))
                .cast(UserDetails.class);
    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        return accountInfoRepository.findByUsername(user.getUsername())
                .switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException(USER_NOT_EXISTS))))
                .map(foundedUser -> {
                    foundedUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
                    return foundedUser;
                })
                .flatMap(updatedUser -> accountInfoRepository.save(updatedUser))
                .cast(UserDetails.class);
    }
}