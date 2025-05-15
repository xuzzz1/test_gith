<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="pe.book.BookDTO"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book List</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/booklist.css">
    </head>
    <body>
        <%@ include file="/menu.jsp" %>

        <div class="container">
            <!-- Bộ lọc -->
            <aside class="filter">
                <h3>FILTER BY</h3>
                <form action="MainController" method="GET">
                    <!--input type="hidden" name="action" value="listbook">
                    <!-- Các checkbox lựa chọn -->
                    <ul>
                     <li><input type="checkbox" class="category-checkbox" name="categories" value="Science Fiction"> Science Fiction </li>
<li><input type="checkbox" class="category-checkbox" name="categories" value="History"> History </li>
<li><input type="checkbox" class="category-checkbox" name="categories" value="Dystopian"> Dystopian </li>
<li><input type="checkbox" class="category-checkbox" name="categories" value="Philosophy"> Philosophy </li>
<li><input type="checkbox" class="category-checkbox" name="categories" value="Classic">Classic</li>
<li><input type="checkbox" class="category-checkbox" name="categories" value="Adventure">Adventure</li>

                    </ul>

                    <!-- Ẩn input để chứa các category được chọn -->
                     <!--input type="hidden" name="categories" id="categories"-->

                    <input type="submit" name="action" value="Filter">
                </form>

            </aside>

            <!-- Danh sách sách -->
            <div class="book-container">
                <%
                    List<BookDTO> list = (List<BookDTO>) request.getAttribute("bookList");
                    if (list != null && !list.isEmpty()) {
                        for (BookDTO book : list) {
                            pageContext.setAttribute("book", book);
                %>


                <div class="book-item">
                    <a href="MainController?action=viewBookInfo&id=<%= book.getId()%>">
                        <img src="<%= request.getContextPath()%>/img/<%= book.getImageUrl()%>" 
                             alt="<%= book.getTitle()%>">
                        <p><%= book.getTitle()%></p>
                    </a>

                </div>


                <%
                    }
                } else {
                %>
                <p style="text-align: center; width: 100%;">Not found that book</p>
                <% }%>
            </div>
        </div>
    </body>
</html>
