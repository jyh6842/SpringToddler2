package kr.or.ddit.globalexception;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class CustomExceptionResolver extends SimpleMappingExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
										HttpServletResponse response,
										Object handler,
										Exception ex) {
		// 클라이언트 서버 대상 요청시 서버 내 경량화된 쓰레드 생성
		String currentThread = Thread.currentThread().getName();
		
		// 익셉션 발생 메서드와 해당 메서드를 포함하는 클래스명 추출
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		String exceptionOccuredClazz = handlerMethod.getBean().getClass().getName();
		String exceptionOccuredMethod = handlerMethod.getMethod().getName();
		// 익셉션이 발생한 몇번째 줄인지 코드 넘버
		int lineNumber = ex.getStackTrace()[0].getLineNumber();//최초로 발생한 에러 0번째 인덱스
		String exceptionType = ex.getClass().getName();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY/MM/DD kk:mm:ss");
		// 익셉션 로그 찍는거?
		System.out.println("에러 발생 : " + currentThread +
							" | 클래스 : " + exceptionOccuredClazz +
							" | 메서드 : " + exceptionOccuredMethod +
							" | 라인 : " + lineNumber +
							" | 익셉션 타입 : " + exceptionType +
							" | 발생 시간 : " + dateFormat.format(new Date())
							);
		
		return super.resolveException(request, response, handler, ex);
	}

}
