package kr.or.ddit.freeboard.contriller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.or.ddit.fileitem.service.IFileItemService;
import kr.or.ddit.freeboard.service.IFreeBoardService;
import kr.or.ddit.utiles.CryptoGenerator;
import kr.or.ddit.utiles.RolePaginationUtil;
import kr.or.ddit.vo.FileItemVO;
import kr.or.ddit.vo.FreeboardVO;
import kr.or.ddit.vo.MemberVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/freeboard/")
public class FreeboardController {
	@Autowired
	private CryptoGenerator cryptoGen;
	@Autowired
	private IFreeBoardService freeboardService;
	@Autowired
	private IFileItemService fileitemService;

	// localhost/SpringToddler/user/freeboard/freeboardList.do
	@RequestMapping("freeboardList")
	public ModelAndView freeboardList(Map<String, String> params,
			ModelAndView andView, HttpSession session, String search_keyword,
			String search_keycode, String currentPage,
			HttpServletRequest request,
			RolePaginationUtil pagination,
			@RequestHeader("User-Agent") String agent,
			@RequestHeader("Accept-Language") String language,
			@CookieValue("JSESSIONID") String SessionID 
			)
			throws Exception {

		if (currentPage == null) {
			currentPage = "1";
		}
		Map<String, String> publicKeyMap = this.cryptoGen
				.genneratePairKey(session);

		params.put("search_keyword", search_keyword);
		params.put("search_keycode", search_keycode);

		String totalCount = this.freeboardService.totalCount(params);

		pagination.RolePaginationUtil(request, Integer.parseInt(currentPage),
				Integer.parseInt(totalCount));

		params.put("startCount", String.valueOf(pagination.getStartCount()));
		params.put("endCount", String.valueOf(pagination.getEndCount()));

		List<FreeboardVO> freeboardList = this.freeboardService.freeboardList(params);
		// ModelAndView andView = new ModelAndView();
		andView.addObject("freeboardList", freeboardList);
		andView.setViewName("user/freeboard/freeboardList");
		andView.addObject("publicKeyMap", publicKeyMap);
		andView.addObject("pagination", pagination.getPagingHtmls());

		return andView;

	}

	@RequestMapping("freeboardView")
	@ModelAttribute("memberInfo")
	public FreeboardVO freeboardView(ModelAndView andView, String bo_no,
			String rnum, Map<String, String> params, FreeboardVO freeboardInfo)
			throws Exception {
		params.put("bo_no", bo_no);
		params.put("rnum", rnum);
		
		this.freeboardService.updateHit(params);
		
		freeboardInfo = this.freeboardService.freeboardInfo(params);

//		andView.addObject("freeboardInfo", freeboardInfo);

		return freeboardInfo;
	}

//	@RequestMapping("insertfreeboard")
//	public String insertfreeboard(FreeboardVO freeboardInfo,
//			Map<String, String> params, String chk) throws Exception {
//
//		chk = this.service.insertFreeboard(freeboardInfo);
//		String message = null;
//		if (chk == null) {
//			message = URLEncoder.encode("등록실패", "utf-8");
//		}
//		if (chk != null) {
//			message = URLEncoder.encode("등록성공", "utf-8");
//		}
//
//		return "redirect:/user/freeboard/freeboardList.do?message=" + message;
//	}

	@RequestMapping("deletefreeboard")
	public String deletefreeboard(String bo_no, Map<String, String> params)
			throws Exception {

		params.put("bo_no", bo_no);
		this.freeboardService.deleteFreeboard(params);

		return "redirect:/user/freeboard/freeboardList.do";
	}

	@RequestMapping("updatefreeboard")
	public String updatefreeboard(FreeboardVO freeboardInfo,
			Map<String, String> params) throws Exception {

		this.freeboardService.updateFreeboard(freeboardInfo);
		return "redirect:/user/freeboard/freeboardList.do";
	}

	@RequestMapping("freeboardForm")
	public void freeboardForm() {

	}

	@RequestMapping("freeboardReplyForm")
	public void freeboardReplyForm() {

	}

	/*
	 * var parentInfo =
	 * '&bo_group=${freeboardInfo.bo_group}&bo_seq=${freeboardInfo.bo_seq}&bo_depth=${freeboardInfo.bo_depth}';
	 */

	@RequestMapping("insertfreeboardreply")
	public String insertfreeboardreply(Map<String, String> params,
			FreeboardVO freeboardInfo) throws Exception {

		this.freeboardService.insertFreeboardReply(freeboardInfo);

		return "redirect:/user/freeboard/freeboardList.do";
	}
	
	@RequestMapping("insertFreeboardInfo")
	public String insertFreeboard(FreeboardVO freeboardInfo
									,@RequestParam("files") MultipartFile[] files// 이러한 name으로 들어오게 하고 이 이름이 아니면 에러
									) throws Exception{
		
		this.freeboardService.insertFreeboard(freeboardInfo, files);
		
		return "redirect:/user/freeboard/freeboardList.do";
	}
	
	@RequestMapping("fileDownload")
	public ModelAndView fileDownload(String file_seq
									,Map<String,String> params
									,ModelAndView andView){
		params.put("file_seq", file_seq);
		
		FileItemVO fileitemInfo = this.fileitemService.fileitemInfo(params);
				
		andView.addObject("fileitemInfo", fileitemInfo);
		andView.setViewName("fileDownloadView"); // FileDownloadViewClazz의 component와 이름이 같다.
		
		return andView;
		
		
	}
}
