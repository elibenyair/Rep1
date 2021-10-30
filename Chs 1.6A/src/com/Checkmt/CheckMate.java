package com.Checkmt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Stack;

public class CheckMate {
	static boolean globalPring = false;
	static int counter = 0;
	static boolean mat = false;
	static byte startDeepLevelWhite = 0;
	static byte startDeepLevelBlack = 0;
	static boolean BLACK = false;
	static boolean WHITE = true;
	static boolean DEBUG = false;

	enum Winner {
		WHITE, BLACK, TEKO
	};

	enum GameType {
		CVSC, HVSC
	};

	static int pionScore = 10;
	static int ratzScore = 30;
	static int horseScore = 30;
	static int turaScore = 50;
	static int queenScore = 100;
	static int kingScore = 500;

	// Configuration
	static GameType gt = GameType.CVSC;
	Tool initBoard[][] = BoardsInitPositions.startBoard;

	// Human VS Computer Parameters
	static int deepLevelHVSC = 2;
	static boolean startColorHVSC = WHITE;
	static boolean computerColor = BLACK;

	// Computer VS Computer Parameters
	static int deepLevelWhite = 4;
	static int deepLevelBlack = 5;
	static int totalGames = 5;
	static boolean startColorCVSC = WHITE;

	Weights standartWeights = new Weights(10, 30, 30, 50, 100, 500);
	// Weights anotherWeights = new Weights(100, 40, 40, 100, 50, 500);
	// Weights strongTura = new Weights(100, 40, 30, 100, 50, 500);

	// Weights weightForWhite = standartWeights;
	// Weights weightForBlack = anotherWeights;
	Weights weightForWhite = new Weights(10, 30, 30, 50, 100, 500);;
	Weights weightForBlack = new Weights(10, 30, 30, 50, 100, 500);;

	Weights weights = standartWeights;

	/**
	 * @param args
	 */

	public static void main(String[] args) {

		CheckMate cm = new CheckMate();
		int whiteWins = 0;

		// Computer VS Computer
		if (gt == GameType.CVSC) {
			for (int i = 1; i <= totalGames; i++)
				if (cm.startGameComputerVSComputer(
						deepLevelWhite,
						deepLevelBlack,
						startColorCVSC) == Winner.WHITE)
					whiteWins++;
			System.out.println("White win :" + whiteWins + " out of " + totalGames + " games");
		}
		else { // Human VS Computer
			boolean color = cm
					.startGameComputerVSHuman(deepLevelHVSC, startColorHVSC, computerColor);
			System.out.println(color ? " White loose" : "Black loose");
		}
	}

