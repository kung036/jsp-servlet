<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>OK</title>
</head>
<body>
<h1>로그인 성공</h1>
<%= session.getAttribute("id") %>님 로그인 함<br>
<%= request.getAttribute("message") %><br>
<a href="/Login.do?action=login">홈페이지</a>
</body>
</html>