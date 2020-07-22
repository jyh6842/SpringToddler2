package kr.or.ddit.member.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.MemberVO;

public interface IMemberDao {
	
	public MemberVO memberInfo(Map<String, String> parms) throws Exception;
	// 서비스로 에러를 던짐
	
	public List<MemberVO> memberList(Map<String, String> params) throws Exception;
	
	public void deleteMemberInfo(Map<String, String> params) throws Exception;
	
	public void updateMemberInfo(MemberVO memberInfo) throws Exception;
	
	public void insertMember(MemberVO memberInfo) throws Exception;
	
	public String totalCount(Map<String, String> params) throws Exception;
}
