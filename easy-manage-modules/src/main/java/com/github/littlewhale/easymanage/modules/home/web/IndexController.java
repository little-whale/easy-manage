package com.github.littlewhale.easymanage.modules.home.web;

import com.github.littlewhale.easymanage.modules.commom.response.Result;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 首页请求信息
 *
 * @author cjp
 * @date 2019/1/11
 */
@RestController
public class IndexController {

    @RequestMapping(value = {"/","/index"})
    public Result index(){
        Map<String,String> projectMap = Maps.newHashMap();
        projectMap.put("author","cjp1016,xiaoming9696");
        projectMap.put("url","https://github.com/little-whale/easy-manage");
        return Result.ok(projectMap);
    }
}
