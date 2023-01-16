/*
Shreyansh Thakral
May 18, 2020
Ms. Basaraba
This Program is a slots machine. User starts with a balance of $100 and can make bets of $1, $2 and $5. For each spin there is a 4% win chance. User can read the instructions, can view and clear previous highscores, and exit. Exiting saves their info in case they want to continue playing next time. High scores are updated instantaneously and filed when exiting. All user input is taken by the keyboard. 
*/
import java.awt.*;
import java.io.*;
import javax.swing.JOptionPane;
import hsa.Console;

public class Slots
{
    Console c;
    static int balance, bet; // money values
    final int RANK = 10; // constant number of highscores
    static char choice = '0'; // current choice
    static String name; // username
    String[] [] highscores = new String [RANK + 1] [2]; // holds highsores

    public Slots ()
    {
	c = new Console ("Slots"); // constructor
    }


    private void drawMachine (boolean spinning, int yChange)  // draws slots machine, boolean 'spinning' determines whether arm is up or down, int 'yChange' modifies y value frm defalut
    {
	c.setColour (new Color (207, 181, 59));
	c.fillRoundRect (510, 230 + yChange, 38, 50, 15, 15); // axel
	c.setColour (new Color (163, 0, 0));
	c.fillRoundRect (125, 135 + yChange, 390, 250, 15, 15); //body
	c.fillRoundRect (195, 65 + yChange, 250, 85, 15, 15); // display body
	c.setColour (new Color (113, 0, 0));
	c.fillRoundRect (210, 75 + yChange, 220, 55, 15, 15); // display
	c.fillRoundRect (150, 160 + yChange, 340, 200, 15, 15); // border
	c.setColour (new Color (207, 181, 59));
	c.fillRoundRect (180, 190 + yChange, 280, 140, 15, 15); // panel
	c.setFont (new Font ("Algerian", Font.BOLD, 46));
	c.drawString ("SLOTS", 247, 118 + yChange); // display text
	c.setColour (new Color (230, 225, 225));
	for (int i = 0 ; i < 3 ; i++)
	{
	    c.fillRoundRect (200 + i * 85, 204 + yChange, 70, 116, 15, 15); // reel
	}
	c.setColour (new Color (174, 137, 19));
	if (spinning == true)
	{
	    c.fillRect (526, 280 + yChange, 10, 100); // arm down
	    c.setColour (new Color (113, 0, 0));
	    c.fillOval (520, 360 + yChange, 23, 23); // handle
	}
	else
	{
	    c.fillRect (526, 130 + yChange, 10, 100); // arm up
	    c.setColour (new Color (113, 0, 0));
	    c.fillOval (520, 120 + yChange, 23, 23); // handle
	}
    }


    private String reelCalc ()
    {
	int r1 = (int) (Math.round (Math.random () * 4) + 1); //generate 3 random ints from 1 to 5, inclusive
	int r2 = (int) (Math.round (Math.random () * 4) + 1);
	int r3 = (int) (Math.round (Math.random () * 4) + 1);
	return String.valueOf (r1 * 100 + r2 * 10 + r3); // returns the 3 random ints as a 3 charachter long String
    }


