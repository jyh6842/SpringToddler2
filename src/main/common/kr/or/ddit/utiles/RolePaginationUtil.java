package kr.or.ddit.utiles;

import javax.servlet.http.HttpServletRequest;

public class RolePaginationUtil {
	private int currentPage;		// 현재 페이지
	private int totalCount;			// 전체 게시글 갯수
	private int totalPage;			// 전체 페이지 갯수
	private int blockCount = 10; 	// 페이지별 출력될 게시글 갯수
	private int blockPage = 5;		// 페이지네이션 메뉴 갯수
	private int startPage;			// 페이지네이션 메뉴 시작 페이지 번호
	private int endPage;			// 페이지네이션 메뉴 끝 페이지 번호
	private int startCount;			// 해당 페이지 내 게시글 시작번호
	private int endCount;			// 해당 페이지 내 게시글 끝번호
	private HttpServletRequest request;
	private StringBuffer pagingHtmls; // String 을 사용 하면 변화가 일어날때 마다 계속해서 새로운 객체를 만든다.
	
	public void RolePaginationUtil(HttpServletRequest request, int currentPage, int totalCount)	{
		this.request = request;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		
		pagingHtmls = new StringBuffer();
		
		makePagination();
	}

	private void makePagination() {
		// 전체 페이지 갯수
		this.totalPage = (int)Math.ceil(this.totalCount / (double) this.blockCount);
		if(this.totalPage == 0){
			this.totalPage = 1;
		}
		
		// 해당 페이지 내 게시글 시작 번호, 끝 번호
		this.startCount = this.totalCount - (this.currentPage - 1) * this.blockCount;
		this.endCount = this.startCount - this.blockCount + 1;
		if(this.endCount < 0){
			this.endCount = 1;
		}
		// 페이지별 페이지네이션 메뉴 갯수 시작 페이지 번호, 끝 페이지 번호
		// 이전|1|2|3|4|5|다음
		// 이전|6|7|8|9|10|다음
		this.startPage = ((this.currentPage - 1) /this.blockPage * this.blockPage) +1;
		this.endPage = this.startPage + this.blockPage - 1;
		if(this.endPage > this.totalPage){
			this.endPage = this.totalPage;
		}
		
		this.pagingHtmls.append("<div class='text-center'>");
		this.pagingHtmls.append("<ul class='pagination mtm mbm'>");
		
		String requestURI = request.getRequestURI();
		
		// 이전|1|2|3|4|5|다음
		// 이전
		if((this.currentPage - 1) == 0){
			this.pagingHtmls.append("<li class='disabled'><a href='#'>&laquo;</a></li>"); // &laquo; 왼쪽 꺽쇠
			
		}else{
			this.pagingHtmls.append("<li><a href='"+requestURI+"?currentPage="+(this.currentPage - 1)+"'>&laquo;</a></li>"); // &laquo; 왼쪽 꺽쇠
		}
		// 1|2|3|4|5
		for (int i = this.startPage; i <= this.endPage; i++) {
			if(this.currentPage == i ){ // 현재 페이지
				this.pagingHtmls.append("<li class='active'><a href='#'>"+ i +"</a></li>");
			}else{ // 현재 페이지가 아닌 애들
				this.pagingHtmls.append("<li><a href='"+requestURI+"?currentPage="+ i +"'>"+ i +"</a></li>"); // &laquo; 왼쪽 꺽쇠
			}
		}
		
		// 다음 
		if(this.currentPage < this.totalPage){ // 다음 페이지가 눌리지 않는 경우와 눌리는 경우
			this.pagingHtmls.append("<li><a href='"+requestURI+"?currentPage="+(this.currentPage + 1)+"'>&raquo;</a></li>"); // &raquo; 오른쪽 꺽쇠
		}else{ // 못쓰게
			this.pagingHtmls.append("<li class='disabled'><a href='#'>&raquo;</a></li>"); // &laquo; 오른쪽 꺽쇠
		}
		
		this.pagingHtmls.append("</ul>");
		this.pagingHtmls.append("</div>");
	}

	public int getStartCount() {
		return startCount;
	}

	public int getEndCount() {
		return endCount;
	}

	public String getPagingHtmls() { // 이거 반환 할때 StringBuffer 이면 나중에 String 타입으로 반환해서 써야함
		return pagingHtmls.toString(); // .toString() 붙여줘서 String 으로 바로 쓸수 있게함
	}
	
	
	
	
	
}
