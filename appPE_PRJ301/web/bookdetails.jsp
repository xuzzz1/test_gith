
<%@page import="pe.rate.RateDTO"%>
<%@page import="java.util.List"%>
<%@page import="pe.rate.RateDAO"%>
<%@page import="pe.book.BookDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Details</title>
        <style>
   .book-details-container {
    font-family: Arial, sans-serif;
    background-color: #f8f9fa;
    text-align: center;
    padding: 20px;
}

.book-details-container h1 {
    color: #333;
}

.book-details-container table {
    width: 50%;
    margin: 20px auto;
    border-collapse: collapse;
    background: white;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
    overflow: hidden;
}

.book-details-container td {
    padding: 12px;
    border: 1px solid #ddd;
    text-align: left;
}

.book-details-container td:first-child {
    font-weight: bold;
    background-color: #007bff;
    color: white;
    width: 30%;
}

.book-details-container td:last-child {
    width: 70%;
}

.book-details-container form {
    margin-top: 10px;
}

.book-details-container input[type="submit"] {
    font-size: 14px;
    font-weight: bold;
    border: none;
    padding: 8px 12px;
    cursor: pointer;
    border-radius: 5px;
    transition: 0.3s;
    margin: 5px;
}

.book-details-container .edit-btn {
    background-color: #ffc107;
    color: black;
}

.book-details-container .edit-btn:hover {
    background-color: #e0a800;
}

.book-details-container .delete-btn {
    background-color: #dc3545;
    color: white;
}

.book-details-container .delete-btn:hover {
    background-color: #c82333;
}

    </style>
    <a href="bookdetails.jsp"></a>
    
    </head>
    <body>
        <jsp:include page="/menu.jsp" flush="true" />
 <div class="book-details-container">
        <h1>Book Details </h1>         

        <table>
            <tr><td>Id: </td><td>${requestScope.object.id}</td></tr>
            <tr><td>title: </td><td>${requestScope.object.title }</td></tr>
            <tr><td>quantity: </td><td>${requestScope.object.quantity}</td></tr>	
            <tr><td>authorname: </td><td>${requestScope.object.authorName}</td></tr>
            <tr><td>categoryname: </td><td>${requestScope.object.categoryName}</td></tr>
            <tr><td>description: </td><td>${requestScope.object.description}</td></tr>
            <tr><td>status: </td><td>${requestScope.object.status}</td></tr>
        </table>

<%
    String role = (String) session.getAttribute("role");
    if ("admin".equals(role)) {
%>
    <!-- Chỉ admin mới thấy -->
    <form action="MainController" method="post">
        <input type="hidden" name="id" value="${requestScope.object.id}">
        <input type="hidden" name="action" value="edit">
        <input type="submit" value="Edit">
    </form>
    
    <form action="MainController" method="POST">
        <input name="action" value="delete" type="hidden">
        <input name="id" value="${requestScope.object.id}" type="hidden">
        <input type="submit" value="Delete">
    </form>
       
        <a href="MainController?action=manage">Exit</a>
<%
    }
%>

 </div>
    </body>
</html>
