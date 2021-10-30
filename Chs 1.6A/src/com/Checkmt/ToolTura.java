package com.Checkmt;

import java.util.LinkedList;
import java.util.List;

public class ToolTura extends Tool {

	static int m[][] = { { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 },
			{ 2, -1 }, { 2, 1 } };
	static byte nameCode = 't';

	public ToolTura(boolean c) {
		super(c, nameCode, CheckMate.turaScore);

	}

	public void setScore(Weights w) {
		score = w.turaScore * (color ? 1 : -1);

	}

	@Override
	protected List<Movement> getMovements(Board b, short x, short y) {
		List<Movement> mvts = new LinkedList<Movement>();
		// for debug mvts.add(new Movement((short) 0, (short) 2, (short) 0,
		// (short) 0));
		addTuraMoves(mvts, b, x, y);
		return mvts;
	}
}
