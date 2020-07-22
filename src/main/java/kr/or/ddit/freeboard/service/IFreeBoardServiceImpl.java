package kr.or.ddit.freeboard.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.fileitem.dao.IFileItemDao;
import kr.or.ddit.freeboard.dao.IFreeBoardDao;
import kr.or.ddit.utiles.AttachFileMapper;
import kr.or.ddit.vo.FileItemVO;
import kr.or.ddit.vo.FreeboardVO;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

// 설정파일  : <bean name="iFreeboardServiceImpl" = > @Service("freeboardService") 이거 넣으면 bean name="freeboardService" 이렇게 바뀜
//			class="kr.or.ddit.freeboard.service.IFreeBoardServiceImpl"
@Service("freeboardService")
public class IFreeBoardServiceImpl implements IFreeBoardService {

	@Autowired
	private IFreeBoardDao dao;
	@Autowired
	private IFileItemDao fileitemDao;

	@Transactional(propagation=Propagation.REQUIRED, readOnly=true) // 트랜잭션 매니저가 일할 필요 없다. 그냥 읽는 거라고 표시 했기 때문에
	@Override
	public List<FreeboardVO> freeboardList(Map<String, String> params)
			throws Exception {

		return dao.freeboardList(params);

	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) // rollbackFor={Exception.class} 어떤 익셉션이 발생하면 롤백 해주세요.
	@Override
	public String insertFreeboard(FreeboardVO freeboardInfo, MultipartFile[] items)
			throws Exception {
		String bo_no = dao.insertFreeboard(freeboardInfo);
		List<FileItemVO> fileItemList = AttachFileMapper.mapper(items, bo_no);

		fileitemDao.insertFileItem(fileItemList);

		return bo_no;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) // rollbackFor={Exception.class} 어떤 익셉션이 발생하면 롤백 해주세요.
	@Override
	public String insertFreeboardReply(FreeboardVO freeboardInfo)
			throws Exception {
		return dao.insertFreeboardReply(freeboardInfo);

	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=true) // 트랜잭션 매니저가 일할 필요 없다. 그냥 읽는 거라고 표시 했기 때문에
	@Override
	public FreeboardVO freeboardInfo(Map<String, String> params)
			throws Exception {
		FreeboardVO freeboardInfo = null;
		freeboardInfo = dao.freeboardInfo(params);
		return freeboardInfo;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true) // 트랜잭션 매니저가 일할 필요 없다. 그냥 읽는 거라고 표시 했기 때문에
	@Override
	public FreeboardVO freeboardInfo2(Map<String, String> params)
			throws Exception {
		FreeboardVO freeboardInfo = null;
		freeboardInfo = dao.freeboardInfo2(params);
		return freeboardInfo;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) // rollbackFor={Exception.class} 어떤 익셉션이 발생하면 롤백 해주세요.
	@Override
	public void deleteFreeboard(Map<String, String> params) throws Exception {
		dao.deleteFreeboard(params);
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) // rollbackFor={Exception.class} 어떤 익셉션이 발생하면 롤백 해주세요.
	@Override
	public void updateFreeboard(FreeboardVO freeboardInfo) throws Exception {
		dao.updateFreeboard(freeboardInfo);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=true) // 트랜잭션 매니저가 일할 필요 없다. 그냥 읽는 거라고 표시 했기 때문에
	@Override
	public String totalCount(Map<String, String> params) throws Exception {
		return dao.totalCount(params);
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) // rollbackFor={Exception.class} 어떤 익셉션이 발생하면 롤백 해주세요.
	@Override
	public void updateHit(Map<String, String> params) throws Exception {
		dao.updateHit(params);
	}


}
