package com.momodog.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author DengWei
 * @date 2020/1/2 15:03
 */
public class FirstFilter extends ZuulFilter {

    //返回字符串，代表过滤器的类型
    @Override
    public String filterType() {
        //设置这个过滤器为前置过滤器
        return "pre";
    }
    //通过返回的int值来定义过滤器的执行顺序，数字越小优先级越高
    @Override
    public int filterOrder() {
        //顺序设置为1
        return 1;
    }
    //判断该过滤器是否需要执行
    @Override
    public boolean shouldFilter() {
        //返回true 代表过滤器生效
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //过去Zuul提供的请求上下文对象
        RequestContext ctx = RequestContext.getCurrentContext();
        //从上下文中获取request对象
        HttpServletRequest request = ctx.getRequest();
        ctx.getResponse().setCharacterEncoding("UTF-8");
        ctx.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
        //从请求中获取数据
        String id = request.getParameter("id");
        //判断
        if(StringUtils.isEmpty(id)){
            //没有参数，拦截
            ctx.setSendZuulResponse(false);
            //返回401状态码，可以考虑重定向到登录页
            ctx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            ctx.setResponseBody("参数为空，zuul拦截");
            return null;
        }
        if (id.equals("100")){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            ctx.setResponseBody("参数为100，zuul拦截");

            return null;
        }
        //校验通过，可以考虑把用户信息放入上下文，继续向后执行

        return null;
    }
}
