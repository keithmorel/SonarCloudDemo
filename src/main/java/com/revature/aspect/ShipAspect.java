package com.revature.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.revature.model.Ship;
import com.revature.template.MessageTemplate;

@Aspect
@Component
public class ShipAspect {

	@Pointcut("execution(public * com.revature.controller.ShipController.getShipById(..))")
	public void getShipByIdMethodInShipController() {};
	
	@Before("getShipByIdMethodInShipController()")
	public void adviceBeforeGetShipById(JoinPoint jp) {
		
		String method = jp.getSignature().toLongString();
		System.out.println("Executing @Before advice on method: " + method);
	}
	
	@AfterReturning(pointcut="execution(public * com.revature.controller.ShipController.getShipById(..))", returning="result")
	public void afterReturningGetShipById(JoinPoint jp, Object result) {
		
		Object[] args = jp.getArgs();
		for (Object o: args) {
			System.out.println(o);
		}
		
		if (result instanceof ResponseEntity) {
			Object obj = ((ResponseEntity) result).getBody();
			if (obj instanceof Ship) {
				System.out.println(obj);
			} else if (obj instanceof MessageTemplate) {
				System.out.println("MessageTemplate: " + ((MessageTemplate) obj).getMessage());
			}
		}
		 
	}

}
