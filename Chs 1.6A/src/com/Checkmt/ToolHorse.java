package com.Checkmt;

import java.util.LinkedList;
import java.util.List;

public class ToolHorse extends Tool {

	static transient int m[][] = { { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, { 1, -2 },
			{ 1, 2 }, { 2, -1 }, { 2, 1 } };
	static byte nameCode = 'h';

	public ToolHorse(boolean color) {
		super(color, nameCode, CheckMate.horseScore);
	}

	public void setScore(Weights w) {
		score = w.horseScore * (color ? 1 : -1);

	}

	@Override
	protected List<Movement> getMovements(Board b, short x, short y) {
		List<Movement> mvts = new LinkedList<Movement>();
		for (int i = 0; i < m.length; i++) {
			short toX = (short) (x + m[i][0]);
			short toY = (short) (y + m[i][1]);

			if ((toX >= 0) && (toX < 8) && (toY >= 0) && (toY < 8)) {
				if (b.pbrd[toY][toX] != null) {
					if (!(b.pbrd[toY][toX].color) == color)

						mvts.add(new Movement(x, y, toX, toY));
				}
				else
					mvts.add(new Movement(x, y, toX, toY));
			}

		}
		return mvts;
	}

}
