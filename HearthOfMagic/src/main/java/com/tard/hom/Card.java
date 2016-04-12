package com.tard.hom;

public class Card
{
	private String name;
	private Rare rare;
	private int life;
	private int attack;
	private int cost;
	private boolean provocation;
	private Element element;

	public Card(String name, Rare rare, int life, int attack, int cost, boolean provocation, Element element) 
	{
		this.name = name;
		this.rare = rare;
		this.life = life;
		this.attack = attack;
		this.cost = cost;
		this.provocation = provocation;
		this.element = element;
	}

	public String getName() 
	{
		return name;
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
	
	public boolean getProvocation()
	{
		return provocation;
	}
	
	public Element getElement()
	{
		return element;
	}
}
