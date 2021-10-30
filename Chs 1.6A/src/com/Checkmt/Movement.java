package com.Checkmt;

public class Movement {
	short fromX;
	short fromY;
	short toX;
	short toY;
	int score = 0;
	boolean hatzracha = false;
	boolean thisMoveEatTheKing = false;
	boolean byThisMoveMyKingMayBeEaten = false;
	boolean mat = false;
	boolean pat = false;
	int deepLevel = 0;

	boolean iLoosedByThisMoveAndMyAllBrotherMoves = false;

	// boolean iWinByThisMove = false;

	public Movement(short fX, short fY, short tX, short tY) {
		fromX = fX;
		fromY = fY;
		toX = tX;
		toY = tY;
		return;
	}

	public Movement(short fX, short fY, short tX, short tY, boolean h) {
		fromX = fX;
		fromY = fY;
		toX = tX;
		toY = tY;
		hatzracha = h;
		return;
	}

	public void setScore(int s) {
		score = s;
	}

	public void printMovement() {
		System.out.println("Move from x:" + fromX + " , y:" + fromY);
		System.out.println("Move to x:" + toX + " , y:" + toY);
	}
}