package com.Checkmt;

import java.util.LinkedList;
import java.util.List;

public class ToolRatz extends Tool {

	static int m[][] = { { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 },
			{ 2, -1 }, { 2, 1 } };
	static byte nameCode = 'r';

	public ToolRatz(boolean c) {
		super(c, nameCode, CheckMate.ratzScore);

	}

	public void setScore(Weights w) {
		score = w.ratzScore * (color ? 1 : -1);

	}

	@Override
	protected List<Movement> getMovements(Board b, short x, short y) {
		List<Movement> mvts = new LinkedList<Movement>();
		addRatzMoves(mvts, b, x, y);
		return mvts;
	}
}
