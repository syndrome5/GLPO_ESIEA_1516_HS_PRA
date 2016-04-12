package com.tard.hom;

import java.io.IOException;

public class Launcher 
{
	private static Game game;
	
	public static void main(String[] args) throws IOException
	{
		game = new Game("J1","J2",1,true,2,false);
		game.start();
	}
}