    private void drawReel (String symbols, int yChange)  // 'symbols' holds the String that cooresponds to the desired symbol, yChange modifies y-value from default
    {
	for (int i = 0 ; i < 3 ; i++) // repeat 3 times for 3 reels
	{
	    c.setColour (new Color (230, 225, 225));
	    c.fillRoundRect (200 + i * 85, 204 + yChange, 70, 116, 15, 15); // empty reel
	    // the following if-structure draws coresponding symbols depending on the parameters passsed
	    if (symbols.charAt (i) == '1') // charachter is '1'
	    {
		c.setColour (new Color (207, 181, 59)); // draw stylized '$' at valid location
		c.setFont (new Font ("Algerian", Font.BOLD, 99));
		c.drawString ("$", 203 + i * 85, 295 + yChange);
		c.setColour (new Color (163, 0, 0));
		c.setFont (new Font ("Algerian", Font.BOLD, 90));
		c.drawString ("$", 205 + i * 85, 293 + yChange);
	    }
	    else if (symbols.charAt (i) == '2') // charachter is '2'
	    {
		c.setColour (new Color (207, 181, 59)); // draw stylized '7' at valid location
		c.setFont (new Font ("Algerian", Font.BOLD, 110));
		c.drawString ("7", 198 + i * 85, 302 + yChange);
		c.setColour (new Color (163, 0, 0));
		c.setFont (new Font ("Algerian", Font.BOLD, 100));
		c.drawString ("7", 200 + i * 85, 300 + yChange);
	    }
	    else if (symbols.charAt (i) == '3') // charachter is '3'
	    {
		c.setColour (new Color (163, 0, 0)); // draws a BAR symbol at valid location
		c.fillRoundRect (203 + i * 85, 250 + yChange, 64, 30, 10, 10);
		c.setColour (new Color (207, 181, 59));
		c.drawRoundRect (203 + i * 85, 250 + yChange, 64, 30, 10, 10);
		c.setFont (new Font ("Garamond", Font.BOLD, 30));
		c.drawString ("BAR", 203 + i * 85, 276 + yChange);
	    }
	    else if (symbols.charAt (i) == '4') // charachter is '4'
	    {
		c.setColour (new Color (207, 181, 59)); // draws a stylized star at valid location
		c.fillStar (203 + i * 85, 235 + yChange, 60, 60);
		c.setColour (new Color (163, 0, 0));
		c.fillStar (207 + i * 85, 233 + yChange, 60, 60);
	    }
	    else // charachter is '5'
	    {
		c.setColour (new Color (163, 0, 0));  // draws a pair of cherries at valid location
		c.fillArc (202 + i * 85, 253 + yChange, 23, 23, 0, 180); // first cherry
		c.fillArc (217 + i * 85, 253 + yChange, 23, 23, 0, 180);
		c.fillArc (202 + i * 85, 248 + yChange, 38, 33, 0, -180);
		c.fillArc (230 + i * 85, 263 + yChange, 23, 23, 0, 180); // second cherry
		c.fillArc (245 + i * 85, 263 + yChange, 23, 23, 0, 180);
		c.fillArc (230 + i * 85, 258 + yChange, 38, 33, 0, -180);
		c.setColour (Color.black);
		c.drawArc (230 + i * 85, 263 + yChange, 23, 23, 100, 80); // border between the 2 cherries
		c.drawArc (230 + i * 85, 258 + yChange, 38, 33, 180, 20);
		c.drawArc (221 + i * 85, 220 + yChange, 60, 75, 100, 80); // stems
		c.drawArc (210 + i * 85, 220 + yChange, 40, 95, 0, 64);
	    }
	}
    }


    private void updateHighscores ()
    {
	BufferedReader input;
	String[] dataIn = new String [2]; // input array
	int pos = RANK - 1; // holds the position where the new highscore needs to be inserted
	try // load highscores
	{
	    input = new BufferedReader (new FileReader ("highscores")); // specifies file that needs to be read
	    for (int i = 0 ; i < RANK ; i++)  // for each index of highscores [] []
	    {
		dataIn = input.readLine ().split (" "); // reads a line
		for (int j = 0 ; j < 2 ; j++)
		{
		    highscores [i] [j] = dataIn [j]; // assigns the information to the correct indicies
		}
	    }
	}
	catch (IOException e)
	{
	}

	if (Integer.parseInt (highscores [9] [1]) < balance && balance > 100) // if user has set a new highscore
	{
	    for (int i = RANK - 1 ; i >= 0 ; i--) // scan the loop from bottom to top
	    {
		if (Integer.parseInt (highscores [i] [1]) < balance) // decrements pos if user's current balance is greater than the rank in question
		{
		    pos--;
		}
	    }
	    for (int j = RANK - 1 ; j > pos ; j--) // nested for loop scans from bottom up and moves everything that succeeds the desired position down by one index
	    {
		for (int k = 0 ; k < 2 ; k++)
		{
		    highscores [j + 1] [k] = highscores [j] [k];
		}
	    }
	    highscores [pos + 1] [0] = name; // assigns new values for name and balance at the respective indicies
	    highscores [pos + 1] [1] = String.valueOf (balance);
	}
    }



