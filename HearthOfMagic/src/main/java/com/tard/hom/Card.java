package com.tard.hom;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Card
{
	private String name;
	private Rare rare;
	private int life;
	private int attack;
	private int cost;
	private int id;
	private boolean provocation;
	private boolean canAttack;
	private Element element;

	public Card(String name, Rare rare, boolean provocation, Element element, int id) throws IOException 
	{
		this.name = name;
		this.rare = rare;
		this.life = 0;
		this.attack = 0;
		this.cost = 0;
		this.provocation = provocation;
		this.element = element;
		this.id = id;
		this.canAttack = false;
	}
	
	String getName() 
	{
		return name;
	}
	
	String getRealName() 
	{
		return name.replace("*", " ");
	}

	public Rare getRare() 
	{
		return rare;
	}
	
	public int getLife()
	{
		return life;
	}
	
	public int getAttack()
	{
		return attack;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setLife(int life)
	{
		this.life = life;
	}
	
	public void setAttack(int attack)
	{
		this.attack = attack;
	}
	
	public void setCost(int cost)
	{
		this.cost = cost;
	}
	
	public boolean getProvocation()
	{
		return provocation;
	}
	
	public Element getElement()
	{
		return element;
	}
	
	public void setCanAttack(boolean b)
	{
		this.canAttack = b;
	}
	
	public boolean getCanAttack()
	{
		return canAttack;
	}
}
