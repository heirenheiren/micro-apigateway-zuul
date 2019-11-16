package com.micro.apigateway.zuul.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.micro.apigateway.zuul.helper.RequestContextHelper;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.java.Log;

/**
 * 用户登录校验Filter
 */
@Log
public class AuthorizedRequestFilter extends ZuulFilter{
  /**
   * 过滤器类型
   * @return
   */
  @Override
  public String filterType() {
	// 在进行Zuul过滤的时候可以设置其过滤执行的位置，那么此时有如下几种类型：
      // 1、pre：在请求发出之前执行过滤，如果要进行访问，肯定在请求前设置头信息
      // 2、route：在进行路由请求的时候被调用；
      // 3、post：在路由之后发送请求信息的时候被调用；
      // 4、error：出现错误之后进行调用
      return "Pre";
  }
  /**
   * 过滤器顺序(多个过滤器时, 越小的越先执行)
   * 设置优先级，数字越大优先级越低
   * @return
   */
  @Override
  public int filterOrder() {
      return 0;
  }
  /**
   * 过滤器是否生效
   *  为false时就不会走run()里面的业务逻辑
   * @return
   */
  @Override
  public boolean shouldFilter() {
      RequestContext requestContext = RequestContext.getCurrentContext();
      HttpServletRequest  request = requestContext.getRequest();

      log.info(request.getMethod());
      log.info(request.getRequestURI());
      log.info(request.getRequestURL().toString());
//拦截指定url, 进行参数校验,对不符合格式的URL或者参数进行拦截
//      if ("/v1/micro-service/study/getStudy".equalsIgnoreCase(request.getRequestURI())){
//          return true;
//      }
      return true;
  }
  /**
   * 业务逻辑:拿到用户名和密码请求数据库判断是否存在
   * @return
   * @throws ZuulException
   */
  @Override
  public Object run() throws ZuulException {
	  HystrixRequestContext.initializeContext();
      RequestContextHelper.set(RequestContext.getCurrentContext());
      
      System.out.println("拦截逻辑");
      RequestContext requestContext =  RequestContext.getCurrentContext();
      HttpServletRequest  request = requestContext.getRequest();

      //token对象
      String token = request.getHeader("token");

      if(StringUtils.isBlank((token))){
    	  token  = request.getParameter("token");
      }

      //如果id参数为null就程序停止,  同时返回  HttpStatus.UNAUTHORIZED  状态码
      if (StringUtils.isBlank(token)) {
          requestContext.setSendZuulResponse(false);
          requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
          requestContext.setResponseBody("网关认证失败，停止路由");
          requestContext.set("code", 0);
          HttpServletResponse response = requestContext.getResponse();
          response.setHeader("content-type", "text/html;charset=utf-8");
      }else {
    	  requestContext.setSendZuulResponse(true);
          requestContext.setResponseStatusCode(200);
          requestContext.set("code", 1);
      }
      return null;
  }
}
