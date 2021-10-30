package com.Checkmt;

public class Weights {
	int pionScore = 10;
	int ratzScore = 30;
	int horseScore = 30;
	int turaScore = 50;
	int queenScore = 100;
	int kingScore = 500;

	public Weights(int pion, int horse, int ratz, int tura, int queen, int king) {
		pionScore = pion;
		ratzScore = ratz;
		horseScore = horse;
		turaScore = tura;
		queenScore = queen;
		kingScore = king;
		return;
	}
}
