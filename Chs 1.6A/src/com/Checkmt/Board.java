package com.Checkmt;

public class Board implements java.io.Serializable {

	public static int counter = 0;
	Tool pbrd[][] = new Tool[8][8];

	int boardScore = 0;
	byte firstWhitePositiondeTool = 0;
	byte firstBlackPositiondeTool = 0;
	Tool whiteKingPos = null;
	Tool blackKingPos = null;
	byte firstPositionTool[] = new byte[2];

	// Eli 30/10/2021
	public Board(Tool brd[][], Weights w) {
		initBoard(brd, w);
		return;

	}

	public void printBoard() {
		printBoard(new Movement((short) 9, (short) 9, (short) 9, (short) 9));

	}

	public void printBoard(Movement lastMovement) {
		System.out.println();
		System.out.println(
				" -------------------------------------------------------------------------------------------------");
		for (int y = 7; y >= 0; y--) {
			System.out.println(
					" |           |           |           |           |           |           |           |           |");

			System.out.print("" + (y) + "|");
			for (int x = 0; x < 8; x++) {
				if (lastMovement.toY == y && lastMovement.toX == x)
					System.out.print("*");
				else
					System.out.print(" ");
				System.out.print(pbrd[y][x] == null ? "  -----    " : pbrd[y][x].value() + "|");
			}
			System.out.println();
			System.out.println(
					" |           |           |           |           |           |           |           |           |");
			System.out.println(
					" -------------------------------------------------------------------------------------------------");

		}
		System.out.print("  ");
		for (int x = 0; x < 8; x++)
			System.out.print("     " + x + "      ");
		System.out.println();

	}

	public void printBoard1(Movement lastMovement) {
		System.out.println();
		System.out.println(
				"--------------------------------------------------------------------------------");
		for (int y = 7; y >= 0; y--) {
			System.out.print("" + (y) + ")");
			for (int x = 0; x < 8; x++) {
				if (lastMovement.toY == y && lastMovement.toX == x)
					System.out.print("*");
				else
					System.out.print(" ");
				System.out.print(pbrd[y][x] == null ? "  -----    " : pbrd[y][x].value() + "|");
			}
			System.out.println();
		}
		for (int x = 0; x < 8; x++)
			System.out.print("     " + x + "      ");
		System.out.println();

	}

	public void initBoard(Tool startBoard[][], Weights w) {
		// Y
		// 7
		// 6
		// 5
		// 4
		// 3
		// 2
		// 1
		// 0
		// X 0 1 2 3 4 5 6 7

		Tool whitePositionedTool = null;
		Tool blackPositionedTool = null;

		pbrd = startBoard;
		Tool npt = null;
		for (short y = 0; y < 8; y++)
			for (short x = 0; x < 8; x++)
				if (startBoard[y][x] != null) {
					startBoard[y][x].xCoord = x;
					startBoard[y][x].yCoord = y;

					npt = startBoard[y][x];
					pbrd[y][x] = npt;
					if (startBoard[y][x].color) {
						if (whitePositionedTool == null) {
							whitePositionedTool = npt;
							firstWhitePositiondeTool = npt.getBytePosition();
						}
						else {
							whitePositionedTool.nextPositionedTool = npt.getBytePosition();
							npt.prevPositionedTool = whitePositionedTool.getBytePosition();
							whitePositionedTool = startBoard[whitePositionedTool.nextPositionedTool >> 4][whitePositionedTool.nextPositionedTool
									& 0xF];

						}
					}
					else {
						if (blackPositionedTool == null) {
							blackPositionedTool = npt;
							firstBlackPositiondeTool = (byte) (npt.xCoord + (npt.yCoord << 4));
						}
						else {

							blackPositionedTool.nextPositionedTool = npt.getBytePosition();
							npt.prevPositionedTool = blackPositionedTool.getBytePosition();
							blackPositionedTool = startBoard[blackPositionedTool.nextPositionedTool >> 4][blackPositionedTool.nextPositionedTool
									& 0xF];

						}
					}
				}
		whitePositionedTool.nextPositionedTool = firstWhitePositiondeTool;
		startBoard[firstWhitePositiondeTool >> 4][firstWhitePositiondeTool
				& 0xF].prevPositionedTool = whitePositionedTool.getBytePosition();
		blackPositionedTool.nextPositionedTool = firstBlackPositiondeTool;
		startBoard[firstBlackPositiondeTool >> 4][firstBlackPositiondeTool
				& 0xF].prevPositionedTool = blackPositionedTool.getBytePosition();

		// whiteKingPos = pbrd[0][4];
		// blackKingPos = pbrd[7][4];
		// whiteKingPos = pbrd[0][7];
		// blackKingPos = pbrd[7][7];
		Tool t = getToolAtBoard(firstWhitePositiondeTool);
		do {
			if (t.nameCode == 'k')
				whiteKingPos = t;
			t = getToolAtBoard(t.nextPositionedTool);
		}
		while (t != getToolAtBoard(firstWhitePositiondeTool));

		t = getToolAtBoard(firstBlackPositiondeTool);
		do {
			if (t.nameCode == 'k')
				blackKingPos = t;
			t = getToolAtBoard(t.nextPositionedTool);
		}
		while (t != getToolAtBoard(firstBlackPositiondeTool));

		reWeight(true, w); // Init White weights
		reWeight(false, w); // Init Black weights

		boardScore = calcScore();
	}

