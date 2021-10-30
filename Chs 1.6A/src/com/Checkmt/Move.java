package com.Checkmt;

import java.io.Serializable;

public class Move implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Move(int[] xy) {
		moveX = xy[0];
		moveY = xy[1];
	}

	int moveX;
	int moveY;

}
