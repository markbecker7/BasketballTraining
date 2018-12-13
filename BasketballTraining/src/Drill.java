import java.util.*;
import java.sql.*;

public class Drill {
	private static DataConnection connection;
	private int drillID;
	private String drillInstruction;
	private int drillDifficulty;
	private String categoryName;
	
	//Table and Column Names
	public static final String drillTableName = "Drill";
	public static final String drillIDColumn = "Drill_ID";
	public static final String drillDifficultyColumn = "Drill_Difficulty";
	private static final String drillInstructionColumn = "Drill_Instruction";
	private static final String categoryNameColumn = "Category_Name";
	
	public void openConnection() throws SQLException {
		connection = new DataConnection();
	}
	
	public void closeConnection() throws SQLException {
		connection.closeConnection();
	}
	
	//Boolean states whether the connection to the Drill table should be closed after displaying.
	
	public void displayDrillTable(boolean closeConnection) throws SQLException {
		System.out.println("Here is the list of Drills:");
		String query = "SELECT* FROM " + drillTableName;
		ResultSet result = connection.runQuery(query);
		
		while(result.next()) {
			int tempDrillID = result.getInt(drillIDColumn);
			String tempInstruction = result.getString(drillInstructionColumn);
			int tempDrillDifficulty = result.getInt(drillDifficultyColumn);
			String tempCategory = result.getString(categoryNameColumn);
			System.out.print("DrillID:" + tempDrillID + "  Drill Category:" + tempCategory +
								"     Drill difficulty:" + tempDrillDifficulty + "   Drill Instruction:");
			System.out.println(tempInstruction);
		}
		
		System.out.println("");
		if(closeConnection == true) {
			connection.closeConnection();
		}
	}
	
	public void addDrill() throws SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Drill Instruction: ");
		drillInstruction = scan.nextLine();
		System.out.println("Enter the Category of the drill (Shooting, Passing, Slashing, "
				+ "Dribbling, Defense, or Conditioning): ");
		categoryName = scan.nextLine();
		System.out.println("Enter Drill Difficulty: ");
		drillDifficulty = scan.nextInt();
		System.out.println("Enter Drill ID Number");
		drillID = scan.nextInt();
		
		String query = "INSERT INTO " + drillTableName + "(" + drillIDColumn + "," +
						drillInstructionColumn + "," + drillDifficultyColumn + "," +
						categoryNameColumn + ")";
		query += "VALUES(" + drillID + ", ?," + drillDifficulty + ", ?)";
		
		connection.updateData(query, drillInstruction, categoryName);
		scan.close();
		
	}
	
	public void deleteDrill() throws SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the Drill ID number of the drill you want to delete: ");
		drillID = scan.nextInt();
		
		String query = "DELETE FROM " + drillTableName + "WHERE " + drillIDColumn + "="
				+ drillID;
		
		connection.updateData(query);
		scan.close();
	}
}
