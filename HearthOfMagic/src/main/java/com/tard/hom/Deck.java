package com.tard.hom;

import java.io.IOException;
import java.util.List;

public interface Deck 
{
	public List<Card> getDeck() throws NumberFormatException, IOException ;
}