	public int calcScore() {
		int s = 0;
		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 8; y++)
				s += pbrd[y][x] != null ? pbrd[y][x].score : 0;
		return s;
	}

	public UndoMoveData move(Movement m) {
		// if (m.fromX == 0 && m.fromY == 6 && m.toX == 0 && m.toY == 5)
		// System.out.println("Here");
		// if (!checkBoard()) {
		// throw new RuntimeException();
		// }

		// System.out.println("---------------------- Move Start----------");
		// System.out.println("Before Movement: Hatzracha" + m.hatzracha);
		// printToolChain();
		// printMovement(m);
		// printBoard(); // debug

		UndoMoveData umd = new UndoMoveData(m, this);
		CheckMate.counter++;
		Tool currentTool = pbrd[m.fromY][m.fromX];
		Tool eatenTool = pbrd[m.toY][m.toX];

		int multiply = currentTool.color ? 1 : -1;

		// --------------------Update Score
		// Update Score for Pion steps
		if (currentTool.nameCode == 'p') {
			boardScore = boardScore + (m.toY - m.fromY);// no need to
			Tool opositeKingPos = (currentTool.color ? blackKingPos : whiteKingPos);
			boardScore += (((Math.abs(opositeKingPos.yCoord - m.fromY)
					- Math.abs(opositeKingPos.yCoord - m.toY))
					+ (Math.abs(opositeKingPos.xCoord - m.fromX)
							- Math.abs(opositeKingPos.xCoord - m.toX)))
					* multiply) / 2;

			// multiply by multiply,since score is objective number

			// Insert queen
			if (m.toY == 0 || m.toY == 7) {
				umd.currentToolBeforeInsertQueen = currentTool;
				boardScore = boardScore + 90;
				currentTool = new ToolQueen(currentTool.color);
				currentTool.xCoord = m.fromX;
				currentTool.yCoord = m.fromY;
				currentTool.prevPositionedTool = pbrd[m.fromY][m.fromX].prevPositionedTool;
				currentTool.nextPositionedTool = pbrd[m.fromY][m.fromX].nextPositionedTool;
				pbrd[m.fromY][m.fromX] = currentTool;
				boardScore = boardScore + 90;
			}
		}

		// Handle Horse
		if (currentTool.nameCode == 'h') {
			Tool opositeKingPos = (currentTool.color ? blackKingPos : whiteKingPos);
			boardScore += (((Math.abs(opositeKingPos.yCoord - m.fromY)
					- Math.abs(opositeKingPos.yCoord - m.toY))
					+ (Math.abs(opositeKingPos.xCoord - m.fromX)
							- Math.abs(opositeKingPos.xCoord - m.toX)))
					* multiply) / 2;
		}

		if (currentTool.nameCode == 'k') {
			((ToolKing) (pbrd[m.fromY][m.fromX])).hatzrachaAllowed = false;

			if (currentTool.color)
				whiteKingPos = currentTool;
			else
				blackKingPos = currentTool;

			// ----------------Check Hatzracha
			// ----------------------------------
			if (m.hatzracha) { // no matter if its white or black

				if (m.toX == 6) {

					// Move Tura
					updateToolLocation(
							pbrd[m.fromY][7],
							new Movement((short) 7, m.fromY, (short) 5, m.toY));
				}
				else {
					// Move Tura
					updateToolLocation(
							pbrd[m.fromY][0],
							new Movement((short) 0, m.fromY, (short) 3, m.toY));

				}

			}
		}
		// ----------------------------------------------------------------

		currentTool.xCoord = m.toX;
		currentTool.yCoord = m.toY;

		// Update Vectors only if tool was eaten
		if (eatenTool != null) {

			// if King eaten notify caller
			if (eatenTool.nameCode == 'k') {
				m.thisMoveEatTheKing = true;
				umd.thisMoveEatKing = true;
				boardScore = boardScore - eatenTool.score * m.deepLevel;

			}
			else
				// not king
				boardScore = boardScore - eatenTool.score;

			if (firstWhitePositiondeTool == m.toX + (m.toY << 4))
				firstWhitePositiondeTool = eatenTool.nextPositionedTool;
			if (firstBlackPositiondeTool == m.toX + (m.toY << 4))
				firstBlackPositiondeTool = eatenTool.nextPositionedTool;

			pbrd[(eatenTool.prevPositionedTool) >> 4][(eatenTool.prevPositionedTool)
					& 0xF].nextPositionedTool = eatenTool.nextPositionedTool;
			pbrd[(eatenTool.nextPositionedTool) >> 4][(eatenTool.nextPositionedTool)
					& 0xF].prevPositionedTool = eatenTool.prevPositionedTool;
		}

		// update Vector of current color
		// Check if this is the last tool on the board, so last current and next
		// are the same
		if (getToolAtBoard(currentTool.nextPositionedTool) != currentTool) {

			if (firstWhitePositiondeTool == m.fromX + (m.fromY << 4))
				firstWhitePositiondeTool = (byte) (m.toX + (m.toY << 4));
			if (firstBlackPositiondeTool == m.fromX + (m.fromY << 4))
				firstBlackPositiondeTool = (byte) (m.toX + (m.toY << 4));

			Tool prevToolOfMoovingTool = pbrd[(currentTool.prevPositionedTool) >> 4][(currentTool.prevPositionedTool)
					& 0xF];
			Tool nextToolOfMoovingTool = pbrd[(currentTool.nextPositionedTool) >> 4][(currentTool.nextPositionedTool)
					& 0xF];

			prevToolOfMoovingTool.nextPositionedTool = (byte) (m.toX + (m.toY << 4));
			nextToolOfMoovingTool.prevPositionedTool = (byte) (m.toX + (m.toY << 4));

		}
		else { // current tool is the last tool in the board for his color
			currentTool.nextPositionedTool = (byte) (m.toX + (m.toY << 4));
			currentTool.prevPositionedTool = (byte) (m.toX + (m.toY << 4));

			// After fixing firstW Pointers
			if (firstWhitePositiondeTool == m.fromX + (m.fromY << 4))
				firstWhitePositiondeTool = (byte) (m.toX + (m.toY << 4));
			if (firstBlackPositiondeTool == m.fromX + (m.fromY << 4))
				firstBlackPositiondeTool = (byte) (m.toX + (m.toY << 4));

		}
		pbrd[m.toY][m.toX] = pbrd[m.fromY][m.fromX];
		pbrd[m.fromY][m.fromX] = null;

		////////////////////////////////////////////////////////////////////

		m.score = boardScore;

		// System.out.println("After Movement: Hatzracha:" + m.hatzracha);
		// printMovement(m);
		// printBoard(); // debug
		// printToolChain();

		return umd;
	}

	public void updateToolLocation(Tool currentTool, Movement m) {

		currentTool.xCoord = m.toX;
		currentTool.yCoord = m.toY;

		// update Vector of current color
		// Check if this is the last tool on the board, so last current and next
		// are the same
		if (getToolAtBoard(currentTool.nextPositionedTool) != currentTool) {

			// After fixing firstW Pointers
			if (firstWhitePositiondeTool == m.fromX + (m.fromY << 4))
				firstWhitePositiondeTool = (byte) (m.toX + (m.toY << 4));
			if (firstBlackPositiondeTool == m.fromX + (m.fromY << 4))
				firstBlackPositiondeTool = (byte) (m.toX + (m.toY << 4));

			Tool prevToolOfMoovingTool = pbrd[(currentTool.prevPositionedTool) >> 4][(currentTool.prevPositionedTool)
					& 0xF];
			Tool nextToolOfMoovingTool = pbrd[(currentTool.nextPositionedTool) >> 4][(currentTool.nextPositionedTool)
					& 0xF];

			prevToolOfMoovingTool.nextPositionedTool = (byte) (m.toX + (m.toY << 4));
			nextToolOfMoovingTool.prevPositionedTool = (byte) (m.toX + (m.toY << 4));

		}
		else { // current tool is the last tool in the board for his color
			currentTool.nextPositionedTool = (byte) (m.toX + (m.toY << 4));
			currentTool.prevPositionedTool = (byte) (m.toX + (m.toY << 4));

		}

		pbrd[m.toY][m.toX] = pbrd[m.fromY][m.fromX];
		pbrd[m.fromY][m.fromX] = null;

	}

	public boolean moveAndCalculateDeepLevelScore(Movement myMove, int deepLevel, Boolean color) {

		UndoMoveData umd = move(myMove);
		if (myMove.toX == 7 && myMove.toY == 7 && deepLevel == 5) {
			System.out.println("here");
		}

		if (deepLevel > 1 && !umd.thisMoveEatKing) {
			// printBoard();
			Movement myOponentMove = getBestMovement(!color, --deepLevel);
			if (myOponentMove == null)
				// no move found. this is not a suitable solution, so the score
				// is very bad
				throw new RuntimeException("myOponent move is null. he can't move...");
			else {
				myMove.score = myOponentMove.score;
				if (myOponentMove.thisMoveEatTheKing) {
					// by move m, my king may be eaten
					myMove.byThisMoveMyKingMayBeEaten = true;
					// myMove.score = color ? -1000 : 1000;

					// // check pat -- this code should be move to elsewhere...
					// Board b = (Board) DeepCopy.copy(this);
					// if (b.getBestMovement(!color, 1).thisMoveEatTheKing) {
					// myMove.score = 0;
					// myMove.pat = true;
					// }

				}
				if (myOponentMove.iLoosedByThisMoveAndMyAllBrotherMoves
						|| myOponentMove.byThisMoveMyKingMayBeEaten) {
					// m cause to loose so score is bad
					// myMove.score = color ? 1000 : -1000;

					// Identify PAT by check if color can eat the king
					// (deepLevel is 1)
					Board boardToCheckIfMatOrPat = (Board) DeepCopy.copy(this);
					// if (deepLevel == 3) {
					// System.out.println("here");
					// }

					Movement m1 = boardToCheckIfMatOrPat.getBestMovement(color, 1);
					if (m1.thisMoveEatTheKing) {
						myMove.mat = true;
					}
					else {
						myMove.pat = true;
						myMove.score = 0;
					}
				}

			}
		}
		// printBoard(m);
		undoMove(umd);
		// printBoard(m);
		// umd.kingEaten is true only if color eat the king in this move (and
		// not in a
		// deaper level)
		return umd.thisMoveEatKing;

	}

	private void undoMove(UndoMoveData umd) {

		// if (umd.movement.fromX == 7 && umd.movement.fromY == 6 &&
		// umd.movement.toX == 7
		// && umd.movement.toY == 5)
		// System.out.println("Here");
		// System.out
		// .println("-----------------------------Undo
		// -----------------------------");
		// System.out.println("Undo Before Movement: hatzrach: " +
		// umd.movement.hatzracha);
		// System.out.println("UMD last:" + umd.lastOfCurrent);
		// System.out.println("UMD next:" + umd.nextOfCurrent);
		// System.out.println("UMD currentFirst:" + umd.currentFirst);
		// if ((byte) (umd.movement.toX
		// + (umd.movement.toY << 4)) ==
		// pbrd[umd.movement.toY][umd.movement.toX].nextPositionedTool)
		// System.out.println("Found the bug - next");
		// if ((byte) (umd.movement.toX
		// + (umd.movement.toY << 4)) ==
		// pbrd[umd.movement.toY][umd.movement.toX].prevPositionedTool)
		// System.out.println("Found the bug - prev");
		// printToolChain();
		// printMovement(umd.movement);
		// printBoard(); // debug

		Tool current = pbrd[umd.movement.toY][umd.movement.toX];
		byte bytePosBeforeUndo = current.getBytePosition();
		// in Hatzraha
		if (umd.movement.hatzracha) {
			// Undo also Tura move, since its implicit by the Hatzrach

			// Rollback pbrd
			if (umd.current.xCoord == 6) { // Small Hatzrach

				updateToolLocation(
						pbrd[umd.movement.fromY][5],
						new Movement((short) 5, umd.movement.fromY, (short) 7, umd.movement.fromY));

			}
			else // Big Hatzrach
			{
				updateToolLocation(
						pbrd[umd.movement.fromY][3],
						new Movement((short) 3, umd.movement.fromY, (short) 0, umd.movement.fromY));

			}

		}
		if (umd.current.nameCode == 'k')
			((ToolKing) (umd.current)).hatzrachaAllowed = umd.hatzrachaAllowed;
		// Rollback Score
		boardScore = umd.boardScore;
		// Rollback pbrd
		pbrd[umd.movement.fromY][umd.movement.fromX] = pbrd[umd.movement.toY][umd.movement.toX];
		pbrd[umd.movement.toY][umd.movement.toX] = umd.eatenTool;

		// Rollback from Insert Queen
		if (umd.currentToolBeforeInsertQueen != null) {
			pbrd[umd.movement.fromY][umd.movement.fromX] = umd.currentToolBeforeInsertQueen;
		}

		// update coordination on tool itself
		current.xCoord = umd.movement.fromX;
		current.yCoord = umd.movement.fromY;

		// update links on current tool
		current.nextPositionedTool = umd.nextOfCurrent;
		current.prevPositionedTool = umd.lastOfCurrent;

		// update link of last tool
		getToolAtBoard(umd.lastOfCurrent).nextPositionedTool = current.getBytePosition();

		// update link next tool
		getToolAtBoard(umd.nextOfCurrent).prevPositionedTool = current.getBytePosition();

		// update first of current
		if (umd.currentFirst) {
			if (current.color)
				firstWhitePositiondeTool = current.getBytePosition();
			else
				firstBlackPositiondeTool = current.getBytePosition();
		}

		// Update link on eating tools (if any)
		if (umd.eatenTool != null)

		{
			// update link of last of eaten tool
			getToolAtBoard(umd.lastOfEaten).nextPositionedTool = umd.eatenTool.getBytePosition();

			// update link of next of eaten tool
			getToolAtBoard(umd.nextOfEaten).prevPositionedTool = umd.eatenTool.getBytePosition();

			// update first of eaten
			if (umd.eatenFirst) {
				if (umd.eatenTool.color)
					firstWhitePositiondeTool = umd.eatenTool.getBytePosition();
				else
					firstBlackPositiondeTool = umd.eatenTool.getBytePosition();

			}

		}

		// update kings
		if (current.nameCode == 'k') {
			if (current.color)
				whiteKingPos = current;
			else
				blackKingPos = current;
		}

		// System.out.println("Undo After Movement: hatzrach: " +
		// umd.movement.hatzracha);
		// printBoard(); // debug
		// printToolChain();
		// printMovement(umd.movement);

	}

	public Movement getBestMovement(boolean c, int deepLevel) {
		// deepLevel--;
		boolean matOnMe = true;
		int intColor = (c ? 1 : -1);
		int count = 2;
		Tool ptList = null;
		Movement bestMovement = null;
		if (c)
			ptList = getToolAtBoard(firstWhitePositiondeTool);
		else
			ptList = getToolAtBoard(firstBlackPositiondeTool);
		// add movements of all tools in board
		Tool pList1 = ptList;
		do {
			// add allowed movements of current tool in board
			if (ptList == null) {
				int k = 3;
				k++;
			}
			Movement bestMovementForCurrentTool = ptList.getBestMovement(this, deepLevel);
			// if (bestMovementForCurrentTool.toX == 1 &&
			// bestMovementForCurrentTool.toY == 4
			// && deepLevel == 3) {
			// System.out.println("here");
			// }

			if (bestMovementForCurrentTool != null) {
				matOnMe = bestMovementForCurrentTool.byThisMoveMyKingMayBeEaten && matOnMe;

				if (bestMovementForCurrentTool.thisMoveEatTheKing)// trying to

					return bestMovementForCurrentTool;
				if (bestMovement == null)
					bestMovement = bestMovementForCurrentTool;
				else {
					int scr = bestMovementForCurrentTool.score * intColor
							- bestMovement.score * intColor;
					if (scr > 0)
						bestMovement = bestMovementForCurrentTool;
					else if (scr == 0) {
						if (Math.random() < (1 / (double) count))
							bestMovement = bestMovementForCurrentTool;
						count++;

					}
				}
			}
			ptList = getToolAtBoard(ptList.nextPositionedTool);

		}
		while (ptList != pList1);

		// calculate best movement

		return bestMovement;
	}

	Tool getToolAtBoard(byte position) {

		return pbrd[position >> 4][position & 0xF];
	}

	boolean checkBoard() {

		byte p = firstWhitePositiondeTool;
		do {
			if (getToolAtBoard(p) == null)
				return false;
			p = getToolAtBoard(p).prevPositionedTool;
		}
		while (p != firstWhitePositiondeTool);
		p = firstWhitePositiondeTool;
		do {
			if (getToolAtBoard(p) == null)
				return false;
			p = getToolAtBoard(p).nextPositionedTool;
		}
		while (p != firstWhitePositiondeTool);

		p = firstBlackPositiondeTool;
		do {
			if (getToolAtBoard(p) == null)
				return false;
			p = getToolAtBoard(p).prevPositionedTool;

		}
		while (p != firstBlackPositiondeTool);
		p = firstBlackPositiondeTool;
		do {
			if (getToolAtBoard(p) == null)
				return false;
			p = getToolAtBoard(p).nextPositionedTool;

		}
		while (p != firstBlackPositiondeTool);

		return true;
	}

	public Movement getCorrectMovement(boolean c, int deepLevel) {
		// Check it Teko (no winner)
		// If number of tools of both sides is 2 and they don't have queen and
		// tura then its Teko
		if (isTeko())
			return null;

		Movement m = getBestMovement(c, deepLevel);

		// This code is in cover cases when c is in chess and deepLevel
		// calculate move may loose the king
		// so in that case calculate 3 level only.
		Board b = (Board) DeepCopy.copy(this);
		b.move(m);
		if (b.getBestMovement(!c, 1).thisMoveEatTheKing) {
			m = getBestMovement(c, 3);
		}
		// Check if Mat (There is a bug the move m do not distinct mat between
		// level 1 or deeper level, so addition check is done temporary
		//
		// if (m.mat) {
		Board b2 = (Board) DeepCopy.copy(this);
		b2.move(m);
		Movement mm = b2.getBestMovement(!c, 2);
		m.mat = mm.byThisMoveMyKingMayBeEaten;
		// int score = mm.score;
		// System.out.println("Score:" + score + " CurrentScore:" +
		// boardScore);
		// if (Math.abs(score - boardScore) > 300)
		// m.mat = true;
		// else
		// m.mat = false;
		// }
		return m;

	}

	public void printMovement(Movement m) {
		System.out.println(
				"from(x,y):" + m.fromX + "," + m.fromY + " to(x,y): " + m.toX + "," + m.toY);
		System.out.println("Hatzracha:" + m.hatzracha);

	}

	public boolean isTeko() {
		short currentOne = firstWhitePositiondeTool;
		int count = 0;
		boolean wCanWin = true;
		boolean bCanWin = true;

		do {
			Tool t = pbrd[currentOne >> 4][currentOne & 0xF];
			if (t.nameCode == 't' || t.nameCode == 'q')
				return false;
			currentOne = t.nextPositionedTool;
			count++;
		}
		while (currentOne != firstWhitePositiondeTool);
		if (count <= 2)
			wCanWin = false;
		count = 0;
		currentOne = firstBlackPositiondeTool;
		System.out.println("Black:");
		do {
			Tool t = pbrd[currentOne >> 4][currentOne & 0xF];
			if (t.nameCode == 't' || t.nameCode == 'q')
				return false;
			currentOne = t.nextPositionedTool;
			count++;
		}
		while (currentOne != firstBlackPositiondeTool);
		if (count <= 2)
			bCanWin = false;
		if (bCanWin == false && wCanWin == false)
			return true;
		else
			return false;
	}

	public void printToolChain() {
		short currentOne = firstWhitePositiondeTool;
		System.out.println("White:");
		do {
			Tool t = pbrd[currentOne >> 4][currentOne & 0xF];
			System.out.print("Current:" + currentOne);
			System.out.println(" " + t.nameCode + "at (y,x)" + t.yCoord + "," + t.xCoord);
			currentOne = t.nextPositionedTool;
		}
		while (currentOne != firstWhitePositiondeTool);

		currentOne = firstBlackPositiondeTool;
		System.out.println("Black:");
		do {
			Tool t = pbrd[currentOne >> 4][currentOne & 0xF];
			System.out.print("Current:" + currentOne);
			System.out.println(" " + t.nameCode + "at (y,x)" + t.yCoord + "," + t.xCoord);
			currentOne = t.nextPositionedTool;

		}
		while (currentOne != firstBlackPositiondeTool);

	}

	public void reWeight(Weights w) {
		reWeight(true, w);
		reWeight(false, w);

	}

	public void reWeight(boolean player, Weights w) {
		byte currentOne = player ? firstWhitePositiondeTool : firstBlackPositiondeTool;
		byte currentOne1 = currentOne;
		do {
			Tool t = pbrd[currentOne >> 4][currentOne & 0xF];
			t.setScore(w);
			currentOne = t.nextPositionedTool;
		}
		while (currentOne != currentOne1);

	}

}