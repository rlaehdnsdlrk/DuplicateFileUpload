<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<form action="AddMember" method="post">
		ID : <input type="text" name="id"> <br>
		PW : <input type="password" name="pw"> <br>
		이름 : <input type="text" name="name"> <br>
		<input type="submit" value="가입">
		<input type="reset" value="취소">
	</form>
</body>
</html>