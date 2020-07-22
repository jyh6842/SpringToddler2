package kr.or.ddit.member.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.MemberVO;
public interface IMemberService {

	
	public MemberVO memberInfo(Map<String, String> parms) throws Exception;
	
	public List<MemberVO> memberList(Map<String, String> params) throws Exception;
	
	public void deleteMemberInfo(Map<String, String> params) throws Exception;
	
	public void updateMemberInfo(MemberVO memberInfo) throws Exception;
	
	public void insertMember(MemberVO memberInfo) throws Exception;
	
	public String totalCount(Map<String, String> params) throws Exception;
}