    public void splashScreen ()
    {
	c.setColour (Color.black);
	c.fillRect (0, 0, 640, 500); //background
	drawMachine (true, 50); // draw machine with arm down, shifted down frm default position
	drawReel ("222", 50); // draws "777" onto reel
	c.setColour (new Color (113, 0, 0));
	c.fillRoundRect (210, 125, 220, 55, 15, 15); // clears display
	c.setColour (new Color (207, 181, 59));
	c.setFont (new Font ("Algerian", Font.BOLD, 42));
	c.drawString ("Jackpot!", 215, 168); // draws win display
	SplashScreen p = new SplashScreen (c); // creates the animation thread
	p.start (); //starts the thread
	try
	{
	    p.join (); // waits for the thread to end
	}
	catch (Exception e)
	{
	}
    }


    public void getData ()
    {
	BufferedReader lastPlayed; // loads last user's info
	int cont = -1; // whether user wants to continue with previous game
	String[] dataIn = new String [2]; // input array
	String msg = "Your Name";
	try
	{
	    lastPlayed = new BufferedReader (new FileReader ("lastPlayed")); // sets name and balance to that of previous player.
	    name = lastPlayed.readLine (); // sets name and balance to that of the last game
	    balance = Integer.parseInt (lastPlayed.readLine ());

	}
	catch (Exception e)  // catches NullPointerExceptions, program doesnt crash if there is no stored info on the previous game
	{
	}

	if (balance > 0) // user did not lose in the previous game
	{
	    while (cont == -1) // repeat until user gives input
	    {
		// asks user whether they want to continue the previous game
		cont = JOptionPane.showConfirmDialog (null, "Would you like to continue playing as \"" + name + "\"?", "Continue Playing?", JOptionPane.YES_NO_OPTION);
	    }
	}
	else // user lost in the last game
	    cont = 1; // user has to start a new game
	if (cont == 1) // if user wants to start a new game
	{
	    while (true) // error trapped input
	    {
		try
		{
		    try
		    {
			name = JOptionPane.showInputDialog ("Please Enter " + msg); // gets user input for new name
			if (name.length () > 12 || name.length () < 3) // if name is an invalid length
			    throw new Exception ();
			for (int i = 0 ; i < name.length () ; i++)
			{
			    if (name.charAt (i) == ' ') // if name contains ' ' characters
				throw new Exception ();
			}
			break; // input is valid, exit loop

		    }
		    catch (Exception e)
		    {
			if (name.length () > 12) // if structure detects why this exception has occured
			    msg = "a Shorter Name"; // specifies what was wrong with the previous input
			else if (name.length () < 3 && name.length () > 0)
			    msg = "a Longer Name";
			else if (name.length () > 0)
			    msg = "a Name without space characters";

		    }
		}
		catch (NullPointerException e)  // catches null pointer exception: program doesn't crash if user closes dialog box without giving any input
		{
		}
	    }
	    balance = 100; // sets starting balance
	}
    }


    public void mainMenu ()
    {
	c.setColour (Color.black);
	c.fillRect (0, 0, 640, 500); // base
	c.setColour (new Color (163, 0, 0));
	c.fillRoundRect (100, 135, 440, 300, 25, 25); // panel
	c.setColour (new Color (207, 181, 59));
	c.setFont (new Font ("Garamond", Font.BOLD, 20));
	c.drawString ("Balance = $" + balance, 5, 495); // shows current balance
	c.setFont (new Font ("Algerian", Font.BOLD, 42));
	c.drawString ("Main Menu", 207, 85); // title
	c.setFont (new Font ("Algerian", Font.BOLD, 36));
	c.drawString ("1. Instructions", 125, 205); // main menu options
	c.drawString ("2. Play Game", 125, 245);
	c.drawString ("3. Highscores", 125, 285);
	c.drawString ("4. Exit", 125, 325);
	while (true) // error trapped input
	{
	    try
	    {
		choice = c.getChar (); // gets input
		if (choice < '1' || choice > '4') // if input is invalid
		    throw new Exception ();
		break; // input is valid, exit loop
	    }
	    catch (Exception e)
	    {
		c.drawString ("PLEASE ENTER A VALID OPTION.", 30, 475); //  informs user to enter another, valid input
	    }
	}
    }


