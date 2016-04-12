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
	private int id;

	public Card(String name, Rare rare, boolean provocation, Element element, int id) 
	{
		this.name = name;
		this.rare = rare;
		this.life = 0;
		this.attack = 0;
		this.cost = 0;
		this.provocation = provocation;
		this.element = element;
		this.id = id;
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
}
