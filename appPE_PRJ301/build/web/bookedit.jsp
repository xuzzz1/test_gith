<%@page import="pe.book.BookDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>book Details</title>
      <style>
            .book-edit-container {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                text-align: center;
                padding: 20px;
            }

            .book-edit-container h1 {
                color: #333;
            }

            .book-edit-container form {
                background: white;
                width: 50%;
                margin: 20px auto;
                padding: 20px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
                text-align: left;
            }

            .book-edit-container label {
                font-weight: bold;
                display: block;
                margin-top: 10px;
            }

            .book-edit-container input, 
            .book-edit-container select, 
            .book-edit-container textarea {
                width: 100%;
                padding: 8px;
                margin-top: 5px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 14px;
            }

            .book-edit-container textarea {
                resize: vertical;
            }

            .book-edit-container input[type="submit"] {
                font-size: 16px;
                font-weight: bold;
                background-color: #E65100;
                color: white;
                border: none;
                padding: 10px 15px;
                cursor: pointer;
                border-radius: 5px;
                transition: 0.3s;
                margin-top: 15px;
                display: block;
                width: 100%;
            }

            .book-edit-container input[type="submit"]:hover {
                background-color: #bf360c;
            }
        </style>
    </head>
    <body>

        <jsp:include page="/menu.jsp" flush="true" /> 
          <div class="book-edit-container">
        <h1>Book Edit </h1>
<form action="MainController" method="POST">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${requestScope.object.id}">

    <label>Title:</label>
    <input name="title" value="${requestScope.object.title}" required><br>

    <label>Quantity:</label>
    <input name="quantity" value="${requestScope.object.quantity}" required><br>
    
      <!-- Author Name -->
    <label>Author Name:</label>
    <input name="authorName" value="${requestScope.object.authorName}" ><br>

    <!-- Category Name -->
    <label>Category:</label>
    <input name="categoryName" value="${requestScope.object.categoryName}" ><br>
    
    <!-- Description -->
    <label>Description:</label>
    <textarea name="description" rows="4" cols="50">${requestScope.object.description}</textarea><br>

    
    <label>Status:</label>
    <select name="status" required>
        <option value="Available" ${requestScope.object.status == 'Available' ? 'selected' : ''}>Available</option>
        <option value="Borrowed" ${requestScope.object.status == 'Borrowed' ? 'selected' : ''}>Borrowed</option>
    </select><br>
    
     <label>Upload Image:</label>
     <input type="text" name="image" value="${requestScope.object.imageUrl}"><br>
    <input type="submit" value="save">
</form>
<a href="MainController?action=details&id=${requestScope.object.id}">Exit</a>
          </div>
    </body>
</html>
