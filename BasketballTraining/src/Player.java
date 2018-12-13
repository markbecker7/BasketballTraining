import java.util.*;
import java.sql.*;
public class Player {
	private static DataConnection connection;
	private int playerID;
	private String playerFirstName;
	private String playerLastName;
	
	//Table and column Names
	private static final String playerTableName = "Player";
	private static final String workoutOwnerTableName = "WorkoutOwner";
	private static final String playerIDColumn = "Player_ID";
	private static final String playerFirstNameColumn = "Player_First_Name";
	private static final String playerLastNameColumn = "Player_Last_Name";
	private static final String playerScoreColumn = "Player_Score";
	
	
	public void openConnection() throws SQLException {
		connection = new DataConnection();
	}
	
	public void closeConnection() throws SQLException {
		connection.closeConnection();
	}
	
	//Boolean states whether the connection to the Player table should be closed after displaying.
	
	public void displayPlayerTable(boolean closeConnection) throws SQLException {
		System.out.println("Here is the up-to-date player list:");
		String query = "SELECT* FROM " + playerTableName;
		ResultSet result = connection.runQuery(query);
		
		while(result.next()) {
			int tempPlayerID = result.getInt(playerIDColumn);
			System.out.print("PlayerID:" + tempPlayerID + "  Name:");
			String tempFirstName = result.getString(playerFirstNameColumn);
			String tempLastName = result.getString(playerLastNameColumn);
			int tempPlayerScore = result.getInt(playerScoreColumn);
			System.out.println(tempFirstName + " " + tempLastName + "  Player Score:" + tempPlayerScore);
		}
		
		if(closeConnection == true) {
			connection.closeConnection();
		}
		
		System.out.println("");
	}
	
	public void displayPlayerWorkouts(boolean closeConnection) throws SQLException {
		Scanner scan = new Scanner(System.in);
		displayPlayerTable(false);
		System.out.println("Which player's workout(s) would you like to see? Enter Player ID:");
		playerID = scan.nextInt();
		
		String query = "SELECT WO." + Workout.workoutIDColumn + ", W." + Workout.workoutTitleColumn + " FROM " + workoutOwnerTableName + 
				" AS WO INNER JOIN "  + Workout.workoutTableName + " AS W ON WO." + Workout.workoutIDColumn + "= W." + Workout.workoutIDColumn
				+ " AND " + playerIDColumn + " = " + playerID;
		ResultSet result = connection.runQuery(query);
		
		while(result.next()) {
			int tempWorkoutID = result.getInt(Workout.workoutIDColumn);
			String tempWorkoutTitle = result.getString(Workout.workoutTitleColumn);
			System.out.println("Workout ID: " + tempWorkoutID + "   WorkoutTitle: " + tempWorkoutTitle);
		}
		
		if(closeConnection == true) {
			connection.closeConnection();
		}
		
		System.out.println("");
		
		
	}
	
	private void displayPlayerWorkouts(int playerID) throws SQLException {
		System.out.println("Here is the up-to-date Workout list for this player: ");
		
		String query = "SELECT WO." + Workout.workoutIDColumn + ", W." + Workout.workoutTitleColumn + " FROM " + workoutOwnerTableName + 
				" AS WO INNER JOIN "  + Workout.workoutTableName + " AS W ON WO." + Workout.workoutIDColumn + "= W." + Workout.workoutIDColumn
				+ " AND " + playerIDColumn + " = " + playerID;
		ResultSet result = connection.runQuery(query);
		
		while(result.next()) {
			int tempWorkoutID = result.getInt(Workout.workoutIDColumn);
			String tempWorkoutTitle = result.getString(Workout.workoutTitleColumn);
			System.out.println("Workout ID: " + tempWorkoutID + "   WorkoutTitle: " + tempWorkoutTitle);
		}
		
		System.out.println("");
	}
	
	public void addPlayer() throws SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Player First Name: ");
		playerFirstName = scan.next();
		System.out.println("Enter Player Last Name: ");
		playerLastName = scan.next();
		System.out.println("Enter the Player ID number: ");
		playerID = scan.nextInt();
		
		String query = "INSERT INTO " + playerTableName + "(" + playerIDColumn + "," +
						playerFirstNameColumn + "," + playerLastNameColumn + "," +
						playerScoreColumn + ")";
		query += "VALUES(" + playerID + ", ?, ?, 0)";
		
