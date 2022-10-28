package com.estone.it.oauth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.estone.it.oauth.entity.Users;
import com.estone.it.oauth.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @创建人 yangshuai
 * @创建时间 2022/10/26
 * @描述
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<Users> lambdaQueryWrapper = new LambdaQueryWrapper();
        Users users = usersMapper.selectOne(lambdaQueryWrapper.eq(Users::getUsername,username));
        if(Objects.isNull(users)){
            throw new RuntimeException("当前用户不存在");
        }
        return User.builder().username(username).password(users.getPassword()).authorities("ADMIN","USER").build();
    }
}
