import java.sql.SQLException;
import java.util.Scanner;

public class TestCode {
   	public static void main(String[] args) {
   		boolean done = false;
   		int actionNumber = 0;
   		while(!done) {
	   		try {
	   			Player player = new Player();
	   			player.openConnection();
	   			Scanner scan = new Scanner(System.in);
				System.out.println("What would you like to do? Enter a number 1-7.");
				System.out.println("	1. Display the Player Table");
				System.out.println("	2. Insert a new Player");
				System.out.println("	3. Update a Player score");
				System.out.println("	4. Delete a Player");
				System.out.println("	5. Insert a new Player and Assign a Workout");
				System.out.println("	6. Display a Player's Workout(s)");
				System.out.println("	7. Exit");
				
				boolean getInteger = false;
				
				while(!getInteger) {
					if(scan.hasNextInt()) {
						actionNumber = scan.nextInt();
						getInteger = true;
					} else {
						scan.next();
						System.out.println("Please enter an integer.");
					}
				}
				
				
				if(actionNumber == 1) {
					player.displayPlayerTable(true);
	   			} else if (actionNumber == 2) {
					player.addPlayer();
				} else if (actionNumber == 3) {
					player.updatePlayerScore();
	   			} else if (actionNumber == 4) {
					player.deletePlayer();
				} else if (actionNumber == 5) {
					player.addPlayerAndAssignWorkout();
				} else if (actionNumber == 6) {
					player.displayPlayerWorkouts(true);
				} else if (actionNumber == 7) {
					System.out.println("Have a splendid day!");
					scan.close();
					player.closeConnection();
					done = true;
				} else {
					System.out.println("Please enter a valid integer 1-7");
				}
				
				if(actionNumber >= 2 && actionNumber <= 5) {
					player.displayPlayerTable(true);
				}
				
			} catch (SQLException e) {
				System.out.println("Something went terribly wrong.");
				e.printStackTrace();
			}
   		}
   	}
}
