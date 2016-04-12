package com.tard.hom;

public class Mana 
{
	private int mana;
	private int manaMax;
	
	public Mana()
	{
		this.mana = 0;
		this.manaMax = 0;
	}
	
	public int getMana()
	{
		return mana;
	}
	
	public int getManaMax()
	{
		return manaMax;
	}
	
	public void reset()
	{
		this.mana = 0;
		this.manaMax = 0;
	}
	
	public void addMana()
	{
		if (this.manaMax != this.mana) this.mana++;
	}
	
	public void resetAddTourMax()
	{
		this.mana = this.manaMax;
	}
	
	public void resetAddTour()
	{
		this.manaMax++;
		this.mana = this.manaMax;
	}
}
