package com.estone.it.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.estone.it.account.entity.TAccount;
import com.estone.it.account.service.ITAccountService;
import com.estone.it.common.dto.AccountDTO;
import com.estone.it.common.enums.RspStatusEnum;
import com.estone.it.common.response.ObjectResponse;
import com.estone.it.account.mapper.TAccountMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * * @author lidong
 *
 * @since 2019-09-04
 */
@Service
public class TAccountServiceImpl extends ServiceImpl<TAccountMapper, TAccount> implements ITAccountService {

    @Override
    public ObjectResponse decreaseAccount(AccountDTO accountDTO) {
        int account = baseMapper.decreaseAccount(accountDTO.getUserId(), accountDTO.getAmount().doubleValue());
        ObjectResponse<Object> response = new ObjectResponse<>();
        if (account > 0) {
            response.setStatus(RspStatusEnum.SUCCESS.getCode());
            response.setMessage(RspStatusEnum.SUCCESS.getMessage());
            return response;
        }

        response.setStatus(RspStatusEnum.FAIL.getCode());
        response.setMessage(RspStatusEnum.FAIL.getMessage());
        return response;
    }
}
