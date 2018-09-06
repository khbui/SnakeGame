package snakeminigame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener {
	private final int HORIZONTAL = 1000;
	private final int VERTICAL = 900;
	private final int EAST = 1;
	private final int WEST = -1;
	private final int NORTH = 2;
	private final int SOUTH = -2;
	private List<Snake> snakes;
	private List<Wall> walls;
	private Food food;
	private Random r;
	private int level;
	private Timer timer;
	private int timerate;
	private int score;
	private boolean lose;

	public Game() {
		timerate = 40;
		lose = false;
		level = 1;
		score = 0;
		r = new Random();
		snakes = new ArrayList<Snake>();
		walls = new ArrayList<Wall>();
		// Create wall barrier
		for (int i = 0; i < 6; i++) {
			boolean validWall = true;
			int tx = r.nextInt(910);
			int ty = r.nextInt(800);
			int tw = r.nextInt(60) + 30;
			int tl = r.nextInt(60) + 50;
			Wall w = new Wall(tx, ty, tw, tl);
			for (Wall cw : walls) {
				if (overlappedWall(cw, w)) {
					validWall = false;
					break;
				}
			}
			if (validWall) {
				walls.add(w);
			} else {
				i--;
				validWall = true;
			}
		}
		// Create snake
		for (int i = 20; i >= 0; i--) {
			Snake s = new Snake(i * 10, VERTICAL / 2, EAST, 10);
			snakes.add(s);
		}
		// Create food
		food = new Food(HORIZONTAL / 2, VERTICAL / 2);
		while (eatFood(snakes.get(0), food) || hiddenFood(walls, food)) {
			int foodnewX = r.nextInt(960);
			int foodnewY = r.nextInt(860);
			food.setPosX(foodnewX);
			food.setPosY(foodnewY);
		}

		addKeyListener(this);
		setFocusable(true);
		// setFocusTraversalKeysEnabled(false);
		timer = new Timer(timerate, this);
		timer.start();
	}

	// Draw function
	public void paint(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		g.setColor(Color.white);
		g.fillRect(0, 0, HORIZONTAL, VERTICAL);
		
		// Draw snake head, and then its body
		g.setColor(Color.red);
		g.fillRect(snakes.get(0).getPosX(), snakes.get(0).getPosY(), snakes
				.get(0).getSpeed(), snakes.get(0).getSpeed());
		g.setColor(Color.black);
		for (int i = snakes.size() - 1; i > 0; i--) {
			g.fillRect(snakes.get(i).getPosX(), snakes.get(i).getPosY(), snakes
					.get(i).getSpeed(), snakes.get(i).getSpeed());
		}
		// Draw food
		g.setColor(Color.blue);
		g.fillRect(food.getPosX(), food.getPosY(), 10, 10);
		// Draw wall
		g.setColor(Color.gray);
		for (Wall w : walls) {
			g.fillRect(w.getPosX(), w.getPosY(), w.getWidth(), w.getLength());
		}
		if (lose) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 34));
			g.setColor(Color.red);
			g.drawString("GAME OVER", HORIZONTAL / 2 - 90, VERTICAL / 2);
			timer.stop();
		}
		// Draw information
				g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
				g.setColor(Color.black);
				g.drawString("Score: " + score + " - Level: " + level, HORIZONTAL - 200,
						VERTICAL - 50);
		g.dispose();
	}

	// Action listener
	public void actionPerformed(ActionEvent e) {
		// Update the body
		for (int i = snakes.size() - 1; i > 0; i--) {
			snakes.get(i).setPosX(snakes.get(i - 1).getPosX());
			snakes.get(i).setPosY(snakes.get(i - 1).getPosY());
			snakes.get(i).setDirection(snakes.get(i - 1).getDirection());
		}
		// Update the head
		switch (snakes.get(0).getDirection()) {
		case EAST:
			snakes.get(0).setPosX(
					snakes.get(0).getPosX() + snakes.get(0).getSpeed());
			if (snakes.get(0).getPosX() > 975)
				snakes.get(0).setPosX(0);
			break;
		case WEST:
			snakes.get(0).setPosX(
					snakes.get(0).getPosX() - snakes.get(0).getSpeed());
			if (snakes.get(0).getPosX() < 2)
				snakes.get(0).setPosX(975);
			break;
		case NORTH:
			snakes.get(0).setPosY(
					snakes.get(0).getPosY() - snakes.get(0).getSpeed());
			if (snakes.get(0).getPosY() < 2)
				snakes.get(0).setPosY(875);
			break;
		case SOUTH:
			snakes.get(0).setPosY(
					snakes.get(0).getPosY() + snakes.get(0).getSpeed());
			if (snakes.get(0).getPosY() > 875)
				snakes.get(0).setPosY(0);
			break;
		default:
			break;
		}
		if (eatFood(snakes.get(0), food)) {
			Snake newS = new Snake(snakes.get(snakes.size() - 1).getPosX(),
					snakes.get(snakes.size() - 1).getPosY(), snakes.get(
							snakes.size() - 1).getDirection(), snakes.get(
							snakes.size() - 1).getSpeed());
			snakes.add(newS);
			do {
				int foodnewX = r.nextInt(980);
				int foodnewY = r.nextInt(880);
				food.setPosX(foodnewX);
				food.setPosY(foodnewY);
			} while (eatFood(snakes.get(0), food) || hiddenFood(walls, food));
			score++;
			if (score % 5 == 0 && score != 0) {
				level++;
				timerate -= 5;
				timer.setDelay(timerate);
				boolean validWall = true;
//				do {
					int tx = r.nextInt(910);
					int ty = r.nextInt(800);
					int tw = r.nextInt(60) + 30;
					int tl = r.nextInt(60) + 50;
					Wall w = new Wall(tx, ty, tw, tl);
					for (Wall cw : walls) {
						if (overlappedWall(cw, w)) {
							validWall = false;
							break;
						}
					}
					if (hiddenFood(walls, food))
						validWall = false;
					if (validWall) {
						walls.add(w);
					} else {
						validWall = true;
					}
//				} while (validWall);
			}
		}
		if (accident(snakes.get(0), walls))
			lose = true;
		repaint();
	}

	// Key listener
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			if (snakes.get(0).getDirection() == NORTH
					|| snakes.get(0).getDirection() == SOUTH) {
				snakes.get(0).setDirection(EAST);
			}
			break;
		case KeyEvent.VK_LEFT:
			if (snakes.get(0).getDirection() == NORTH
					|| snakes.get(0).getDirection() == SOUTH) {
				snakes.get(0).setDirection(WEST);
			}
			break;
		case KeyEvent.VK_UP:
			if (snakes.get(0).getDirection() == EAST
					|| snakes.get(0).getDirection() == WEST) {
				snakes.get(0).setDirection(NORTH);
			}
			break;
		case KeyEvent.VK_DOWN:
			if (snakes.get(0).getDirection() == EAST
					|| snakes.get(0).getDirection() == WEST) {
				snakes.get(0).setDirection(SOUTH);
			}
			break;
		default:
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		// Do nothing
	}

	public void keyTyped(KeyEvent e) {
		// Do nothing
	}

	// Helper function
	private boolean eatFood(Snake head, Food f) {
		Rectangle r1 = new Rectangle(head.getPosX(), head.getPosY(),
				head.getSpeed(), head.getSpeed());
		Rectangle r2 = new Rectangle(f.getPosX(), f.getPosY(), 10, 10);
		return r1.intersects(r2);
	}

	private boolean overlappedWall(Wall w, Wall t) {
		Rectangle r1 = new Rectangle(w.getPosX(), w.getPosY(), w.getWidth(),
				w.getLength());
		Rectangle r2 = new Rectangle(t.getPosX(), t.getPosY(), t.getWidth(),
				t.getLength());
		return r1.intersects(r2);
	}

	private boolean hiddenFood(List<Wall> lw, Food f) {
		Rectangle r2 = new Rectangle(f.getPosX(), f.getPosY(), 10, 10);
		for (Wall w : lw) {
			Rectangle r1 = new Rectangle(w.getPosX(), w.getPosY(),
					w.getWidth(), w.getLength());
			if (r2.intersects(r1))
				return true;
		}
		return false;
	}

	private boolean accident(Snake head, List<Wall> lw) {
		Rectangle r1 = new Rectangle(head.getPosX(), head.getPosY(),
				head.getSpeed(), head.getSpeed());
		for (Wall w : lw) {
			Rectangle r2 = new Rectangle(w.getPosX(), w.getPosY(),
					w.getWidth(), w.getLength());
			if (r2.intersects(r1))
				return true;
		}
		return false;
	}

}
