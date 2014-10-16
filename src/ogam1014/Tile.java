package ogam1014;

import java.awt.Point;

public enum Tile{
	TL, T, TR, WATERBORDER_TL, WATERBORDER_T, WATERBORDER_TR,
	TTL, TT, TTR, WATERBORDER_L, GRASS, WATERBORDER_R,
	L, GRASS2, R, WATERBORDER_BL, WATERBORDER_B, WATERBORDER_BR,
	TLa, aT, TaR, WATaERBORDER_TL, WATERaBORDER_T, WATERBORaDER_TR,
	TaTL, aTT, aTTR, WATERBOaRDER_L, GRaASS, WATEaRBORDER_R,
	La, GaRASS2, aR, WAaTERBORDER_BL, WATaERBORDER_B, WATERaBORDER_BR,
	TgL, Tg, TRg, WATERBORDEgR_TL, WATgERBORDER_T, WATERBgORDER_TR,
	TTgL, gTT, gTTR, WATERBgORDER_L, GRAgSS, WATERBORDEgR_R,
	VOID;

	public static final int SIZE = 16;
	
	public boolean isWall() {
		return this == TL
			|| this == TR
			|| this == T
			|| this == VOID;
	}
	
	public static Point convertToTilePos(double x, double y){
		return new Point((int) (x / SIZE), (int) (y / SIZE));
	}
}