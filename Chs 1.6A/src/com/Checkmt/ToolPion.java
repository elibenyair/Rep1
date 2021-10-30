package com.Checkmt;

import java.util.LinkedList;
import java.util.List;

public class ToolPion extends Tool {
	static int m[][] = { { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 },
			{ 2, -1 }, { 2, 1 } };
	static byte nameCode = 'p';

	public ToolPion(boolean c) {
		super(c, nameCode, CheckMate.pionScore);

	}

	public void setScore(Weights w) {
		score = w.pionScore * (color ? 1 : -1);
	}

	@Override
	protected List<Movement> getMovements(Board b, short x, short y) {
		List<Movement> mvts = new LinkedList<Movement>();
		// if (y == 0 || x == 7)
		// return mvts;
		// white movements
		if (b.pbrd[y][x].color) {
			if (y < 7 && b.pbrd[y + 1][x] == null) {
				mvts.add(new Movement(x, y, x, (short) (y + 1)));
				// Double move of Pion
				if (y == 1 && b.pbrd[y + 2][x] == null)
					mvts.add(new Movement(x, y, x, (short) (y + 2)));
			}
			// Check if can eat
			if (x < 7 && b.pbrd[y + 1][x + 1] != null)
				// try to eat to the right
				tryToAddAndCheckEat(mvts, new Movement(x, y, (short) (x + 1), (short) (y + 1)), b);
			if (x > 0 && b.pbrd[y + 1][x - 1] != null)
				// try to eat to the left
				tryToAddAndCheckEat(mvts, new Movement(x, y, (short) (x - 1), (short) (y + 1)), b);
		}
		// Black movements
		else {
			if (y > 0 && b.pbrd[y - 1][x] == null) {
				mvts.add(new Movement(x, y, x, (short) (y - 1)));
				// Double move of Pion
				if (y == 6 && b.pbrd[y - 2][x] == null)
					mvts.add(new Movement(x, y, x, (short) (y - 2)));
			}
			if (x < 7 && b.pbrd[y - 1][x + 1] != null)
				tryToAddAndCheckEat(mvts, new Movement(x, y, (short) (x + 1), (short) (y - 1)), b);
			if (x > 0 && b.pbrd[y - 1][x - 1] != null)
				tryToAddAndCheckEat(mvts, new Movement(x, y, (short) (x - 1), (short) (y - 1)), b);
		}
		return mvts;
	}
}
