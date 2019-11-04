package com.micro.apigateway.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 拦截指定url, 进行参数校验
 */
@Component
public class UrlFilter extends ZuulFilter{
	//    private String pre_filterType = PRE_TYPE;    // 前置过滤器(url之前执行)
//  private String post_filterType = POST_TYPE;  // 后置过滤器
//  private String error_filterType = ERROR_TYPE;// 异常过滤器
  /**
   * 过滤器类型
   * @return
   */
  @Override
  public String filterType() {
      return "Pre";
  }
  /**
   * 过滤器顺序(多个过滤器时, 越小的越先执行)
   * @return
   */
  @Override
  public int filterOrder() {

      return 3;
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

      if ("/apigateway/order/api/v1/order/find".equalsIgnoreCase(request.getRequestURI())){
          return true;
      }
      return false;
  }
  /**
   * 业务逻辑
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
      return null;
  }
}