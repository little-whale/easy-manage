# Spring-Boot整合说明

## SpringBoot 自定义异常和4xx，5xx返回

1. [springboot继承AbstractErrorController实现全局的异常处理](
https://blog.csdn.net/qq_29684305/article/details/82286469)
2. [解决springboot配置@ControllerAdvice不能捕获NoHandlerFoundException问题](https://my.oschina.net/u/3049656/blog/1798583)
3. [Spring Boot自定义错误页面](https://www.jianshu.com/p/393f70b55b1b)

`WebMvcAutoConfiguration`,`ErrorMvcAutoConfiguration`,SpringBoot会默认资源映射。
`DefaultHandlerExceptionResolver`此时处理的异常是非业务异常：包括参数类型不匹配，参数绑定失败，参数格式校验失败， 容器运行时异常(包括outOfMemory，数组越界等)；
对方法resolveException 捕获到的 Exception e 异常进行 类型判断； 针对不同的类型，返回对应的信息：

网上说采用`@ControllerAdvice`拦截器拦截NoHandlerFoundException。

```yaml
spring: 
  mvc:
    throw-exception-if-no-handler-found: true
    static-path-pattern: /statics/**
  resources:
    add-mappings: false
```


下面列出简单代码实现类
```java
import com.github.littlewhale.easymanage.modules.commom.response.Result;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 4xx,5xx 错误简单处理逻辑
 * <p>
 * ErrorMvcAutoConfiguration、
 * DefaultErrorAttributes、
 * BasicErrorController(被替换)、
 * DefaultErrorViewResolver
 * </p>
 *
 * @author cjp
 * @date 2019/1/7
 */
@Controller
@RequestMapping("/error")
public class SimpleErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping
    @ResponseBody
    public Result error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        return Result.instance(status.value(), status.getReasonPhrase(), null);
    }

    /**
     * 获取status
     *
     * @param request
     * @return
     */
    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
```

## SpringBoot Redis整合
[SpringBoot整合Lettuce Redis](http://www.spring4all.com/article/1152)

## SpringBoot Shiro JWT 整合

1. [SpringBoot系列 - 集成JWT实现接口权限认证](http://ju.outofmemory.cn/entry/341269)

RESTful API认证方式
一般来讲，对于RESTful API都会有认证(Authentication)和授权(Authorization)过程，保证API的安全性。

Authentication vs. Authorization

Authentication指的是确定这个用户的身份，Authorization是确定该用户拥有什么操作权限。

认证方式一般有三种

Basic Authentication

这种方式是直接将用户名和密码放到Header中，使用 Authorization: Basic Zm9vOmJhcg== ，使用最简单但是最不安全。

TOKEN认证

这种方式也是再HTTP头中，使用 Authorization: Bearer <token> ，使用最广泛的TOKEN是JWT，通过签名过的TOKEN。

OAuth2.0

这种方式安全等级最高，但是也是最复杂的。如果不是大型API平台或者需要给第三方APP使用的，没必要整这么复杂。

### 什么是jwt
Json web token (JWT), 是为了在网络应用环境间传递声明而执行的一种基于JSON的开放标准（(RFC 7519).该token被设计为紧凑且安全的，特别适用于分布式站点的单点登录（SSO）场景。JWT的声明一般被用来在身份提供者和服务提供者间传递被认证的用户身份信息，以便于从资源服务器获取资源，也可以增加一些额外的其它业务逻辑所必须的声明信息，该token也可直接被用于认证，也可被加密。

JWT官网： https://jwt.io/

JWT是由三段信息构成的，将这三段信息文本用.链接一起就构成了Jwt字符串。就像这样:
第一部分我们称它为头部（header),第二部分我们称其为载荷（payload, 类似于飞机上承载的物品)，第三部分是签证（signature)

#### header（头部）
```
jwt的头部承载两部分信息,然后将头部进行base64加密（该加密是可以对称解密的),构成了第一部分
                     
- 声明类型，这里是jwt
- 声明加密的算法 通常直接使用 HMAC SHA256
这里的加密算法是单向函数散列算法，常见的有MD5、SHA、HAMC。这里使用基于密钥的Hash算法HMAC生成散列值。

MD5 message-digest algorithm 5 （信息-摘要算法）缩写，广泛用于加密和解密技术，常用于文件校验。校验？不管文件多大，经过MD5后都能生成唯一的MD5值
SHA (Secure Hash Algorithm，安全散列算法），数字签名等密码学应用中重要的工具，安全性高于MD5
HMAC (Hash Message Authentication Code，散列消息鉴别码，基于密钥的Hash算法的认证协议。用公开函数和密钥产生一个固定长度的值作为认证标识，用这个标识鉴别消息的完整性。常用于接口签名验证
```
#### playload（载荷）
            
```
载荷就是存放有效信息的地方，这些有效信息包含三个部分：

标准中注册的声明
公共的声明(公共的声明可以添加任何的信息，一般添加用户的相关信息或其他业务需要的必要信息.但不建议添加敏感信息，因为该部分在客户端可解密)
私有的声明(私有声明是提供者和消费者所共同定义的声明，一般不建议存放敏感信息，因为base64是对称解密的，意味着该部分信息可以归类为明文信息。)
标准中注册的声明 (建议但不强制使用) ：
iss: jwt签发者
sub: jwt所面向的用户
aud: 接收jwt的一方
exp: jwt的过期时间，这个过期时间必须要大于签发时间
nbf: 定义在什么时间之前，该jwt都是不可用的.
iat: jwt的签发时间
jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。

然后将其进行base64加密，得到Jwt的第二部分：
```

#### signature（签名）
jwt的第三部分是一个签证信息，这个签证信息由三部分组成：
```
header (base64后的)
payload (base64后的)
secret

这个部分需要base64加密后的header和base64加密后的payload使用.连接组成的字符串，然后通过header中声明的加密方式进行加盐secret组合加密，然后就构成了jwt的第三部分。

var encodedString = base64UrlEncode(header) + '.' + base64UrlEncode(payload);
var signature = HMACSHA256(encodedString, 'secret');
```
注意：
secret是保存在服务器端的，jwt的签发生成也是在服务器端的，secret就是用来进行jwt的签发和jwt的验证，所以，它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。

#### 如何使用
一般是在请求头里加入Authorization，并加上Bearer标注：
```js
fetch('api/user/1', {
  headers: {
    'Authorization': 'Bearer ' + token
  }
})
```
服务器负责解析这个HTTP头来做用户认证和授权处理。大致流程如下：

![图片](https://xnstatic-1253397658.file.myqcloud.com/jwt20.png)
