package kr.or.ddit.fileitem.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.fileitem.dao.IFileItemDao;
import kr.or.ddit.fileitem.dao.IFileItemDaoImpl;
import kr.or.ddit.vo.FileItemVO;

@Service
public class IFileItemServiceImpl implements IFileItemService{
	
//	@Qualifier 타입이 중복되면 이거 사용
	@Autowired
	private IFileItemDao dao;
	

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) // rollbackFor={Exception.class} 어떤 익셉션이 발생하면 롤백 해주세요. 
	@Override
	public void insertFileItem(List<FileItemVO> fileitemList) {
		try {
			dao.insertFileItem(fileitemList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=true) // 트랜잭션 매니저가 일할 필요 없다. 그냥 읽는 거라고 표시 했기 때문에
	@Override
	public FileItemVO fileitemInfo(Map<String, String> params) {
		FileItemVO fileitemInfo = null;
		try {
			fileitemInfo = dao.fileitemInfo(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileitemInfo;
	}
	
}
