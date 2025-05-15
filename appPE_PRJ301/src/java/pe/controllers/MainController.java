/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import pe.book.BookDAO;
import pe.book.BookDTO;
import pe.rate.RateDAO;
import pe.rate.RateDTO;
import pe.users.UserDAO;
import pe.users.UserDTO;

/**
 *
 * @author hd
 */
public class MainController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;
        BookDAO bookDAO = new BookDAO();
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }
        String sortCol = request.getParameter("colSort");

        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            String username = request.getParameter("user");
            String password = request.getParameter("password");

            if (action == null || action.equals("login")) {
                if (username != null && password != null) {
                    UserDAO dao = new UserDAO();
                    UserDTO user = dao.login(username, password);

                    if (user != null) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("usersession", user);
                        if ("AD".equals(user.getRole())) {
                            session.setAttribute("role", "admin"); // Nếu user là admin
                        } else {
                            session.setAttribute("role", "user"); // Nếu user là user bình thường
                        }
                        response.sendRedirect("MainController?action=listbook");

                        return;
                    } else {
                        request.setAttribute("error", "Username or password is incorrect");
                        //RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                        //rd.forward(request, response);
                    }
                }
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
            } // logout
         else if (action.equals("logout")) {
    HttpSession session = request.getSession(false);
    String message = "Logout successfully.";

    if (session != null) {
        UserDTO user = (UserDTO) session.getAttribute("usersession");
        if (user != null) {
            message = "Logout successfully, see you again "
                    + (user.getFirstName() != null && !user.getFirstName().isEmpty() 
                        ? user.getFirstName() 
                        : user.getUsername());
        }
        session.invalidate();
    }

    request.setAttribute("message", message);
    request.getRequestDispatcher("login.jsp").forward(request, response);
}



             //listbook====================================
            else if (action.equals("listbook")) {
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("usersession") == null) {
                    response.sendRedirect(LOGIN_PAGE);

                    return;
                }
                BookDAO dao = new BookDAO();
                // Xử lý tham số lọc danh mục

                if (sortCol == null || sortCol.trim().isEmpty()) {
                    sortCol = "BookID"; // Giá trị mặc định
                }
                List<BookDTO> list = dao.list(keyword, sortCol);

                request.setAttribute("bookList", list);
                request.setAttribute("keyword", keyword);

                RequestDispatcher rd = request.getRequestDispatcher("booklist.jsp");
                rd.forward(request, response);
                return;
            } //=============
            else if ("viewBookInfo".equals(action)) {
                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }

                BookDTO book = null;
                if (id != null) {
                    book = bookDAO.load(id);
                }
                RateDAO rateDAO = new RateDAO();
                // Hiển thị danh sách đánh giá
                List<RateDTO> rates = rateDAO.load(book.getId());
                request.setAttribute("rates", rates);
                request.setAttribute("book", book);
                RequestDispatcher rd = request.getRequestDispatcher("bookinfo.jsp");
                rd.forward(request, response);
            } //============================================================
            else if ("viewProfile".equals(action)) {
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("usersession") == null) {
                    response.sendRedirect(LOGIN_PAGE);
                    return;
                }

                UserDTO user = (UserDTO) session.getAttribute("usersession");
                request.setAttribute("userProfile", user);
                RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
                rd.forward(request, response);
            } //==============================================
            else if ("resetPassword".equals(action)) {
                HttpSession session = request.getSession();
                UserDTO user = (UserDTO) session.getAttribute("usersession");

                if (user == null) {
                    System.out.println("Session không có dữ liệu user!");
                    request.setAttribute("resetError", "Bạn chưa đăng nhập!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }

                username = user.getUsername(); // Gán giá trị thay vì khai báo mới
                System.out.println("Username lấy từ session: " + username); // Debug

                String newPassword = request.getParameter("password");
                String confirmPassword = request.getParameter("confirm_password");

                if (newPassword == null || confirmPassword == null) {
                    request.setAttribute("resetError", "Vui lòng nhập đầy đủ thông tin!");
                    request.getRequestDispatcher("reset-password.jsp").forward(request, response);
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    request.setAttribute("resetError", "Mật khẩu xác nhận không khớp!");
                    request.getRequestDispatcher("reset-password.jsp").forward(request, response);
                    return;
                }

                UserDAO userDAO = new UserDAO();
                boolean success = false;
                try {
                    success = userDAO.updatePassword(username, newPassword);
                    System.out.println("Kết quả cập nhật mật khẩu: " + success); // Debug
                } catch (Exception e) {
                    System.out.println("Lỗi hệ thống: " + e.getMessage());
                    request.setAttribute("resetError", "Lỗi hệ thống: " + e.getMessage());
                    request.getRequestDispatcher("reset-password.jsp").forward(request, response);
                    return;
                }

                if (success) {
                    session.invalidate(); // Đăng xuất sau khi đổi mật khẩu
                    response.sendRedirect("login.jsp?message=Đổi mật khẩu thành công!");
                } else {
                    request.setAttribute("resetError", "Không tìm thấy tài khoản hoặc lỗi hệ thống!");
                    request.getRequestDispatcher("reset-password.jsp").forward(request, response);
                }
            } // ========== Cập nhật hồ sơ cá nhân ==========
            else if ("updateProfile".equals(action)) {
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("usersession") == null) {
                    response.sendRedirect(LOGIN_PAGE);
                    return;
                }

                UserDTO user = (UserDTO) session.getAttribute("usersession");
                String lastName = request.getParameter("lastName");
                String firstName = request.getParameter("firstName");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String gender = request.getParameter("gender");
                String studentID = request.getParameter("studentID");

                try {
                    UserDAO dao = new UserDAO();
                    boolean updated = dao.updateProfile(user.getUsername(), firstName, lastName, phone, email, gender, studentID);

                    if (updated) {
                        // Cập nhật lại session với thông tin mới (thiếu)
                        user.setFirstName(firstName);
                        user.setLastName(lastName);
                        user.setPhoneNumber(phone);
                        user.setEmail(email);
                        user.setGender(gender);
                        user.setStudentID(studentID);
                        session.setAttribute("usersession", user);
                        request.setAttribute("message", "Cập nhật hồ sơ thành công!");
                    } else {
                        request.setAttribute("error", "Cập nhật không thành công. Vui lòng thử lại!");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("error", "Lỗi khi cập nhật hồ sơ!");
                }

                RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
                rd.forward(request, response);
            } // details
            else if (action.equals("details")) {
                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }

                BookDTO book = null;
                if (id != null) {
                    book = bookDAO.load(id);

                }

                request.setAttribute("object", book);
                RequestDispatcher rd = request.getRequestDispatcher("bookdetails.jsp");
                rd.forward(request, response);

            } // edit
            else if (action.equals("edit")) {
                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    request.setAttribute("error", "Invalid ID format.");
                    request.getRequestDispatcher("bookedit.jsp").forward(request, response);
                    return;
                }

                BookDTO book = bookDAO.load(id);

                if (book == null) {
                    request.setAttribute("error", "Book not found.");
                    request.getRequestDispatcher("booklist.jsp").forward(request, response);
                    return;
                }

                List<BookDTO> bookList = bookDAO.getAllBooks();
                System.out.println("Số sách lấy được: " + bookList.size()); // 📌 Debug dữ liệu

                request.setAttribute("bookList", bookList);
                request.setAttribute("object", book);
                request.setAttribute("nextaction", "update");
                request.getRequestDispatcher("bookedit.jsp").forward(request, response);
            } // tạo create
            else if (action.equals("create")) {
                BookDTO book = new BookDTO();

                int randomId = new Random().nextInt(100);
                book.setId(randomId);

                request.setAttribute("object", book);
                request.setAttribute("nextaction", "insert");
                RequestDispatcher rd = request.getRequestDispatcher("bookcreate.jsp");
                rd.forward(request, response);
            } //update
            else if (action.equals("update")) {
                Integer id;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    request.setAttribute("error", "Invalid ID format.");
                    request.getRequestDispatcher("bookedit.jsp").forward(request, response);
                    return;
                }

                String title = request.getParameter("title");
                int quantity = 0;
                String status = request.getParameter("status");
                String authorName = request.getParameter("authorName");
                String categoryName = request.getParameter("categoryName");
                String description = request.getParameter("description");
                String imageUrl =request.getParameter("image");
                try {
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                } catch (NumberFormatException ex) {
                    log("Parameter quantity has wrong format.");
                }
                try {
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                    if (quantity < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    request.setAttribute("error", "Invalid quantity format.");
                    request.getRequestDispatcher("bookedit.jsp").forward(request, response);
                    return;
                }
                BookDTO book = bookDAO.load(id);
                if (book == null) {
                    request.setAttribute("error", "book not found.");
                    request.getRequestDispatcher("bookedit.jsp").forward(request, response);
                    return;
                }
                book.setTitle(title);
                book.setQuantity(quantity);
                book.setStatus(status);
                book.setAuthorName(authorName);
                book.setCategoryName(categoryName);
                book.setDescription(description);
                book.setImageUrl(imageUrl);
                if (!bookDAO.update(book)) {
                    request.setAttribute("error", "Update failed. Please try again.");
                    request.setAttribute("object", book);
                    request.getRequestDispatcher("bookedit.jsp").forward(request, response);
                    return;
                }

                book = bookDAO.load(id);
                if (book == null) {
                    request.setAttribute("error", "Error reloading book details after update.");
                    request.getRequestDispatcher("bookedit.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("object", book);
                request.getRequestDispatcher("bookdetails.jsp").forward(request, response);
            } //insert
            else if (action.equals("insert")) {

                String title = request.getParameter("title");
                int quantity = 0;
                String status = request.getParameter("status");
                String authorName = request.getParameter("authorName");
                String categoryName = request.getParameter("categoryName");
                String description = request.getParameter("description");
                String image = request.getParameter("image");
                try {
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                } catch (NumberFormatException ex) {
                    log("Parameter quantity has wrong format.");
                }

                if (title == null || title.trim().isEmpty() || status == null || status.trim().isEmpty()) {
                    request.setAttribute("error", "All fields are required.");
                    request.getRequestDispatcher("bookedit.jsp").forward(request, response);
                    return;
                }

                try {
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                    if (quantity < 0) {  // Kiểm tra số lượng không âm
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    request.setAttribute("error", "Invalid quantity format.");
                    request.getRequestDispatcher("bookedit.jsp").forward(request, response);
                    return;
                }

                BookDTO book = new BookDTO();

                book.setTitle(title.trim());
                book.setQuantity(quantity);
                book.setStatus(status.trim());
                book.setAuthorName(authorName);
                book.setCategoryName(categoryName);
                book.setDescription(description);
                book.setImageUrl(image);
                int id = bookDAO.insert(book);

                book.setId(id);

                request.setAttribute("object", book);
                RequestDispatcher rd = request.getRequestDispatcher("bookdetails.jsp");
                rd.forward(request, response);
            } // delete
            else if (action.equals("delete")) {
                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                    request.setAttribute("error", "Invalid ID format");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }

                bookDAO.delete(id);

                List<BookDTO> list = bookDAO.list(keyword, sortCol);

                request.setAttribute("bookList", list);
                request.getRequestDispatcher("/booklist.jsp").forward(request, response);

            }
            // delete    user
           else if (action.equals("deleteUser")) {
    Integer id = null;
    try {
        id = Integer.parseInt(request.getParameter("id"));
    } catch (NumberFormatException ex) {
        log("Invalid User ID format.");
        request.setAttribute("error", "Invalid ID format");
        request.getRequestDispatcher("error.jsp").forward(request, response);
        return;
    }

    UserDAO dao = new UserDAO();
    try {
        dao.deleteUser(id); // Xóa user

        // ✅ Sau khi xóa, chuyển hướng lại manageUser (để tránh F5 bị xóa lại)
        response.sendRedirect("MainController?action=manageUser");

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "Error deleting user: " + e.getMessage());
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }
}





//add  
            else if ("addToShelf".equals(action)) {
                HttpSession session = request.getSession();
                List<BookDTO> shelf = (List<BookDTO>) session.getAttribute("shelf");

                if (shelf == null) {
                    shelf = new ArrayList<>();
                }

                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Invalid coupon ID format.");
                }

                if (id != null) {

                    BookDTO book = bookDAO.load(id);

                    if (book != null && !shelf.contains(book)) {
                        shelf.add(book);
                    }
                }

                session.setAttribute("shelf", shelf);
                response.sendRedirect("MainController?action=viewShelf");
            } else if ("viewShelf".equals(action)) {
                HttpSession session = request.getSession();
                List<BookDTO> shelf = (List<BookDTO>) session.getAttribute("shelf");

                if (shelf == null) {
                    shelf = new ArrayList<>();
                    session.setAttribute("shelf", shelf);
                }

                request.setAttribute("shelfList", shelf);
                request.getRequestDispatcher("shelflist.jsp").forward(request, response);
            } // Xóa sách khỏi giỏ hàng
            else if ("removeFromShelf".equals(action)) {
                HttpSession session = request.getSession();
                List<BookDTO> shelf = (List<BookDTO>) session.getAttribute("shelf");
                String idStr = request.getParameter("id");

                if (shelf != null && idStr != null) {
                    try {
                        int id = Integer.parseInt(idStr);
                        shelf.removeIf(book -> book.getId() == id);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format: " + idStr);
                    }
                }

                session.setAttribute("shelf", shelf);
                response.sendRedirect("MainController?action=viewShelf");
            } // save
            else if (action.equals("save")) {
                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    request.setAttribute("error", "Invalid ID format.");
                    request.getRequestDispatcher("bookedit.jsp").forward(request, response);
                    return;
                }

                String title = request.getParameter("title");
                String authorname = request.getParameter("authorname");  // Lấy giá trị tác giả
                String categoryname = request.getParameter("categoryname"); // Lấy giá trị thể loại
                int quantity;
                try {
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                } catch (NumberFormatException ex) {
                    request.setAttribute("error", "Quantity must be a number.");
                    request.getRequestDispatcher("bookedit.jsp").forward(request, response);
                    return;
                }
                String status = request.getParameter("status");

                // Kiểm tra xem sách có tồn tại không
                BookDTO book = bookDAO.load(id);
                if (book == null) {
                    request.setAttribute("error", "Book not found.");
                    request.getRequestDispatcher("booklist.jsp").forward(request, response);
                    return;
                }

                // Cập nhật thông tin sách
                book.setTitle(title);
                book.setAuthorName(authorname);  // Cập nhật tác giả
                book.setCategoryName(categoryname); // Cập nhật thể loại
                book.setQuantity(quantity);
                book.setStatus(status);

                boolean isUpdated = bookDAO.update(book);

                if (!isUpdated) {
                    request.setAttribute("error", "Update failed. Please try again.");
                    request.getRequestDispatcher("bookedit.jsp").forward(request, response);
                } else {
                    response.sendRedirect("MainController?action=details&id=" + id); // Chuyển về trang chi tiết sách
                }
            } //==========================
            else if (action.equals("borrow")) {
                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    request.setAttribute("error", "Invalid book ID.");
                    request.getRequestDispatcher("booklist.jsp").forward(request, response);
                    return;
                }

                // Kiểm tra sách có tồn tại không
                BookDTO book = bookDAO.load(id);  // 🔥 Kiểm tra load sách có trả về null không
                if (book == null) {
                    request.setAttribute("error", "Không tìm thấy thông tin sách.");
                    request.getRequestDispatcher("booklist.jsp").forward(request, response);
                    return;
                }

                // Cập nhật trạng thái sách thành "Borrowed"
                book.setStatus("Borrowed");
                boolean isUpdated = bookDAO.update(book);

                if (!isUpdated) {
                    request.setAttribute("error", "Mượn sách thất bại.");
                    request.getRequestDispatcher("bookinfo.jsp").forward(request, response);
                } else {
                    response.sendRedirect("borrowSuccess.jsp?id=" + id);
                }
            } else if (action.equals("Filter")) {
                String categories = request.getParameter("categories");

                // Kiểm tra sách có tồn tại không
                List<BookDTO> books = bookDAO.filter(categories);  // 🔥 Kiểm tra load sách có trả về null không
                if (books != null) {
                    request.setAttribute("bookList", books);
                    request.getRequestDispatcher("booklist.jsp").forward(request, response);
                    return;
                }
            } //Rate book
            else if ("RateBook".equals(action)) {
                String bookIdStr = request.getParameter("bookId");
                String rateStarStr = request.getParameter("rateStar");
                String comment = request.getParameter("comment");
                UserDTO user = (UserDTO) request.getSession().getAttribute("usersession");
                System.out.println("RateBook HERE");
                if (bookIdStr != null && rateStarStr != null) {
                    int bookId = Integer.parseInt(bookIdStr);
                    int rateStar = Integer.parseInt(rateStarStr);
                    int userId = user.getUserID(); // Lấy userId từ session

                    RateDAO rateDAO = new RateDAO();
                    RateDTO rate = new RateDTO(1, userId, bookId, "aqua", rateStar, comment);
                    rateDAO.insert(rate);

                    // Load lại danh sách đánh giá
                    
                }

                // Trả về danh sách đánh giá mới (chỉ phần review, không phải toàn bộ trang)
                request.getRequestDispatcher("MainController?action=viewBookInfo&id=" + bookIdStr).forward(request, response);
            }
           else if ("manage".equals(action)) {
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("usersession") == null) {
        response.sendRedirect(LOGIN_PAGE);
        return;
    }

    // Kiểm tra quyền Admin
    UserDTO user = (UserDTO) session.getAttribute("usersession");
    if (user != null && "AD".equals(user.getRole())) {
        BookDAO dao = new BookDAO();
        List<BookDTO> list = dao.list("", "BookID"); // Lấy toàn bộ sách, có thể thêm sort, keyword nếu muốn
        request.setAttribute("bookList", list);

        RequestDispatcher rd = request.getRequestDispatcher("manage.jsp");
        rd.forward(request, response);
        return;
    } else {
        // Nếu không phải Admin → có thể chuyển về home hoặc báo lỗi
        response.sendRedirect("booklist.jsp");
        return;
    }
            }
    else if (action.equals("manageUser")) {
    try {
        UserDAO dao = new UserDAO();
        List<UserDTO> list = dao.getAllUsers(); // lấy danh sách user từ DB
        request.setAttribute("userList", list);
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("ERROR", "Error loading user list: " + e.getMessage());
    }
    request.getRequestDispatcher("manage_user.jsp").forward(request, response);
}

           

        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
            e.printStackTrace();
        } finally {
            if (!response.isCommitted() && url != null) {
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            String username = request.getParameter("user");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm_password");

            UserDAO userDAO = new UserDAO();

            try {
                // Kiểm tra username đã tồn tại
                if (userDAO.checkUserExists(username)) {
                    request.setAttribute("registerError", "Username already exists.");
                    request.setAttribute("showRegister", true);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Kiểm tra xác nhận mật khẩu
            if (!password.equals(confirmPassword)) {
                request.setAttribute("registerError", "Passwords do not match.");
                request.setAttribute("showRegister", true); // Giữ tab đăng ký
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            // Đăng ký tài khoản mới
            UserDTO newUser = new UserDTO(username, password);
            try {
                if (userDAO.register(newUser)) {
                    request.setAttribute("successMessage", "Registration successful. Please login.");
                } else {
                    request.setAttribute("registerError", "Registration failed. Try again.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            processRequest(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
