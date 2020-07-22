package kr.or.ddit.utiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.global.GlobalConstant;
import kr.or.ddit.vo.FileItemVO;

public class AttachFileMapper {
	public static List<FileItemVO> mapper(MultipartFile[] items, String bo_no){
//		public static List<FileItemVO> mapper(FileItem[] items, String bo_no){
		
		List<FileItemVO> fileItemList = new ArrayList<FileItemVO>();
		
		if(items != null){
			FileItemVO fileItemInfo = null;
			for (MultipartFile item : items) {
//				for (FileItem item : items) {
				if(item.getSize() > 0){
						
					
					fileItemInfo = new FileItemVO();
					fileItemInfo.setFile_bo_no(bo_no);
					
					// 파일명 취득
					// 브라우저별 d:\\temp\image\a.png
					//		or a.png
	//				String fileName = FilenameUtils.getName(item.getName()); // 바뀐 부분
					String fileName = FilenameUtils.getName(item.getOriginalFilename());
					fileItemInfo.setFile_name(fileName);
					
					// 파일 실제 저장소 : D:\\temp\\files
					// 저장용 파일명을 별도로 작성 -> 똑같은 이름은 덮어 쓰기가 되기 때문에
					// a.png => a
					String baseName = FilenameUtils.getBaseName(fileName);
					// a.png => .png
					String extension = FilenameUtils.getExtension(fileName);
					//  |---------유니크한 랜덤값---|
					// a342891745839321749832174.png
					// UUID(Univerally Unique Identifier) : 128bit 유일 값 생성('-'값이 포함) 마이너스 값이 포함되어 있다.
					String genID = UUID.randomUUID().toString().replace("-", ""); // - 삭제함
					String saveFileName = baseName + genID + "." + extension;
					// 저장할 곳
					fileItemInfo.setFile_save_name(saveFileName);
					
					fileItemInfo.setFile_content_type(item.getContentType());
					fileItemInfo.setFile_size(String.valueOf(item.getSize())); // String.valueOf(item.getSize()) 하는 이유는?
					
					fileItemList.add(fileItemInfo); // 추가
					
					saveFile(saveFileName, item); //실제 저장소에 덮어쓰기가 안되도록 할려고 한거니까
				}// if
				
			}
		}
		return fileItemList;
	}

	private static void saveFile(String saveFileName, MultipartFile item) {
//		private static void saveFile(String saveFileName, FileItem item) {
		File saveFile = new File(GlobalConstant.FILE_PATH, saveFileName);
		
		try {
			item.transferTo(saveFile);
//			item.write(saveFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
