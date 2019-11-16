package com.micro.apigateway.zuul.helper;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.netflix.zuul.context.RequestContext;

public class RequestContextHelper {
	private final static HystrixRequestVariableDefault<RequestContext> context = new HystrixRequestVariableDefault();

	public static void set(RequestContext currentContext) {
		// TODO Auto-generated method stub
		context.set(currentContext);
	}

	public static RequestContext get() {
		// TODO Auto-generated method stub
		init();
		return context.get();
	}

	private static void init() {
		// TODO Auto-generated method stub
		if (!HystrixRequestContext.isCurrentThreadInitialized()) {
			HystrixRequestContext.initializeContext();
		}
	}

	public static void destroy() {
		if (HystrixRequestContext.isCurrentThreadInitialized()) {
			HystrixRequestContext.getContextForCurrentThread().shutdown();
		}
	}
}
