package com.development.tymaterialmanagement.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.development.tymaterialmanagement.common.QueryPageParam;
import com.development.tymaterialmanagement.common.Result;
import com.development.tymaterialmanagement.entity.Menu;
import com.development.tymaterialmanagement.entity.User;
import com.development.tymaterialmanagement.service.IMenuService;
import com.development.tymaterialmanagement.service.IUserService;
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
 * @since 2024-03-27
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IMenuService menuService;
    @GetMapping("/list")
    public List<User> list(){return  userService.list();}
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String account){
        List list = userService.lambdaQuery().eq(User::getAccount,account).list();
        return list.size()>0?Result.success(list):Result.fail();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        return userService.save(user)?Result.success():Result.fail();
    }
    //更新
    @PostMapping("/update1")
    public Result update1(@RequestBody User user){
        return userService.updateById(user)?Result.success():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return userService.removeById(id)?Result.success():Result.fail();
    }
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List list =userService.lambdaQuery()
                .eq(User::getAccount,user.getAccount())
                .eq(User::getPassword,user.getPassword()).list();

        if (list.size()>0){
            User user1 =(User)list.get(0);
            List menuList = menuService.lambdaQuery().like(Menu::getMenuright,user1.getRole()).list();
            HashMap res = new HashMap();
            res.put("user",user1);
            res.put("menu",menuList);
            return Result.success(res);
        }
        return Result.fail();
    }
    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody User user){
        return userService.updateById(user);
    }
    //新增或修改
    @PostMapping("/saveOrUpdate")
    public boolean SaveOrUpdate(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }
    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id)
    {
        return  userService.removeById(id);
    }
    //查询(模糊like、匹配eq)
    @PostMapping("/select")
    public Result select(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(user.getName())){
            lambdaQueryWrapper.like(User::getName,user.getName());
        }
        return Result.success(userService.list(lambdaQueryWrapper));
    }
    //分页处理
    @PostMapping("/page")
    public List<User> page(@RequestBody QueryPageParam query){
        //System.out.println(query);
        //System.out.println("num==="+query.getPageNum());
        //System.out.println("size==="+query.getPageSize());

        HashMap param = query.getParam();
        String name =(String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));
//        System.out.println("no==="+(String)param.get("no"));


        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);

        IPage result = userService.page(page,lambdaQueryWrapper);
        System.out.println("total=="+result.getTotal());
        return result.getRecords();
    }
    //自定义分页处理
    @PostMapping("/pageC")
    public Result pageC(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name =(String)param.get("name");
        String role =(String)param.get("role");
        //System.out.println("name==="+(String)param.get("name"));

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name)){
            lambdaQueryWrapper.like(User::getName,name);
        }
        if (StringUtils.isNotBlank(role)){
            lambdaQueryWrapper.eq(User::getRole,role);
        }
        //IPage result = userService.pageC(page); //一个参数
        IPage result = userService.pageCC(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }
}
