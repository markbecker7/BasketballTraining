import java.util.*;
import java.sql.*;
public class Workout {
	
	private static DataConnection connection;
	private int workoutID;
	private String workoutTitle;
	
	//Table and Column Names
	public final static String workoutTableName = "Workout";
	private final static String workoutContentTableName = "WorkoutContent";
	public final static String workoutIDColumn = "Workout_ID";
	public final static String workoutTitleColumn = "Workout_Title";
	
	public void openConnection() throws SQLException {
		connection = new DataConnection();
	}
	
	public void closeConnection() throws SQLException {
		connection.closeConnection();
	}
	
	//Boolean states whether the connection to the Workout table should be closed after displaying.
	
	public void displayWorkoutTable(boolean closeConnection) throws SQLException {
		System.out.println("Here is the list of workouts:");
		String query = "SELECT* FROM " + workoutTableName;
		ResultSet result = connection.runQuery(query);
		
		while(result.next()) {
			int tempWorkoutID = result.getInt(workoutIDColumn);
			System.out.print("WorkoutID:" + tempWorkoutID + "  Workout Title:");
			String tempWorkoutTitle = result.getString(workoutTitleColumn);
			System.out.println(tempWorkoutTitle);
		}
		
		System.out.println("");
		if(closeConnection == true) {
			connection.closeConnection();
		}
	}
	
	public void addWorkout() throws SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a workout title: ");
		workoutTitle = scan.nextLine();
		System.out.println("Enter a workout ID number: ");
		workoutID = scan.nextInt();
		
		String query = "INSERT INTO " + workoutTableName + "(" + workoutIDColumn + "," +
						workoutTitleColumn + ")";
		query += "VALUES(" + workoutID + ", ?)";
		
		connection.updateData(query, workoutTitle);
		scan.close();

	}
	
	public void addWorkoutDrill() throws SQLException {
		Scanner scan = new Scanner(System.in);
		Workout workout = new Workout();
		workout.displayWorkoutTable(false);
		System.out.println("What workout do you want to add a drill to? Please enter the workout ID: ");
		workoutID = scan.nextInt();
		
		Drill drill = new Drill();
		drill.openConnection();
		drill.displayDrillTable(false);
		System.out.println("What drill do you want to add to this workout? Enter the drill ID: ");
		int drillID = scan.nextInt();
		
		String query = "INSERT INTO " + workoutContentTableName;
		query += " VALUES(" + drillID + ", " + workoutID + ")";
		
		connection.updateData(query);
		drill.closeConnection();
		scan.close();
		
	}
	
	public void deleteWorkoutDrill() throws SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.println("What workout do you want to delete a drill from? Please enter the workout ID: ");
		workoutID = scan.nextInt();
		System.out.println("What drill do you want to delete from this workout? Enter the drill ID: ");
		int drillID = scan.nextInt();
		
		String query = "DELETE FROM " + workoutContentTableName;
		query += " WHERE " + Drill.drillIDColumn + "= " + drillID + " AND " + workoutIDColumn + 
				"= " + workoutID;
		
		connection.updateData(query);
		scan.close();
	}
	
	public void deleteWorkout() throws SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the Workout ID number of the workout you want to delete: ");
		workoutID = scan.nextInt();
		
		String query = "DELETE FROM " + workoutTableName + "WHERE " + workoutIDColumn + "="
				+ workoutID;
		
		connection.updateData(query);
		scan.close();
	}
	
	public int computeWorkoutDifficulty(String workoutID) throws SQLException {
		int workoutDifficulty = 0;
		
		String query1 = "SELECT SUM(D." + Drill.drillDifficultyColumn + ") AS WorkoutDifficulty FROM " +
						workoutTableName + " AS W INNER JOIN " + workoutContentTableName + " AS WC " +
						"ON W." + workoutIDColumn + " = WC." + workoutIDColumn + " AND W." + workoutIDColumn
						+ " = ? INNER JOIN " + Drill.drillTableName + " AS D ON WC." + Drill.drillIDColumn +
						" = D." + Drill.drillIDColumn + " GROUP BY W." + workoutIDColumn + ", W." + workoutTitleColumn;
		ResultSet result = connection.runQuery(query1, workoutID);
		
		while(result.next()) {
			workoutDifficulty = result.getInt(1);
		}
		
		return workoutDifficulty;
		
	}
	
}
