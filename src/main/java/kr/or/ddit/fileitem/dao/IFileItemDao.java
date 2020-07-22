package kr.or.ddit.fileitem.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.FileItemVO;

public interface IFileItemDao {
	
	public void insertFileItem(List<FileItemVO> fileitemList) throws Exception;
	
	public FileItemVO fileitemInfo(Map<String, String> params) throws Exception;
}
