<%@ page language="JAVA" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시글 목록</title>
<script type="text/javascript">
	$(function() {
		if(${!empty param.message} ){
			
			alert( '${param.message}');
		}
		
		$('tbody tr').click(function(){
			var bo_no = $(this).find('td:eq(0) input').val();
			var rnum = $(this).find('td:eq(0)').text();

			$(location).attr('href', '${pageContext.request.contextPath}/user/freeboard/freeboardView.do?bo_no='+bo_no+'&rnum='+rnum);
		});
		$('#btn1').on('click', function() {
			$(location).attr('href','${pageContext.request.contextPath}/user/freeboard/freeboardForm.do')
		})
	})
</script>
</head>
<body>
<div id="freeboardList_content">
	<div class="panel panel-blue">
    	<div class="panel-heading">게시판 목록</div>
		<table class="table table-bordered table-hover">
			<thead>
				<tr>
					<th scope="col" width="5%">No</th>
					<th scope="col" width="65%">제목</th>
					<th scope="col" width="10%">작성자</th>
					<th scope="col" width="10%">작성일</th>
					<th scope="col" width="10%">조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="freeboardInfo" items="${freeboardList }">
					<tr>
					<td><input type="hidden" value="${freeboardInfo.bo_no}" />${freeboardInfo.rnum }</td>
					<td align="left">
						<c:forEach begin="1" end="${freeboardInfo.bo_depth }" varStatus="stat">
							&nbsp;&nbsp;&nbsp;
							<c:if test="${stat.last}">
								<i class="fa fa-angle-right"></i>
							</c:if>
						</c:forEach>
						${freeboardInfo.bo_title}</td>
					<td>${freeboardInfo.bo_nickname }</td>
					<td>${freeboardInfo.bo_reg_date }</td>
					<td>${freeboardInfo.bo_hit }</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
		${pagination }
</div>
<div >
<form action="${pageContext.request.contextPath}/user/freeboard/freeboardList.do" method="post" class="form-inline pull-right">
		<input name="search_keyword" type="text" placeholder="검색어 입력..." class="form-control" />
		<select class="form-control" name="search_keycode" >
			<option>검색조건</option>
			<option value="TOTAL">전체</option>
			<option value="TITLE">제목</option>
			<option value="CONTENT">내용</option>
			<option value="WRITER">작성자</option>
		</select>
	    <button type="submit" class="btn btn-primary form-control">검색</button>
	    <button id="btn1" type="button" class="btn btn-info form-control">게시글 등록</button>
</form>
</div>	
</body>
</html>