    public void instructions ()
    {
	c.setColour (Color.black);
	c.fillRect (0, 0, 640, 500); // background
	c.setColour (new Color (163, 0, 0));
	c.fillRoundRect (100, 135, 440, 300, 25, 25); // panel
	c.setColour (new Color (207, 181, 59));
	c.setFont (new Font ("Algerian", Font.BOLD, 42));
	c.drawString ("Instructions", 175, 85); // title
	c.setFont (new Font ("Algerian", Font.BOLD, 32));
	c.drawString ("Press any key to continue.", 82, 485); // informs user how to cont.
	c.setFont (new Font ("Garamond", Font.BOLD, 28));
	c.drawString ("Welcome to Slots! Each player", 120, 175); // instructions
	c.drawString ("starts with a balance of $100 and", 120, 205);
	c.drawString ("can make a bet of $1, $2 or $5 for", 120, 235);
	c.drawString ("each spin. A Jackpot is won when", 120, 265);
	c.drawString ("all three symbols on the slots", 120, 295);
	c.drawString ("machine match. If you win, you", 120, 325);
	c.drawString ("will get 100X your original bet.", 120, 355);
	c.drawString ("To spin, enter your bet and press", 120, 385);
	c.drawString ("any key. Good Luck!", 120, 415);
	c.getChar (); // key pressed
	choice = '0'; // go to main menu
    }


    public void playGame ()
    {
	c.setColour (Color.black);
	c.fillRect (0, 0, 640, 500); //background
	drawMachine (false, 0); // draw machine with arm up
	c.setColour (new Color (207, 181, 59));
	c.setFont (new Font ("Algerian", Font.BOLD, 38));
	c.drawString ("Enter your bet: $1, $2 or $5", 25, 485); // asks for input
	c.setFont (new Font ("Garamond", Font.BOLD, 20));
	c.drawString ("Balance = $" + balance, 5, 17); // shows current balance
	while (true)
	{
	    try
	    {
		bet = Integer.parseInt (String.valueOf (c.getChar ())); // Citation for Char to Int Conversion: https://www.javatpoint.com/java-char-to-int
		if (bet != 1 && bet != 2 && bet != 5)
		    throw new NumberFormatException (); // if bet is invalid
		else if (balance - bet < 0) // if balnce is too low
		{
		    c.setColour (Color.black);
		    c.fillRect (0, 405, 640, 35); // clear old msgs
		    c.setColour (new Color (207, 181, 59));
		    c.setFont (new Font ("Algerian", Font.BOLD, 28));
		    c.drawString ("Balance too low: Enter a smaller Bet", 10, 435); // informs user that bet is too high
		}
		else
		    break; // user input succesfull
	    }
	    catch (NumberFormatException e)
	    {
		c.setColour (Color.black);
		c.fillRect (0, 405, 640, 35); // clear old msgs
		c.setColour (new Color (207, 181, 59));
		c.setFont (new Font ("Algerian", Font.BOLD, 32));
		c.drawString ("Please Enter a Valid Bet", 90, 435); // informs user that input is invalid
	    }
	}


	balance -= bet; // update balance
	c.setColour (Color.black);
	c.fillRect (97, 0, 100, 20); // clears old msgs and balance
	c.fillRect (0, 405, 640, 35);
	c.fillRect (0, 450, 640, 40);
	c.setColour (new Color (207, 181, 59));
	c.setFont (new Font ("Garamond", Font.BOLD, 20));
	c.drawString ("$" + balance, 97, 17); // display updated balance
	c.drawString ("Bet = $" + bet, 5, 35); // display bet
	c.setFont (new Font ("Algerian", Font.BOLD, 38));
	c.drawString ("Press any key to spin!", 85, 485); // informs user on how to continue
	c.getChar (); // key pressed
    }


