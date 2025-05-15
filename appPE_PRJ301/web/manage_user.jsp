<%@page import="pe.users.UserDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Manage Users</title>
        <style>
            .container {
                width: 80%;
                margin: auto;
                text-align: center;
                padding: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 12px;
            }
            th {
                background-color: #007bff;
                color: white;
            }
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            .create-btn {
                padding: 10px 20px;
                background-color: #28a745;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            .create-btn:hover {
                background-color: #218838;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/menu.jsp" flush="true" />
        <div class="container">
            <h1>Manage Users</h1>
            <%
                List<UserDTO> list = (List<UserDTO>) request.getAttribute("userList");
                if (list != null && !list.isEmpty()) {
            %>
            <table>
                <tr>

                    <th>Username</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Gender</th>
                    <th>StudentID</th>
                    <th>Passwoed</th>
                    <th>Role</th>
                    <th>Action</th>
                </tr>
                <% for (UserDTO user : list) {%>
                <tr>

                    <td><%= user.getUsername()%></td>
                    <td><%= user.getFirstName() + " " + user.getLastName()%></td>
                    <td><%= user.getEmail()%></td>
                    <td><%= user.getPhoneNumber()%></td>
                    <td><%= user.getGender()%></td>
                    <td><%= user.getStudentID()%></td>
                    <td><%= user.getPassword()%></td>
                    <td><%= user.getRole()%></td>

                    <td>
                        <% if (!"AD".equals(user.getRole())) {%>
                        <a href="MainController?action=deleteUser&id=<%= user.getUserID()%>" 
                           onclick="return confirm('Are you sure you want to delete this user?');">Delete</a>
                        <% } else { %>
                        <span>Admin</span> <!-- Hoặc để trống -->
                        <% } %>
                    </td>
                </tr>
                <% } %>
            </table>
            <% } else { %>
            <p>No users found.</p>
            <% }%>
        </div>
    </body>
</html>
