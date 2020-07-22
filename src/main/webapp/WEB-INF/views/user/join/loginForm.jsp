<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="registMemberURI" value="/user/member/memberForm.do"></c:url>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/admin.css" type="text/css">
<title><spring:message code="cop.memberMngr.login"></spring:message></title>
<script type='text/javascript' src = 'http://code.jquery.com/jquery-latest.js'></script>

<script type="text/javascript">
	window.onload = function(){
		
			if(eval('${!empty param.message}')){
				alert('${param.message}');
			}
			

			$('.loginBtn').click(function(){
	               var mem_id = $('input[name=mem_id]').val();
	               var mem_pass = $('input[name=mem_pass]').val();
	       
	               var $frm = $('<form action="${pageContext.request.contextPath }/user/join/loginCheck.do" method="post"></form>');
	               var $inputID = $('<input type="hidden" value="' +mem_id+ '" name="mem_id" />');
	               var $inputPWD = $('<input type="hidden" value="' +mem_pass+ '" name="mem_pass" />');
	               $frm.append($inputID);
	               $frm.append($inputPWD);
	               $(document.body).append($frm);
	               $frm.submit();
	            });
		
				
	}
</script>
</head>
<body>
<form>
	<table width="770" border="0" align="center" cellpadding="0"
		cellspacing="0" style="margin: 90px;">
		<tr>
			<td height="150" align="center"><img src="${pageContext.request.contextPath }/image/p_login.gif" /></td>
		</tr>
		<tr>
			<td height="174"
				style="background: url(${pageContext.request.contextPath }/image/login_bg.jpg); border: 1px solid #e3e3e3;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="260" height="110" align="center"
							style="border-right: 1px dotted #736357;">
							<img src="${pageContext.request.contextPath }/image/logo.jpg" />
						</td>
						<td>
							<table border="0" align="center" cellpadding="5"
								cellspacing="0">
								<tr>
									<td><b><spring:message code="cop.id"></spring:message></b></td>
									<td><input type="text" name="mem_id" class="box" tabindex="3" height="18" /></td>
									<td rowspan="2">
										<img src="${pageContext.request.contextPath }/image/login.gif" class="loginBtn"/>
									</td>
								</tr>
								<tr>
									<td><b><spring:message code="cop.password"></spring:message></b></td>
									<td><input type="password" name="mem_pass" class="box" tabindex="3" height="18" /></td>
								</tr>
								<tr>
									<td colspan="2">
										<spring:message code="cop.id_save"></spring:message> : <input type="checkbox" name="saveID">
								<a href = '${registMemberURI }'>회원가입을 원하세요?</a>
									</td>
								</tr>
							</table>
							
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>	
</body>
</html>
