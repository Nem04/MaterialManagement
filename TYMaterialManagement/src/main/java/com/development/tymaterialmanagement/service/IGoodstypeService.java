package com.development.tymaterialmanagement.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.development.tymaterialmanagement.entity.Goodstype;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author develpment
 * @since 2024-04-08
 */
public interface IGoodstypeService extends IService<Goodstype> {
    IPage pageCC(IPage<Goodstype> page, Wrapper wrapper);


}
