package com.fvthree.micromcashusers.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;

@Aspect
@Component
public class RestControllerAspect {

	private final Logger logger = LoggerFactory.getLogger("RestControllerAspect");
	
	Counter userCreatedCounter = Metrics.counter("com.fvthree.micromcashusers.user.created");
	
	@Before("execution(public * com.fvthree.micromcashusers.api.controller.*Controller.*(..))")
	public void generalAllMethodASpect() {
		logger.info("All Method Calls invoke this general aspect method");
	}
	
	@AfterReturning("execution(public * com.fvthree.micromcashusers.api.controller.*Controller.register(..))")
	public void getsCalledOnProductSave() {
		logger.info("This aspect is fired when the save method of the controller is called");
		userCreatedCounter.increment();
	}
}
