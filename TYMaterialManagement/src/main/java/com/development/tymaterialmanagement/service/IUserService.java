package com.development.tymaterialmanagement.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.development.tymaterialmanagement.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author develpment
 * @since 2024-03-27
 */
public interface IUserService extends IService<User> {

    IPage pageC(IPage<User> page);

    IPage pageCC(IPage<User> page, Wrapper wrapper);
}
