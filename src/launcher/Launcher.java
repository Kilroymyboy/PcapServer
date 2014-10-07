package launcher;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Launcher
{
	Robot robot = new Robot();
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Clipboard clipboard = toolkit.getSystemClipboard();

	public static void main(String[] args) throws AWTException
	{
		new Launcher();
	}

	public Launcher() throws AWTException
	{

		String nightbot = "https://www.nightbot.tv/login";
		String chat = "http://www.twitch.tv/kilroymyboy/chat";
		String browser = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";

		Runtime runtime = Runtime.getRuntime();
		try 
		{
			runtime.exec(new String[] {browser, nightbot});
			runtime.exec(new String[] {browser, chat});
		} catch (IOException e) {e.printStackTrace();}

		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.delay(5000);

		if(switchToPage("https://www.nightbot.tv/login"))
		{
			for (int i = 0; i < 17; i++)
				keyPress(KeyEvent.VK_TAB);
			type("Kilroymyboy");
			keyPress(KeyEvent.VK_TAB);
			type("");
			robot.delay(5000);
			robot.delay(5000);
			keyPress(KeyEvent.VK_ENTER);
			robot.delay(5000);
			
		}

		if (switchToPage("https://www.nightbot.tv/index"))
		{
			for (int i = 0; i < 32; i++)
				keyPress(KeyEvent.VK_TAB);
			keyPress(KeyEvent.VK_ENTER);
			keyPress(KeyEvent.VK_TAB);
			keyPress(KeyEvent.VK_ENTER);
		}
		
		switchToPage("http://www.twitch.tv/kilroymyboy/chat");

		for (int i = 0; i < 31; i++)
			keyPress(KeyEvent.VK_TAB);
		type("Test Message");
		keyPress(KeyEvent.VK_ENTER);

		System.exit(0);
	}
	
	
	
	
	
	
	
	
	
	

	private void keyPress(int key)
	{
		robot.keyPress(key);
		robot.keyRelease(key);
		robot.delay(40);
	}
	private void keyPress(int key, int key2)
	{
		robot.keyPress(key);
		robot.keyPress(key2);
		robot.keyRelease(key);
		robot.keyRelease(key2);
		robot.delay(10);
	}



	private void leftClick()
	{
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(200);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.delay(200);
	}

	private void type(int i)
	{
		robot.delay(4);
		robot.keyPress(i);
		robot.keyRelease(i);
	}

	private void type(String s)
	{
		byte[] bytes = s.getBytes();
		for (byte b : bytes)
		{
			int code = b;
			// keycode only handles [A-Z] (which is ASCII decimal [65-90])
			if (code > 96 && code < 123) code = code - 32;
			robot.delay(40);
			robot.keyPress(code);
			robot.keyRelease(code);
		}
	}

	private boolean switchToPage(String URL)
	{
		String result = "FAILED";
		int failSafe = 0;
		do
		{
			keyPress(KeyEvent.VK_CONTROL, KeyEvent.VK_TAB);
			robot.delay(50);
			keyPress(KeyEvent.VK_CONTROL, KeyEvent.VK_L);
			keyPress(KeyEvent.VK_CONTROL, KeyEvent.VK_C);

			try 
			{
				result = (String) clipboard.getData(DataFlavor.stringFlavor);
			} 
			catch (UnsupportedFlavorException | IOException e) 
			{
				e.printStackTrace();
			}


			failSafe++;
			if (failSafe > 6)
			{
				System.out.println("failed to find " + URL);
				robot.delay(2000);
				return false;
			}
		}
		while (result.compareToIgnoreCase(URL) != 0);
		
		return true;
	}
}