package com.development.tymaterialmanagement.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.development.tymaterialmanagement.common.QueryPageParam;
import com.development.tymaterialmanagement.common.Result;
import com.development.tymaterialmanagement.entity.Goods;
import com.development.tymaterialmanagement.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author develpment
 * @since 2024-04-09
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods){
        return goodsService.save(goods)?Result.success():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Goods goods){return goodsService.updateById(goods)?Result.success():Result.fail();}
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return goodsService.removeById(id)?Result.success():Result.fail();
    }
    @PostMapping("/page")
    public Result page(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name =(String)param.get("name");
        String goodstype = (String)param.get("goodstype");
        String storage = (String)param.get("storage");

        Page<Goods> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name)){
            lambdaQueryWrapper.like(Goods::getName,name);
        }
        if (StringUtils.isNotBlank(goodstype)){
            lambdaQueryWrapper.eq(Goods::getGoodstype,goodstype);
        }
        if (StringUtils.isNotBlank(storage)){
            lambdaQueryWrapper.eq(Goods::getStorage, storage);
        }
        IPage result = goodsService.pageCC(page,lambdaQueryWrapper);

        return Result.success(result.getRecords(), result.getTotal());
    }

}
