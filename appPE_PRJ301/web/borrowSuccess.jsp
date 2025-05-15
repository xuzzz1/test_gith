<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
     <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            padding: 50px;
        }

        .container {
            background: white;
            width: 50%;
            margin: auto;
            padding: 20px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        h1 {
            color: #E65100;
        }

        p {
            font-size: 18px;
            color: #333;
        }

        a {
            display: inline-block;
            font-size: 16px;
            font-weight: bold;
            background-color: #007bff;
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 5px;
            margin: 10px;
            transition: 0.3s;
        }

        a:hover {
            background-color: #E65100;
        }
    </style>
</head>
<body>
    <h1>Borrow succesful</h1>
    <p>Bạn đã mượn thành công cuốn sách có ID: ${param.id}.</p>
    <a href="MainController?action=details&id=${param.id}">Xem chi tiết sách</a>
    <br>
    <a href="MainController?action=listbook">Exit</a>
</body>
</html>
