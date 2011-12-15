package fr.shortcircuit.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import fr.shortcircuit.db.DbManager;


public class DesktopWindowView extends JFrame 
{
	public JInternalFrame 		jifEx;
	
	public DbManager 				myDbManager;
	
	//Desktop permettant de contenir des fenetres internes.
	public JDesktopPane				desk;	

	//Elements de la barre d'outils: containeur principal, boutons et ressources graphiques
	public JToolBar 				toolBar;

	//Elements de la barre de menus
	public JMenuBar 				menuBar;
	public JMenu					menuAbout;
	public JMenuItem				menuAboutContent;

	public JTable					tableResults;
	
	private JButton					boutonUpdate;
	private JButton 				boutonQuitter;

	
	
	public DesktopWindowView(DbManager myDbManager)
	{
		super("Projet Java J-CSI");
		setMyDbManager(myDbManager);
		buildDesktopPane();
		buildToolbarAndMenu();
		buildTable();
	}
	
 
	      
	public void buildDesktopPane()
	{	 
		
		this.boutonUpdate = new JButton("Update");
	      // Le constructeur de JButton prend en argument le nom du bouton
	    this.boutonQuitter = new JButton("Quitter");
		MouseListener mouseListenerQuit = new MouseAdapter() {
		      public void mousePressed(MouseEvent mouseEvent) {
		        int modifiers = mouseEvent.getModifiers();
		        if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
		        	System.out.println("Bye !");
		    		System.exit(0);
		        }
		      }
	 	};
		MouseListener mouseListenerUpdate = new MouseAdapter() {
		      public void mousePressed(MouseEvent mouseEvent) {
		        int modifiers = mouseEvent.getModifiers();
		        if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
		        	jifEx.getContentPane().doLayout();
		        	jifEx.updateUI();
		        }
		      }
	 	};
	 	boutonQuitter.addMouseListener(mouseListenerQuit);
	 	boutonUpdate.addMouseListener(mouseListenerUpdate);
	   
		this.desk				= new JDesktopPane();
		
		this.getContentPane().add(desk, BorderLayout.CENTER);
		this.getContentPane().add(boutonUpdate, BorderLayout.PAGE_START);
		this.getContentPane().add(boutonQuitter, BorderLayout.PAGE_END);
		
		desk.putClientProperty("JDesktopPane.dragMode", "faster");

		this.addWindowListener(new DesktopWindowControler(this));
	}

	public void buildToolbarAndMenu()
	{
		this.menuBar			= new JMenuBar();
		this.toolBar			= new JToolBar(SwingConstants.HORIZONTAL);

		toolBar.putClientProperty("JToolbar.isRollover", Boolean.TRUE);

		//menu
		menuAbout				= new JMenu("About");	
		menuAboutContent		= new JMenuItem("About JCSI");
		
		menuAbout.add(menuAboutContent);
		menuBar.add(menuAbout);
		
		this.setJMenuBar(menuBar);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);	
		
		//Exemple d'utilisation d'une "inner" classe: redéfinition à la volée des méthodes de l'instance de l'objet. 
		menuAboutContent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(getParent(), "Projet java Epitech J-CSI"); 
			}
		});
	}				
	
 	public void buildTable()
 	{
 		DefaultTableModel 	tableModelResults 	= new DefaultTableModel(myDbManager.arrayContent, myDbManager.arrayHeader); 		
 		JTable 				tableResults 		= new JTable(tableModelResults);
 		JScrollPane			scpTable 			= new JScrollPane(tableResults);
 		jifEx				= new JInternalFrame("Table Content", true, true, true, true);
 		
 		jifEx.getContentPane().add(scpTable, BorderLayout.PAGE_START); 
 		desk.add(jifEx, 1);
 		
 		//Layout & size managment
 		scpTable.setPreferredSize(new Dimension(370, 200)); 		
 		tableResults.setVisible(true);
 		jifEx.setVisible(true);
 		jifEx.setBounds(0, 0, 300, 200); //x, y, width, height								
 		jifEx.pack();
 		
 		//Ex de méthode d'update graphique du composant de plus haut niveau
 		//jifEx.getContentPane().doLayout();		
 		//jifEx.updateUI();			
 	}

	public DbManager getMyDbManager() 					{return myDbManager;}

	public void setMyDbManager(DbManager myDbManager) 	{this.myDbManager = myDbManager;}
	


}