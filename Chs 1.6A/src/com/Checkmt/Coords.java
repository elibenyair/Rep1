package com.Checkmt;

import java.io.Serializable;

public class Coords implements Serializable {

	Coords(short x1, short y1) {
		x = x1;
		y = y1;
		return;
	}

	short x;
	short y;

}
