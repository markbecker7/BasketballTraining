import java.sql.*;
public class DataConnection {
	
	private Connection connection;
	
	public DataConnection() throws SQLException {
		connection = SimpleDataSource.getConnection();
	}
	
	public ResultSet runQuery(String query, String... arguments) throws SQLException 
	{  // Get data from the database – use for SELECT.

		if (connection == null)
			connection = SimpleDataSource.getConnection();

		PreparedStatement statement = connection.prepareStatement(query);
		setArguments(statement, arguments);
		ResultSet result = statement.executeQuery();

		return result;
	}
	
	private void setArguments(PreparedStatement statement, String... arguments)
	          throws SQLException 
	{
		for (int count = 1; count < arguments.length+1; count++)
			statement.setString(count, arguments[count-1]);
	}
	
	public void updateData(String query, String... arguments) 					throws SQLException
	{
		if (connection == null)
			connection = SimpleDataSource.getConnection();
	
		PreparedStatement statement = 	connection.prepareStatement(query);
		setArguments(statement, arguments);
	
		statement.execute();
		statement.close();
	}
	
	public void closeConnection() throws SQLException {
		connection.close();
	}



}
