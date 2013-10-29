package com.manbu.tool;

import javax.swing.UIManager;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		try {
		    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}
    	
    	new MainFrame();
        System.out.println( "Hello World!" );
    }
}
