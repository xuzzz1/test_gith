<%@page import="pe.rate.RateDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="pe.book.BookDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Thông tin Sách</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bookinfo.css">

    </head>
    <body>
        <%@ include file="/menu.jsp" %>
        <div class="container">
            <% BookDTO book = (BookDTO) request.getAttribute("book"); %>
            <% if (book != null) {%>
            <div class="content-wrapper">
                <div class="left-section">
                    <!-- Hình ảnh sản phẩm -->
                    <div class="product-images">
                        <img src="<%= request.getContextPath()%>/img/<%= book.getImageUrl()%>" 
                             alt="<%= book.getTitle()%>">
                    </div>
                    <!-- Tên sách dưới hình ảnh -->
                    <h2 class="book-title">
                        <% if ("admin".equals(session.getAttribute("role"))) {%>
                        <a href="MainController?action=details&id=<%= book.getId()%>">
                            <%= book.getTitle()%>
                        </a>
                        <% } else {%>
                        <%= book.getTitle()%>
                        <% }%>
                    </h2>

                    <!-- Nút Thêm vào giỏ hàng và Mượn ngay -->
                    <div class="button-group">
                        <form action="MainController" method="POST">
                            <input type="hidden" name="action" value="addToShelf">
                            <input type="hidden" name="id" value="<%= book.getId()%>">
                            <button type="submit" class="add-to-cart">Add to cart</button>
                        </form>

                        <form action="MainController" method="POST"> <!-- Thêm form cho nút Mượn -->
                            <input type="hidden" name="action" value="borrow">
                            <input type="hidden" name="id" value="<%= book.getId()%>">
                            <button type="submit" class="borrow-now">Borrow now</button>
                        </form>
                    </div>

                </div>
                <div class="right-section">
                    <p><strong>Author:</strong> <%= book.getAuthorName()%></p>
                    <p><strong>Category:</strong> <%= book.getCategoryName()%></p>
                    <p><strong>Quantity:</strong> <%= book.getQuantity()%></p>

                    <!-- Mô tả sản phẩm -->
                    <div class="description">
                        <h3>Product description:</h3>
                        <p><%= book.getDescription()%></p> <!-- Hiển thị mô tả sách -->
                    </div>
                </div>

            </div>

            <!-- Form đánh giá -->
            <div class="review-section">
                <h3>Your review</h3>
                <form action="MainController" method="POST">
                    <input type="hidden" name="action" value="RateBook"> <!-- Thêm action -->
                    <input type="hidden" name="bookId" value="<%= book.getId()%>">

                    <!-- Chọn sao -->
                    <div class="star-rating">
                        <input type="radio" id="star5" name="rateStar" value="5"><label for="star5">&#9733;</label>
                        <input type="radio" id="star4" name="rateStar" value="4"><label for="star4">&#9733;</label>
                        <input type="radio" id="star3" name="rateStar" value="3"><label for="star3">&#9733;</label>
                        <input type="radio" id="star2" name="rateStar" value="2"><label for="star2">&#9733;</label>
                        <input type="radio" id="star1" name="rateStar" value="1"><label for="star1">&#9733;</label>
                    </div>

                    <!-- Ô nhập bình luận -->
                    <div class="comment-input">
                        <textarea name="comment" id="comment" placeholder="Nhập đánh giá của bạn..."></textarea>
                    </div>

                    <!-- Nút gửi -->
                    <button class="submit-review" type="submit">Send review</button>
                </form>
            </div>

            <!-- Danh sách đánh giá -->
            <div class="review-list">
                <h3>Review before</h3>
                <% List<RateDTO> rates = (List<RateDTO>) request.getAttribute("rates"); %>
                <% if (rates != null && !rates.isEmpty()) { %>
                <% for (RateDTO rate : rates) {%>
                <div class="review-item">
                    <p><strong><%= rate.getUsername()%></strong> - 
                        <span class="stars">
                            <% for (int i = 1; i <= 5; i++) { %>
                            <% if (i <= rate.getRateStar()) { %>
                            <span style="color: gold;">&#9733;</span> <!-- Ngôi sao đầy -->
                            <% } else { %>
                            <span style="color: lightgray;">&#9733;</span> <!-- Ngôi sao trống -->
                            <% } %>
                            <% }%>
                        </span>
                    </p>
                    <p><%= rate.getComment()%></p>
                </div>
                <% } %>
                <% } else { %>
                <p>No review</p>
                <% } %>
            </div>

            <% } else { %>
            <p style="color: red; text-align: center;">Book information not found.</p>
            <% }%>
        </div>
    </body>
</html>
