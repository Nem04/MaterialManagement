package com.development.tymaterialmanagement.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.development.tymaterialmanagement.entity.Goods;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author develpment
 * @since 2024-04-09
 */
public interface IGoodsService extends IService<Goods> {
    IPage pageCC(IPage<Goods> page, Wrapper wrapper);

}
