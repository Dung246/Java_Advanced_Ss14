package safe_tranfer;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String fromId = "ACC01";
        String toId = "ACC02";
        double amount = 1000;
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            String checkSql = "SELECT Balance FROM Accounts WHERE AccountId = ?";
            double balance = 0;
            try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                ps.setString(1, fromId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    balance = rs.getDouble("Balance");
                } else {
                    throw new Exception("Không tồn tại tài khoản này");
                }
            }
            if (balance < amount) {
                throw new Exception("Số dư không đủ");
            }
            String callSql = "{CALL sp_UpdateBalance(?, ?)}";
            try (CallableStatement cs = conn.prepareCall(callSql)) {
                cs.setString( 1, fromId);
                cs.setDouble(2, -amount);
                cs.execute();

                cs.setString(1, toId);
                cs.setDouble(2, amount);
                cs.execute();
            }
            conn.commit();
            System.out.println("Chuyển khoản thành công");
            String resultSql = "SELECT * FROM Accounts WHERE AccountId IN (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(resultSql)) {
                ps.setString(1, fromId);
                ps.setString(2, toId);
                ResultSet rs = ps.executeQuery();
                System.out.println("KẾT QUẢ");
                while (rs.next()) {
                    System.out.println(rs.getString("AccountId") + " | " + rs.getString("FullName") + " | " + rs.getDouble("Balance"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi hệ thống");
            try (Connection conn = DBConnection.getConnection()) {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}