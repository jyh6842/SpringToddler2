package kr.or.ddit.first.controller;

import kr.or.ddit.vo.MemberVO;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 클라이언트의 요청을 실제 처리하는 커맨드 컨트롤러 ? DL이 작동 했다?
//@Controller
public class FirstController implements ApplicationContextAware{
	// http://localhost/SpringToddler/first/hello.first
	@RequestMapping("/first/hello.first")
	public String firstMethod(){
		System.out.println("FirstController의 firstMethod() 콜백");
		return "first/hello";
	}

	@Override
	public void setApplicationContext(ApplicationContext webApplicationContext) // webApplicationContext 요걸로 bean 을 주입 받게 해준다.
			throws BeansException {
		// setApplicationContext() 은 컨트롤러 메서드(firstMethod()) 콜백 전 호출
		// 설정 파일 내 해당 빈 등록시 id속성값을 활용한 해당 빈의 인스턴스 취득
		MemberVO memberInfo = (MemberVO) webApplicationContext.getBean("memberInfo"); // id, name(별명) 으로 불러오는거 가능
		MemberVO m1 = (MemberVO) webApplicationContext.getBean("m1");
		MemberVO m2 = (MemberVO) webApplicationContext.getBean("m2");
		MemberVO m3 = webApplicationContext.getBean("m3", MemberVO.class); // MemberVO.class 이 친구가 위의 MemberVO 캐스팅 역할을 해준다.
		System.out.println(System.identityHashCode(memberInfo));
		System.out.println(System.identityHashCode(m1));
		System.out.println(System.identityHashCode(m2));
		System.out.println(System.identityHashCode(m3)); // 4개 모두 같은 주소를 가리킨다. ( 싱글톤 ! )
		System.out.println(m1.getMem_regno1() + " | " + m1.getMem_regno2());
	}
}
