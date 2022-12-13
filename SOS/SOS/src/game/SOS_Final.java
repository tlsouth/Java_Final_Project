package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SOS_Final extends JFrame
{
	//initializing GUI panels and variables for images and audio for the GUI
	private static final long serialVersionUID = 1975L;
	private JTextArea prompt = new JTextArea("");
	private JButton option1 = new JButton("");
	private JButton option2 = new JButton("");
	//setting initial GUI images for startup
	ImageIcon imageStart = new ImageIcon(path + "/SOS_Images/3.png");
	private JButton start =  new JButton(imageStart);
	ImageIcon imageCancel = new ImageIcon(path + "/SOS_Images/4.png");
	private JButton cancel =  new JButton(imageCancel);
	private JLabel homeScreen = new JLabel(new ImageIcon(path + "/SOS_Images/mainScreen.gif"));
	//these are for the first GUI frame that appears and takes the user's name and filepath
	private JButton startB = new JButton("Begin");
	private JTextField nameHere = new JTextField();
	private JLabel enterName = new JLabel ("Please type your name: ");
	private JTextField pathHere = new JTextField();
	private JLabel enterPath = new JLabel ("Please type the path to where the 'SOS' folder is located:");
	private JLabel example1 = new JLabel ("Ex: If the path to the 'SOS' folder is located on your Desktop, type '/Users/yourUserName/Desktop/SOS''");
	private JLabel example2 = new JLabel ("Please have your audio on, but low. Music will play once you hit 'Begin'. It can be loud.");
	//setting up clip for audio use
	Clip clip = AudioSystem.getClip();
	//method that allows us to play audio on the GUI
	public void music() throws Exception
	{
		//audio was clipped from a TikTok: https://www.tiktok.com/t/ZTRVAQmMt/. We hereby declare that we do not own the rights to this music/song. All rights belong to the owner. 
		//No Copyright Infringement Intended.
		//getting audio filepath
		File audio = new File(path + "/SOS_Audio/Inter_mix.wav");
		AudioInputStream ais = AudioSystem.getAudioInputStream(audio);
		//opening audio
		clip.open(ais);
		//starting audio
		clip.start();
	}
	//boolean for managing first GUI and game GUI display
	private static boolean setUPStatus = true;
	//empty variables for storing the name and filepath
	private static String name = "";
	private static String path = "";
	//actionListener for the setup GUI
	private class setupActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{				
			setUPStatus = false;
			startB.setEnabled(false);
			name = nameHere.getText();
			path = pathHere.getText();
			//hide the GUI once the user has input their info and hit begin
			setVisible(false);
			try {
				new SOS_Final(setUPStatus);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		
	}
}
	//gives the user a base initial sequence and setup random for use in sequence modification
	StringBuffer seq = new StringBuffer("AGTC");
	Random rand = new Random();
	//method for decreasing the sequence
	public void decreaseSeq()
	{
		//if the sequence is longer than three base pairs, they will survive the loss in sequence, so proceed with decreasing
		if(seq.length() > 3)
		{
			//deletes a random base in the sequence 3 times
			for(int x = 0; x < 3; x++)
			{
				//finds random index in sequence
				int index = rand.nextInt(0,seq.length());
				//deletes the base at the index
				seq.delete(index, (index + 1));
			}
		}
		//if sequence is shorter than three base pairs, they won't survive the event so print they died and don't bother calculating
		else
		{
			System.out.println("You've lost your sequence and died!\n Hit 'Start' to try again!");
			start.setEnabled(true);
			option1.setEnabled(false);
			option2.setEnabled(false);
		}
	}
	//method for increasing the sequence
	public void increaseSeq()
	{
		//generates float for the dice roll
		float baseChance = rand.nextFloat();
		//if the generated float is less than/equal to 35%, the user gets +1 G base; the end calculation for survival only cares about GC content
		if(baseChance <= 0.35)
		{
			seq.append("ATG");
		}
		//if the roll is greater than 35%, the user gets GC added
		else if(baseChance >= 0.36)
		{
			seq.append("GC");
		}
	}
	//stops playing the music once the user hits 'start' on the opening screen and changes the image
	public void openingScene()
	{
		clip.stop();
		ImageIcon image = new ImageIcon(path + "/SOS_Images/opening.jpg");
		homeScreen.setIcon(image);
		//sets new prompt, giving the user the options
		updatePrompt(name + "! On your NASA-sponsered mission to find life in outerspace, your rocket ship "
			+ "failed due to faulty coding and you crash-landed onto another planet. You now must fight your way through this unkown land "
			+ "in the hopes of getting back home. Be careful, a single desicion could make all the difference...");
		//sets buttons to event options and sets up the listeners
		option1.setText("Climb out of your crashed ship");
		option2.setText("Hide forever and give up");
		option1.addActionListener(new openingListener());
		option2.addActionListener(new opening2Listener());
	}
	//actionListener moving the player to the first event if they opt to continue
	private class openingListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			eventOne();
		}
	}
	//ends the game if the user doesn't continue
	private class opening2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			updatePrompt("You die alone. Game over. Hit 'Start' to try again!");
			start.setEnabled(true);
			option1.setEnabled(false);
			option2.setEnabled(false);
		}
	}
	//the first event
	//most events work the same, so their outline will be defined here:
	//the image is updated along with a new prompt and new option buttons (plus associated actionListeners)
	//to update the actionListeners, the previous ones are removed and new once are added
	public void eventOne()
	{
		ImageIcon image = new ImageIcon(path + "/SOS_Images/eventOne.jpeg");
		homeScreen.setIcon(image);
		
		updatePrompt("After climbing out of the crashed rocket, you spot a cliff up ahead and want to get to "
			+ "the top to get a better view of your surroundings. "
			+ "Do you decide to climb the cliff free solo style or "
			+ "do you take the longer path and hike up?");
		option1.setText("Climb");
		option2.setText("Hike");
		
		 for (ActionListener listener : option1.getActionListeners())
		 	{	
			 	option1.removeActionListener(listener);
				option1.addActionListener(new event1option1Listener());
		    }
		 for (ActionListener listener : option2.getActionListeners())
		    {
		       option2.removeActionListener(listener);
		       option2.addActionListener(new event1option2Listener());
		    }
	
	}
	//the actionListeners are generally the same in function for each event
	//they run the method for either increasing or decreasing the sequence, print the curent sequence in the terminal, and move the player to the next event
	private class event1option1Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			increaseSeq();
			System.out.println(seq);
			eventTwo();
		}
	}
	private class event1option2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			decreaseSeq();
			System.out.println(seq);
			eventTwo();
		}
	}
	public void eventTwo()
	{
		ImageIcon image = new ImageIcon(path + "/SOS_Images/eventTwo.jpeg");
		homeScreen.setIcon(image);
		
		updatePrompt("You reach the top of the cliff and see the surrounding landscape. In the distance you see another cliffside with what appears to be a ship. "
				+ "You know that long term survival on this planet isn’t possible, and that the ship may be your only escape. You remember something your mom always said "
				+ "“You can’t fight crime if you aren’t cute, and you can’t escape an alien planet if you’re hungry,” so you decide it is time to find something to eat.");
		option1.setText("Enter a nearby cave to search for\n mushrooms or other soft troglodytes"); //+GC
		option2.setText("Dive in the nearby lake in search\n for a bottom dweller to make a meal of"); //++GC bc of protein, or small -GC chance bc of drown
		 for (ActionListener listener : option1.getActionListeners())
		 	{	
			 	option1.removeActionListener(listener);
				option1.addActionListener(new event2option1Listener());
		    }
		 for (ActionListener listener : option2.getActionListeners())
		    {
		        option2.removeActionListener(listener);
		       option2.addActionListener(new event2option2Listener());
		    }
	}
	
	private class event2option1Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			increaseSeq();
			System.out.println(seq);
			eventThree();
		}
	}
	private class event2option2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		
		{
			//generating random float because this choice has two possible outcomes in regard to the sequence
			float f = rand.nextFloat();
			if(f >= 0.21)
			{
				//large chance to greatly increase GC because you got a high protein meal
				for(int x = 0; x < 2; x++)
				{
					increaseSeq();
				}
			}
			else if(f <= 0.20)
			{
				//small chance to decrease GC because you drown a little while trying to find food
				decreaseSeq();
			}
			System.out.println(seq);
			eventThree();
		}
	}
	public void eventThree()
	{
		ImageIcon image = new ImageIcon(path + "/SOS_Images/eventThree.jpeg");
		homeScreen.setIcon(image);
		updatePrompt("You draw a makeshift map in the dirt based on your observations from the top of the cliff. You see two possible routes to take.");
		option1.setText("Foggy path");
		option2.setText("Forest");
		
		for (ActionListener listener : option1.getActionListeners())
	 	{	
		 	option1.removeActionListener(listener);
			option1.addActionListener(new event3option1Listener());
	    }
		for (ActionListener listener : option2.getActionListeners())
	    {
	        option2.removeActionListener(listener);
	        option2.addActionListener(new event3option2Listener());
	    }
	}
	private class event3option1Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			eventAnimal();
		}
	}
	private class event3option2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			eventAnimal();
		}
	}
	public void eventAnimal()
	{
		ImageIcon image = new ImageIcon(path + "/SOS_Images/eventAnimal.jpg");
		homeScreen.setIcon(image);
		updatePrompt("On your way you encounter an animal much larger than yourself. Do you attempt to run away or fight it?");
		option1.setText("Run away"); //you run away and trip, animal hurts you -GC
		option2.setText("Fight"); //you fight the animal and win, +GC
		for (ActionListener listener : option1.getActionListeners())
	 	{	
		 	option1.removeActionListener(listener);
			option1.addActionListener(new eventAnimaloption1Listener());
	    }
		for (ActionListener listener : option2.getActionListeners())
	    {
	        option2.removeActionListener(listener);
	        option2.addActionListener(new eventAnimaloption2Listener());
	    }
	}
	private class eventAnimaloption1Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			float f = rand.nextFloat();
			if(f >= 0.35)
			{
				//small chance to fall when running and get attacked by the animal, losing some GC
				for(int x = 0; x < 2; x++)
				{
					decreaseSeq();
				}
				System.out.println(seq);
				eventFour();
			}
			else
			{
			System.out.println(seq);
			eventFour();
			}
		}
	}
	private class eventAnimaloption2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			increaseSeq();
			System.out.println(seq);
			eventFour();
		}
	}
	public void eventFour()
	{
		ImageIcon image = new ImageIcon(path + "/SOS_Images/eventFour.jpg");
		homeScreen.setIcon(image);
		
		updatePrompt("You manage to cover some ground, but you miss the sensation of carbonation and aspartame. You need to find something to drink.");
		option1.setText("Pluck a fruit from the nearby tree, it looks juicy"); //+GC, endosymbiosis
		option2.setText("Use your water vaporizer to heat the water so it is safe to drink"); // no GC change
		
		for (ActionListener listener : option1.getActionListeners())
	 	{	
		 	option1.removeActionListener(listener);
			option1.addActionListener(new event4option1Listener());

	    }
		for (ActionListener listener : option2.getActionListeners())
	    {
	        option2.removeActionListener(listener);
	        option2.addActionListener(new event4option2Listener());
	    }
	}
	private class event4option1Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			increaseSeq();
			System.out.println(seq);
			eventFive();
		}
	}
	private class event4option2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		
		{
			System.out.println(seq);
			eventFive();
		}
	}
	public void eventFive()
	{
		ImageIcon image = new ImageIcon(path + "/SOS_Images/eventFive.jpg");
		homeScreen.setIcon(image);
		
		updatePrompt("The wind brings in a rainstorm and your path splits in two. You could set course through a nearby valley covered by trees in the hopes that the trees shield you from the rain, "
				+ "or you could high tail it across an open field and avoid the risk of any falling trees.");
		option1.setText("Walk through the valley");
		option2.setText("Hike across the field");
		for (ActionListener listener : option1.getActionListeners())
	 	{	
		 	option1.removeActionListener(listener);
			option1.addActionListener(new event5option1Listener());
	    }
		for (ActionListener listener : option2.getActionListeners())
	    {
	        option2.removeActionListener(listener);
	        option2.addActionListener(new event5option2Listener());
	    }
	}
	private class event5option1Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println(seq);
			eventSix();
		}
	}
	private class event5option2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println(seq);
			eventSix();
		}
	}
	public void eventSix()
	{
		ImageIcon image = new ImageIcon(path + "/SOS_Images/eventSix.jpeg");
		homeScreen.setIcon(image); 
		
		updatePrompt("As you and the rain pass each other, the ground turns from soil to freshly cooled lava. Before you is a wide river of flowing liquid rock. "
				+ "The rain managed to cool the river in some places, so you believe crossing the river is possible.");
		option1.setText("Try jumping across quickly");
		option2.setText("Slowly make your way across");
		for (ActionListener listener : option1.getActionListeners())
	 	{	
		 	option1.removeActionListener(listener);
			option1.addActionListener(new event6option1Listener());
	    }
		for (ActionListener listener : option2.getActionListeners())
	    {
	        option2.removeActionListener(listener);
	        option2.addActionListener(new event6option2Listener());
	    }
	}
	private class event6option1Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//you miss step in your hurry and burn your foot some
			decreaseSeq();
			System.out.println(seq);
			eventSeven();
		}
	}
	private class event6option2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//carefully make your way across and feel stronger for it
			increaseSeq();
			System.out.println(seq);
			eventSeven();
		}
	}
	public void eventSeven()
	{
		ImageIcon image = new ImageIcon(path + "/SOS_Images/eventSeven.jpg");
		homeScreen.setIcon(image); 
		
		updatePrompt("After you catch your breath from crossing the river, you realize the cliffside on which the ship is perched is nearby. "
				+ "You make your way to the base of the mountain and feel like your journey has just begun."
				+ "For old time’s sake, you could decide to Honnold it up the cliffside one more time, or "
				+ "you could enjoy the beauty of this planet one last time as you hike your way up the cliffside to the ship. "
				+ "Along the way you mayfind some berries and mushrooms and have a small snack as you reach the top");
		option1.setText("Climb"); //++GC
		option2.setText("Hike"); //+GC
		for (ActionListener listener : option1.getActionListeners())
	 	{	
		 	option1.removeActionListener(listener);
	        option1.addActionListener(new event7option1Listener());
	    }
		for (ActionListener listener : option2.getActionListeners())
	    {
	        option2.removeActionListener(listener);
	        option2.addActionListener(new event7option2Listener());
	    }
	}
	//if the user decides to climb one last time, they are heavily rewarded in GC content (GC increased 3 times)
	private class event7option1Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//increasing GC content three times
			for(int x = 0; x < 3; x++)
			{
				increaseSeq();
			}
			System.out.println(seq);
			eventEight();
		}
	}
	private class event7option2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			increaseSeq();
			System.out.println(seq);
			eventEight();
		}
	}
	public void eventEight()
	{
		ImageIcon image = new ImageIcon(path + "/SOS_Images/eventEight.jpg");
		homeScreen.setIcon(image); 
		updatePrompt("You finally reach the ship. After kicking and prying at it for a while, you see a small glass oval on the wall beside the door. "
				+ "While you don’t want to give the aliens your data, you feel that pressing your thumb against this glass is the only way to gain access to the ship.");
		//code to check the user's sequence and determine if they escape or not
		option1.setText("SuRrEnDeR yOuR dAtA");
		option2.setText("Don't interact and die on this planet alone"); 
		for (ActionListener listener : option1.getActionListeners())
	 	{	
		 	option1.removeActionListener(listener);
	        option1.addActionListener(new event8option1Listener());
	    }
		for (ActionListener listener : option2.getActionListeners())
	    {
	        option2.removeActionListener(listener);
	        option2.addActionListener(new event8option2Listener());
	    }
	}
	//checks final GC count of the sequence generated 
	private class event8option1Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String finalSeq = seq.toString();
			char G = 'G';
			char C = 'C';
			float gcCount = 0;
			//for character in the string
			for(int i = 0; i < finalSeq.length(); i++)
			{
				//if the character is G or C
				if (finalSeq.charAt(i) == G || finalSeq.charAt(i) == C)
				{
					//add to gcCount 
					gcCount++;
				}
			}
			float floatLen = finalSeq.length();
			float content = (gcCount/floatLen);
			System.out.println(gcCount);
			System.out.println(floatLen);
			System.out.println(content);
			//if GC content is >60%, proceed to escapeEnding 
			if(content > 0.60)
			{
				escapeEnding();
			}
			//else go to marooedEnding 
			else
			{
				maroonedEnding();
			}
			//disables option buttons 
			option1.setEnabled(false);
			option2.setEnabled(false);
		}
	}
	private class event8option2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			updatePrompt("You decide that it's just easier to stay here where there are no haters. You don't interact with the ship.");
			//disables the option buttons 
			option1.setEnabled(false);
			option2.setEnabled(false);
		}
	}
	public void escapeEnding()
	{
		//path to image, followed by setting image
		ImageIcon image = new ImageIcon(path + "/SOS_Images/escape.jpg");
		homeScreen.setIcon(image); 
		updatePrompt("The ship's biometric lock recognizes you as a member of its creator's species. You are able to enter the ship and launch back into space in search of your people.");
	}
	public void maroonedEnding()
	{
		//path to image, followed by setting image
		ImageIcon image = new ImageIcon(path + "/SOS_Images/stay.jpg");
		homeScreen.setIcon(image);
		updatePrompt("The ship doesn't open for you, so you climb back down the cliff and live the rest of your days trying to diagnose thousands of diseases with a single drop of blood");
	}
	private void updatePrompt(String text)
	{
		//when updatePromt() is called, the prompt is updated 
		prompt.setText(text);
	}
	//ActionListener for start button 
	private class StartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{	
			start.setEnabled(false);
			option1.setEnabled(true);
			option2.setEnabled(true);
			seq = new StringBuffer ("AGTC");
			//calls opening scene 
			openingScene();
			validate();
		}
	}
	//ActionListener for cancel button
	private class CancelListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//if user hits the cancel button, the GUI closes
			start.setEnabled(true);
			option1.setEnabled(false);
			option2.setEnabled(false);
			updatePrompt("Hit Start to restart the game");
		}
	}
	//pane for scene images 
	private JPanel image() throws IOException
	{
		JPanel panel = new JPanel();
		panel.add(homeScreen);
		panel.setBounds(100,100,100,100);
		panel.setBackground(Color.black);
		return panel;
	}
	//pane for buttons 
	private JPanel bottom()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,2));
		panel.add(option1);
		panel.add(option2);
		panel.add(start);
		panel.add(cancel);
		return panel;
	}
	//pane for name collection and 'SOS' folder path collection 
	private JPanel getUserName()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(enterName);
		panel.add(nameHere);
		panel.add(enterPath);
		panel.add(pathHere);
		return panel;
	}
	//pane for sound warning and example for path entry 
	private JPanel pathInstructions()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		//example for path entry 
		panel.add(example1);
		//sound warning 
		panel.add(example2);
		return panel;
	}
	public SOS_Final(Boolean tf) throws Exception
	{
		super("Final Project: Survival of the Sequenced");
		//if setting up, run these GUI settings
		if(setUPStatus) {
			setLocationRelativeTo(null);
			setSize(800,150);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			getContentPane().setLayout(new BorderLayout());
			//adding pane for begin button 
			getContentPane().add(startB, BorderLayout.SOUTH);
			//adding pane for 'SOS' folder path entry example and sound warning 
			getContentPane().add(pathInstructions(), BorderLayout.CENTER);
			//adding pane for user name collection 
			getContentPane().add(getUserName(), BorderLayout.NORTH);
			startB.addActionListener(new setupActionListener());
			setVisible(true);
		}
		//if not setting up, run these GUI settings 
		if(!setUPStatus)
		{
			//calling the method for sound 
			music();
			setLocationRelativeTo(null);
			setSize(1000,900);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			getContentPane().setLayout(new BorderLayout());
			//adding the pane for images
			getContentPane().add(image(), BorderLayout.NORTH);
			//adding the pane for the prompts
			getContentPane().add(prompt, BorderLayout.CENTER);
			//adding the pane for the buttons 
			getContentPane().add(bottom(), BorderLayout.SOUTH);
			//makes prompt area non editable  
			prompt.setEditable(false);
			//sets font and size of text
			prompt.setFont(new Font("Futura", Font.ITALIC, 16));
			//wraps the words
			prompt.setLineWrap(true);
			prompt.setWrapStyleWord(true);
			//sets colors 
			prompt.setBackground(Color.black);		
			prompt.setForeground(Color.white);
			setVisible(true);
			//adds action listener to start button
			start.addActionListener(new StartListener());
			start.setContentAreaFilled(false);
			start.setFocusPainted(false);
			//removes start button border
			start.setBorderPainted(false);
			//adds action listener to cancel button
			cancel.addActionListener(new CancelListener());
			cancel.setContentAreaFilled(false);
			//removes cancel button border
			cancel.setFocusPainted(false);
			cancel.setBorderPainted(false);
		}
	}
	//initializes new GUI 
	public static void main(String[] args) throws Exception
	{
		new SOS_Final(setUPStatus);
	}
}