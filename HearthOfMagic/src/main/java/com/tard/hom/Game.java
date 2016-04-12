package com.tard.hom;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Game implements ActionListener
{
	private Player P1;
	private Player P2;
	private List<Integer> boules;
	private List<Card> allCards;
	private int tour;
	private int tourBackup;
	private int nbCartes;
	private Boolean partieEnCours;
	
	private JButton passerB, capitulerB, pretB, NewGameB, changeDeckB;
	private JPanel top5,top0,top4,top,top2,top3,infos,msg;
	
	private int width;
	private int height;
	private Boolean accepterEnDouble;
	
	public Game(String n1, String n2, int tour, int nbCartes, Boolean accepterEnDouble)
	{
		this.P1 = new Player(n1);
		this.P2 = new Player(n2);
		final CsvEuro Euro = new CsvEuro();
		this.boules = Euro.findBoules();
		final CsvDeck Deck = new CsvDeck();
		this.allCards = Deck.getDeck();
		this.tourBackup = tour;
		this.nbCartes = nbCartes;
		this.accepterEnDouble = accepterEnDouble;
		this.width = 116;
		this.height = 162;
		this.partieEnCours = false;
	}
	
	public void start() throws IOException
	{
		init();
		GUI();
	}
	
	public void GUI() throws IOException
	{
		JFrame frame = new JFrame("Hearth Of Magics");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(1140,960));
		
		frame.setLayout(new BorderLayout());
		
		JPanel largeBL = new JPanel(new BorderLayout());
		
		JPanel infbutt2 = new JPanel(new GridLayout(1,1));
		infbutt2.add(new JPanel(new FlowLayout(FlowLayout.LEFT)));
		top0 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//top0.setMinimumSize(new Dimension(width,height));
		updatePtsJ1();
		infbutt2.add(top0);
		JPanel buttonsO = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		changeDeckB = new JButton("Nouveau deck");
		changeDeckB.setActionCommand("Nouveau deck");
		changeDeckB.addActionListener(this);
		buttonsO.add(changeDeckB);
		NewGameB = new JButton("Nouvelle partie");
		NewGameB.setEnabled(false);
		NewGameB.setActionCommand("Nouvelle partie");
		NewGameB.addActionListener(this);
		buttonsO.add(NewGameB);
		infbutt2.add(buttonsO);
		frame.add(infbutt2, BorderLayout.NORTH);
		
		JPanel grid = new JPanel(new GridLayout(2,7));
		top = new JPanel(new FlowLayout(FlowLayout.CENTER));
		updateHandJ1();
		grid.add(top);
		top2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		updateBoardJ1();
		grid.add(top2);
		largeBL.add(grid, BorderLayout.NORTH);
		
		JPanel infbutt = new JPanel(new GridLayout(1,1));
		infos = new JPanel(new FlowLayout(FlowLayout.LEFT));
		updateTourStr();
		infos.add(pretB);
		infbutt.add(infos);
		msg = new JPanel(new FlowLayout(FlowLayout.CENTER));
		updateMsg("");
		infbutt.add(msg);
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		passerB = new JButton("Passer");
		passerB.setEnabled(false);
		passerB.setActionCommand("Passer");
		passerB.addActionListener(this);
		buttons.add(passerB);
		capitulerB = new JButton("Capituler");
		capitulerB.setEnabled(false);
		capitulerB.setActionCommand("Capituler");
		capitulerB.addActionListener(this);
		buttons.add(capitulerB);
		infbutt.add(buttons);
		largeBL.add(infbutt, BorderLayout.CENTER);
		
		JPanel grid2 = new JPanel(new GridLayout(2,7));
		top3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		updateBoardJ2();
		grid2.add(top3);
		top4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		updateHandJ2();
		grid2.add(top4);
		largeBL.add(grid2, BorderLayout.SOUTH);
		
		frame.add(largeBL, BorderLayout.CENTER);
		
		top5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		updatePtsJ2();
		frame.add(top5, BorderLayout.SOUTH);
		
		frame.pack();
		frame.setVisible(true);
		// this.updateUI();
	}
	
	private void updateMsg(String str)
	{
		msg.removeAll();
		msg.add(new JLabel(str));
		msg.updateUI();
	}
	
	private void updateTourStr()
	{
		infos.removeAll();
		if (partieEnCours == true)
		{
			if (tour > 2)
			{
				infos.add(new JLabel("Ca va être au tour de " + getPlayer(getTour()-2).getName()));
			}
			else infos.add(new JLabel("Au tour de " + getPlayer(getTour()).getName()));
		}
		else infos.add(new JLabel("En attente"));
		pretB = new JButton("Prêt");
		pretB.setEnabled(false);
		pretB.setActionCommand("Prêt");
		pretB.addActionListener(this);
		infos.add(pretB);
		infos.updateUI();
	}
	
	private void updateBoardJ1() throws IOException
	{
		top2.removeAll();
		top2.setBorder(BorderFactory.createTitledBorder(""));
		if (partieEnCours == true)
		{
			for (int i=0;i<P1.getSizeBoard();i++)
			{
				if (tour==1)
				{
					
				}
				else
				{
					addToPanel(top2, "img/dos.png", width, height, false,"","","","");
				}
			}
		} else addToPanel(top2, "img/vide.png", width, height, false,"","","","");
		top2.updateUI();
	}
	
	private void updateBoardJ2() throws IOException
	{
		top3.removeAll();
		top3.setBorder(BorderFactory.createTitledBorder(""));
		if (partieEnCours == true)
		{
			for (int i=0;i<P2.getSizeBoard();i++)
			{
				if (tour==1)
				{
					
				}
				else
				{
					addToPanel(top3, "img/dos.png", width, height, false,"","","","");
				}
			}
		} else addToPanel(top3, "img/vide.png", width, height, false,"","","","");
		top3.updateUI();
	}
	
	private void updateHandJ1() throws IOException
	{
		top.removeAll();
		top.setBorder(BorderFactory.createTitledBorder("Main de " + getPlayer(1).getName()));
		if (partieEnCours == true)
		{
			for (int i=0;i<P1.getSizeHand();i++)
			{
				if (tour==1)
				{
					Card c = P1.getCardFromHandById(i,false);
					String str = "";
					switch (c.getElement())
					{
						case FIRE:
							str = "feu";
						break;
						case WATER:
							str = "eau";
						break;
						case EARTH:
							str = "terre";
						break;
						case AIR:
							str = "air";
						break;
					}
					if (c.getProvocation()==true) str+="P";
					str+=".png";
					addToPanel(top, "img/"+str, width, height, true, Integer.toString(c.getAttack()), Integer.toString(c.getLife()), Integer.toString(c.getCost()), c.getName());
				}
				else
				{
					addToPanel(top, "img/dos.png", width, height, false,"","","","");
				}
			}
		} else addToPanel(top, "img/vide.png", width, height, false,"","","","");
		top.updateUI();
	}
	
	private void updateHandJ2() throws IOException
	{
		top4.removeAll();
		top4.setBorder(BorderFactory.createTitledBorder("Main de " + getPlayer(2).getName()));
		if (partieEnCours == true)
		{
			for (int i=0;i<P2.getSizeHand();i++)
			{
				if (tour==2)
				{
					Card c = P2.getCardFromHandById(i,false);
					String str = "";
					switch (c.getElement())
					{
						case FIRE:
							str = "feu";
						break;
						case WATER:
							str = "eau";
						break;
						case EARTH:
							str = "terre";
						break;
						case AIR:
							str = "air";
						break;
					}
					if (c.getProvocation()==true) str+="P";
					str+=".png";
					addToPanel(top4, "img/"+str, width, height, true, Integer.toString(c.getAttack()), Integer.toString(c.getLife()), Integer.toString(c.getCost()), c.getName());
				}
				else
				{
					addToPanel(top4, "img/dos.png", width, height, false,"","","","");
				}
			}
		} else addToPanel(top4, "img/vide.png", width, height, false,"","","","");
		top4.updateUI();
	}
	
	private void updatePtsJ1() throws IOException
	{
		top0.removeAll();
		if (partieEnCours == true)
		{
			for (int i=0;i<P1.getMana().getMana();i++)
			{
				addToPanel(top0, "img/e.png", 34, 42, false,"","","","");
			}
			for (int i=0;i<P1.getMana().getManaMax()-P1.getMana().getMana();i++)
			{
				addToPanel(top0, "img/ev.png", 34, 42, false,"","","","");
			}
		}
		else addToPanel(top0, "img/vide.png", 34, 42, false,"","","","");
		top0.updateUI();
	}
	
	private void updatePtsJ2() throws IOException
	{
		top5.removeAll();
		if (partieEnCours == true)
		{
			for (int i=0;i<P2.getMana().getMana();i++)
			{
				addToPanel(top5, "img/e.png", 34, 42, false,"","","","");
			}
			for (int i=0;i<P2.getMana().getManaMax()-P2.getMana().getMana();i++)
			{
				addToPanel(top5, "img/ev.png", 34, 42, false,"","","","");
			}
		}
		else addToPanel(top5, "img/vide.png", 34, 42, false,"","","","");
		top5.updateUI();
	}
	
	private void updatePts()
	{
		if (tour == 1) 
		{
			if (P1.getMana().getManaMax()==10) P1.getMana().resetAddTourMax();
			else P1.getMana().resetAddTour();
		}
		else if (tour == 2) 
		{
			if (P2.getMana().getManaMax()==10) P2.getMana().resetAddTourMax();
			else P2.getMana().resetAddTour();
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case "Nouveau deck":
				P1.clearDeck();
				P2.clearDeck();
				P1.addRandomInDeck(nbCartes, boules, allCards);
				P2.addRandomInDeck(nbCartes, boules, allCards);
				updateMsg("De nouveaux Decks ont été construits");
				if (P1.getSizeDeck() == 0 || P2.getSizeDeck() == 0) {return;}
				else
				{
					NewGameB.setEnabled(true);
				}
			break;
			case "Nouvelle partie":
				try 
				{
					P1.addRandomInHand(2, boules);
					P2.addRandomInHand(3, boules);
					partieEnCours = true;
					updatePtsJ1();
					updatePtsJ2();
					updateHandJ1();
					updateHandJ2();
					updateBoardJ1();
					updateBoardJ2();
					updateTourStr();
					updateMsg("");
					NewGameB.setEnabled(false);
					changeDeckB.setEnabled(false);
					pretB.setEnabled(true);
				} catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			break;
			case "Prêt":
				try
				{
					switchTour();
					if (tour == 1) 
					{
						P1.addRandomInHand(1, boules);
					}
					else if (tour == 2) 
					{
						P2.addRandomInHand(1, boules);
					}
					updatePts();
					updatePtsJ1();
					updatePtsJ2();
					updateHandJ1();
					updateHandJ2();
					updateBoardJ1();
					updateBoardJ2();
					updateTourStr();
					passerB.setEnabled(true);
					capitulerB.setEnabled(true);
				} catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			break;
			case "Passer":
				try
				{
					switchTour();
					updatePtsJ1();
					updatePtsJ2();
					updateHandJ1();
					updateHandJ2();
					updateBoardJ1();
					updateBoardJ2();
					updateTourStr();
					passerB.setEnabled(false);
					capitulerB.setEnabled(false);
					pretB.setEnabled(true);
				} catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			break;
			case "Capituler":
				if (tour == 1) updateMsg(P2.getName() + " gagne !");
				else if (tour == 2) updateMsg(P1.getName() + " gagne !");
				passerB.setEnabled(false);
				capitulerB.setEnabled(false);
				NewGameB.setEnabled(true);
				changeDeckB.setEnabled(true);
				reset();
			break;
		}
	}
	
	private static void addToPanel(JPanel p, String token, int x, int y, Boolean affO, String attack, String life, String cost, String name) throws IOException
	{
		File file = new File(Launcher.class.getClassLoader().getResource(token).toString().substring(6));
		if (!file.exists()) 
		{
			System.exit(0);
		}
		BufferedImage img = ImageIO.read(file);
		Graphics g = img.getGraphics();
	    g.setFont(g.getFont().deriveFont(180f));
	    g.setColor(Color.WHITE);
	    if (affO == true)
	    {
	    	g.drawString(cost, 1150, 170);
	    	g.drawString(attack, 50, 1960);
	    	g.drawString(life, 1290, 1960);
	    	g.setFont(g.getFont().deriveFont(130f));
	    	g.drawString(name, 150, 1100);
	    }
	    g.dispose();
		ImageIcon icon = new ImageIcon(img.getScaledInstance(x, y, Image.SCALE_SMOOTH)); // Create the image from the filename
		JLabel label = new JLabel(icon); // Associate the image to a label
		p.add(label); // Add the label to a panel
	}
	
	private void init()
	{
		if (boules.size() == 0 || allCards.size() == 0)
		{
			return;
		}
		reset();
	}
	
	private void reset()
	{
		P1.clearBoard();
		P1.clearHand();
		P2.clearBoard();
		P2.clearHand();
		P1.getMana().reset();
		P2.getMana().reset();
		P1.resetLife();
		P2.resetLife();
		this.tour = this.tourBackup+2;
	}
	
	private void switchTour()
	{
		if (this.tour == 1) this.tour=4;
		else if (this.tour == 4) this.tour=2;
		else if (this.tour == 2) this.tour=3;
		else this.tour=1;
	}
	
	public int getTour()
	{
		return tour;
	}
	
	public Player getPlayer(int i)
	{
		if (i==1) return P1;
		else if (i==2) return P2;
		else return new Player("?");
	}

}
