package pe.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import pe.utils.DBUtils;

public class UserDAO {

    public UserDTO login(String username, String password) throws ClassNotFoundException {
        UserDTO user = null;
        String sql = "SELECT UserID, FirstName, LastName, PhoneNumber, Username, Email, Gender, StudentID, Role FROM Users "
                + " WHERE Username = ? AND Password = ?";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            if (con != null) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        user = new UserDTO();
                        user.setUserID(rs.getInt("UserID"));
                        user.setFirstName(rs.getString("FirstName"));
                        user.setLastName(rs.getString("LastName"));
                        user.setPhoneNumber(rs.getString("PhoneNumber"));
                        user.setUsername(rs.getString("Username"));
                        user.setEmail(rs.getString("Email"));
                        user.setGender(rs.getString("Gender"));
                        user.setStudentID(rs.getString("StudentID"));
                        user.setRole(rs.getString("Role"));

                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in UserDAO: " + ex.getMessage());
            ex.printStackTrace();
        }
        return user;
    }

    public boolean checkUserExists(String username) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Users WHERE Username = ?";
        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Nếu COUNT(*) > 0 thì username đã tồn tại
                }
            }
        }
        return false;
    }

    public boolean register(UserDTO user) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Users (Username, Password, FirstName, LastName, Email, Gender, StudentID, PhoneNumber) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getGender());
            ps.setString(7, user.getStudentID());
            ps.setString(8, user.getPhoneNumber());
            return ps.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: Username '" + user.getUsername() + "' already exists.");
            return false;
        }
    }

    public boolean updateProfile(String username, String firstName, String lastName, String phone,
            String email, String gender, String studentID)
            throws SQLException, ClassNotFoundException {
        boolean updated = false;
        String sql = "UPDATE Users SET FirstName=?, LastName=?, Email=?, Gender=?, PhoneNumber=?, StudentID=? WHERE Username=?";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement stm = con.prepareStatement(sql)) {

            stm.setString(1, firstName);
            stm.setString(2, lastName);
            stm.setString(3, email);
            stm.setString(4, gender);
            stm.setString(5, phone);
            stm.setString(6, studentID);
            stm.setString(7, username); // Cột Username làm điều kiện WHERE

            updated = stm.executeUpdate() > 0;
        }
        return updated;

    }

    public boolean updatePassword(String username, String newPassword) throws SQLException, ClassNotFoundException {
        if (username == null || newPassword == null || newPassword.isEmpty()) {
            System.out.println("Lỗi: Username hoặc Password trống!");
            return false;
        }

        String sql = "UPDATE Users SET Password = ? WHERE Username = ?";
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword);
            stmt.setString(2, username);

            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Debug: Rows updated = " + rowsUpdated);  // Kiểm tra số dòng cập nhật

            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi SQL khi cập nhật mật khẩu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public UserDTO getUserByUsername(String username) throws SQLException, ClassNotFoundException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT FirstName, LastName, Email, PhoneNumber, Gender, StudentID "
                        + "FROM Users WHERE Username = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                rs = ps.executeQuery();
                if (rs.next()) {
                    user = new UserDTO();
                    user.setFirstName(rs.getString("FirstName"));
                    user.setLastName(rs.getString("LastName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhoneNumber(rs.getString("PhoneNumber"));
                    user.setGender(rs.getString("Gender"));
                    user.setStudentID(rs.getString("StudentID"));

                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return user;
    }

    public List<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT UserID, Username, FirstName, LastName, Email, PhoneNumber, Gender, StudentID, Role, Password FROM Users";
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUserID(rs.getInt("UserID"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setGender(rs.getString("Gender"));
                user.setStudentID(rs.getString("StudentID"));
                user.setPassword(rs.getString("Password"));
                user.setRole(rs.getString("Role"));
                list.add(user);
            }
        }
        return list;
    }
public boolean deleteUser(int id) throws SQLException, ClassNotFoundException {
    String sql = "DELETE FROM Users WHERE UserID = ?";
    try (Connection conn = DBUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }
}

}
