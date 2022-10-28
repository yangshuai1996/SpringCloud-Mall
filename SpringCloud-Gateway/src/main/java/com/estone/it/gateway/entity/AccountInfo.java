package com.estone.it.gateway.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("tb_accountinfo")
public class AccountInfo {
    private String username;

    private String password;
}
