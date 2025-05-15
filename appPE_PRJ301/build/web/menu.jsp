<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Library Header</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/menu.css">
    </head>
    <body>

        <div class="header">
            <div class="logo"> 
                <a href="MainController?action=listbook">
                    <img src="img/Untitled-1.jpg" alt="Library Logo">
                </a>
            </div>


            <!-- Thanh tìm ki?m n?m gi?a -->
            <form action="MainController" method="GET" class="search-form">
                <input type="hidden" name="action" value="listbook">
                <input type="text" name="keyword" placeholder="Search books..." 
                       value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : ""%>">
                <button type="submit">search</button>
            </form>

            <div class="menu">

                <a href="MainController?action=viewNotifications">
                    <i class="fas fa-bell"></i> Notifications
                </a>

                <a href="MainController?action=viewShelf">
                    <i class="fas fa-shopping-cart"></i> Cart
                </a>

                <a href="MainController?action=viewProfile">
                    <i class="fas fa-user"></i> Profile
                </a>

            </div>
        </div>

    </body>
</html>

<!--a href="login.jsp">Login</a>
             <a href="MainController?action=logout">Logout</a-->