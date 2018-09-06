package snakeminigame;

public class Snake {
	private int posX;
	private int posY;
	private int direction;
	private int speed;

	public Snake(int x, int y, int direction, int speed) {
		posX = x;
		posY = y;
		this.setDirection(direction);
		this.speed = speed;
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

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
