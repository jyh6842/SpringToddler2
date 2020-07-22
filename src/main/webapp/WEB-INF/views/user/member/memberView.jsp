<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type='text/javascript' src = 'http://code.jquery.com/jquery-latest.js'></script>
<script type="text/javascript">
$(function(){
	$('input[value=탈퇴]').click(function(){
		$(location).attr('href', '${pageContext.request.contextPath}/user/member/deleteMemberInfo/${memberInfo.mem_id}.do');
	});
	$('input[value=회원목록]').click(function(){
		$(location).attr('href', '${pageContext.request.contextPath}/user/member/memberList.do');
	});
	$('input[value=로그아웃]').click(function(){
		$(location).attr('href', '${pageContext.request.contextPath}/user/join/logout.do');
	});
	
});
</script>
</head>
<body>
<form action='${pageContext.request.contextPath }/user/member/updateMemberInfo.do' method='post'>      
	<input type="hidden" name='mem_id'  value='${memberInfo.mem_id }'/>
	<table>                                                               
		<tr>                                                              
			<td>아이디</td>                                                  
			<td>                                                          
				<input type='text' name='mem_id' disabled="disabled" value='${memberInfo.mem_id }'/>                   
			</td>                                                         
		</tr>                                                             
		<tr>                                                              
			<td>패스워드</td>                                                 
			<td>                                                          
				<input type='text' name='mem_pass' value='${memberInfo.mem_pass }'/>                      
			</td>                                                         
		</tr>                                                             
		<tr>                                                              
			<td>성명</td>                                                   
			<td><input type='text' name='mem_name' disabled="disabled" value='${memberInfo.mem_name }'/></td>
		</tr>                                                             
		<tr>                                                              
			<td>주민등록번호</td>                                               
			<td>                                                          
				<input type='text' name='mem_regno1' disabled="disabled" value='${memberInfo.mem_regno1 }'/>        
				<input type='text' name='mem_regno2' disabled="disabled" value='${memberInfo.mem_regno2 }'/>         
			</td>                                                         
		</tr>                                                             
		<tr>                                                              
			<td>생년월일</td>                                                 
			<td><input type='text' name='mem_bir' value='${memberInfo.mem_bir }'/></td> 
		</tr>                                                             
		<tr>                                                              
			<td>우편번호</td>                                                 
			<td><input type='text' name='mem_zip' value='${memberInfo.mem_zip }'/></td>
		</tr>                                                             
		<tr>                                                              
			<td>주소</td>                                                   
			<td>                                                          
				<input type='text' name='mem_add1' value='${memberInfo.mem_add1 }'/>-                    
				<input type='text' name='mem_add2' value='${memberInfo.mem_add2 }'/>                     
			</td>                                                         
		</tr>                                                             
		<tr>                                                              
			<td>집전화번호</td>                                                
			<td><input type='text' name='mem_hometel' value='${memberInfo.mem_hometel }'/></td>              
		</tr>                                                             
		<tr>                                                              
			<td>회사전화번호</td>                                               
			<td><input type='text' name='mem_comtel' value='${memberInfo.mem_comtel }'/></td>               
		</tr>                                                             
		<tr>                                                              
			<td>휴대폰번호</td>                                                
			<td><input type='text' name='mem_hp' value='${memberInfo.mem_hp }'/></td>                    
		</tr>                                                             
		<tr>                                                              
			<td>이메일</td>                                                  
			<td><input type='text' name='mem_mail' value='${memberInfo.mem_mail }'/></td>                
		</tr>                                                             
		<tr>                                                              
			<td>직업</td>                                                   
			<td><input type='text' name='mem_job' value='${memberInfo.mem_job }'/></td>                 
		</tr>                                                             
		<tr>                                                              
			<td>취미</td>                                                   
			<td><input type='text' name='mem_like' value='${memberInfo.mem_like }'/></td>                 
		</tr>                                                             
		<tr>                                                              
			<td colspan='2'>                                              
				<input type='submit' value='수정' />
				<input type="button" value='탈퇴'/>                      
				<input type='reset' value='취소' />                         
				<input type="button" value='회원목록'/>                      
				<input type="button" value='로그아웃'/>                      
			</td>                                                         
		</tr>                                                             
	</table>                                                              
</form>                                                                  
</body>                                                                  
</html>                                                                  
