package kr.or.ddit.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.stereotype.Component;

@Component("logPrintClazz")
public class LogPrintAspectClazz {
	// 로그 출력되는 모든 빈 클래스(빈)들에서 뺏었다.
	// 로그 출력 시점
	//	1.시작할때 2.익셉션에 들어갈때 3.정상 종료 될때
	
	@Loggable
	public static Logger logger;
	
	// 메서드(joinpoint), 메서드들(pointcut) 대상 호출전(advice) 주입될 공통 코드(aspect)의 실행 (weaving)
	public void theWholeJoinpointCallBefore(JoinPoint joinPoint){
		// 해당 메서드(joinpoint)가 포함된 빈 클래스 정보
		String beanClazzName = joinPoint.getTarget().getClass().getName();
		String beanMethodName = joinPoint.getSignature().getName();
		
		logger.debug("target Bean class : {} | joinpoint : {}"
							, beanClazzName, beanMethodName);
	}
	
	// 메서드(joinpoint), 메서드들(pointcut) 대상 종료 후(advice) 주입될 공통 코드(aspect)의 실행 (weaving)
	public void theWholeJoinpointCallAfter(JoinPoint joinPoint){
		// 해당 메서드(joinpoint)가 포함된 빈 클래스 정보
		String beanClazzName = joinPoint.getTarget().getClass().getName();
		String beanMethodName = joinPoint.getSignature().getName();
		
		logger.debug("target Bean class : {} | joinpoint : {}가 종료 되었습니다."
				, beanClazzName, beanMethodName);
	}
	
	// 메서드(joinpoint), 메서드들(pointcut) 대상 호출 및 익셉션 발생 후(advice) 주입될 공통 코드(aspect)의 실행 (weaving)
	public void theWholeJoinpointCallThrowing(JoinPoint joinPoint, Exception ex){ /*발생된 익셉션도 주입*/
		// 해당 메서드(joinpoint)가 포함된 빈 클래스 정보
		String beanClazzName = joinPoint.getTarget().getClass().getName();
		String beanMethodName = joinPoint.getSignature().getName();
		
		logger.debug("target Bean class : {} | joinpoint : {}가 호출 후 {} 익셉션이 발생되었습니다."
				, beanClazzName, beanMethodName, ex.getMessage());
	}
	
	// 메서드(joinpoint), 메서드들(pointcut) 대상 호출 전과 종료 후(advice) 주입될 공통 코드(aspect)의 실행 (weaving)
		public Object theWholeJoinpointCallAround(ProceedingJoinPoint joinPoint) throws Throwable{ /*발생된 익셉션도 주입*/
			// 해당 메서드(joinpoint)가 포함된 빈 클래스 정보
			String beanClazzName = joinPoint.getTarget().getClass().getName();
			String beanMethodName = joinPoint.getSignature().getName();
			
			logger.debug("target Bean class : {} | joinpoint : 가 호출되고 ........."
					, beanClazzName, beanMethodName);
			
			// 상단 코드 : 해당 메서드(joinpoint) 호출 전 실행
			// 경계
			Object methodReturnValue = joinPoint.proceed();
			// 하단 코드 : 해당 메서드(joinpoint) 종료 후 실행
			
			logger.debug("target Bean class : {} | joinpoint : 가 종료됩니다."
					, beanClazzName, beanMethodName);
			
			
			return methodReturnValue; // joinPoint.proceed(); 사용하면 반드시 return 필요
		}
}
