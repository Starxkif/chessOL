package com.hmmnx.chessweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmmnx.chessweb.pojo.User;
import com.hmmnx.chessweb.service.UserService;
import com.hmmnx.chessweb.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-10-25 15:02:12
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




