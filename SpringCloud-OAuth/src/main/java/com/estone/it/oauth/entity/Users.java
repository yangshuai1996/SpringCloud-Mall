package com.estone.it.oauth.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @创建人 yangshuai
 * @创建时间 2022/10/26
 * @描述
 */

@Data
@TableName("users")
public class Users {
    @TableId
    private Long id;
    private String username;
    private String password;
}
