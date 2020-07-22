package kr.or.ddit.member.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.member.dao.IMemberDao;
import kr.or.ddit.vo.MemberVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// 설정파일 : <bean name="iMemberServiceImpl"
//			class="kr.or.ddit.member.service.IMemberServiceImpl"
//			c:dao-ref="iMemberDaoImpl"/>
// 스프링이기 때문에 이미 싱글톤이기 때문에 싱글톤 관련된 것 필요 없음
@Service
public class IMemberServiceImpl implements IMemberService{
	@Autowired
	private IMemberDao dao;
	
//	private IMemberServiceImpl(IMemberDao dao) throws Exception {
//		this.dao = dao;
//	}
	// 왜 필요 없음? c:dao-ref="iMemberDaoImpl" 이거 되서? @Autowired?


	@Transactional(propagation=Propagation.REQUIRED, readOnly=true) // 트랜잭션 매니저가 일할 필요 없다. 그냥 읽는 거라고 표시 했기 때문에
	@Override
	public MemberVO memberInfo(Map<String, String> parms) throws Exception {
		return dao.memberInfo(parms);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=true) // 트랜잭션 매니저가 일할 필요 없다. 그냥 읽는 거라고 표시 했기 때문에
	@Override
	public List<MemberVO> memberList(Map<String, String> params) throws Exception {
			return dao.memberList(params);
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) // rollbackFor={Exception.class} 어떤 익셉션이 발생하면 롤백 해주세요.
	@Override
	public void deleteMemberInfo(Map<String, String> params) throws Exception {
			dao.deleteMemberInfo(params);
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) // rollbackFor={Exception.class} 어떤 익셉션이 발생하면 롤백 해주세요.
	@Override
	public void updateMemberInfo(MemberVO memberInfo) throws Exception {
			dao.updateMemberInfo(memberInfo);
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) // rollbackFor={Exception.class} 어떤 익셉션이 발생하면 롤백 해주세요.
	@Override
	public void insertMember(MemberVO memberInfo) throws Exception {
			dao.insertMember(memberInfo);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=true) // 트랜잭션 매니저가 일할 필요 없다. 그냥 읽는 거라고 표시 했기 때문에
	@Override
	public String totalCount(Map<String, String> params) throws Exception {
			return dao.totalCount(params);
	}
}
