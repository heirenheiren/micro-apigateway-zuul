package com.micro.apigateway.zuul.filter;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
/**
 * 登录成功后把登录信息塞到请求头里面
 * @author Polin
 *
 */
public class HttpResponseFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		HystrixRequestContext context = HystrixRequestContext.getContextForCurrentThread();
        if (HystrixRequestContext.isCurrentThreadInitialized()) {
        	//不清除会有内存溢出风险
            context.shutdown();
        }
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "Post";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 2;
	}

}