		connection.updateData(query, playerFirstName, playerLastName);
		
	}
	
	public void addPlayerOwner() throws SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Who do you want to assign a workout? Enter Player First Name: ");
		playerFirstName = scan.next();
		System.out.println("Enter Player Last Name: ");
		playerLastName = scan.next();
		Workout workout = new Workout();
		workout.displayWorkoutTable(false);
		System.out.println("What workout would you like to assign this player? Enter workout ID: ");
		int workoutID = scan.nextInt();
		
		String query = "INSERT INTO " + workoutOwnerTableName;
		query += " VALUES((SELECT TOP 1 " + playerIDColumn + " FROM " + playerTableName + " WHERE "
					+ playerFirstNameColumn + " = ? AND " + playerLastNameColumn + "=?), "
					+ workoutID + ")";
		
		connection.updateData(query, playerFirstName, playerLastName);
		
	}
	
	public void addPlayerAndAssignWorkout() throws SQLException {
		boolean done = false;
		int numberOfWorkouts = 7;
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Player First Name: ");
		playerFirstName = scan.next();
		System.out.println("Enter Player Last Name: ");
		playerLastName = scan.next();
		System.out.println("Enter the Player ID number: ");
		playerID = scan.nextInt();
		
		Workout workout = new Workout();
		workout.openConnection();
		workout.displayWorkoutTable(false);
		System.out.println("What workout would you like to assign this player? Enter workout ID: ");
		int workoutID = 0;
		
		while(!done) {
			if(scan.hasNextInt()) {
				workoutID = scan.nextInt();
				if(workoutID <= numberOfWorkouts && workoutID > 0) {
					done = true;
				} else {
					System.out.println("Please enter a valid workoutID");
				}
			} else {
				scan.next();
				System.out.println("Please enter an integer.");
			}
		}
			
		String query = "BEGIN TRY BEGIN TRAN ";
		query += "INSERT INTO " + playerTableName + "(" + playerIDColumn + "," +
				playerFirstNameColumn + "," + playerLastNameColumn + "," +
				playerScoreColumn + ")";
				query += "VALUES(" + playerID + ", ?, ?, " + "0); ";
		query += "INSERT INTO " + workoutOwnerTableName;
		query += " VALUES(" + playerID + ","
				+ workoutID + ") ";
		query += " COMMIT TRAN END TRY BEGIN CATCH ROLLBACK TRAN END CATCH;";
		
		connection.updateData(query, playerFirstName, playerLastName);
		workout.closeConnection();
		displayPlayerWorkouts(playerID);
	}
	
	public void deletePlayerOwner() throws SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Who do you want to delete a workout from? Enter Player First Name: ");
		playerFirstName = scan.next();
		System.out.println("Enter Player Last Name: ");
		playerLastName = scan.next();
		System.out.println("Enter the workout ID number: ");
		int workoutID = scan.nextInt();
		
		String query = "DELETE FROM " + workoutOwnerTableName;
		query += " WHERE " + playerIDColumn + "=" + " (SELECT TOP 1 " + playerIDColumn +
					" FROM " + playerTableName + " WHERE "
					+ playerFirstNameColumn + " = ? AND " + playerLastNameColumn + "=?) AND "
					+ Workout.workoutIDColumn + "=" + workoutID;
		
		connection.updateData(query, playerFirstName, playerLastName);
		
	}
	
	public void deletePlayer() throws SQLException {
		int numberOfPlayers = 8;
		boolean done = false;
		Player player = new Player();
		player.displayPlayerTable(false);
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the playerID of the player you want to delete: ");
		
		while(!done) {
			if(scan.hasNextInt()) {
				playerID = scan.nextInt();
				if(playerID <= numberOfPlayers && playerID > 0) {
					done = true;
				} else {
					System.out.println("Please enter a valid playerID");
				}
			} else {
				scan.next();
				System.out.println("Please enter an integer.");
			}
		}
		
		String query = "DELETE FROM " + playerTableName + " WHERE " + playerIDColumn + "= " + playerID + ";";
		
		connection.updateData(query);
	}
	
	public void updatePlayerScore() throws SQLException {
		int numberOfPlayers = 8;
		Player player = new Player();
		player.displayPlayerTable(false);
		Workout workout = new Workout();
		workout.openConnection();
		workout.displayWorkoutTable(false);
		boolean done = false;
		Scanner scan = new Scanner(System.in);
		System.out.println("Which player would you like to add a score to? Please enter the playerID: ");
		
		while(!done) {
			if(scan.hasNextInt()) {
				playerID = scan.nextInt();
				if(playerID <= numberOfPlayers && playerID > 0) {
					done = true;
				} else {
					System.out.println("Please enter a valid playerID");
				}
			} else {
				scan.next();
				System.out.println("Please enter an integer.");
			}
		}
		
		System.out.println("Which workout did the player perform? Please enter the workout ID: ");
		String workoutID = scan.nextInt() + "";
		int workoutDifficulty = workout.computeWorkoutDifficulty(workoutID);
		
		String query = "UPDATE " + playerTableName + " SET " + playerScoreColumn + " += " + workoutDifficulty
						+ " WHERE " + playerIDColumn + " = " + playerID;
		
		connection.updateData(query);
		workout.closeConnection();
		
	}
}

