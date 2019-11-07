package com.micro.apigateway.zuul.filter;

import java.nio.charset.Charset;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 用户登录校验Filter
 */
@Component
public class AuthorizedRequestFilter extends ZuulFilter{
	//    private String pre_filterType = PRE_TYPE;    // 前置过滤器(url之前执行)
//  private String post_filterType = POST_TYPE;  // 后置过滤器
//  private String error_filterType = ERROR_TYPE;// 异常过滤器
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

      // System.out.println(request.getRequestURI());
      // System.out.println(request.getRequestURL());
//拦截指定url, 进行参数校验
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
      System.out.println("拦截逻辑");
      RequestContext requestContext =  RequestContext.getCurrentContext();
      HttpServletRequest  request = requestContext.getRequest();

      //token对象
      String id = request.getHeader("id");

      if(StringUtils.isBlank((id))){
          id  = request.getParameter("id");
      }

      //如果id参数为null就程序停止,  同时返回  HttpStatus.UNAUTHORIZED  状态码
      if (StringUtils.isBlank(id)) {
          requestContext.setSendZuulResponse(false);
          requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
      }
      
      String auth = "studyjava:hello"; // 认证的原始信息
      byte[] encodedAuth = Base64.getEncoder()
              .encode(auth.getBytes(Charset.forName("US-ASCII"))); // 进行一个加密的处理
      // 在进行授权的头信息内容配置的时候加密的信息一定要与“Basic”之间有一个空格
      String authHeader = "Basic " + new String(encodedAuth);
      requestContext.addZuulRequestHeader("Authorization", authHeader);
      return null;
  }
}
