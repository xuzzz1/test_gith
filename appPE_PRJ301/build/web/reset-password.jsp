<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt lại mật khẩu</title>
     <link rel="stylesheet" href="<%= request.getContextPath()%>/css/reset-password.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div id="register-form">
        <h2>Đặt lại mật khẩu</h2>
        <form action="MainController" method="POST">
            <input type="hidden" name="action" value="resetPassword">

            <input name="username" type="text" placeholder="Nhập Username" required>
            <input name="password" type="password" placeholder="Mật khẩu mới" required>
            <input name="confirm_password" type="password" placeholder="Xác nhận mật khẩu mới" required>

            <% String resetError = (String) request.getAttribute("resetError"); %>
            <% if (resetError != null) { %>
                <h4 style="color: #E65100;"><%= resetError %></h4>
            <% } %>

            <input value="Đổi mật khẩu" type="submit">
        </form>
    </div>
</body>
</html>
