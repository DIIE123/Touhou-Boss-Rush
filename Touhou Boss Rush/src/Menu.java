// Names: Dylan Wang and Simon Gao
// Date: June 14, 2024
// Description: Method for the menu

import javax.imageio.ImageIO;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.awt.*;
import javax.sound.sampled.*;

public class Menu extends JPanel implements KeyListener, ActionListener{
	JFrame frame;

	//music
	Clip music;

	//thread boolean
	boolean threadLoop = true;


	//background 
	static BufferedImage bg;
	static BufferedImage bg2;



	//Title
	JLabel title;

	static BufferedImage menuSpriteSheet;



	//main menu, character, level, enter name, about, scoreboard
	JPanel[] menus = {new JPanel(), new JPanel(), new JPanel(), new JPanel(),new JPanel(),new JPanel()};
	int menuNumber = 0;


	//main menu select

	//play, about, player data (scoreboard), exit
	JLabel[] menuDisplay = {new JLabel(), new JLabel(), new JLabel(), new JLabel()};
	JLabel[] menuOptionSelected = {new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(689, 49, 154, 31).getScaledInstance(300,60, java.awt.Image.SCALE_SMOOTH))),new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(691, 238, 171, 35).getScaledInstance(300,60, java.awt.Image.SCALE_SMOOTH))),new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(691,176,162,32).getScaledInstance(300,60, java.awt.Image.SCALE_SMOOTH))),new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(689, 303, 66, 31).getScaledInstance(160,80, java.awt.Image.SCALE_SMOOTH)))};
	JLabel[] menuOption = {new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(850, 49, 154, 31).getScaledInstance(300,60, java.awt.Image.SCALE_SMOOTH))),new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(867, 238, 171, 35).getScaledInstance(300,60, java.awt.Image.SCALE_SMOOTH))),new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(866, 175, 162, 32).getScaledInstance(300,60, java.awt.Image.SCALE_SMOOTH))),new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(767, 303, 66, 31).getScaledInstance(160,80, java.awt.Image.SCALE_SMOOTH)))};




	int optionSelect = 0;

	//character select
	JPanel charPanel;
	JLabel charTextLabel;
	JLabel charLabel;
	JLabel stats;
	JLabel[] statsList = {new JLabel("<html>Reimu Hakurei<br>Attack Type: Homing<br>Bomb: Fantasy Seal<br>Speed: Slow<br></html>"), new JLabel("<html>Marisa Kirisame<br>Attack Type: Straight<br>Bomb: Master Spark<br>Speed: Fast<br></html>"), new JLabel("<html>Sanae Kochiya<br>Attack Type: Spread<br>Bomb: Stardust Reverie<br>Speed: Medium<br></html>")};


	//char 1, char 2, char 3
	JLabel[] charOption = {new JLabel(new ImageIcon("Images/Touhou Reimu Portrait.png")),new JLabel(new ImageIcon("Images/Touhou Marisa Portrait.png")),new JLabel(new ImageIcon("Images/Touhou Sanae Portrait.png"))};

	int charSelect = 0;

	//level select
	JLabel levelTitle;
	JLabel levelLabel;
	//easy normal hard
	JLabel[] levelOption = {new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(716, 860, 239, 93).getScaledInstance(400, 180, java.awt.Image.SCALE_SMOOTH))),new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(716, 953, 239, 93).getScaledInstance(400, 180, java.awt.Image.SCALE_SMOOTH))),new JLabel(new ImageIcon(menuSpriteSheet.getSubimage(716, 1055, 239, 93).getScaledInstance(400, 180, java.awt.Image.SCALE_SMOOTH)))};
	int level = 0;

	//how to play
	JLabel infoLabel;


	//enter name
	JTextField enterName;
	JLabel nameLabel;
	JButton nameButton;
	String name;
	

	//scoreboard
	JLabel scoreTextLabel;
	JTextArea scoreLabel;



	// Description: Constructor method
	// Parameters: ---
	// Return: ---
	public Menu() { 
		frame = new JFrame("Touhou");
		frame.addKeyListener(this);

		frame.setPreferredSize(new Dimension (900,700));

		//music
		//music loader
		try {
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File ("Sounds/menu.wav"));
			music = AudioSystem.getClip();
			music.open(sound);
			music.start();
			music.loop(-1);
		}
		catch (Exception e) {}



		//menu
		menus[0].setLayout(new BoxLayout(menus[0], BoxLayout.Y_AXIS));	
		for (int i = 0; i < menus.length; i ++) {
			menus[i].setOpaque(false);
		}
		title = new JLabel (new ImageIcon("Images/title.png"));
		title.setBorder(new EmptyBorder(50, 0, 50, 0));
		menus[0].add(title);
		menuDisplay[0].setIcon(menuOptionSelected[0].getIcon());
		menuDisplay[0].setOpaque(false);		
		for (int i = 1; i < menuDisplay.length; i ++) {
			menuDisplay[i].setIcon(menuOption[i].getIcon());

		}

		for (int i = 0; i < menuDisplay.length; i++) {
			menus[0].add(menuDisplay[i]);
		}

		//character selection
		charTextLabel = new JLabel("Select a Character:");
		charTextLabel.setOpaque(true);
		charTextLabel.setBackground(Color.WHITE);
		charTextLabel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK,5), new EmptyBorder(10, 20, 10, 20)));
		charTextLabel.setFont(new Font("Courier", Font.BOLD,40));
		charTextLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		charLabel = new JLabel();
		stats = new JLabel();
		stats.setOpaque(true);
		stats.setBackground(Color.WHITE);
		stats.setBorder(new CompoundBorder(new LineBorder(Color.black,5), new EmptyBorder(20, 20, 20, 20)));
		stats.setFont(new Font("Courier", Font.BOLD, 30));

		charLabel.setIcon(charOption[0].getIcon());
		stats.setText(statsList[0].getText());
		
		charPanel = new JPanel();
		charPanel.setOpaque(false);
        charPanel.add(charLabel);
        charPanel.add(stats);
		menus[1].setLayout(new BoxLayout(menus[1], BoxLayout.Y_AXIS));
		menus[1].add(charTextLabel);
		menus[1].add(charPanel);


		//level selection
		menus[2].setLayout(new BoxLayout(menus[2], BoxLayout.Y_AXIS));
		
		levelTitle = new JLabel("Select a Level:");
		levelTitle.setOpaque(true);
		levelTitle.setBackground(Color.WHITE);
		levelTitle.setBorder(new CompoundBorder(new LineBorder(Color.BLACK,5), new EmptyBorder(10, 20, 10, 20)));
		levelTitle.setFont(new Font("Courier", Font.BOLD,40));
		
		levelLabel = new JLabel();

		
		menus[2].add(levelTitle);
		levelLabel.setIcon(levelOption[0].getIcon());
		levelLabel.setBorder(new EmptyBorder(150, 0, 0, 0));
		menus[2].add(levelLabel);

		//entering name
		menus[3].setLayout(new BoxLayout(menus[3], BoxLayout.Y_AXIS));	
		nameLabel = new JLabel("Enter Your Name:");
		nameLabel.setOpaque(true);
		nameLabel.setBackground(Color.WHITE);
		nameLabel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK,5), new EmptyBorder(10, 20, 10, 20)));
		nameLabel.setFont(new Font("Courier", Font.BOLD,40));
		nameLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		enterName = new JTextField();
		enterName.setPreferredSize(new Dimension(200,200));
		enterName.setFont(new Font("Courier", Font.BOLD,40));
		enterName.setHorizontalAlignment(SwingConstants.CENTER);
		
		nameButton = new JButton("Start");
		nameButton.setFont(new Font("Courier", Font.BOLD,20));
		nameButton.setBorder(new LineBorder(Color.BLACK, 5));
		nameButton.setMaximumSize(new Dimension(500,100));
		nameButton.setPreferredSize(new Dimension(500,100));
		nameButton.setAlignmentX(CENTER_ALIGNMENT);
		nameButton.setActionCommand("start");
		nameButton.addActionListener(this);

		menus[3].add(nameLabel);
		menus[3].add(Box.createRigidArea(new Dimension(0,50)));
		menus[3].add(enterName);
		menus[3].add(Box.createRigidArea(new Dimension(0,50)));
		menus[3].add(nameButton);

		//how to play
		infoLabel = new JLabel(new ImageIcon("Images/aboutus.png"));
		infoLabel.setOpaque(true);
		infoLabel.setBackground(Color.WHITE);
		infoLabel.setFont(new Font("Courier", Font.BOLD,20));
		menus[4].add(infoLabel);




		//Scoreboard
		scoreTextLabel = new JLabel("Scoreboard:");
		scoreTextLabel.setOpaque(true);
		scoreTextLabel.setBackground(Color.WHITE);
		scoreTextLabel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK,5), new EmptyBorder(10, 20, 10, 20)));
		scoreTextLabel.setFont(new Font("Courier", Font.BOLD,40));
		scoreTextLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		scoreLabel = new JTextArea();
		scoreLabel.setEditable(false);
		scoreLabel.setFocusable(false);
		scoreLabel.setOpaque(true);
		scoreLabel.setBackground(Color.WHITE);
		scoreLabel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK,5), new EmptyBorder(10, 20, 10, 20)));
		scoreLabel.setFont(new Font("Courier", Font.BOLD,20));
		
		menus[5].setLayout(new BoxLayout(menus[5], BoxLayout.Y_AXIS));	
		menus[5].add(scoreTextLabel);
		menus[5].add(Box.createRigidArea(new Dimension(0,100)));
		menus[5].add(scoreLabel);
		
		//adding the top 10 to scoreLabel
		
		try {
			String score = String.format("%8s%-10s%s\n", "", "Name", "Score");
			int i = 1;
			Scanner s = new Scanner(new File("score.txt"));
			while (i < 11) {
				if (s.hasNextLine()) {
					String input = s.nextLine();
					String nameText = input.substring(0, input.lastIndexOf(" "));
					int scoreText = Integer.parseInt(input.substring(input.lastIndexOf(" ") + 1));
					score += String.format("%5d)%2s%-10s%010d%5s\n", i, "", nameText, scoreText, "");
				}
				else {
					score += String.format("%5d)%2s%-10s%010d%5s\n", i, "", "---", 0, "");
				}
				i++;
			}
			scoreLabel.setText(score);
			
		}
		catch (FileNotFoundException e) {
			
		}


		//adding all the panels
		for (int i = 0; i < menus.length; i ++) {
			add(menus[i]);
			menus[i].setVisible(false);
		}

		menus[0].setVisible(true);

		frame.add(this);


		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int a = e.getKeyCode();
		if (a == KeyEvent.VK_UP) {
			//up arrow key
			if (menuNumber == 0) {
				playSound("switch.wav");
				if (optionSelect == 0) {
					menuDisplay[optionSelect].setIcon(menuOption[optionSelect].getIcon());
					optionSelect = 3;
					menuDisplay[optionSelect].setIcon(menuOptionSelected[optionSelect].getIcon());
				}
				else {
					menuDisplay[optionSelect].setIcon(menuOption[optionSelect].getIcon());
					optionSelect --;
					menuDisplay[optionSelect].setIcon(menuOptionSelected[optionSelect].getIcon());
				}
			}
		}

		else if (a == KeyEvent.VK_DOWN) {
			//down arrow key
			if (menuNumber == 0) {
				playSound("switch.wav");
				if (optionSelect == 3) {
					menuDisplay[optionSelect].setIcon(menuOption[optionSelect].getIcon());
					optionSelect = 0;
					menuDisplay[optionSelect].setIcon(menuOptionSelected[optionSelect].getIcon());
	
				}
				else {
					menuDisplay[optionSelect].setIcon(menuOption[optionSelect].getIcon());
					optionSelect ++;
					menuDisplay[optionSelect].setIcon(menuOptionSelected[optionSelect].getIcon());
				}
			}
		}

		else if (a == KeyEvent.VK_ENTER || a == KeyEvent.VK_Z ) {
			//enter key
			if (menuNumber == 0) {
				playSound("select.wav");
				menus[0].setVisible(false);
				if (optionSelect == 0) {
					menuNumber = 1;
					menus[menuNumber].setVisible(true);

				}

				else if (optionSelect == 1) {
					menuNumber = 4;
					menus[menuNumber].setVisible(true);				
				}

				else if (optionSelect == 2) {
					menuNumber = 5;
					menus[menuNumber].setVisible(true);
				}

				else {
					System.exit(0);
				}
			}

			else if (menuNumber == 1 || menuNumber == 2) {
				playSound("select.wav");
				menus[menuNumber].setVisible(false);
				menuNumber ++ ;
				menus[menuNumber].setVisible(true);

			}

		}
		else if (a == KeyEvent.VK_X) {
			//back
			playSound("back.wav");
			menus[menuNumber].setVisible(false);
			if (menuNumber == 2 || menuNumber == 3) {
				menuNumber--;
				menus[menuNumber].setVisible(true);
			}

			else {
				menuNumber = 0;
				menus[menuNumber].setVisible(true);

			}

		}

		else if (a == KeyEvent.VK_RIGHT) {

			if (menuNumber == 1) {
				playSound("switch.wav");
				charSelect ++;
				if (charSelect == 3 ) {
					charSelect = 0;
				}
				charLabel.setIcon(charOption[charSelect].getIcon());
				stats.setText(statsList[charSelect].getText());

			}

			else  if (menuNumber == 2){
				playSound("switch.wav");
				level++;
				if (level == 3 ) {
					level = 0;
				}

				levelLabel.setIcon(levelOption[level].getIcon());
			}

		}

		else if (a == KeyEvent.VK_LEFT) {
			if (menuNumber == 1) {
				playSound("switch.wav");
				charSelect --;
				if (charSelect == -1 ) {
					charSelect = 2;
				}
				charLabel.setIcon(charOption[charSelect].getIcon());
				stats.setText(statsList[charSelect].getText());

			}

			else  if (menuNumber == 2){
				playSound("switch.wav");
				level--;
				if (level == -1 ) {
					level = 2;
				}
				levelLabel.setIcon(levelOption[level].getIcon());

			}
		}
		this.repaint();
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


	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub


	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//background
		if (menuNumber == 0) {
			g.drawImage(bg2, -10,0, 1000, 700, this);
		}

		else {
			g.drawImage(bg, 0,0, 900, 700, this);
		}

	}


	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		name = enterName.getText();
		if (name.isBlank()) {
			nameLabel.setText("Invalid. Enter your name:");
		}
		else if (name.length() >= 10) {
		     nameLabel.setText("Too Long! Enter your name:");
		}

		else {
			music.close();
			JFrame frame = new JFrame ("Touhou");
			Main myPanel = new Main (charSelect, level, frame, name);
			frame.add(myPanel);
			frame.addKeyListener(myPanel);
			frame.pack();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			this.frame.dispose();
		}
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			menuSpriteSheet = ImageIO.read(new File("Images/menu.png"));
			bg = ImageIO.read(new File("Images/background2.png"));
			bg2 = ImageIO.read(new File("Images/background.png"));
		}
		catch(IOException e) {

		}
		new Menu();
	}
}






