package com.tard.hom;

import java.io.IOException;

public class Launcher 
{
	private static Game game;
	
	public static void main(String[] args) throws IOException
	{
		game = new Game("J1","J2",1,20); // On commence par joueur 1, 40 cartes/personne
		game.start();
	}
}
