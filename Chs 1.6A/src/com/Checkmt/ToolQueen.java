package com.Checkmt;

import java.util.LinkedList;
import java.util.List;

public class ToolQueen extends Tool {

	static int m[][] = { { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 },
			{ 2, -1 }, { 2, 1 } };
	static byte nameCode = 'q';

	public ToolQueen(boolean c) {
		super(c, nameCode, CheckMate.queenScore);

	}

	public void setScore(Weights w) {
		score = w.queenScore * (color ? 1 : -1);
	}

	@Override
	protected List<Movement> getMovements(Board b, short x, short y) {
		List<Movement> mvts = new LinkedList<Movement>();
		addRatzMoves(mvts, b, x, y);
		addTuraMoves(mvts, b, x, y);
		return mvts;
	}
}