package com.development.tymaterialmanagement.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.development.tymaterialmanagement.common.QueryPageParam;
import com.development.tymaterialmanagement.common.Result;
import com.development.tymaterialmanagement.entity.Storage;
import com.development.tymaterialmanagement.entity.User;
import com.development.tymaterialmanagement.service.IStorageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author develpment
 * @since 2024-04-08
 */
@RestController
@RequestMapping("/storage")
public class StorageController {
    @Autowired
    private IStorageService storageService;
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage){
        return storageService.save(storage)?Result.success():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Storage storage){return storageService.updateById(storage)?Result.success():Result.fail();}
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return storageService.removeById(id)?Result.success():Result.fail();
    }
    @PostMapping("/page")
    public Result page(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name =(String)param.get("name");

        Page<Storage> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name)){
            lambdaQueryWrapper.like(User::getName,name);
        }
        IPage result = storageService.pageCC(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }
    @GetMapping("/list")
    public Result list(){
        List list = storageService.list();
        return Result.success(list);
    }
}
