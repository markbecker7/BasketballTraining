IF DB_ID('BasketballTraining') IS NULL
	CREATE DATABASE BasketballTraining;

GO
USE BasketballTraining;
IF OBJECT_ID('WorkoutOwner', 'U') IS NOT NULL DROP TABLE WorkoutOwner;
IF OBJECT_ID('WorkoutContent', 'U') IS NOT NULL DROP TABLE WorkoutContent;
IF OBJECT_ID('Player', 'U') IS NOT NULL DROP TABLE Player;
IF OBJECT_ID('Drill', 'U') IS NOT NULL DROP TABLE Drill;
IF OBJECT_ID('Workout', 'U') IS NOT NULL DROP TABLE Workout;
GO
CREATE TABLE Player (
	Player_ID	INT	PRIMARY KEY,
	Player_First_Name	VARCHAR(30) NOT NULL,
	Player_Last_Name	VARCHAR(30) NOT NULL,
	Player_Score	INT		NOT NULL
);
CREATE TABLE Drill (
	Drill_ID	INT PRIMARY KEY,
	Drill_Instruction	VARCHAR(2000) NOT NULL,
	Drill_Difficulty	INT NOT NULL,
	Category_Name	VARCHAR(30)	NOT NULL
	CHECK(Category_Name IN ('Passing', 'Shooting', 'Slashing', 'Dribbling', 'Defense', 'Conditioning')),
	CHECK(Drill_Difficulty BETWEEN 1 AND 10)
);
CREATE TABLE Workout (
	Workout_ID	INT	PRIMARY KEY,
	Workout_Title	VARCHAR(50)	NOT NULL
);
CREATE TABLE WorkoutOwner (
	Player_ID	INT NOT NULL,
	Workout_ID	INT NOT NULL,
	PRIMARY KEY(Player_ID, Workout_ID),
	FOREIGN KEY(Player_ID) REFERENCES Player(Player_ID),
	FOREIGN KEY (Workout_ID) REFERENCES Workout(Workout_ID)
);
CREATE TABLE WorkoutContent (
	Drill_ID	INT NOT NULL,
	Workout_ID	INT NOT NULL,
	PRIMARY KEY(Drill_ID, Workout_ID),
	FOREIGN KEY(Drill_ID) REFERENCES Drill(Drill_ID),
	FOREIGN KEY(Workout_ID) REFERENCES Workout(Workout_ID)
);
GO
INSERT INTO Player VALUES(1, 'Luke', 'Becker', 0);
INSERT INTO Player VALUES(2, 'Adam', 'Hermsen', 0);
INSERT INTO Player VALUES(3, 'Matt', 'Pelzer', 0);
INSERT INTO Player VALUES(4, 'Trey', 'Byers', 0);
GO
INSERT INTO Workout VALUES(1, 'MondayWorkout');
INSERT INTO Workout VALUES(2, 'FridayWorkout');
INSERT INTO Workout VALUES(3, 'Buckets');
INSERT INTO Workout VALUES(4, 'Shooting');
INSERT INTO Workout VALUES(5, 'HardDay');
INSERT INTO Workout VALUES(6, 'EasyDay');
INSERT INTO Workout VALUES(7, 'Pregame');
GO
INSERT INTO Drill VALUES(1, 'Shoot 100 Free Throws', 1, 'Shooting');
INSERT INTO Drill VALUES(2, 'Pass a ball off a wall with only 1 hand from 5 feet away. Pass 50 times and then switch hands.', 5, 'Passing');
INSERT INTO Drill VALUES(3, 'Mikan Shooting. Make 10 on each side of the rim, forward and backward.', 2, 'Slashing');
INSERT INTO Drill VALUES(4, 'Pound the ball waist high for 20 dribbles, then switch hands.', 1, 'Dribbling');
INSERT INTO Drill VALUES(5, 'Defensive slide from the corner of the court to the free-throw line. Then turn and shuffle to the half-court line, opposite free-throw line, and opposite corner. Repeat 4 times.', 5, 'Defense');
INSERT INTO Drill VALUES(6, 'Running the short length of the court, touch the out of bounds line 17 times, going as fast as possible.', 8, 'Conditioning');
INSERT INTO Drill VALUES(7, 'Make 5 three-pointers from the corner, wing, top, opposite wing, and opposite corner. 25 total makes.', 7, 'Shooting');
GO
INSERT INTO WorkoutContent VALUES(1, 1);
INSERT INTO WorkoutContent VALUES(1, 2);
INSERT INTO WorkoutContent VALUES(1, 4);
INSERT INTO WorkoutContent VALUES(1, 5);
INSERT INTO WorkoutContent VALUES(1, 6);
INSERT INTO WorkoutContent VALUES(2, 1);
INSERT INTO WorkoutContent VALUES(2, 2);
INSERT INTO WorkoutContent VALUES(2, 6);
INSERT INTO WorkoutContent VALUES(4, 1);
INSERT INTO WorkoutContent VALUES(4, 7);
INSERT INTO WorkoutContent VALUES(5, 6);
INSERT INTO WorkoutContent VALUES(6, 1);
INSERT INTO WorkoutContent VALUES(7, 4);
INSERT INTO WorkoutContent VALUES(7, 7);

GO
INSERT INTO WorkoutOwner VALUES(1, 1);
INSERT INTO WorkoutOwner VALUES(1, 2);
INSERT INTO WorkoutOwner VALUES(3, 3);
INSERT INTO WorkoutOwner VALUES(3, 4);
INSERT INTO WorkoutOwner VALUES(4, 5);
INSERT INTO WorkoutOwner VALUES(4, 6);
INSERT INTO WorkoutOwner VALUES(4, 7);
GO
SELECT* FROM Player
SELECT* FROM Workout
SELECT* FROM Drill
SELECT* FROM WorkoutContent
SELEcT* FROM WorkoutOwner
GO