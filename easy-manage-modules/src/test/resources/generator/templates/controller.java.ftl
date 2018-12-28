package ${package.Controller};

import com.github.littlewhale.easymanage.common.response.Result;
import ${package.Service}.${table.serviceName};

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.annotation.Resource;

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Resource
    private ${table.serviceName} ${table.serviceName?uncap_first};

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
</#if>
