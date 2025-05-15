<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thêm Sách Mới</title>
         <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            padding: 20px;
        }

        h2 {
            color: #333;
        }

        form {
            background: white;
            max-width: 500px;
            margin: 20px auto;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }

        label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
            text-align: left;
        }

        input, select, textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }

        textarea {
            height: 80px;
            resize: none;
        }

        input[type="submit"] {
            background-color:#E65100;
            color: white;
            font-size: 18px;
            font-weight: bold;
            border: none;
            padding: 12px;
            margin-top: 15px;
            cursor: pointer;
            border-radius: 5px;
        }

        input[type="submit"]:hover {
            background-color: #E65100;
        }

        .error-message {
            color: red;
            font-size: 14px;
            margin-top: 10px;
        }

        .back-link {
            display: inline-block;
            margin-top: 15px;
            text-decoration: none;
            font-size: 16px;
            color: #E65100;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
    </head>
    <body>
        <h2>Add New Books</h2>

        <form action="MainController" method="POST" >

            <label>Title:</label>
            <input type="text" name="title" value="Test book" required><br>

            <label for="author-select">Select an author:</label>
            <select id="author-select" name="authorName">
                <!-- Option for all authors -->
                <option value="">All Authors</option>
                <!-- Replace with dynamically generated options from your data -->
                <option value="Isaac Asimov" selected="selected">Isaac Asimov</option>
                <option value="Stephen Hawking">Stephen Hawking</option>
                <option value="Dan Brown">Dan Brown</option>
                <option value="Yuval Noah Harari">Yuval Noah Harari</option>
                <option value="J.D. Salinger">J.D. Salinger</option>
                <option value="Harper Lee">Harper Lee</option>
                <option value="George Orwell">George Orwell</option>
                <option value="Aldous Huxley">Aldous Huxley</option>
                <option value="F. Scott Fitzgerald">F. Scott Fitzgerald</option>
                <option value="Herman Melville">Herman Melville</option>
                <option value="Leo Tolstoy">Leo Tolstoy</option>
                <option value="Fyodor Dostoevsky">Fyodor Dostoevsky</option>
                <option value="Jane Austen">Jane Austen</option>
                <option value="J.R.R. Tolkien">J.R.R. Tolkien</option>
            </select><br>

            <label for="category-select">Select a category:</label>
            <select id="category-select" name="categoryName">
                <!-- Option for all categories -->
                <option value="">All Categories</option>
                <!-- Unique categories from your data -->
                <option value="Science Fiction" selected="selected">Science Fiction</option>
                <option value="Science">Science</option>
                <option value="Mystery">Mystery</option>
                <option value="History">History</option>
                <option value="Classic">Classic</option>
                <option value="Dystopian">Dystopian</option>
                <option value="Adventure">Adventure</option>
                <option value="Historical Fiction">Historical Fiction</option>
                <option value="Psychological Fiction">Psychological Fiction</option>
                <option value="Romance">Romance</option>
                <option value="Fantasy">Fantasy</option>
            </select><br>
            
            <label>Quantity:</label>
            <input type="number" name="quantity" min="1" required><br>
            <label>Status:</label>
            <select name="status">
                <option value="Available">Available</option>
                <option value="Borrowed">Borrowed</option>
            </select><br>
             
            <label>description:</label>
             <textarea id="description" name="description" placeholder="Enter details..."></textarea>
            
             <label>Upload Image:</label>
             <input type="text" name="image"><br>
   
                   
             
             
            <input type="submit" name="action" value="insert">
        </form>
  

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) {%>
        <p style="color: red;"><%= error%></p>
        <% }%>

        <a href="MainController?action=listbook">Quay lại</a>

    </body>
</html>
