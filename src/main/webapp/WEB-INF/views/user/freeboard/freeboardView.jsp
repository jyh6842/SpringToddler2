<%@ page language="JAVA" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시글 정보</title>
<!-- 이미지 슬라이드 사이즈 조정 -->
<style type="text/css">
.carousel{
	width:200px;
    height:150px;
    margin-left: 200px;
}
.carousel-inner > .item > img{
    width:200px;
    height:150px;
}       
</style>
<script>
$(function(){
	// 섬머노트를 div를 활용한 textarea에 추가.
	// http://summernote.org 활용
    $('#bo_content').summernote({
			height: 150,
			codemirror: {
			theme: 'monokai'
		}
    });
    
    $('#bo_title').val('${freeboardInfo.bo_title}');
	
	$('#bo_nickname').val('${freeboardInfo.bo_nickname}');
	
	$('#bo_pwd').val('${freeboardInfo.bo_pwd}');
	
	$('#bo_mail').val('${freeboardInfo.bo_mail}');
	
	$('#bo_content').summernote('code','${freeboardInfo.bo_content}');
	
	$('.form-horizontal').on('submit', function() {
		if('${LOGIN_MEMBERINFO.mem_id == freeboardInfo.bo_writer}'){
			const bo_content = $('#bo_content').summernote('code');
			const ipt_bo_no = $("<input type='hidden' name='bo_no' value='${freeboardInfo.bo_no}' />");
			const ipt_bo_content = $("<input type='hidden' name='bo_content' value='" + bo_content + "'/>");
			const ipt_bo_writer = $("<input type='hidden' name='bo_writer' value='${LOGIN_MEMBERINFO.mem_id}'/>");
			const ipt_bo_ip = $("<input type='hidden' name='bo_id' value='${pageContext.request.remoteAddr}/>'");
			
			$(this).append(ipt_bo_content);
			$(this).append(ipt_bo_writer);
			$(this).append(ipt_bo_no);
			$(this).append(ipt_bo_ip);
		}else{
			alert("작성자만 수정할수있습니다.");
		}
	});
});
</script>
</head>
<body>
<form class="form-horizontal" role="form" action="${pageContext.request.contextPath }/user/freeboard/updatefreeboard.do" method="post">
	<div class="form-group">
		<label class="control-label col-sm-2" for="bo_title">제목:</label>
		<div class="col-sm-10">
			<input type="text" class="form-control" id="bo_title" name="bo_title" >
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="bo_nickname">작성자 대화명:</label>
		<div class="col-sm-10"> 
			<input type="text" class="form-control" id="bo_nickname" name="bo_nickname" >
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="bo_pwd">패스워드:</label>
		<div class="col-sm-10"> 
			<input type="password" class="form-control" id="bo_pwd" name="bo_pwd" >
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="bo_mail">메일:</label>
		<div class="col-sm-10"> 
			<input type="text" class="form-control" id="bo_mail" name="bo_mail" >
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="bo_content">내용:</label>
		<div class="col-sm-10"> 
			<div id="bo_content"><p></p></div>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="bo_content">첨부파일:</label>
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>

			</ol>
	
			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox" style="height: 200px;">
				<c:forEach items="${freeboardInfo.items }" var="fileitemInfo" varStatus="stat">
					<c:if test="${stat.first }">
						<div class="item active">
					</c:if>
					<c:if test="${stat.last }">
						<div class="item">
					</c:if>
							<img alt="pic1" src="/files/${fileitemInfo.file_save_name }"
								onclick="javascript:location.href='${pageContext.request.contextPath}/user/freeboard/fileDownload.do?file_seq=${fileitemInfo.file_seq }'">
<%-- 								<c:forEach items="${freeboardInfo.items }" var="fileitemInfo" varStatus="stat"> 이거를 위에 사용해서 가져온다.--%>
						</div>	
				</c:forEach> 
			</div>
			<!-- Left and right controls -->
			<a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
			<a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
		</div>
	</div>
	<div class="form-group"> 
		<div class="col-sm-offset-2 col-sm-10">
			<button id="btn1" type="button" class="btn btn-success">댓글쓰기</button>
			<button id="btn2" type="button" class="btn btn-danger">삭제</button>
			<button id="btn4" type="button" class="btn btn-info">목록</button>
			<button  type="submit" class="btn btn-default" style="float: right">수정</button>
		</div>
	</div>
</form>
<script type="text/javascript">
	$(function() {
		//댓글작성
		$('#btn1').on('click', function() {
			const parentInfo = '&bo_group=${freeboardInfo.bo_group}&bo_seq=${freeboardInfo.bo_seq}&bo_depth=${freeboardInfo.bo_depth}';

			$(location).attr('href', '${pageContext.request.contextPath}/user/freeboard/freeboardReplyForm.do?rnum=${param.rnum}&bo_title=${freeboardInfo.bo_title}&bo_writer=${freeboardInfo.bo_writer}'+parentInfo);
		});
		//삭제
		$('#btn2').on('click', function() {
			if('${LOGIN_MEMBERINFO.mem_id == freeboardInfo.bo_writer}'){
			$(location).attr('href', '${pageContext.request.contextPath}/user/freeboard/deletefreeboard.do?bo_no=${freeboardInfo.bo_no}');
			}else alert('작성자만 삭제할 수 있습니다.');
		});
		//목록
		$('#btn4').on('click', function() {
			$(location).attr('href', '${pageContext.request.contextPath}/user/freeboard/freeboardList.do');
		});
	});
	
</script>
</body>
</html>