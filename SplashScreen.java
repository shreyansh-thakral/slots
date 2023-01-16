/*
Shreyansh Thakral
March 26th 2020
Ms. Basaraba
The Splash Screen Thread runs at the start of the program.
*/
import java.awt.*;
import hsa.Console;

public class SplashScreen extends Thread 
{
    private Console c;
    public void drawAnimation () 
    {
	int j;
	for (int i = 1 ; i < 60 ; i++) //  animation for-loop
	{
	    j = (int) Math.round(i * 1.5); // j holds modified count
	    c.setColour (Color.black);
	    c.fillRect (297 - j, 85 - i, 250, 30 + i); // clearing rectangle gets bigger but stays centered
	    c.setColour (new Color (207, 181, 59));
	    c.setFont (new Font ("Algerian", Font.BOLD, 5 + i)); // Font gets bigger
	    c.drawString ("SLOTS!", 297 - j, 85); // text stays centered as it gets bigger
	    try
	    {
		Thread.sleep (100);
	    }
	    catch (Exception e)
	    {
	    }
	}
    }


    public SplashScreen (Console con)  
    {
	c = con;
    }


    public void run ()  //starts animation
    {
	drawAnimation ();
    }
}

