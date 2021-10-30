package com.Checkmt;

import java.util.Iterator;
import java.util.List;

public abstract class Tool implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	// private static final long serialVersionUID = 1L;
	int score;
	boolean color;
	byte nameCode;
	short xCoord;
	short yCoord;

	byte nextPositionedTool = 0;
	byte prevPositionedTool = 0;

	public Tool(boolean c, byte nm, int scr) {
		color = c;
		nameCode = nm;
		score = scr * (color ? 1 : -1);
	}

	public abstract void setScore(Weights w);

	// public PositionedTool(Tool t, Coords c, Board b) {
	// tool = t;
	// coords = c;
	// board = b;
	// // TODO Auto-generated constructor stub
	// }

	public String value() {
		String fullName = null;

		switch (nameCode) {
		case 'p':
			fullName = "Pion   ";
			break;
		case 'h':
			fullName = "Horse  ";
			break;
		case 'r':
			fullName = "Ratz   ";
			break;
		case 't':
			fullName = "Tura   ";
			break;
		case 'q':
			fullName = "Queen  ";
			break;
		case 'k':
			fullName = "King   ";
			break;
		}
		return fullName + (color ? "(W)" : "(B)");
		// switch (nameCode) {
		// case 'p':
		// fullName = "פיון ";
		// break;
		// case 'h':
		// fullName = "סוס ";
		// break;
		// case 'r':
		// fullName = "רץ ";
		// break;
		// case 't':
		// fullName = "טורה ";
		// break;
		// case 'q':
		// fullName = "מלכה ";
		// break;
		// case 'k':
		// fullName = "מלך ";
		// break;
		// }
		// return fullName + (color ? "(ל)" : "(ש)");
	}

	public int getAbsScore() {
		return (color ? (score * -1) : score);
	}

	boolean isValidMovement(Board board, Movement m) {
		List<Movement> mvts = getMovements(board, m.fromX, m.fromY);
		Iterator<Movement> it = mvts.iterator();
		while (it.hasNext()) {
			Movement currentMove = it.next();
			if (currentMove.toX == m.toX && currentMove.toY == m.toY) {
				m.hatzracha = currentMove.hatzracha;
				return true;
			}
		}
		return false;
	}

	// abstract int allowedMovements(Board b, Coords crds, List<Board> lb);
	abstract List<Movement> getMovements(Board b, short x, short y);

	public Movement getBestMovement(Board board, int deepLevel) {
		// This function retrun kingEaten=true if one of the movement of the
		// current tool Eat the king. This will be probably (need to insure) the
		// bestMovement
		boolean matOnMe = true;
		List<Movement> mvts = getMovements(board, xCoord, yCoord);
		Iterator<Movement> it = mvts.iterator();
		Board b1 = null;
		int intColor = (color ? 1 : -1);
		Movement bestMovement = null;
		while (it.hasNext()) {
			int count = 2;
			b1 = board;
			// b1 = (Board) DeepCopy.copy(board);
			Movement currentMove = it.next();
			currentMove.deepLevel = deepLevel;
			boolean moveEatKing = b1.moveAndCalculateDeepLevelScore(currentMove, deepLevel, color);
			matOnMe = currentMove.byThisMoveMyKingMayBeEaten && matOnMe;
			if (bestMovement == null) {
				bestMovement = currentMove;
			}
			else {
				int scr = (currentMove.score - bestMovement.score) * intColor;
				if (scr > 0)
					bestMovement = currentMove;

				else if (scr == 0) {
					if (Math.random() < (1 / (double) count))
						bestMovement = currentMove;
					count++;

				}
				if (moveEatKing)
					// || currentMove.mat); this code also should remove
					return currentMove;

			}

		}
		// if (bestMovement != null)
		// bestMovement.thisMoveEatTheKing = bestMovement.thisMoveEatTheKing;//
		// kingEaten;
		if (bestMovement != null)
			bestMovement.iLoosedByThisMoveAndMyAllBrotherMoves = matOnMe;
		return bestMovement;

	}

	public void tryToAdd(List<Movement> mvts, Movement m) {
		if (m.toX >= 0 && m.toX <= 7 && m.toY >= 0 && m.toY <= 7)
			mvts.add(m);
	}

	public void tryToAddAndCheckEat(List<Movement> mvts, Movement m, Board b) {
		if (m.toX >= 0 && m.toX <= 7 && m.toY >= 0 && m.toY <= 7 && (b.pbrd[m.toY][m.toX] != null
				&& b.pbrd[m.toY][m.toX].color != b.pbrd[m.fromY][m.fromX].color))

			mvts.add(m);
	}

	public void addTuraMoves(List<Movement> mvts, Board b, short x, short y) {
		boolean currentToolColor = b.pbrd[y][x].color;
		for (short i = (short) (x + 1); i < 8; i++) {
			Tool targetPosition = b.pbrd[y][i];
			if (targetPosition == null)
				mvts.add(new Movement(x, y, i, y));
			else {
				if (targetPosition.color != currentToolColor)
					mvts.add(new Movement(x, y, i, y));
				break;

			}
		}

		for (short i = (short) (x - 1); i >= 0; i--) {
			Tool targetPosition = b.pbrd[y][i];
			if (targetPosition == null)
				mvts.add(new Movement(x, y, i, y));
			else {
				if (targetPosition.color != currentToolColor)
					mvts.add(new Movement(x, y, i, y));
				break;

			}
		}
		for (int i = y + 1; i < 8; i++) {
			Tool targetPosition = b.pbrd[i][x];
			if (targetPosition == null)
				mvts.add(new Movement(x, y, x, (short) i));
			else {
				if (targetPosition.color != currentToolColor)
					mvts.add(new Movement(x, y, x, (short) i));
				break;

			}
		}
		for (short i = (short) (y - 1); i >= 0; i--) {
			Tool targetPosition = b.pbrd[i][x];
			if (targetPosition == null)
				mvts.add(new Movement(x, y, x, i));
			else {
				if (targetPosition.color != currentToolColor)
					mvts.add(new Movement(x, y, x, i));
				break;

			}
		}
	}

	public void addRatzMoves(List<Movement> mvts, Board b, short x, short y) {
		boolean currentToolColor = b.pbrd[y][x].color;

		for (short i = 1; i < 8; i++) {
			if ((x + i) > 7 || (y + i) > 7)
				break;
			Tool eatenPositionTool = b.pbrd[y + i][x + i];
			if (eatenPositionTool == null)
				mvts.add(new Movement(x, y, (short) (x + i), (short) (y + i)));
			else {
				if (eatenPositionTool.color != currentToolColor)
					mvts.add(new Movement(x, y, (short) (x + i), (short) (y + i)));
				break;

			}
		}
		for (short i = 1; i < 8; i++) {
			if ((x - i) < 0 || (y - i) < 0)
				break;
			Tool eatenPositionTool = b.pbrd[y - i][x - i];
			if (eatenPositionTool == null)
				mvts.add(new Movement(x, y, (short) (x - i), (short) (y - i)));
			else {
				if (eatenPositionTool.color != currentToolColor)
					mvts.add(new Movement(x, y, (short) (x - i), (short) (y - i)));
				break;

			}
		}

		for (short i = 1; i < 8; i++) {
			if ((x - i) < 0 || (y + i) > 7)
				break;
			Tool eatenPositionTool = b.pbrd[y + i][x - i];
			if (eatenPositionTool == null)
				mvts.add(new Movement(x, y, (short) (x - i), (short) (y + i)));
			else {
				if (eatenPositionTool.color != currentToolColor)
					mvts.add(new Movement(x, y, (short) (x - i), (short) (y + i)));
				break;

			}
		}

		for (short i = 1; i < 8; i++) {
			if ((x + i) > 7 || (y - i) < 0)
				break;
			Tool eatenPositionTool = b.pbrd[y - i][x + i];
			if (eatenPositionTool == null)
				mvts.add(new Movement(x, y, (short) (x + i), (short) (y - i)));
			else {
				if (eatenPositionTool.color != currentToolColor)
					mvts.add(new Movement(x, y, (short) (x + i), (short) (y - i)));
				break;

			}
		}
		return;
	}

	byte getBytePosition() {
		return (byte) (xCoord + (yCoord << 4));
	}
}
