<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<h1>회원 정보</h1>
<form action="/member/Member.do" method="get">
    <fieldset>
        <legend>회원정보</legend>
        <table>
            <tr>
                <td class="label">아이디</td>
                <td class="field"><input type="text" name="userid" value="${member.id}" readonly></td>
            </tr>
            <tr>
                <td class="label">비밀번호</td>
                <td class="field"><input type="password" name="password" value="${member.password}" readonly></td>
            </tr>
            <tr>
                <td class="label">이름</td>
                <td class="field"><input type="text" name="name" value="${member.name}" readonly></td>
            </tr>
            <tr>
                <td class="label">이메일</td>
                <td class="field"><input type="text" name="email" value="${member.email}" readonly></td>
            </tr>
            <tr>
                <td class="label">주소</td>
                <td class="field"><input type="text" name="address" size="50" value="${member.address}" readonly></td>
            </tr>
        </table>
        <a href="/member/Member.do?action=update">회원정보 변경</a>
    </fieldset>
</form>
</body>
</html>