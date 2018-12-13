import java.sql.*;
public class SimpleDataSource {
	private static String url = "jdbc:sqlserver://localhost:1433; database=BasketballTraining; integratedSecurity=true";
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url);
	}
}