	private Winner startGameComputerVSComputer(
			int deepLevelWhite,
			int deepLevelBlack,
			boolean color) {
		Board b1 = new Board(initBoard, standartWeights);
		startDeepLevelWhite = (byte) deepLevelWhite;
		startDeepLevelBlack = (byte) deepLevelBlack;
		Board board = (Board) DeepCopy.copy(b1);
		int deepLevel;
		board.printBoard(new Movement((short) 9, (short) 9, (short) 9, (short) 9));
		System.out.println("Init Score is: " + board.calcScore());
		int totalMoves = 0;
		Movement m = null;
		while (true) {
			counter = 0;
			java.util.Date date = new java.util.Date();
			Timestamp ts1 = new Timestamp(date.getTime());
			if (color == WHITE) {
				deepLevel = deepLevelWhite;
				board.reWeight(weightForWhite);

			}
			else { // BLACK turn
				deepLevel = deepLevelBlack;
				board.reWeight(weightForBlack);
			}
			m = board.getCorrectMovement(color, deepLevel);
			if (m == null) {
				System.out.println("Teko");
				return Winner.TEKO;
			}
			java.util.Date date1 = new java.util.Date();
			Timestamp ts2 = new Timestamp(date1.getTime());
			board.move(m);
			board.printBoard(m);
			long duration = (ts2.getTime() - ts1.getTime());
			System.out.println("Duration (ms)" + (ts2.getTime() - ts1.getTime()));
			// System.out.println(
			// "Avergae 1M movement per second: " + 1000 * (double) (counter /
			// duration));
			System.out.println("total calculated boards" + counter);
			System.out.println("Board Scode: " + m.score);
			System.out.println("Total moves: " + totalMoves++);
			if (m.pat) {
				System.out.println("Pat!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				break;
			}
			if (m.mat) {
				System.out.println("Mat!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				break;
			}

			color = !color;
		}

		System.out.println(color ? " White win" : "Black win");

		if (color)
			return Winner.WHITE;
		else
			return Winner.BLACK;
	}

	private boolean startGameComputerVSHuman(
			int deepComputerLevel,
			boolean color,
			boolean computerColor) {

		Movement mvt = null;
		Board board = new Board(BoardsInitPositions.startBoard, weights);
		// Board board = new Board(hatzraha);

		board.printBoard(new Movement((short) 9, (short) 9, (short) 9, (short) 9));
		System.out.println("Init Score is: " + board.calcScore());
		int totalMoves = 0;
		Stack<Board> boardStack = new Stack<Board>();
		// Push copy of board to board stack for Undo
		boardStack.push((Board) DeepCopy.copy(board));
		// Movement m = null;
		while (true) {

			if (color != computerColor) {
				do {
					mvt = readMove();
					// Handle undo
					if (mvt.fromX == 9) {
						board = boardStack.pop();
						board.printBoard(mvt);
						mvt = new Movement((short) 0, (short) 0, (short) 0, (short) 0);
					}

				}
				while (board.pbrd[mvt.fromY][mvt.fromX] == null
						|| !board.pbrd[mvt.fromY][mvt.fromX].isValidMovement(board, mvt));

				// Push copy of board to board stach for Undo
				boardStack.push((Board) DeepCopy.copy(board));

				board.move(mvt);
				board.printBoard(mvt);
			}
			else {
				java.util.Date date = new java.util.Date();
				Timestamp ts1 = new Timestamp(date.getTime());
				mvt = board.getCorrectMovement(color, deepComputerLevel);
				java.util.Date date1 = new java.util.Date();
				Timestamp ts2 = new Timestamp(date1.getTime());
				board.move(mvt);
				board.printBoard(mvt);
				System.out.println("Duration (ms)" + (ts2.getTime() - ts1.getTime()));
				System.out.println("total calculated boards" + counter);
				System.out.println("Board Scode: " + board.boardScore);
				System.out.println("Total moves: " + totalMoves++);
				counter = 0;
			}

			if (mvt.mat) {
				System.out.println("Mat!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				break;
			}
			if (mvt.pat) {
				System.out.println("Pat!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				break;
			}

			color = !color;
		}
		System.out.println(color ? " White loose" : "Black loose");

		return color;
	}

	private static Movement readMove() {

		System.out.print("Please enter your move (<from x><from y><to x><to y> or 9000 for undo: ");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		try {
			line = bufferedReader.readLine();
			System.out.print("your move :" + line);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(" first: " + Character.getNumericValue(line.charAt(0)));

		return new Movement((short) (Character.getNumericValue(line.charAt(0))),
				(short) Character.getNumericValue(line.charAt(1)),
				(short) Character.getNumericValue(line.charAt(2)),
				(short) Character.getNumericValue(line.charAt(3)));

	}

	static boolean isMat(Board b1, boolean color) {
		globalPring = false;
		int currentScore = b1.calcScore();
		Board b2 = (Board) DeepCopy.copy(b1);
		Movement mm = b2.getBestMovement(color, 2);
		int score = mm.score;
		globalPring = false;
		System.out.println("Score:" + score + " CurrentScore:" + currentScore);
		if (Math.abs(score - currentScore) > 300)
			return true;
		return false;
	}

}
