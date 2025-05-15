

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/login.css">
    </head>
    <body>
        <div class="form-container">
            <div class="tabs">
                <div class="tab active" id="login-tab">Log in</div>
                <div class="tab" id="register-tab">Register</div>
            </div>

            <!-- Form đăng nhập -->
            <div id="login-form">
                <h2>Login</h2>
                <form action="MainController" method="POST">
                    <input type="hidden" name="action" value="login">
                    <input name="user" type="text" placeholder="Username" required>
                    <input name="password" type="password" placeholder="Password" required>

                    <% String error = (String) request.getAttribute("error"); %>
                    <% if (error != null) {%>
                    <h4 style="color: #E65100;"><%= error%></h4>
                    <% } %>

                    <!--div class="forgot-password">
                        <a href="forgot-password.jsp">Quên mật khẩu?</a>
                    </div-->

                    <input value="Login" type="submit">
                </form>
            </div>

            <!-- Form đăng ký -->
            <div id="register-form" style="display: none;">
                <h2>Register</h2>
                <form action="MainController" method="POST">
                    <input type="hidden" name="action" value="register">
                    <input name="user" type="text" placeholder="Username" required>
                    <input name="password" type="password" placeholder="Password" required>
                    <input name="confirm_password" type="password" placeholder="Confirm Password" required>

                    <% String registerError = (String) request.getAttribute("registerError"); %>
                    <% if (registerError != null) {%>
                    <h4 style="color: #E65100;"><%= registerError%></h4>
                    <% }%>

                    <input value="Register" type="submit">
                </form>
            </div>
        </div>

        <script>
            document.getElementById("login-tab").addEventListener("click", function () {
                document.getElementById("login-form").style.display = "block";
                document.getElementById("register-form").style.display = "none";
                this.classList.add("active");
                document.getElementById("register-tab").classList.remove("active");
            });

            document.getElementById("register-tab").addEventListener("click", function () {
                document.getElementById("login-form").style.display = "none";
                document.getElementById("register-form").style.display = "block";
                this.classList.add("active");
                document.getElementById("login-tab").classList.remove("active");
            });
        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var showRegister = "<%= request.getAttribute("showRegister")%>";
                if (showRegister === "true") {
                    document.getElementById("register-tab").click(); // Chuyển sang tab đăng ký
                }
            });
        </script>
    </body>

</html>

