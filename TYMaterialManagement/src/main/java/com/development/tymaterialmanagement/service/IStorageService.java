package com.development.tymaterialmanagement.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.development.tymaterialmanagement.entity.Storage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author develpment
 * @since 2024-04-08
 */
public interface IStorageService extends IService<Storage> {
    IPage pageCC(IPage<Storage> page, Wrapper wrapper);


}
