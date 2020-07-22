package kr.or.ddit.ibatis.factory;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlMapClientFactory {
	private static SqlMapClient client;
	
	static{ // static 블록을 하는 이유는?
		// utf-8  읽기
		Charset charSet = Charset.forName("UTF-8");
		Resources.setCharset(charSet);
		
		try {
			Reader reader = Resources.getResourceAsReader("kr/or/ddit/ibatis/config/SqlMapConfig.xml"); // 어디에 읽는지
			// Controller
			//		Service instance
			//			DAO instance
			//				SqlMapClient instance
			//					SqlMapConfig.xml
			//					Mapper(member.xml)
			client = SqlMapClientBuilder.buildSqlMapClient(reader); // 여기서는 언제?
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SqlMapClient getSqlMapClient(){
		return client;
	}
}
