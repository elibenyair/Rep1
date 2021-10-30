package com.Checkmt;

import java.util.LinkedList;
import java.util.List;

public class ToolKing extends Tool {

	// temporary sets to false. should be true
	public boolean hatzrachaAllowed = true;

	static int m[][] = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 },
			{ 1, 1 } };
	static byte nameCode = 'k';

	public ToolKing(boolean c) {
		super(c, nameCode, CheckMate.kingScore);

	}

	public void setScore(Weights w) {
		score = w.kingScore * (color ? 1 : -1);
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

		// small Hatzracha
		if (hatzrachaAllowed) {
			// small Hatzracha
			if ((b.pbrd[y][5] == null) && (b.pbrd[y][6] == null) && (b.pbrd[y][7] != null))
				if (b.pbrd[y][7].nameCode == 't')
					mvts.add(new Movement(x, y, (short) 6, y, true));
			// big Hazracha
			if (b.pbrd[y][1] == null && b.pbrd[y][2] == null && b.pbrd[y][3] == null
					&& b.pbrd[y][0] != null)
				if (b.pbrd[y][0].nameCode == 't')
					mvts.add(new Movement(x, y, (short) 2, y, true));

		}
		return mvts;
	}
}
