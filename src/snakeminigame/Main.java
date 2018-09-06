package snakeminigame;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame gameframe = new JFrame();
		Game ng = new Game();
		gameframe.setBounds(500, 50, 1000, 900);
		gameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameframe.setTitle("Snake Game");
		gameframe.add(ng);
		gameframe.setVisible(true);
	}
}
