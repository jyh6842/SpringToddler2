package kr.or.ddit.freeboard.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.FreeboardVO;

public interface IFreeBoardDao {

	public List<FreeboardVO> freeboardList(Map<String, String> parmas) throws Exception;
	
	public String insertFreeboard(FreeboardVO freeboardInfo) throws Exception;
	
	public String insertFreeboardReply(FreeboardVO freeboardInfo) throws Exception;
	
	public void updateFreeboard(FreeboardVO freeboardInfo) throws Exception;
	
	public FreeboardVO freeboardInfo(Map<String, String> params) throws Exception;
	
	public void deleteFreeboard(Map<String, String> params) throws Exception;
	
	public String totalCount(Map<String, String> params) throws Exception;
	
	public FreeboardVO freeboardInfo2(Map<String, String> params) throws Exception;
	
	public void updateHit(Map<String, String> params) throws Exception;
}
