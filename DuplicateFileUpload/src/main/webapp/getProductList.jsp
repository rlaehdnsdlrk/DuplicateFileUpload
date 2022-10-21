<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 리스트</title>
</head>
<body>
	<h1>상품 리스트</h1>
	<p>${id }계정으로 로그인 하였습니다.</p> <a href="Logout">로그아웃</a>
	<table border="1">
		<tr>
			<th>상품코드</th>
			<th>등록일자</th>
		</tr>
		<c:forEach items="${list}" var="list">
		<tr>
			<td> <a href="GetProduct?code=${list.code}">${list.code}</a> </td>
			<td>${list.regdate}</td>
		</tr>
		</c:forEach>
	</table>
	<a href="index.jsp">홈</a>
</body>
</html>