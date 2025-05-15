<%@page import="pe.users.UserDTO"%>
<%@page import="pe.book.BookDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hồ sơ cá nhân</title>
        <link rel="stylesheet" href="css/profile.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    </head>
    <body>
        <jsp:include page="/menu.jsp" flush="true" />
        <%
            UserDTO user = (UserDTO) session.getAttribute("usersession");
            if (user != null) {
                String role = user.getRole(); // Giả sử UserDTO có phương thức getRole()
        %>
        <div class="profile-container">
            <!-- Sidebar -->
            <div class="sidebar">

                <ul>
                    <li class="active"><i class="fas fa-user"></i>Account information</li>
                    <li><i class="fas fa-id-card"></i>Personal profile</li>
                    <li><i class="fas fa-bell"></i>Notification</li>
                    <li><a href="MainController?action=resetPassword"><i class="fas fa-lock"></i>Change password</a></li>
                    <li><i class="fas fa-history"></i> History borrow</li>
                        <% if (user != null && "AD".equals(user.getRole())) { %>
                  
                    <li><a href="MainController?action=manage"><i class="fas fa-book"></i>Manage book</a></li>
                    <li><a href="MainController?action=manageUser"><i class="fas fa-user"></i>Manage user</a></li>
                    <% }%>

                    <li><a href="MainController?action=logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                </ul>
            </div>

            <!-- Form Hồ sơ cá nhân -->
            <div class="profile-form">
                <h2>Hồ sơ cá nhân</h2>
                <form action="MainController" method="POST">
                    <div class="form-group">
                        <label>Tên *</label>
                        <input type="text" name="lastName" value="<%= user.getLastName()%>" required>
                    </div>
                    <div class="form-group">
                        <label>Họ *</label>
                        <input type="text" name="firstName" value="<%= user.getFirstName()%>" required>
                    </div>
                    <div class="form-group">
                        <label>Số điện thoại</label>
                        <input type="text" name="phone" value="<%= user.getPhoneNumber() != null ? user.getPhoneNumber() : ""%>">

                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" name="email" placeholder="Chưa có email" value="<%= user.getEmail()%>">

                    </div>
                    <!--div class="form-group gender-group">
                        <label>Giới tính</label>
                        <input type="radio" name="gender" value=""> Nam
                        <input type="radio" name="gender" value="> Nữ
                    </div-->
                    <div class="form-group gender-group">
                        <label>Giới tính</label>
                        <input type="radio" name="gender" value="Male" <%= "Male".equals(user.getGender()) ? "checked" : ""%>> Nam
                        <input type="radio" name="gender" value="Female" <%= "Female".equals(user.getGender()) ? "checked" : ""%>> Nữ
                    </div>

                    <div class="form-group">
                        <label>Mã số sinh viên:</label>
                        <input type="text" name="studentID" value="<%= user.getStudentID()%>">
                    </div>
                    <input type="hidden" name="action" value="updateProfile">
                    <!-- Thêm thông báo khi update thành công or thất bại-->

                    <% String message = (String) request.getAttribute("message"); %>
                    <% String error = (String) request.getAttribute("error"); %>

                    <% if (message != null) {%>
                    <div class="alert alert-success"><%= message%></div>
                    <% } %>

                    <% if (error != null) {%>
                    <div class="alert alert-danger"><%= error%></div>
                    <% } %>




                    <button type="submit" class="save-btn">Save changes</button>


                </form>
            </div>
        </div>

        <%
            }
        %>


    </body>
</html>
