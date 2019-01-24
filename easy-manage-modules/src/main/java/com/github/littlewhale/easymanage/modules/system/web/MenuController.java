package com.github.littlewhale.easymanage.modules.system.web;

import com.github.littlewhale.easymanage.modules.commom.response.Result;
import com.github.littlewhale.easymanage.modules.system.service.IMenuService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjp
 * @since 2019-01-14
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController {

    @Resource
    private IMenuService iMenuService;

    /** 新增 */
    @PostMapping("/add")
    public Result add() {
        return Result.ok();
    }

    /** 删除 */
    @PostMapping("/delete")
    public Result delete() {
        return Result.ok();
    }

    /** 更新 */
    @PostMapping("/update")
    public Result update() {
        return Result.ok();
    }

    /** 查询单个 */
    @PostMapping("/detail")
    public Result detail(@RequestParam Long pkid) {
        return Result.ok();
    }

    /** 分页查询 */
    @PostMapping("/list")
    public Result list() {
        return Result.ok();
    }



}
