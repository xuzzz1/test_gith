<%@page import="pe.book.BookDTO"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Book List</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/booklist.css">
    <style>
        .book-manage table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
        }
        .book-manage th, .book-manage td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        .book-manage th {
            background-color: #f2f2f2;
        }
        .book-manage img {
            width: 60px;
            height: 80px;
            object-fit: cover;
        }
        .create-btn {
            display: block;
            margin: 20px auto;
            padding: 10px 20px;
            background-color: #B71C1C;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        .create-btn:hover {
            background-color: #45a049;
        }
        h2.title {
            text-align: center;
            margin-top: 30px;
        }
    </style>
</head>
<body>
    <jsp:include page="/menu.jsp" flush="true" />

    <div class="container book-manage">

         <form action="MainController" method="POST"> 
                        <input type="submit" name="action" value="create" class="create-btn">
                     
          </form>
        <%
            List<BookDTO> list = (List<BookDTO>) request.getAttribute("bookList");
            if (list != null && !list.isEmpty()) {
        %>
         
        <table>
            <tr>
                <th>Image</th>
                <th>Title</th>
                <th>AuthorName</th>
                <th>CategoryName</th>
                <th>Quantity</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <% for (BookDTO book : list) { %>
            <tr>
                <td>
                    <img src="<%= request.getContextPath()%>/img/<%= book.getImageUrl()%>" 
                         alt="<%= book.getTitle()%>">
                </td>
                <td><%= book.getTitle()%></td>
                <td><%= book.getAuthorName()%></td>
                <td><%= book.getCategoryName() %></td>
                <td><%= book.getQuantity()%></td>
                <td><%= book.getStatus()%></td>
                <td>
                    <a href="MainController?action=details&id=<%= book.getId()%>">detail</a>
                </td>
            </tr>
            <% } %>
        </table>

        <% } else { %>
            <p style="text-align: center;">Can not found</p>
        <% } %>
    </div>
</body>
</html>
