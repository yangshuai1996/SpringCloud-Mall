package com.estone.it.gateway.repository;

import com.estone.it.gateway.entity.AccountInfo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AccountInfoRepository extends ReactiveCrudRepository<AccountInfo, Long> {

    Mono<AccountInfo> findByUsername(String username);
}
