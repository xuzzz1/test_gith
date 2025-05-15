package pe.rate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.utils.DBUtils;

public class RateDAO {
    
    // Thêm đánh giá mới
    public boolean insert(RateDTO rate) throws ClassNotFoundException {
        String sql = "INSERT INTO Rate (UserID, BookID, RateStar, Comment) VALUES (?, ?, ?, ?)";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, rate.getUserId());
            stmt.setInt(2, rate.getBookId());
            stmt.setInt(3, rate.getRateStar());
            stmt.setString(4, rate.getComment());

            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách đánh giá theo BookID
    public List<RateDTO> load(int bookId) throws ClassNotFoundException {
        List<RateDTO> rates = new ArrayList<>();
        String sql = "SELECT r.RateID, r.UserID, r.BookID, u.username, r.RateStar, r.Comment " +
                     "FROM Rate r JOIN Users u ON r.UserID = u.UserID " +
                     "WHERE r.BookID = ?";

        try (Connection con = DBUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RateDTO rate = new RateDTO(
                    rs.getInt("RateID"),
                    rs.getInt("UserID"),
                    rs.getInt("BookID"),
                    rs.getString("username"),
                    rs.getInt("RateStar"),
                    rs.getString("Comment")
                );
                rates.add(rate);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rates;
    }

    // Cập nhật đánh giá
    public boolean update(RateDTO rate) throws ClassNotFoundException {
        String sql = "UPDATE Rate SET RateStar = ?, Comment = ? WHERE RateID = ?";

        try (Connection con = DBUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, rate.getRateStar());
            stmt.setString(2, rate.getComment());
            stmt.setInt(3, rate.getRateId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update rate error! " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Xóa đánh giá
    public boolean delete(Integer rateId) throws ClassNotFoundException {
        String sql = "DELETE FROM Rate WHERE RateID = ?";

        try (Connection con = DBUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, rateId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Delete rate error! " + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }
}

