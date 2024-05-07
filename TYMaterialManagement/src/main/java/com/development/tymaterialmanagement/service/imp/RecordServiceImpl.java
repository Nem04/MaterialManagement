package com.development.tymaterialmanagement.service.imp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.development.tymaterialmanagement.entity.Goods;
import com.development.tymaterialmanagement.entity.Record;
import com.development.tymaterialmanagement.mapper.GoodsMapper;
import com.development.tymaterialmanagement.mapper.RecordMapper;
import com.development.tymaterialmanagement.service.IRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author develpment
 * @since 2024-04-15
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {
    @Resource
    private RecordMapper recordMapper;

    @Override
    public IPage pageCC(IPage<Record> page, Wrapper wrapper) {
        return recordMapper.pageCC(page,wrapper);
    }
}
