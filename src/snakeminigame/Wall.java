package snakeminigame;

public class Wall {
	//y axis
	private int width;
	//x axis
	private int length;
	private int posX;
	private int posY;

	public Wall(int x, int y, int length, int width) {
		posX = x;
		posY = y;
		this.width = width;
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
}