    public void reelSpin ()
    {
	String reel; // stores final reel
	drawMachine (true, 0); // draws machine with arm down
	c.setColour (Color.black);
	c.fillRect (0, 450, 640, 40); // clears old msgs
	c.fillRect (520, 120, 40, 110); // clears old machine arm
	for (int i = 1 ; i < 50 ; i++) // runs loop 49 times
	{
	    drawReel (reelCalc (), 0); // draws random reel
	    try
	    {
		Thread.sleep (3 * i + 50); // spin speed slows down over time
	    }
	    catch (Exception e)
	    {
	    }
	}
	reel = reelCalc (); // calculates final reel
	drawReel (reel, 0); // draws final reel on 50th time
	if (reel.charAt (0) == reel.charAt (1) && reel.charAt (1) == reel.charAt (2)) //detects win
	{
	    balance += bet * 100; // updates balance
	    c.setColour (Color.black);
	    c.fillRect (97, 0, 100, 20); // clears old balance
	    c.setColour (new Color (113, 0, 0));
	    c.fillRoundRect (210, 75, 220, 55, 15, 15); // clears display
	    c.setColour (new Color (207, 181, 59));
	    c.setFont (new Font ("Garamond", Font.BOLD, 20));
	    c.drawString ("$" + balance, 97, 17); // displays updated balance
	    c.setFont (new Font ("Algerian", Font.BOLD, 42));
	    c.drawString ("Jackpot!", 215, 118); // draws win display
	    for (int i = 0 ; i <= bet * 100 ; i += 10) // display winnings animation
	    {
		c.setColour (Color.black);
		c.fillRect (386, 385, 100, 45); // clears previous number
		c.setColour (new Color (207, 181, 59));
		c.drawString ("You won $" + i + "!", 165, 425); // draws new msg
		try
		{
		    Thread.sleep (45); // delays msg
		}
		catch (Exception e)
		{
		}
	    }
	}
	else // user did not win
	{
	    c.setColour (new Color (207, 181, 59));
	    c.setFont (new Font ("Algerian", Font.BOLD, 32));
	    c.drawString ("Better luck next time :(", 105, 425); // wishes better luck
	}
	if (balance > 0) // only asks for further input if user has money left
	{
	    c.setFont (new Font ("Algerian", Font.BOLD, 26));
	    c.drawString ("Press \'m\' for main menu and \'r\' to respin.", 30, 485); // asks for input
	    while (true) // error trapped input
	    {
		try
		{
		    choice = c.getChar (); // gets input
		    if (choice != 'm' && choice != 'M' && choice != 'r' && choice != 'R') // if input is invalid
			throw new Exception ();
		    break; // input is valid, exit loop
		}
		catch (Exception e)
		{
		    c.drawString ("PLEASE ENTER A VALID OPTION.", 120, 460); //  informs user to enter another, valid input
		}
	    }
	    if (choice == 'm' || choice == 'M')
		choice = '0'; // go to main menu
	    else if (choice == 'r' || choice == 'R')
		choice = '2'; // play again
	}
	else // user has lost
	{
	    try
	    {
		Thread.sleep (1500); // gives user time to see the result of the last spin
	    }
	    catch (Exception e)
	    {
	    }
	}
    }


