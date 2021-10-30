package com.Checkmt;

public class UndoMoveData {

	Movement movement = null;
	Tool eatenTool = null;
	int boardScore = 0;
	boolean thisMoveEatKing = false;
	byte lastOfCurrent = 0;
	byte nextOfCurrent = 0;
	byte lastOfEaten = 0;
	byte nextOfEaten = 0;
	boolean currentFirst = false;
	boolean eatenFirst = false;
	Tool currentToolBeforeInsertQueen = null;
	boolean hatzrachaAllowed = false;
	Tool current = null;

	UndoMoveData(Movement m, Board b) {

		movement = m;
		boardScore = b.boardScore;
		eatenTool = b.pbrd[m.toY][m.toX];
		current = b.pbrd[m.fromY][m.fromX];
		hatzrachaAllowed = (current instanceof ToolKing) ? ((ToolKing) (current)).hatzrachaAllowed
				: false;
		lastOfCurrent = current.prevPositionedTool;
		nextOfCurrent = current.nextPositionedTool;
		if (eatenTool != null) {
			lastOfEaten = eatenTool.prevPositionedTool;
			nextOfEaten = eatenTool.nextPositionedTool;

			if (b.firstWhitePositiondeTool == eatenTool.getBytePosition()
					|| b.firstBlackPositiondeTool == eatenTool.getBytePosition())
				eatenFirst = true;

		}
		byte bt = current.getBytePosition();
		if (b.firstWhitePositiondeTool == bt || b.firstBlackPositiondeTool == bt)
			currentFirst = true;

		return;
	}
}
