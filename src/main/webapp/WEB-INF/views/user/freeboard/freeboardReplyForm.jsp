<%@ page language="JAVA" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시글 댓글등록</title>
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
    
    $('#btn1').on('click', function() {
		$(location).attr('href','${pageContext.request.contextPath}/user/freeboard/freeboardList.do')
	})
	
	$('.form-horizontal').on('submit', function() {
		const bo_title = $('#bo_title').val();
		const bo_nickname = $('#bo_nickname').val();
		const bo_pwd = $('#bo_pwd').val();
		const bo_mail = $('#bo_mail').val();
		const bo_content = $('#bo_content').summernote('code');
		
		if (bo_title === "") {
			printAlert('제목을 입력해주세요!');
			return false;
		}
		if (bo_nickname === "") {
			printAlert('대화명을 입력해주세요!');
			return false;
		}
		if (bo_pwd === "") {
			printAlert('비밀번호를 입력해주세요!');
			return false;
		}
		if (bo_mail === "") {
			printAlert('메일을 입력해주세요!');
			return false;
		}
		
		$(this).append('<input type ="hidden" name="bo_content" value="'+bo_content+'"/>');
		$(this).append('<input type ="hidden" name="bo_writer" value="${LOGIN_MEMBERINFO.mem_id}"/>');
		$(this).append('<input type ="hidden" name="bo_ip" value="${pageContext.request.remoteAddr}"/>');
		$(this).append('<input type ="hidden" name="bo_group" value="${param.bo_group}"/>');
		$(this).append('<input type ="hidden" name="bo_seq" value="${param.bo_seq}"/>');
		$(this).append('<input type ="hidden" name="bo_depth" value="${param.bo_depth}"/>');
	})
});

function printAlert(message) {
	alert(message);
}
</script>
</head>
<body>
<div class="row">
	 <div class="col-sm-3">
		 <label class="col-sm-2 control-label">No :${param.rnum} }</label>
  		 <p class="form-control-static">111</p>
	 </div>
	 <div class="col-sm-8">
	 	<label class="col-sm-2 control-label">제목 :${param.bo_title}</label>
    	<p class="form-control-static"></p>
	 </div>
	 <div class="col-sm-1">
	 	<p class="text-right text-danger bg-danger">${param.bo_writer}의 댓글</p>
	 </div>
</div>
<hr />
<form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/user/freeboard/insertfreeboardreply.do" method="post">
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
			<input type="password" class="form-control" id="bo_mail" name="bo_mail" >
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="bo_content">내용:</label>
		<div class="col-sm-10"> 
			<div id="bo_content"><p></p></div>
		</div>
	</div>
	<div class="form-group"> 
		<div class="col-sm-offset-2 col-sm-10">
			<button type="submit" class="btn btn-success" style="float: right;">댓글등록</button>
			<button type="reset" class="btn btn-danger">취소</button>
			<button id="btn1" type="button" class="btn btn-info">목록</button>
		</div>
	</div>
</form>
</body>
</html>