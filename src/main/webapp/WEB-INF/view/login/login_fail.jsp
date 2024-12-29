<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ERROR</title>
</head>
<body>
<h1>로그인 실패</h1>
<%= request.getAttribute("message") %>
</body>
</html>