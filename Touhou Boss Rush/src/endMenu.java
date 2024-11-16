// Names: Dylan Wang and Simon Gao
// Date: June 14, 2024
// Description: Method for end screen after death or clear

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class endMenu extends JPanel implements KeyListener {
	JFrame frame;
	JPanel buttonPanel;
	static BufferedImage menuSpriteSheet, bg;
	int lives;
	int bombs;
	int playerOption;
	int levelOption;
	
	//replay and main menu
	int optionSelect = 0;
	//replay, quit
	JLabel[] option = {new JLabel(), new JLabel()};
	JLabel[] menuOptionSelected = {new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(689, 144, 96, 32).getScaledInstance(160,80, java.awt.Image.SCALE_SMOOTH))),new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(690, 306, 68, 28).getScaledInstance(160,80, java.awt.Image.SCALE_SMOOTH)))};
	JLabel[] menuOption = {new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(785, 143, 96, 32).getScaledInstance(160,80, java.awt.Image.SCALE_SMOOTH))),new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(768, 304, 68, 28).getScaledInstance(160,80, java.awt.Image.SCALE_SMOOTH)))};

	//win or lose jlabel
	JLabel[] resultLabel = {new JLabel("YOU WIN!"), new JLabel("YOU LOSE.")};
	JLabel resultDisplay;
	JTextArea scoreLabel;
	
	//score
	HashMap <String, Integer> scoreboard = new HashMap <> ();
	int score;
	String name;

	// Description: Constructor method
	// Parameters: win or loss, score, lives, bombs, name, playeroption, leveloption
	// Return: ---
	public endMenu(int winLose, int score, int lives, int bombs, String name, int playerOption, int levelOption) {
		this.score = score;
		this.name = name;
		this.lives = lives;
		this.bombs = bombs;
		this.playerOption = playerOption;
		this.levelOption = levelOption;
		calcScore();
		
		frame = new JFrame();
		frame.addKeyListener(this);
		frame.setPreferredSize(new Dimension(900,700));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		resultDisplay = new JLabel();
		resultDisplay.setFont(new Font("Courier", Font.BOLD,40));
		resultDisplay.setText(resultLabel[winLose].getText());
		resultDisplay.setOpaque(true);
		resultDisplay.setBackground(Color.WHITE);
		resultDisplay.setBorder(new CompoundBorder(new LineBorder(Color.BLACK,5), new EmptyBorder(10, 20, 10, 20)));
		resultDisplay.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		String scoreText = "";
		if (winLose == 1) {
			// lose
			this.score = score;
			resultDisplay.setForeground(Color.red);
			scoreText += String.format("\n%5s%-15s%010d\n\n", "", "Game Score:", score);
			scoreText += String.format("%5s%-10s%-5s%5s\n", "", "Stage:", "N/A", "N/A");
			scoreText += String.format("%5s%-10s%-5s%5s\n", "", "Lives:", "N/A", "N/A");
			scoreText += String.format("%5s%-10s%-5s%5s\n\n", "", "Bombs:", "N/A", "N/A");
			scoreText += String.format("%5s%-15s%010d", "", "Total Score:", this.score);
		}
		else {
			// win
			resultDisplay.setForeground(Color.green);
			scoreText += String.format("\n%5s%-15s%010d\n\n", "", "Game Score:", score);
			scoreText += String.format("%5s%-10s%-5d%10d\n", "", "Stage:", levelOption+1, (levelOption + 1) * 2000000);
			scoreText += String.format("%5s%-10s%-5d%10d\n", "", "Lives:", lives, lives * 1000000);
			scoreText += String.format("%5s%-10s%-5d%10d\n\n", "", "Bombs:", bombs, bombs * 200000);
			scoreText += String.format("%5s%-15s%010d", "", "Total Score:", this.score);
		}
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


		resultDisplay.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		add(resultDisplay);

		buttonPanel.add(option[0]);
		buttonPanel.add(Box.createRigidArea(new Dimension(100,0)));
		buttonPanel.add(option[1]);
		
		//displaying score
		scoreLabel = new JTextArea();
		scoreLabel.setEditable(false);
		scoreLabel.setFocusable(false);
		scoreLabel.setPreferredSize(new Dimension(500, 300));
		scoreLabel.setMaximumSize(new Dimension(500, 300));
		scoreLabel.setOpaque(true);
		scoreLabel.setBackground(Color.WHITE);
		scoreLabel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK,5), new EmptyBorder(10, 20, 10, 20)));
		scoreLabel.setFont(new Font("Courier", Font.BOLD,20));
		scoreLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		scoreLabel.setText(scoreText);
		
		add(Box.createRigidArea(new Dimension(0,50)));
		add(scoreLabel);
		
		add(Box.createRigidArea(new Dimension(0,100)));
		buttonPanel.setOpaque(false);
		add(buttonPanel);

		option[0].setIcon(menuOptionSelected[0].getIcon());
		option[1].setIcon(menuOption[1].getIcon());


		frame.add(this);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		setScoreboard();
	}
	
	// Description: Method to calculate score at end of game
	// Parameters: ---
	// Return: ---
	public void calcScore() {
		// Level rewards
		score += (levelOption + 1) * 2000000;
		// Lives rewards
		score += lives * 1000000;
		// Bombs
		score += bombs * 200000;
		
	}
		
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int a = e.getKeyCode();
		if (a == KeyEvent.VK_RIGHT) {
			playSound("switch.wav");
			//right arrow key
			if (optionSelect == 1) {
				option[optionSelect].setIcon(menuOption[optionSelect].getIcon());
				optionSelect = 0;
				option[optionSelect].setIcon(menuOptionSelected[optionSelect].getIcon());

			}
			else {
				option[optionSelect].setIcon(menuOption[optionSelect].getIcon());
				optionSelect ++;
				option[optionSelect].setIcon(menuOptionSelected[optionSelect].getIcon());
			}
		}

		else if (a == KeyEvent.VK_LEFT) {
			playSound("switch.wav");
			//left arrow keys
			if (optionSelect == 0) {
				option[optionSelect].setIcon(menuOption[optionSelect].getIcon());
				optionSelect = 1;
				option[optionSelect].setIcon(menuOptionSelected[optionSelect].getIcon());

			}
			else {
				option[optionSelect].setIcon(menuOption[optionSelect].getIcon());
				optionSelect --;
				option[optionSelect].setIcon(menuOptionSelected[optionSelect].getIcon());
			}
		}

		else if (a == KeyEvent.VK_ENTER || a == KeyEvent.VK_Z ) {
			playSound("select.wav");
			if (optionSelect == 0) {
				JFrame frame = new JFrame ("Touhou");
				Main myPanel = new Main (playerOption, levelOption, frame, name);
				frame.add(myPanel);
				frame.addKeyListener(myPanel);
				frame.pack();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				this.frame.dispose();
			}
			else {
				new Menu();
				this.frame.dispose();
			}
		}

	}

	// Description: Method to play a sound
	// Parameters: File name
	// Return: ---
	public static void playSound(String file)
	{
	    try
	    {
	        Clip clip = AudioSystem.getClip();
	        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("Sounds/" + file));
	        clip.open(inputStream);
	        clip.addLineListener(new CloseClipWhenDone());
	        clip.start();
	    }
	    catch (Exception e)
	    {
	    	System.out.println("Sound Error");
	    }        
	}
	
	// Description: Class to close a sound once it is done playing
	// Parameters: ---
	// Return: ---
	private static class CloseClipWhenDone implements LineListener
	{
	    public void update(LineEvent event)
	    {
	        if (event.getType().equals(LineEvent.Type.STOP))
	        {
	            Line soundClip = event.getLine();
	            soundClip.close();
	        }
	    }
	}
		
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//background
		g.drawImage(bg, 0,0, 900, 700, this);
	}
	
	// Description: Updates the textfile for the scoreboard to display on main menu
	// Parameters: ---
	// Return: ---
	public void setScoreboard() {
		
		try {
			Scanner s = new Scanner(new File("score.txt"));
			while (s.hasNextLine()) {
				StringTokenizer line = new StringTokenizer(s.nextLine());
				scoreboard.put(line.nextToken(), Integer.parseInt(line.nextToken()));
			}
		}
		catch (FileNotFoundException e) {
			
		}
		
		//sets the scoreboard
		if (scoreboard.containsKey(name)) {
			if (score > scoreboard.get(name)) {
				scoreboard.put(name, score);
			}	
		}

		else  {
			scoreboard.put(name, score);
		}
		
		
		HashSet <String> score = new HashSet<>(scoreboard.keySet());
		ArrayList <Score> scoreSort = new ArrayList <Score> ();
		Iterator <String>key = score.iterator();
		while (key.hasNext()) {
			String value = key.next();
			scoreSort.add(new Score(value, scoreboard.get(value)));
		}
		Collections.sort(scoreSort);
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter("score.txt"));
			for (int i = 0; i < scoreSort.size(); i++) {
				outFile.println(scoreSort.get(i).getName() + " " + scoreSort.get(i).getScore());
			}
			outFile.close();
		}
		catch (IOException e) {
			
		}

	}
	

	public static void main (String[] args) {
		try {
			menuSpriteSheet = ImageIO.read(new File("Images/menu.png"));
			bg = ImageIO.read(new File("Images/background2.png"));

		}
		catch(IOException e) {

		}
	}
}

