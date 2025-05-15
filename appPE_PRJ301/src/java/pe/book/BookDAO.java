/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import pe.utils.DBUtils;

/**
 *
 * @author DUNGHUYNH
 */
public class BookDAO {

    public List<BookDTO> list(String keyword, String sortCol) throws ClassNotFoundException {
        List<BookDTO> list = new ArrayList<>();

        String sql = "SELECT BookID, Title, AuthorName, CategoryName, Quantity, Status, ImageUrl, Description FROM Book WHERE 1=1";

        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND (Title LIKE ? OR AuthorName LIKE ?)";
        }

        if (sortCol != null && !sortCol.isEmpty()) {
            sql += " ORDER BY " + sortCol + " ASC";
        }

        try (Connection con = DBUtils.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            if (keyword != null && !keyword.isEmpty()) {
                stmt.setString(1, "%" + keyword + "%");
                stmt.setString(2, "%" + keyword + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("BookID");
                    String title = rs.getString("Title");
                    String authorName = rs.getString("AuthorName");
                    String categoryName = rs.getString("CategoryName");
                    int quantity = rs.getInt("Quantity");
                    String status = rs.getString("Status");
                    String imageUrl = rs.getString("ImageUrl");
                    String description = rs.getString("Description");

                    BookDTO book = new BookDTO(id, title, authorName, categoryName, quantity, status, imageUrl, description);
                    list.add(book);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in DAO. Details: " + ex.getMessage());
            ex.printStackTrace();
        }

        return list;
    }

    public boolean insertBook(BookDTO book) throws ClassNotFoundException {
        String sql = "INSERT INTO Book (Title, AuthorID, CategoryID, Quantity, Status, ImageUrl) VALUES (?, "
                + "(SELECT AuthorID FROM Author WHERE AuthorName = ?), "
                + "(SELECT CategoryID FROM Category WHERE CategoryName = ?), ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
ps.setString(2, book.getAuthorName());  // ✅ Đặt giá trị cho AuthorName
    ps.setString(3, book.getCategoryName());// ✅ Đặt giá trị cho CategoryName
            ps.setInt(4, book.getQuantity());
            ps.setString(5, book.getStatus());
            ps.setString(6, book.getImageUrl());

            return ps.executeUpdate() > 0;  // ✅ Insert thành công trả về true
        } catch (SQLException e) {
            System.out.println("❌ Insert failed: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /*
    Load student with id
     */
    public BookDTO load(int id) throws ClassNotFoundException {
        String sql = "SELECT BookID, Title, AuthorName, CategoryName, Quantity, Status, ImageUrl, Description FROM Book WHERE BookID = ?";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString("Title");
                    String authorName = rs.getString("AuthorName");
                    String categoryName = rs.getString("CategoryName");
                    int quantity = rs.getInt("Quantity");
                    String status = rs.getString("Status");
                    String imageUrl = rs.getString("ImageUrl");
                    String description = rs.getString("Description");

                    return new BookDTO(id, title, authorName, categoryName, quantity, status, imageUrl, description);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Query Book error! " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean update(BookDTO book) throws ClassNotFoundException {
        String sqlUpdate = "UPDATE Book SET Title = ?, AuthorName = ?, CategoryName = ?, Quantity = ?, Status = ?, ImageUrl = ?, Description = ? WHERE BookID = ?";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {

            // ✅ Cập nhật thông tin sách
            psUpdate.setString(1, book.getTitle());
            psUpdate.setString(2, book.getAuthorName());
            psUpdate.setString(3, book.getCategoryName());
            psUpdate.setInt(4, book.getQuantity());
            psUpdate.setString(5, book.getStatus());
            psUpdate.setString(6, book.getImageUrl());
            psUpdate.setString(7, book.getDescription());
            psUpdate.setInt(8, book.getId());

            int rowsUpdated = psUpdate.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("❌ Update book error! " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Integer insert(BookDTO book) throws ClassNotFoundException {
        String sql = "INSERT INTO Book (Title, AuthorName, CategoryName, Quantity, Status, ImageUrl, Description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, book.getTitle());        // Tiêu đề sách
            ps.setString(2, book.getAuthorName());   // Lưu trực tiếp tên tác giả
            ps.setString(3, book.getCategoryName()); // Lưu trực tiếp tên danh mục
            ps.setInt(4, book.getQuantity());        // Số lượng sách
            ps.setString(5, book.getStatus());       // Trạng thái sách
            ps.setString(6, book.getImageUrl());     // Đường dẫn ảnh
            ps.setString(7, book.getDescription());  // Mô tả sách

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Lấy BookID vừa chèn vào
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("❌ Insert book error! " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean delete(Integer id) throws ClassNotFoundException {
        String sql = "DELETE FROM Book WHERE BookID = ? ";

        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Delete Book error!" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public List<BookDTO> getAllBooks() throws ClassNotFoundException {
        List<BookDTO> bookList = new ArrayList<>();
        String sql = "SELECT * FROM Book";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BookDTO book = new BookDTO();
                book.setId(rs.getInt("BookID"));      // ✅ Lấy BookID
                book.setTitle(rs.getString("Title")); // ✅ Lấy Title
                book.setQuantity(rs.getInt("Quantity")); // ✅ Lấy Quantity
                book.setStatus(rs.getString("Status")); // ✅ Lấy Status
                book.setImageUrl(rs.getString("ImageUrl")); // ✅ Lấy ImageUrl
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

      public List<BookDTO> filter(String categories) throws ClassNotFoundException {
        List<BookDTO> bookList = new ArrayList<>();
        String sql = "SELECT * FROM Book WHERE CategoryName = ?";

        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, categories);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookDTO book = new BookDTO();
                book.setId(rs.getInt("BookID"));      // ✅ Lấy BookID
                book.setTitle(rs.getString("Title")); // ✅ Lấy Title
                book.setQuantity(rs.getInt("Quantity")); // ✅ Lấy Quantity
                book.setStatus(rs.getString("Status")); // ✅ Lấy Status
                book.setImageUrl(rs.getString("ImageUrl")); // ✅ Lấy ImageUrl
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }
 

}
