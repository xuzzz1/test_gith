<%@page import="pe.book.BookDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shelf Page</title>
       <style>
        .shelf-container {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            text-align: center;
            padding: 20px;
        }

        .shelf-container h2 {
            color: #333;
        }

        .shelf-container table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            background: white;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            overflow: hidden;
        }

        .shelf-container th, .shelf-container td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        .shelf-container th {
            background-color: #007bff;
            color: white;
        }

        .shelf-container tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        .shelf-container tr:hover {
            background-color: #ddd;
        }

        .shelf-container input[type="submit"] {
            background-color: #dc3545;
            color: white;
            font-size: 14px;
            font-weight: bold;
            border: none;
            padding: 8px 12px;
            cursor: pointer;
            border-radius: 5px;
            transition: 0.3s;
        }

        .shelf-container input[type="submit"]:hover {
            background-color: #c82333;
        }

        .shelf-container .borrow-btn {
            background-color: #28a745;
            font-size: 16px;
            padding: 12px 20px;
            margin-top: 15px;
            border: none;
            border-radius: 5px;
            color: white;
            cursor: pointer;
            transition: 0.3s;
        }

        .shelf-container .borrow-btn:hover {
            background-color: #218838;
        }
    </style>
    </head>
    <body>
      <jsp:include page="/menu.jsp" flush="true" />
   <div class="shelf-container">
      <h2>Book Shelf</h2>
     

      <table border="1">
          <tr>
               <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Quantity</th>
                <th>Status</th>
                <th>Action</th>
              
          </tr>
          
          <%
          List<BookDTO> shelf = (List<BookDTO>) request.getAttribute("shelfList");
          if (shelf != null && !shelf.isEmpty()) {
              for (BookDTO book : shelf) { 
          %>
          <tr>
              <td><%= book.getId() %></td>
              <td><%= book.getTitle() %></td>
              <td>1</td>
              <td><%= book.getStatus() %></td>
              
               <td>
                    <form action="MainController" method="post">
                        <input type="hidden" name="action" value="removeFromShelf">
                        <input type="hidden" name="id" value="<%= book.getId() %>">
                        <input type="submit" value="Remove">
                    </form>
                </td>
                
          </tr>
          <% 
              }
          } else { 
          %>
          <tr>
              <td colspan="5" style="text-align: center;">No book in shelf</td>
          </tr>
          <% } %>
          
      </table>

<br>
<form action="MainController" method="post">
    <input type="hidden" name="action" value="borrow">
    <input type="hidden" name="id" value="<%= shelf != null && !shelf.isEmpty() ? shelf.get(0).getId() : "" %>">
    <input type="submit" value="Borrow now">
</form>
   </div>
    </body>
</html>
