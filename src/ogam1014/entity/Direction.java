package ogam1014.entity;

public enum Direction {
	UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);

	final public int x;
	final public int y;

	private Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