    public void highscores ()
    {
	PrintWriter clear; // files the changes if highscore are cleared
	updateHighscores ();
	c.setColour (Color.black);
	c.fillRect (0, 0, 640, 500); // draws background
	c.setColour (new Color (163, 0, 0));
	c.fillRoundRect (100, 135, 440, 300, 25, 25); // panel
	c.setColour (new Color (207, 181, 59));
	c.setFont (new Font ("Algerian", Font.BOLD, 42));
	c.drawString ("Highscores", 195, 85); // title
	c.setFont (new Font ("Garamond", Font.BOLD, 27));
	for (int i = 0 ; i < RANK ; i++) // repeat for all ranks
	{
	    if (Integer.parseInt (highscores [i] [1]) > 100) // only print if highsocre is above 100, filters out reset scores
	    {
		c.drawString ((i + 1) + ".", 120, 165 + i * 29); // rank numbering
		c.drawString (highscores [i] [0], 180, 165 + i * 29); // name
		c.drawString ("$" + highscores [i] [1], 420, 165 + i * 29); // balance
	    }
	}
	c.setFont (new Font ("Algerian", Font.BOLD, 26));
	c.drawString ("Press \'m\' for main menu and \'r\' to reset.", 30, 485); // asks for input
	while (true) // error trapped input
	{
	    try
	    {
		choice = c.getChar (); // gets input
		if (choice != 'm' && choice != 'M' && choice != 'r' && choice != 'R') // if input is invalid
		    throw new Exception ();
		break; // input is valid, exit loop
	    }
	    catch (Exception e)
	    {
		c.drawString ("PLEASE ENTER A VALID OPTION.", 120, 460); //  informs user to enter, another valid input
	    }
	}
	if (choice == 'm' || choice == 'M')
	    choice = '0'; // go to main menu
	else if (choice == 'r' || choice == 'R') // reset highscores
	{
	    try
	    {
		clear = new PrintWriter (new FileWriter ("highscores")); // initializes output
		for (int k = 0 ; k < RANK ; k++) // repeat for ten highscores
		{
		    highscores [k] [0] = "N"; // reset name
		    highscores [k] [1] = "0"; // reset balance
		    clear.print (highscores [k] [0]); // file changes
		    clear.print (" ");
		    clear.println (highscores [k] [1]);
		}
		clear.close (); // closes output
	    }
	    catch (IOException e)
	    {
	    }
	    choice = '3'; // reload highscores
	}
    }


    public void fileData ()
    {
	PrintWriter output, saveGame; // output streams
	updateHighscores ();
	try
	{
	    output = new PrintWriter (new FileWriter ("highscores")); // file highscores
	    for (int k = 0 ; k < RANK ; k++) // repeat for ten highscores
	    {
		output.print (highscores [k] [0]); // name
		output.print (" ");
		output.println (highscores [k] [1]); // balance
	    }
	    output.close (); // close
	    saveGame = new PrintWriter (new FileWriter ("lastPlayed")); // save game
	    saveGame.println (name); // name
	    saveGame.print (balance); // balance
	    saveGame.close (); // close
	}
	catch (IOException e)
	{
	}
    }


    public void exit ()
    {
	c.setColour (Color.black);
	c.fillRect (0, 0, 640, 500); // Background
	c.setColour (new Color (163, 0, 0));
	c.fillRoundRect (100, 135, 440, 300, 25, 25); //panel
	c.setColour (new Color (207, 181, 59));
	c.setFont (new Font ("Algerian", Font.BOLD, 42));
	c.drawString ("Thanks for using!", 112, 85); // title
	c.setFont (new Font ("Garamond", Font.BOLD, 28)); // goodbye messages
	if (balance >= 100)
	    c.drawString ("Well Played!", 245, 175); // user made overall profit or broke even(winnings>=loss)
	else if (balance > 0)
	    c.drawString ("Better Luck next time!", 190, 175); // user made overall loss (winnings<loss)
	else
	    c.drawString ("You Lost :/", 245, 175); // user had no money left
	if (Integer.parseInt (highscores [9] [1]) < balance && balance > 100) // new high score
	    c.drawString ("You set a new highscore of $" + balance, 130, 205);
	else
	    c.drawString ("Your final balance was $" + balance, 160, 205); // gives final balance
	c.drawString ("This Program was created by", 140, 375); // informs who created program
	c.drawString ("Shreyansh Thakral.", 215, 405);
    }


    public static void main (String[] args)  // main method
    {
	Slots p = new Slots ();
	p.splashScreen (); // splash screen
	p.getData (); // sets name and balance, or loads previous game
	while (balance > 0) // while user has not lost
	{
	    if (choice == '0') // main menu
		p.mainMenu ();
	    else if (choice == '1') // instructions
		p.instructions ();
	    else if (choice == '2') // user is playing
	    {
		p.playGame ();
		p.reelSpin ();
	    }
	    else if (choice == '3') // display highscores
		p.highscores ();
	    else
		break; // user wants to exit
	}
	p.fileData (); // files highscores and player info
	p.exit (); // exit message
    }
}






