package com.development.tymaterialmanagement.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.development.tymaterialmanagement.entity.Record;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author develpment
 * @since 2024-04-15
 */
public interface IRecordService extends IService<Record> {
    IPage pageCC(IPage<Record> page, Wrapper wrapper);

}
