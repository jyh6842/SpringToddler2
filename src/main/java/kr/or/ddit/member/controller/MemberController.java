package kr.or.ddit.member.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.vo.MemberVO;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.org.glassfish.external.statistics.annotations.Reset;

// /SpringToddler/user/member/memberList.do
// /SpringToddler/user/member/memberView.do
// /SpringToddler/user/member/memberForm.do
@Controller
@RequestMapping("/user/member/") // 공통된 주소
public class MemberController {
	@Autowired
	private MessageSourceAccessor accessor;
	@Autowired
	private ObjectMapper mapper; // org.codehaus.jackson.map.ObjectMapper;
	
	@Autowired
	private IMemberService service;
	
	// void 비슷한 Model
	@RequestMapping("memberList")
	public Model memberList(String search_keycode, // 파라미터로 들어가는게 인젝션?
							String search_keyword,
							Map<String, String> params,
							Model model
							) throws Exception{
//		Map<String, String> params = new HashMap<String, String>();
		params.put("search_keycode", search_keycode);
		params.put("search_keyword", search_keyword);
		
		List<MemberVO> memberList = this.service.memberList(params);
		
		// memberList = view resolver => memberList.jsp
//		Model model = new ExtendedModelMap(); 위에서 해주자
		model.addAttribute("memberList", memberList);
		
		return model;
		
	}
	
	@RequestMapping("memberView")
	public ModelMap memberView(String mem_id // ModelMap 는 void와 마찬가지 알아서 return 해서 갈곳을 알아서 model + void = ModelMap
							   ,Map<String, String> params
							   ,ModelMap modelMap
							   ) throws Exception{
		params.put("mem_id", mem_id);
		
		MemberVO memberInfo = this.service.memberInfo(params);
		
//		ModelMap modelMap = new ModelMap();
		
		modelMap.addAttribute("memberInfo", memberInfo);
		
		return modelMap;
	}
	
	@RequestMapping("updateMemberInfo")
	private String updateMember(MemberVO memberInfo // private로 되도 요청은 되지만 public 써야 됨. 왜? 그런 이유가 일단 있다는데
								) throws Exception{
		this.service.updateMemberInfo(memberInfo);
		return "redirect:/user/member/memberList.do";
	}
	
	// /user/member/deleteMemberInfo.do?user_id=a001
	// /user/member/deleteMemberInfo.do?a001.do <- ${memberInfo.mem_id}
//	@RequestMapping("deleteMemberInfo")
	@RequestMapping("deleteMemberInfo/{user_id}")
	public String deleteMember(@PathVariable("user_id") String mem_id // @RequestParam(value="user_id") String mem_id
							   ,Map<String, String> params) throws Exception{
//		public String deleteMember(@RequestParam(required=false, defaultValue="널 대체값" ) String mem_id // @RequestParam(value="user_id") String mem_id
//				,Map<String, String> params){
		params.put("mem_id", mem_id);
		this.service.deleteMemberInfo(params);
		return "redirect:/user/member/memberList.do";
	}
	
	
	@RequestMapping("memberForm")
	public void memberForm(){}
	
	
	@RequestMapping("insertMemberInfo")
	public String insertMember(MemberVO memberInfo
								,@RequestBody String totalParams
								,RedirectAttributes redirectAttributes
			) throws Exception{ // 도메인 오브젝트라고 부른다. membervo memberInfo
		
		System.out.println("@RequestBody" + totalParams);
//		this.service.insertMember(memberInfo);
//		
//		String message = this.accessor.getMessage("cop.regist.msg.confirm", Locale.KOREA);
//		message = URLEncoder.encode(message, "UTF-8");
//		
//		return "redirect:/user/join/loginForm.do?message="+message;
		
		redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다.");
		return "redirect:/user/join/loginForm.do";
		
	}
	
	@RequestMapping("idCheck")
	public ModelAndView idCheck(@RequestParam String mem_id
							,Map<String, String> params
							) throws Exception{
		params.put("mem_id", mem_id);
		
		MemberVO memberInfo = this.service.memberInfo(params);
		
		// Model(view 대상 전송 데이터 저장) + String(view 경로와 이름)
		ModelAndView andView = new ModelAndView();
		andView.addObject("memberInfo", memberInfo);
		
		// <bean id="jsonConvertView" class="..MappingJackson2JsonView"/>
		andView.setViewName("jsonConvetView");
		
		return andView;
		
		
		
		
	}
	
	
}
