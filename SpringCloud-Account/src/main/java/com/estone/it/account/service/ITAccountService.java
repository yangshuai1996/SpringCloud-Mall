package com.estone.it.account.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.estone.it.account.entity.TAccount;
import com.estone.it.common.dto.AccountDTO;
import com.estone.it.common.response.ObjectResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * * @author lidong
 *
 * @since 2019-09-04
 */
public interface ITAccountService extends IService<TAccount> {

    /**
     * 扣用户钱
     */
    ObjectResponse decreaseAccount(AccountDTO accountDTO);
}
