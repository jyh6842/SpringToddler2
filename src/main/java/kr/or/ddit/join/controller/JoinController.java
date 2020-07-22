package kr.or.ddit.join.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.utiles.CryptoGenerator;
import kr.or.ddit.vo.MemberVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

// /SpringToddler/user/join/loginForm.do
// /SpringToddler/user/join/loginCheck.do
// /SpringToddler/user/join/loginOut.do

@Controller /*클라이언트 요청을 처리할 수  있는 클래스가 됨*/
@RequestMapping("/user/join/") /*폼 채크 아웃이 공통으로 쓰는거는 이렇게 미리 선언할 수 있음*/
public class JoinController {
//	private Logger loger = LoggerFactory.getLogger(this.getClass()); logback를 사용하지 않으면 각각의 클래스 마다 이걸 선언해 줘야하고
	// 로그를 찍을 시점(메소드) 안에 로그를 찍게 만들어줘야 한다.
	// 정상적으로 시작 되었는지 정상적으로 끝났는지 익셉션 됬는지 일일이 전부 로그를 만들어 줘야한다. AOP를 사용하면 알아서 해준다.
	@Autowired
	private MessageSourceAccessor accessor; // 다국어 지원 모드를 위해서
	
	@Autowired
	private IMemberService service;
	
	@Autowired
	private CryptoGenerator cryptoGenerator;
	
	   @RequestMapping(value="loginCheck", method=RequestMethod.GET, params={"mem_id=a001"})
	   public String loginCheckHeader(String mem_id, String mem_pass, 
	         HttpServletRequest request, 
	         HttpSession session, 
	         HttpServletResponse response)throws Exception{
	         Map<String, String> params = new HashMap<String, String>();
	         params.put("mem_id", mem_id);
	         params.put("mem_pass",mem_pass);
	         
	         MemberVO memberInfo = this.service.memberInfo(params);
	         
	         
	         if(memberInfo == null){
	         //리다이렉트(컨텍스트 루트|패스 생략)
	         String message =  this.accessor.getMessage("fail.common.join",Locale.KOREA);
	         message = URLEncoder.encode(message,"UTF-8");
	         return "redirect:/user/join/loginForm.do?message="+message;
	         }else{
	         session.setAttribute("LOGIN_MEMBERINFO",memberInfo);
	         // 포워드(컨텍스트 루트|패스 생략)
	         return "forward:/user/member/memberList.do";
	         }
	   }
	
	@RequestMapping("logout")
	public String logout(HttpSession session) throws Exception{
		session.invalidate();
		
		String message = this.accessor.getMessage("success.common.logout", Locale.KOREA); // fail.common.join = 회원이 아닙니다.
		message = URLEncoder.encode(message, "UTF-8");
		return "redirect:${contextPage.request.contextPath}/user/join/loginForm.do?message="+message;
	}
}
