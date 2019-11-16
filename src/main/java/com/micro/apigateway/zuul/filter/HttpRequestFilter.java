package com.micro.apigateway.zuul.filter;

import com.micro.apigateway.zuul.helper.RequestContextHelper;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class HttpRequestFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		RequestContext context = RequestContextHelper.get();
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "Route";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}
