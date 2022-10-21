<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록</title>
</head>
<body>
	<h1>상품 등록</h1>
	<form name="AddProduct" method="post" enctype="multipart/form-data" action="AddProduct">
		상품코드 : <input type="text" name="code"> <br>
		<br> 상품설명 :
		<textarea rows="10" cols="22" style="vertical-align: top;"
			name="description"></textarea>
		<br>
		<br>
		<!-- 파일 업로드 시 form enctype속성은 multipart/form-data. 파일 업로드 경로 "C:\\shopImg"   -->
		상품 이미지1 : &nbsp;<input type="file" name="img1"> <br> 상품
		이미지2 : &nbsp;<input type="file" name="img2"> <br> 상품 이미지3
		: &nbsp;<input type="file" name="img3"> <br> <br>
		<br>
		
		<input type="submit" value="상품등록">
	</form>

</body>


</html>