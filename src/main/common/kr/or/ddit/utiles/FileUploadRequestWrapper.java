package kr.or.ddit.utiles;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import sun.security.pkcs.ParsingException;

// FileUploadRequestWrapper extends HttpServletRequestWrapper
//		HttpServletRequestWrapper extends HttpServletRequest
public class FileUploadRequestWrapper extends HttpServletRequestWrapper{
	
	private boolean multipartFlag = false; // 일반 요청일 때랑 파일 업로드 요청일때 구분?
	// 폼필드 ?mem_id=a001&mem_id=b001 일수 있으니까 String[] 로 넣는다.
	private Map<String, String[]> parameterMap;
	// 파일 정보만 저장하는 Map
	private Map<String, FileItem[]> fileitemMap;
	

	public FileUploadRequestWrapper(HttpServletRequest request) { // 생성자
		this(request, -1, -1); // 누구한테 전달? HttpServletRequestWrapper 에서 받는다. multipart 인지 아닌지 // 생성자를 부른 것이다.
		
	}
	public FileUploadRequestWrapper(HttpServletRequest request, 
									int threshold, int sizeMax) {
		super(request); // 누구한테 전달? HttpServletRequestWrapper 에서 받는다. multipart 인지 아닌지
		
		parsing(request, threshold, sizeMax);
		
		
	}
	private void parsing(HttpServletRequest request, int threshold, int sizeMax) {
		System.out.println("================================================");
		System.out.println(threshold);
		System.out.println(sizeMax);
		System.out.println("================================================");
//		threshold 임시 저장소의 사이즈를 제한하기 위해서 안 받으면 -1
		
		this.multipartFlag = ServletFileUpload.isMultipartContent(request); // 요청이 하나라도 존재하면 true
		
		if(this.multipartFlag){ // 이게 트루이면 ?
			this.parameterMap = new HashMap<String, String[]>();
			this.fileitemMap = new HashMap<String, FileItem[]>();
			
			// 임시 저장소 만들기
			DiskFileItemFactory factory = new DiskFileItemFactory(); // 1024
			// 임시 저장소 대상 저장되는 파일의 사이즈 설정 : -1(무제한) 은 무제한을 의미
			factory.setSizeThreshold(threshold); // 무제한으로 설정
			
			ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
			// 실제 저장소 대상 저장되는 파일의 사이즈 설정 : -1(무제한) 은 무제한을 의미
			servletFileUpload.setSizeMax(sizeMax);
			
			try {
				List<FileItem> items = servletFileUpload.parseRequest(request); // list로 넘어옴
				for(FileItem item : items){
					if(item.getSize() > 0){ // 이걸 하는 이유는? 파일을 업로드 하든 안하든 넘어오는데 -1 로 넘어온다.
						//  mem_id, mem_pass, mem_name, files
						String fieldName = item.getFieldName();
						if(item.isFormField()){
							// 폼필드
							String value = item.getString("UTF-8"); // 한글 넘어오는거 깨질까봐
							// bo_title에 담긴 value 값이 넘어온다. 게시판 제목이 넘어옴
							
							// ?mem_id = a001&mem_id=b001 ....
							String[] values = this.parameterMap.get(fieldName); // 1개 이상일수도 있다.
							
							if(values == null){ // 저장된게 없습니다.
								values = new String[]{ value };
							}else{
//								String[] 배열이 자동적으로 늘어나지 않기 때문에 스와핑 한다.
								String[] tmp = new String[values.length + 1];
								
								// 배열 복사
								// 		스왈로우 카피(얕은 복사) : 원본 배열의 각각의 인덱스에서 레퍼런스하는 개별 요소값의 주소가 복사 대상 배열과 동일.
								//		딥 카피 : 원본 배열의 각가그이 레퍼런스하는 요소 
								//			새로 만들어 버리는 것이다. 원본이 바뀌더라도 복사되었더라도 값이 바뀌지 않는다. 왜냐하면 주소가 다릑 때문에
								//	1. 원본배열
								//	2. 원본배열의 복사 시작 인덱스
								//	3. 복사 대상 배열
								//	4. 복사 대상 배열의 복사 시작 인덱스
								//	5. 원본 배열에서 복사가 될 전체 인덱스 사이즈가 된다.
//								System.arraycopy(src, srcPos, dest, destPos, length);
								System.arraycopy(values, 0, tmp, 0, values.length); // 딥카피 쓰는 것임
								
								tmp[tmp.length - 1] = value; // 길이의 마지막 값이 인덱스의 마지막이다.
								values = tmp;
								
								
							}
								this.parameterMap.put(fieldName, values);
						}else{
							// 파일
							// ?files = a.png&files=b.gif
							FileItem[] values = this.fileitemMap.get(fieldName);
							//fileItemMap에 저장되지 않은 녀석도 있을 것이다.
							if(values == null){
								values = new FileItem[]{item};
							}else{
								FileItem[] tmp = new FileItem[values.length + 1];
								System.arraycopy(values, 0, tmp, 0, values.length);
//								시작 인덱스, 대상벼열, 대상배열 위치, 어디까지 복사
								
								tmp[tmp.length - 1] = item; 
								values = tmp;
							}
							this.fileitemMap.put(fieldName, values);
						}
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
			
		}
	}
	@Override
	public String getParameter(String name) {
		if(this.multipartFlag){
			// 스트리밍 요청
			String[] values = this.parameterMap.get(name);
			if(values == null){ // 값이 없을때
				return null;
			}else{ // 값이 있을때
				return values[0];
			}
		}else{
			// 일반 요청
			// super == HttpServletRequest
			return super.getParameter(name);
			
		}
			
	}
	@Override
	public Map<String, String[]> getParameterMap() {
		if (this.multipartFlag) {
			// 스트리밍 요청
			return this.parameterMap;
		} else {
			// 일반 요청
			return super.getParameterMap();
		}
		
	}
	@Override
	public Enumeration<String> getParameterNames() {
		if (this.multipartFlag) {
			return new Enumeration<String>() {
				// 키 가지고 있는 녀석
				Iterator<String> keys =  parameterMap.keySet().iterator();
				@Override
				public boolean hasMoreElements() { // 다음 접근 가능한게 있으면 트루 없으면 펄스
					return keys.hasNext(); // 이터레이터가 다음에 접근할 키가 있으면 넘겨주고 없으면 펄스 리턴
				}

				@Override
				public String nextElement() {
					return keys.next();
				}
				
			};
		} else {
			return super.getParameterNames();
		}
	}
	@Override
	public String[] getParameterValues(String name) {
		if (this.multipartFlag) {
			return this.parameterMap.get(name);
		} else {
			return super.getParameterValues(name);
		}
	}
	
	public FileItem getFileItem(String name){
		if(this.multipartFlag){
			FileItem[] items = this.fileitemMap.get(name);
			if(items == null){
				return null;
			}else{
				return items[0];
			}
		}else {
			return null;
		}
	}
	
	public FileItem[] getFileItemValues(String name){
		if(this.multipartFlag){
			return this.fileitemMap.get(name);
		}else{
			return null;
		}
	}
	
	public Map<String, FileItem[]> getFileItemMap(){
		if (this.multipartFlag) {
			return this.fileitemMap;
		} else {
			return null;
		}
	}
	
	
	
}
