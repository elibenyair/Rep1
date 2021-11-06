package com.Checkmt;

public class BoardsInitPositions {

	static boolean BLACK = false;
	static boolean WHITE = true;

	static Tool startBoard[][] = {
			{ new ToolTura(WHITE), new ToolHorse(WHITE), new ToolRatz(WHITE), new ToolQueen(WHITE),
					new ToolKing(WHITE), new ToolRatz(WHITE), new ToolHorse(WHITE),
					new ToolTura(WHITE) },

			{ new ToolPion(WHITE), new ToolPion(WHITE), new ToolPion(WHITE), new ToolPion(WHITE),
					new ToolPion(WHITE), new ToolPion(WHITE), new ToolPion(WHITE),
					new ToolPion(WHITE) },

			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },

			{ new ToolPion(BLACK), new ToolPion(BLACK), new ToolPion(BLACK), new ToolPion(BLACK),
					new ToolPion(BLACK), new ToolPion(BLACK), new ToolPion(BLACK),
					new ToolPion(BLACK) },
			{ new ToolTura(BLACK), new ToolHorse(BLACK), new ToolRatz(BLACK), new ToolQueen(BLACK),
					new ToolKing(BLACK), new ToolRatz(BLACK), new ToolHorse(BLACK),
					new ToolTura(BLACK) },

	};
	static Tool hatzraha[][] = {
			{ new ToolTura(WHITE), null, null, null, new ToolKing(WHITE), null, null,
					new ToolTura(WHITE) },

			// { new ToolPion(WHITE), new ToolPion(WHITE), new ToolPion(WHITE),
			// new ToolPion(WHITE),
			// new ToolPion(WHITE), new ToolPion(WHITE), new ToolPion(WHITE),
			// new ToolPion(WHITE) },
			{ null, null, null, null, null, null, null, null },

			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ new ToolPion(BLACK), null, null, null, null, null, null, new ToolPion(BLACK) },

			{ null, null, null, null, null, new ToolKing(BLACK), null, null, null },

	};
	static Tool startBoardTest1[][] = {
			{ new ToolTura(BLACK), null, null, null, new ToolKing(WHITE), null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, new ToolRatz(WHITE), new ToolKing(BLACK), null, null, null },
			{ null, new ToolPion(BLACK), null, null, null, null, null, null },
			{ null, new ToolPion(BLACK), new ToolPion(WHITE), null, null, null, null, null },
			{ null, null, new ToolTura(WHITE), null, null, null, null, null },
			{ null, null, null, null, null, new ToolHorse(WHITE), null, null },
			{ null, null, null, null, null, null, null, null } };

	static Tool startBoardTest2[][] = { { null, null, null, null, null, null, null, null },
			{ new ToolPion(WHITE), null, new ToolTura(BLACK), new ToolKing(WHITE), null,
					new ToolTura(WHITE), null, null },

			{ new ToolPion(BLACK), null, null, null, null, null, null, null },

			{ null, null, null, new ToolKing(BLACK), null, null, null, new ToolPion(WHITE) },
			{ null, null, null, null, new ToolTura(WHITE), null, null, new ToolPion(BLACK) },
			{ null, null, null, null, null, null, new ToolHorse(WHITE), null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },

			{ null, null, null, null, null, null, null, new ToolKing(WHITE) },

	};
	static Tool checkMatBoard[][] = {
			{ null, null, null, new ToolQueen(WHITE), null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ new ToolTura(WHITE), null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },

			{ null, null, new ToolKing(WHITE), null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, new ToolKing(BLACK), null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null } };

	static Tool checkMatIn1[][] = {
			{ null, null, null, new ToolQueen(WHITE), null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, new ToolTura(WHITE), null, null, null, null, null, null, null },

			{ null, null, null, null, null, null, null, null },
			{ new ToolKing(BLACK), null, null, null, null, null, null, null },
			{ null, null, null, new ToolKing(WHITE), null, null, null, null },
			{ null, null, null, null, null, null, null, null } };

	static Tool wrongMat[][] = { { null, null, null, new ToolQueen(WHITE), null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ new ToolTura(WHITE), null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },

			{ null, null, null, null, null, null, null, null },
			{ null, new ToolKing(WHITE), null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, new ToolKing(BLACK), null, null, null, null, null } };

	static Tool checkPat[][] = { { null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },

			{ null, new ToolKing(WHITE), null, null, null, null, null, null },
			{ null, new ToolHorse(BLACK), null, new ToolRatz(WHITE), null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ new ToolKing(BLACK), null, null, null, null, null, null, null } };

	static Tool insertQueen[][] = { { null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },

			{ null, new ToolKing(WHITE), null, null, null, null, null, null },
			{ null, new ToolHorse(BLACK), null, new ToolRatz(WHITE), null, null, null, null },
			{ null, null, null, null, null, null, null, new ToolPion(WHITE) },
			{ new ToolKing(BLACK), null, null, null, null, null, null, null } };

	static Tool weightsTest[][] = { { null, null, null, null, null, null, null, null },
			{ null, new ToolTura(BLACK), null, null, null, null, new ToolPion(WHITE),
					new ToolPion(WHITE) },
			{ new ToolHorse(BLACK), new ToolKing(WHITE), null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, new ToolPion(BLACK), new ToolPion(BLACK) },
			{ null, null, null, null, null, null, null, new ToolKing(BLACK) } };

	static Tool test98765[][] = { { null, null, null, null, null, null, null, null },
			{ null, new ToolTura(BLACK), null, null, null, null, new ToolPion(WHITE),
					new ToolPion(WHITE) },
			{ new ToolHorse(BLACK), new ToolKing(WHITE), null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, new ToolPion(BLACK), new ToolPion(BLACK) },
			{ null, null, null, null, null, null, null, new ToolKing(BLACK) } };

	static Tool test987654321[][] = { { null, null, null, null, null, null, null, null },
			{ null, new ToolTura(BLACK), null, null, null, null, new ToolPion(WHITE),
					new ToolPion(WHITE) },
			{ new ToolHorse(BLACK), new ToolKing(WHITE), null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, new ToolPion(BLACK), new ToolPion(BLACK) },
			{ null, null, null, null, null, null, null, new ToolKing(BLACK) } };

}
