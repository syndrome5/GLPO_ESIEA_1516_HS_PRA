package com.tard.hom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Game extends MouseAdapter implements ActionListener 
{
	private Player P1;
	private Player P2;
	private List<Integer> boules;
	private List<Card> allCards;
	private List<BufferedImage> allCardsI;
	private int tour;
	private int tourBackup;
	private int nbCartes;
	private Boolean partieEnCours;
	
	private JButton passerB, capitulerB, pretB, NewGameB, changeDeckB, faceJ1B, faceJ2B;
	private JPanel top5,top0,top4,top,top2,top3,infos,msg,top1,top6;
	private List<JLabel> J1HandL,J2HandL,J1BoardL,J2BoardL;
	private ImageIcon videII, eII, evII, dosII, videpII;
	
	private int width;
	private int height;
	private String selected;
	
	public Game(String n1, String n2, int tour, int nbCartes) throws IOException
	{
		this.P1 = new Player(n1);
		this.P2 = new Player(n2);
		final CsvEuro Euro = new CsvEuro();
		this.boules = Euro.findBoules();
		final CsvDeck Deck = new CsvDeck();
		this.allCards = Deck.getDeck();
		this.tourBackup = tour;
		this.nbCartes = nbCartes;
		this.width = 116;
		this.height = 162;
		this.partieEnCours = false;
		this.videII = null;
		this.eII = null;
		this.evII = null;
		this.dosII = null;
		this.videpII = null;
		this.allCardsI = new ArrayList<BufferedImage>();
		this.J1HandL = new ArrayList<JLabel>();
		this.J2HandL = new ArrayList<JLabel>();
		this.J1BoardL = new ArrayList<JLabel>();
		this.J2BoardL = new ArrayList<JLabel>();
		this.selected = "";
	}
	
	public void start() throws IOException
	{
		reset();
		int i=0;
		
		JFrame frame = new JFrame("Hearth Of Magics - Loading");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(350,100));
		frame.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		updateLabelInit(panel, i);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
		try 
		{
			videpII = loadImageIcon("img/vide.png",34,42);
			i++; updateLabelInit(panel, i);
			videII = loadImageIcon("img/vide.png",width,height);
			i++; updateLabelInit(panel, i);
			eII = loadImageIcon("img/e.png",34,42);
			i++; updateLabelInit(panel, i);
			evII = loadImageIcon("img/ev.png",34,42);
			i++; updateLabelInit(panel, i);
			dosII = loadImageIcon("img/dos.png",width,height);
			i++; updateLabelInit(panel, i);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		for (Card c:allCards)
		{
			try 
			{
				File file = new File(Launcher.class.getClassLoader().getResource("img/"+c.getId()+".png").toString().substring(6));
				allCardsI.add(ImageIO.read(file));
				i++; updateLabelInit(panel, i);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		frame.setVisible(false);
		
		GUI();
	}
	
	private void updateLabelInit(JPanel panel, int i)
	{
		panel.removeAll();
		panel.add(new JLabel("Chargement des images ("+i+"/"+(allCards.size()+5)+")"));
		panel.updateUI();
	}
	
	public void GUI() throws IOException
	{
		JFrame frame = new JFrame("Hearth Of Magics");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(1140,960));
		
		frame.setLayout(new BorderLayout());
		
		JPanel largeBL = new JPanel(new BorderLayout());
		
		JPanel infbutt2 = new JPanel(new GridLayout(1,1));
		top1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		updateScoreJ1();
		faceJ1B.setEnabled(false);
		infbutt2.add(top1);
		top0 = new JPanel(new FlowLayout(FlowLayout.CENTER));
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
		updateMsg(allCards.size() + " cartes chargées !");
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
		
		JPanel infbutt3 = new JPanel(new GridLayout(1,1));
		top6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		updateScoreJ2();
		faceJ2B.setEnabled(false);
		infbutt3.add(top6);
		top5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		updatePtsJ2();
		infbutt3.add(top5);
		infbutt3.add(new JLabel(""));
		frame.add(infbutt3, BorderLayout.SOUTH);
		
		frame.pack();
		frame.setVisible(true);
		// this.updateUI();
	}
	
	@Override
    public void mouseClicked(MouseEvent e) 
    {
		 JLabel l = new JLabel();
		 l.setName("null");
		 JPanel p = new JPanel();
		 p.setName("null");
		 	
		 try
		 {
			 l = (JLabel) e.getSource();
		 } catch (ClassCastException e1)
		 {
			 try
			 {
				 p = (JPanel) e.getSource();
			 } catch (ClassCastException e11)
			 {
				 return;
			 }
		 }
		 
         int id=0,idRef=0;
         String msg="";
         Card c = null;
         
         if(p.getName().contains("BoardJ1"))
         {
        	 if (tour == 1)
        	 {
	        	 if (selected.contains("J1HandL"))
	        	 {
	        		 id = Integer.parseInt(selected.substring(7));
		        	 c = P1.getCardFromHandById(id, false);
	        		 if (P1.getMana().getMana() >= c.getCost())
	        		 {
	        			 if (P1.getSizeBoard() < 7)
	        			 {
	        				P1.getMana().takeMana(c.getCost());
	        				P1.addCardInBoard(P1.getCardFromHandById(id, true));
		     				try 
		     				{
								majHUD();
			     				updateMsg("Vous avez posé "+c.getRealName()+" sur le board");
			     				
							} catch (IOException e1) 
		     				{
								e1.printStackTrace();
							}
	        			 }
	        			 else
	        			 {
	        				 updateMsg("Le board est complet");
	        			 }
	        		 }
	        		 else
	        		 {
	        			 updateMsg("Vous n'avez pas assez de Mana");
	        		 }
	        		 selected="";
	        	 }
        	 }
         }
         else if(p.getName().contains("BoardJ2"))
         {
        	 if (tour == 2)
        	 {
	        	 if (selected.contains("J2HandL"))
	        	 {
	        		 id = Integer.parseInt(selected.substring(7));
		        	 c = P2.getCardFromHandById(id, false);
	        		 if (P2.getMana().getMana() >= c.getCost())
	        		 {
	        			 if (P2.getSizeBoard() < 7)
	        			 {
	        				P2.getMana().takeMana(c.getCost());
	        				P2.addCardInBoard(P2.getCardFromHandById(id, true));
		     				try 
		     				{
								majHUD();
			     				updateMsg("Vous avez posé "+c.getRealName()+" sur le board");
			     				
							} catch (IOException e11) 
		     				{
								e11.printStackTrace();
							}
	        			 }
	        			 else
	        			 {
	        				 updateMsg("Le board est complet");
	        			 }
	        		 }
	        		 else
	        		 {
	        			 updateMsg("Vous n'avez pas assez de Mana");
	        		 }
	        		 selected="";
	        	 }
        	 }
         }
         else if(l.getName().contains("J1HandL"))
         {
        	 id = Integer.parseInt(l.getName().substring(7));
        	 if (selected.equals("J1HandL"+id))
        	 {
        		 selected="";
        		 updateMsg("");
        		 return;
        	 }
        	 updateMsg("Vous avez sélectionné : "+P1.getCardFromHandById(id, false).getRealName()+" de votre main");
        	 selected = "J1HandL"+id;
         }
         else if(l.getName().contains("J2HandL"))
         {
        	 id = Integer.parseInt(l.getName().substring(7));
        	 if (selected.equals("J2HandL"+id))
        	 {
        		 selected="";
        		 updateMsg("");
        		 return;
        	 }
        	 updateMsg("Vous avez sélectionné : "+P2.getCardFromHandById(id, false).getRealName()+" de votre main");
        	 selected = "J2HandL"+id;
         }
         else if(l.getName().contains("J1BoardL"))
         {
        	 if (tour == 2)
        	 {
        		 if (selected.contains("J2BoardL"))
        		 {
        			 idRef = Integer.parseInt(selected.substring(8));
        			 if (P2.getCardFromBoardById(idRef, false).getCanAttack() == true)
        			 {
        				 id = Integer.parseInt(l.getName().substring(8));
        				 if (P1.gotProvocationInBoard(P1.getCardFromBoardById(id, false)) == false)
        				 {
		        			 msg=P2.getCardFromBoardById(idRef, false).getName()+" attaque "+P1.getCardFromBoardById(id, false).getName() +". ";
		        			 P2.getCardFromBoardById(idRef, false).setLife(P2.getCardFromBoardById(idRef, false).getLife()-P1.getCardFromBoardById(id, false).getAttack());
		        			 P1.getCardFromBoardById(id, false).setLife(P1.getCardFromBoardById(id, false).getLife()-P2.getCardFromBoardById(idRef, false).getAttack());
		        			 P2.getCardFromBoardById(idRef, false).setCanAttack(false);
		        			 if (P2.getCardFromBoardById(idRef, false).getLife()<=0) 
		        			 {
		        				 //msg+=P2.getCardFromBoardById(idRef, false).getName()+" meurt. ";
		        				 P2.getCardFromBoardById(idRef,true);
		        			 }
		        			 if (P1.getCardFromBoardById(id, false).getLife()<=0) 
		        			 {
		        				 //msg+=P1.getCardFromBoardById(id, false).getName()+" meurt. ";
		        				 P1.getCardFromBoardById(id,true);
		        			 }
		        			 updateMsg(msg);
		        			 try 
		        			 {
								majHUD();
							 } catch (IOException e1) {
								 e1.printStackTrace();
							 }
	        			 }
	    				 else
	    				 {
	    					 updateMsg("Il y a une provocation qui protège de cette attaque");
	        				 selected="";
	    				 }
        			 }
        			 else
        			 {
        				 updateMsg(P2.getCardFromBoardById(idRef, false).getName()+" ne peut pas attaquer ce tour");
        				 selected="";
        			 }
        		 }
        	 }
        	 else if (tour == 1)
        	 {
	        	 id = Integer.parseInt(l.getName().substring(8));
	        	 if (selected.equals("J1BoardL"+id))
	        	 {
	        		 selected="";
	        		 updateMsg("");
	        		 return;
	        	 }
	        	 updateMsg("Vous avez sélectionné : "+P1.getCardFromBoardById(id, false).getRealName()+" sur votre terrain");
	        	 selected = "J1BoardL"+id;
        	 }
         }
         else if(l.getName().contains("J2BoardL"))
         {
        	 if (tour == 1)
        	 {
        		 if (selected.contains("J1BoardL"))
        		 {
        			 idRef = Integer.parseInt(selected.substring(8));
        			 if (P1.getCardFromBoardById(idRef, false).getCanAttack() == true)
        			 {
        				 id = Integer.parseInt(l.getName().substring(8));
        				 if (P2.gotProvocationInBoard(P2.getCardFromBoardById(id, false)) == false)
        				 {
		        			 msg=P1.getCardFromBoardById(idRef, false).getName()+" attaque "+P2.getCardFromBoardById(id, false).getName() +". ";
		        			 P1.getCardFromBoardById(idRef, false).setLife(P1.getCardFromBoardById(idRef, false).getLife()-P2.getCardFromBoardById(id, false).getAttack());
		        			 P2.getCardFromBoardById(id, false).setLife(P2.getCardFromBoardById(id, false).getLife()-P1.getCardFromBoardById(idRef, false).getAttack());
		        			 P1.getCardFromBoardById(idRef, false).setCanAttack(false);
		        			 if (P1.getCardFromBoardById(idRef, false).getLife()<=0) 
		        			 {
		        				 //msg+=P1.getCardFromBoardById(idRef, false).getName()+" meurt. ";
		        				 P1.getCardFromBoardById(idRef,true);
		        			 }
		        			 if (P2.getCardFromBoardById(id, false).getLife()<=0) 
		        			 {
		        				 //msg+=P2.getCardFromBoardById(id, false).getName()+" meurt. ";
		        				 P2.getCardFromBoardById(id,true);
		        			 }
		        			 updateMsg(msg);
		        			 try 
		        			 {
								majHUD();
							 } catch (IOException e1) {
								 e1.printStackTrace();
							 }
        				 }
	    				 else
	    				 {
	    					 updateMsg("Il y a une provocation qui protège de cette attaque");
	        				 selected="";
	    				 }
        			 }
        			 else
        			 {
        				 updateMsg(P1.getCardFromBoardById(idRef, false).getName()+" ne peut pas attaquer ce tour");
        				 selected="";
        			 }
        		 }
        	 }
        	 else if (tour == 2)
        	 {
	        	 id = Integer.parseInt(l.getName().substring(8));
	        	 if (selected.equals("J2BoardL"+id))
	        	 {
	        		 selected="";
	        		 updateMsg("");
	        		 return;
	        	 }
	        	 updateMsg("Vous avez sélectionné : "+P2.getCardFromBoardById(id, false).getRealName()+" sur votre terrain");
	        	 selected = "J2BoardL"+id;
        	 }
         }
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
	
	private void updateScoreJ1()
	{
		top1.removeAll();
		top1.add(new JLabel("Points de vie : " + P1.getLife()));
		faceJ1B = new JButton("Attaquer " + P1.getName());
		faceJ1B.setActionCommand("FaceJ1");
		faceJ1B.addActionListener(this);
		top1.add(faceJ1B);
		top1.updateUI();
	}
	
	private void updateScoreJ2()
	{
		top6.removeAll();
		top6.add(new JLabel("Points de vie : " + P2.getLife()));
		faceJ2B = new JButton("Attaquer " + P2.getName());
		faceJ2B.setActionCommand("FaceJ2");
		faceJ2B.addActionListener(this);
		top6.add(faceJ2B);
		top6.updateUI();
	}
	
	private void updateBoardJ1() throws IOException
	{
		J1BoardL.clear();
		top2.removeAll();
		top2.setName("BoardJ1");
		top2.addMouseListener(this);
		top2.setBorder(BorderFactory.createTitledBorder(""));
		if (partieEnCours == true)
		{
			for (int i=0;i<P1.getSizeBoard();i++)
			{
				Card c = P1.getCardFromBoardById(i,false);
				J1BoardL.add(addToPanelCard(c, width, height));
				J1BoardL.get(J1BoardL.size()-1).setName("J1BoardL"+(J1BoardL.size()-1));
				J1BoardL.get(J1BoardL.size()-1).addMouseListener(this);
				top2.add(J1BoardL.get(J1BoardL.size()-1));
			}
		} else top2.add(new JLabel(videII));
		top2.updateUI();
	}
	
	private void updateBoardJ2() throws IOException
	{
		J2BoardL.clear();
		top3.removeAll();
		top3.setName("BoardJ2");
		top3.addMouseListener(this);
		top3.setBorder(BorderFactory.createTitledBorder(""));
		if (partieEnCours == true)
		{
			for (int i=0;i<P2.getSizeBoard();i++)
			{
				Card c = P2.getCardFromBoardById(i,false);
				J2BoardL.add(addToPanelCard(c, width, height));
				J2BoardL.get(J2BoardL.size()-1).setName("J2BoardL"+(J2BoardL.size()-1));
				J2BoardL.get(J2BoardL.size()-1).addMouseListener(this);
				top3.add(J2BoardL.get(J2BoardL.size()-1));
			}
		} else top3.add(new JLabel(videII));
		top3.updateUI();
	}
	
	private void updateHandJ1() throws IOException
	{
		J1HandL.clear();
		top.removeAll();
		top.setBorder(BorderFactory.createTitledBorder("Main de " + getPlayer(1).getName()));
		if (partieEnCours == true)
		{
			for (int i=0;i<P1.getSizeHand();i++)
			{
				if (tour==1)
				{
					Card c = P1.getCardFromHandById(i,false);
					J1HandL.add(addToPanelCard(c, width, height));
					J1HandL.get(J1HandL.size()-1).setName("J1HandL"+(J1HandL.size()-1));
					J1HandL.get(J1HandL.size()-1).addMouseListener(this);
					top.add(J1HandL.get(J1HandL.size()-1));
				}
				else
				{
					top.add(new JLabel(dosII));
				}
			}
		} else top.add(new JLabel(videII));
		top.updateUI();
	}
	
	private void updateHandJ2() throws IOException
	{
		J2HandL.clear();
		top4.removeAll();
		top4.setBorder(BorderFactory.createTitledBorder("Main de " + getPlayer(2).getName()));
		if (partieEnCours == true)
		{
			for (int i=0;i<P2.getSizeHand();i++)
			{
				if (tour==2)
				{
					Card c = P2.getCardFromHandById(i,false);
					J2HandL.add(addToPanelCard(c, width, height));
					J2HandL.get(J2HandL.size()-1).setName("J2HandL"+(J2HandL.size()-1));
					J2HandL.get(J2HandL.size()-1).addMouseListener(this);
					top4.add(J2HandL.get(J2HandL.size()-1));
				}
				else
				{
					top4.add(new JLabel(dosII));
				}
			}
		} else top4.add(new JLabel(videII));
		top4.updateUI();
	}
	
	private void updatePtsJ1() throws IOException
	{
		top0.removeAll();
		if (partieEnCours == true)
		{
			for (int i=0;i<P1.getMana().getMana();i++)
			{
				top0.add(new JLabel(eII));
			}
			for (int i=0;i<P1.getMana().getManaMax()-P1.getMana().getMana();i++)
			{
				top0.add(new JLabel(evII));
			}
		}
		else top0.add(new JLabel(videpII));
		top0.updateUI();
	}
	
	private void updatePtsJ2() throws IOException
	{
		top5.removeAll();
		if (partieEnCours == true)
		{
			for (int i=0;i<P2.getMana().getMana();i++)
			{
				top5.add(new JLabel(eII));
			}
			for (int i=0;i<P2.getMana().getManaMax()-P2.getMana().getMana();i++)
			{
				top5.add(new JLabel(evII));
			}
		}
		else top5.add(new JLabel(videpII));
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
		int id=0;
		switch(e.getActionCommand())
		{
		    case "FaceJ1":
		    	if (tour == 2)
		    	{
		    		if (selected.contains("J2BoardL"))
	        		{
		    			 id = Integer.parseInt(selected.substring(8));
		    			 if (P2.getCardFromBoardById(id, false).getCanAttack() == true)
	        			 {
		    				 if (P1.gotProvocationInBoard(null) == false)
		    				 {
		        				 P1.setLife(P1.getLife()-P2.getCardFromBoardById(id, false).getAttack());
			        			 P2.getCardFromBoardById(id, false).setCanAttack(false);
			        			 updateMsg(P2.getCardFromBoardById(id, false).getRealName() + " inflige directement " + P2.getCardFromBoardById(id, false).getAttack() + " dégâts!");
			        			 if (P1.getLife()<=0)
			        			 {
			        				 if (tour == 1) updateMsg(P1.getName() + " gagne !");
		        					 else if (tour == 2) updateMsg(P2.getName() + " gagne !");
		        					 passerB.setEnabled(false);
		        				 	 capitulerB.setEnabled(false);
		        					 NewGameB.setEnabled(true);
		        					 changeDeckB.setEnabled(true);
		        					 faceJ1B.setEnabled(false);
		        					 faceJ2B.setEnabled(false);
			        			 }
			        			 try 
			        			 {
									majHUD();
								 } catch (IOException e1) {
									 e1.printStackTrace();
								 }
		    				 }
		    				 else
		    				 {
		    					 updateMsg("Il y a une provocation qui protège de cette attaque");
		        				 selected="";
		    				 }
	        			 }
	        			 else
	        			 {
	        				 updateMsg(P2.getCardFromBoardById(id, false).getName()+" ne peut pas attaquer ce tour");
	        				 selected="";
	        			 }
	        		}
		    	}
		    break;
		    case "FaceJ2":
		    	if (tour == 1)
		    	{
		    		if (selected.contains("J1BoardL"))
	        		{
		    			 id = Integer.parseInt(selected.substring(8));
		    			 if (P1.getCardFromBoardById(id, false).getCanAttack() == true)
	        			 {
		    				 if (P2.gotProvocationInBoard(null) == false)
		    				 {
		        				 P2.setLife(P2.getLife()-P1.getCardFromBoardById(id, false).getAttack());
			        			 P1.getCardFromBoardById(id, false).setCanAttack(false);
			        			 updateMsg(P1.getCardFromBoardById(id, false).getRealName() + " inflige directement " + P1.getCardFromBoardById(id, false).getAttack() + " dégâts!");
			        			 if (P2.getLife()<=0)
			        			 {
			        				 if (tour == 1) updateMsg(P1.getName() + " gagne !");
		        					 else if (tour == 2) updateMsg(P2.getName() + " gagne !");
		        					 passerB.setEnabled(false);
		        				 	 capitulerB.setEnabled(false);
		        					 NewGameB.setEnabled(true);
		        					 changeDeckB.setEnabled(true);
		        					 faceJ1B.setEnabled(false);
		        					 faceJ2B.setEnabled(false);
			        			 }
			        			 try 
			        			 {
									majHUD();
								 } catch (IOException e1) {
									 e1.printStackTrace();
								 }
		    				 }
		    				 else
		    				 {
		    					 updateMsg("Il y a une provocation qui protège de cette attaque");
		        				 selected="";
		    				 }
	        			 }
	        			 else
	        			 {
	        				 updateMsg(P1.getCardFromBoardById(id, false).getName()+" ne peut pas attaquer ce tour");
	        				 selected="";
	        			 }
	        		}
		    	}
		    break;
			case "Nouveau deck":
				P1.clearDeck();
				P2.clearDeck();
				P1.addRandomInDeck(nbCartes, boules, allCards);
				P2.addRandomInDeck(nbCartes, boules, allCards);
				updateMsg("De nouveaux Decks ont été construits");
				if (P1.getSizeDeck() < 5 || P2.getSizeDeck() < 5) {return;}
				else
				{
					NewGameB.setEnabled(true);
				}
			break;
			case "Nouvelle partie":
				try 
				{
					reset();
					P1.addRandomInHand(1, boules, Rare.NORMAL);
					P1.addRandomInHand(1, boules, null);
					P2.addRandomInHand(2, boules, Rare.NORMAL);
					P2.addRandomInHand(1, boules, null);
					partieEnCours = true;
					majHUD();
					updateTourStr();
					updateMsg("");
					NewGameB.setEnabled(false);
					changeDeckB.setEnabled(false);
					pretB.setEnabled(true);
					faceJ1B.setEnabled(false);
					faceJ2B.setEnabled(false);
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
						P1.addRandomInHand(1, boules, null);
						P1.resetCanAttack();
					}
					else if (tour == 2) 
					{
						P2.addRandomInHand(1, boules, null);
						P2.resetCanAttack();
					}
					updatePts();
					majHUD();
					if (tour==1) faceJ2B.setEnabled(true);
					else if (tour == 2) faceJ1B.setEnabled(true);
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
					majHUD();
					updateTourStr();
					passerB.setEnabled(false);
					capitulerB.setEnabled(false);
					pretB.setEnabled(true);
					faceJ1B.setEnabled(false);
					faceJ2B.setEnabled(false);
					J1BoardL.clear();
					J2BoardL.clear();
					J1HandL.clear();
					J2HandL.clear();
					this.selected="";
					updateMsg("");
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
				faceJ1B.setEnabled(false);
				faceJ2B.setEnabled(false);
			break;
		}
	}
	
	private JLabel addToPanelCard(Card c, int x, int y) throws IOException
	{
		BufferedImage img = allCardsI.get(c.getId()-1);
		Graphics g = img.getGraphics();
	    g.setFont(g.getFont().deriveFont(180f));
    	g.setColor(Color.YELLOW);
    	if (c.getCost()==10) g.drawString(Integer.toString(c.getCost()), 1080, 170);
    	else g.drawString(Integer.toString(c.getCost()), 1150, 170);
    	g.setColor(Color.WHITE);
    	g.drawString(Integer.toString(c.getAttack()), 50, 1960);
    	g.setColor(Color.BLACK);
    	g.fillRect(1280, 1830, 200, 200);
    	g.setColor(Color.WHITE);
    	g.drawString(Integer.toString(c.getLife()), 1290, 1960);
    	g.setFont(g.getFont().deriveFont(130f));
    	drawLongString(g, c.getName(), 80, 1000);
    	if (c.getProvocation() == true)
    	{
    		g.drawString("Provocation", 380, 1000+3*g.getFontMetrics().getHeight());
    	}
    	g.drawString(c.getRare().getDescription(), 80, 1000+4*g.getFontMetrics().getHeight());
	    g.dispose();
		ImageIcon icon = new ImageIcon(img.getScaledInstance(x, y, Image.SCALE_SMOOTH)); // Create the image from the filename
		JLabel label = new JLabel(icon); // Associate the image to a label
		return label;
	}
	
	private void reloadImg(Card c)
	{
		try 
		{
			File file = new File(Launcher.class.getClassLoader().getResource("img/"+c.getId()+".png").toString().substring(6));
			allCardsI.remove(c.getId());
			allCardsI.add(c.getId(), ImageIO.read(file));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static ImageIcon loadImageIcon(String token, int x, int y) throws IOException
	{
		File file = new File(Launcher.class.getClassLoader().getResource(token).toString().substring(6));
		if (!file.exists()) 
		{
			System.exit(0);
		}
		BufferedImage img = ImageIO.read(file);
		ImageIcon icon = new ImageIcon(img.getScaledInstance(x, y, Image.SCALE_SMOOTH)); // Create the image from the filename
		return icon;
	}
	
	/*private static void addToPanel(JPanel p, String token, int x, int y) throws IOException
	{
		File file = new File(Launcher.class.getClassLoader().getResource(token).toString().substring(6));
		if (!file.exists()) 
		{
			System.exit(0);
		}
		BufferedImage img = ImageIO.read(file);
		ImageIcon icon = new ImageIcon(img.getScaledInstance(x, y, Image.SCALE_SMOOTH)); // Create the image from the filename
		JLabel label = new JLabel(icon); // Associate the image to a label
		p.add(label); // Add the label to a panel
	}*/
	
	private static void drawLongString(Graphics g, String text, int x, int y) 
	{
        for (String line : text.split("\\*"))
        {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }
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
		P1.resetLife();
		P2.resetLife();
		J1HandL.clear();
		J2HandL.clear();
		J1BoardL.clear();
		J2BoardL.clear();
		this.tour = this.tourBackup+2;
		this.selected = "";
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
	
	public void majHUD() throws IOException
	{
		try {
			updatePtsJ1();
			updatePtsJ2();
			updateHandJ1();
			updateHandJ2();
			updateBoardJ1();
			updateBoardJ2();
			updateScoreJ1();
			updateScoreJ2();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
