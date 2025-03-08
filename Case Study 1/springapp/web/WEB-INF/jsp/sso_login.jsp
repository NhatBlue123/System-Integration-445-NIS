<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>SSO Login</title>
</head>
<body>
    <h2>Single Sign-On</h2>
    <p>Chào, ${username}!</p>
    <form action="/sso/send" method="GET">
        <input type="hidden" name="username" value="${username}">
        <button type="submit">Đăng nhập vào HR App</button>
    </form>
</body>
</html>
