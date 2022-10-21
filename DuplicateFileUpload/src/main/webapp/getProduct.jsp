<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 페이지</title>
</head>
<body>
	<h1>상품 페이지</h1>
	상품코드 : ${vo.code}
	<br> 상품설명 : ${vo.description}
	<br> 상품 이미지 1
	<img src="${contextPath}/Download?code=${vo.code}&fileName=filename1">
	<br> 상품 이미지 2
	<img src="${contextPath}/Download?code=${vo.code}&fileName=filename2">
	<br> 상품 이미지 3
	<img src="${contextPath}/Download?code=${vo.code}&fileName=filename3">
	<br>

	<a href="GetProductList">리스트</a>
	<a href="index.jsp">홈</a>

	<hr>
	
	<c:if test="${cnt1 == 0 }">
	<form action="AddOpinion" method="post" name="opinionForm">
		점수 : <input type="number" name="point" min="1" max="5" step="0.5">
		평남기기 :<input type="text" name="opinion"> <input type="hidden"
			name="code" value="${vo.code}"> <input type="submit"
			value="내용전송">
	</form>
	</c:if>
	
	<c:if test="${cnt1 == 1 }">
		<h1>이미 작석했습니다.</h1>
	</c:if>
	
	<hr>
	<h3>총 평점 : ${opinionPoint}</h3>
	
	<table border="1">
		<c:forEach items="${opinionList }" var="opinionList">
			<tr>
				<td>${opinionList.id }</td>
				<td>${opinionList.point }</td>
				<td>${opinionList.opinion }</td>
			</tr>
		</c:forEach>
	</table>
	
	

</body>
</html>