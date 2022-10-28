package com.estone.it.order.feign;

import com.estone.it.common.dto.AccountDTO;
import com.estone.it.common.response.ObjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @创建人 yangshuai
 * @创建时间 2022/10/28
 * @描述
 */
@FeignClient(name = "springcloud-account")
public interface AccountFeignClient {

    @PostMapping("/account/dec_account")
    ObjectResponse decreaseAccount(AccountDTO accountDTO);
}
