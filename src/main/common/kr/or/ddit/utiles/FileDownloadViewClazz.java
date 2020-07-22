package kr.or.ddit.utiles;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import kr.or.ddit.global.GlobalConstant;
import kr.or.ddit.vo.FileItemVO;





// 파일 다운로드 처리 jsp 내
@Component("fileDownloadView")
public class FileDownloadViewClazz extends AbstractView { // 이걸 주면 클래스가 될 준비가 된 것이다.
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, // model에 fileiteminfo가 전달 되었다.
											HttpServletRequest request,
											HttpServletResponse response) throws Exception {
//		컨트롤러 클래스 - ModelAndView.addObject("fileitemInfo", fileitemInfo)이 Map으로 주입
		FileItemVO fileitemInfo = (FileItemVO) model.get("fileitemInfo");
		
File downloadFile = new File(GlobalConstant.FILE_PATH, fileitemInfo.getFile_save_name()); // 여기에서 스트리밍이 일어난다.
		
		if(downloadFile.exists()){
			String realName = URLEncoder.encode(fileitemInfo.getFile_name(), "UTF-8");
			
			response.setHeader("Content-Disposition", "attachmemt;fileName= "+realName); //downloadFileName는 유저가 진짜로 업로드한 파일의 이름이어야한다. 그래서 파라미터 하나를 더 받도록 realName을 받는다.
			response.setContentType("application/octet-stream");
			response.setContentLength((int)downloadFile.length());
			
			byte[] buffer = new byte[(int)downloadFile.length()];
			
			BufferedInputStream inputStream = new BufferedInputStream( // 읽어온 것을 버퍼에 담겠다.
															new FileInputStream( // 빨대 꽂아서 가져오겠다.
																	downloadFile)); // 가져올 것
			BufferedOutputStream outputStream = new BufferedOutputStream(
															response.getOutputStream()); // response에 초기화한 getOutputStream() 가져오면 된다.
			int readCnt = 0;
			while((readCnt = inputStream.read(buffer)) != -1){
				outputStream.write(buffer);
			}
			
			inputStream.close();
			outputStream.close();
						
			
		}
		
	}
	
	


}
