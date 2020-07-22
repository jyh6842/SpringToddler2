package kr.or.ddit.utiles;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 클라이언트 요청(인터셉터 선언 순서대로 동작)
//		1. preHandle() 콜백(default return true)
//		2. 컨트롤러 클래스 내 클라이언트의 해당 요청을 처리하는 컨트롤러 메서드 콜백
//		3. postHandle() 콜백
//		4. afterCompletion() 콜백
//		5. 응답 컨텐츠가 클라이언트 대상 전송
// 클라이언트 비동기식(ajax) 요청(인터셉터 선언 순서대로 동작)
//		1. preHandle() 콜백(default return true)
//		2. 컨트롤러 클래스 내 클라이언트의 해당 요청을 처리하는 컨트롤러 메서드 콜백
//		3. 응답 컨텐츠가 클라이언트 대상 전송
public class EncodingInterceptor extends HandlerInterceptorAdapter{

	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response,
								Object handler,
								Exception ex)
			throws Exception {
		// 응답이 도달하지 직전에 콜백 된다.
		System.out.println("postHandle() 종료 후 컨트롤러 메서드의 반환값이 ViewResolver에 전달로 "
							+ "응답 컨텐츠가 HttpSerlvetResponse의 출력 버퍼에 저장된 직후 콜백");

	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
												HttpServletResponse response,
												Object handler) throws Exception {
		// 아작스 쓸때만 콜백 된다.
		System.out.println("비동기식 통신(ajax) 요청시 postHandle() 콜백 메서드와 afterCompletion() 콜백 메서드가 무시되고 콜백");
		// afterCompletion 이거 호출 안되고 afterConcurrentHandlingStarted 이거 호출 됨
	}

	@Override
	public void postHandle(HttpServletRequest request,
							HttpServletResponse response,
							Object handler,
							ModelAndView modelAndView) throws Exception {
		System.out.println("컨트롤러 클래스 내 클라이언트의 해당 요청을 처리하는 컨트롤러 메서드 종료 후 바로 콜백");
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
							HttpServletResponse response,
							Object handler) throws Exception {
		
		System.out.println("컨트롤러 클래스 내 클라이언트의 해당 요청을 처리하는 컨트롤러 메서드 호출 전 콜백");
		
		String encodingType = request.getCharacterEncoding(); // 특정 인코딩을 취득하는 메서드

		if(StringUtils.isEmpty(encodingType)) { // 인코딩 타입이 null 이고 "" 일 때
			encodingType = "UTF-8";
		}

		request.setCharacterEncoding(encodingType);
		
		//return false : 컨트롤러 메서드 호출이 무시됨(안됨)
			// 직접 만들어서 리턴 해야함
		// return true : 컨트롤러 메서드 호출(default)
		return true;
	}

	

//	@Override
//	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
//			FilterChain chain) throws IOException, ServletException {
//		// 클라이언트 전송하는 쿼리스트링 전송 방식 :
//		//			GET		: server.xml <- connector URIEncoding="UTF-8" 
//		//			POST 	: jsp 내 스크립트릿 영역 request.setCharacterEncoding("UTF-8");
//		// 클라이언트의 쿼리스트링 전송시 특정 인코딩 처리 요구를 요청 헤더에 포함 시킬 수 있다.
//		//			자바스크립트 코드로 가능 : Accept_Charset : UTF-8
//
//		String encodingType = servletRequest.getCharacterEncoding();
//		
//		// http://commons.apache.org
//		//		commons-lang3-3.1.jar
//		// java.lang.String 클래스의 확장 API 라이브러리
//		//	if(encodingType == null && encodingType == "") 이 조건을 확장 라이브러리를 사용하면
//		//		StringUtils.isNotEmpty(encodingType) 이렇게 짧아진다.
//		
//		if (!StringUtils.isNotEmpty(encodingType)) { // 비어 있다면
//			encodingType = "UTF-8";
//		}
//		
//		servletRequest.setCharacterEncoding(encodingType);
//		
//		chain.doFilter(servletRequest, servletResponse);
//		// 여기까지 하면 jsp에 선언된 request.setCharacterEncoding("UTF-8"); 이 선언이 필요 없어진다.
//		
//	}



}
