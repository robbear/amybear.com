/*
 * File: NameSurfer.java
 * ---------------------
 * This program implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	/* Private instance variables */
	private JLabel _nameLabel;
	private JTextField _nameEntry;
	private JButton _graphButton;
	private JButton _clearButton;
	private NameSurferGraph _graph;
	private NameSurferDataBase _database;


	/* Method: reads information from the database and initializes the interactors at 
	 * the top of the window. */
	public void init() {
	   _nameLabel = new JLabel("Name");
	   _nameEntry = new JTextField(20);
	   _graphButton = new JButton("Graph");
	   _clearButton = new JButton("Clear");
	   
	   add(_nameLabel, NORTH);
	   add(_nameEntry, NORTH);
	   add(_graphButton, NORTH);
	   add(_clearButton, NORTH);

	   _nameEntry.addActionListener(this);
	   _graphButton.addActionListener(this);
	   _clearButton.addActionListener(this);
	   
	   _graph = new NameSurferGraph();
	   add(_graph);
	   
	   _database = new NameSurferDataBase(NAMES_DATA_FILE);
	}
	
	/* Method: actionPerformed(e) */
	public void actionPerformed(ActionEvent e) {
		
		/* If the user presses the "Graph" button, or hits return from the _nameEntry field,
		 * this method takes the text from _nameEntry and searches within the database to 
		 * see if that name exists. If the name exists in _database, updates the graph 
		 * with the name rankings for each decade.*/
		if ((e.getSource() == _graphButton) || (e.getSource() == _nameEntry)) {
			String name = _nameEntry.getText();
			NameSurferEntry entry = _database.findEntry(name); 
			if (entry != null) { 
				_graph.addEntry(entry);
				_graph.update();
			}
		}
	
		/* If the user presses the "Clear" button, it clears the graph of all name data
		 * and updates the graph with a new, empty grid. */
		if (e.getSource() == _clearButton) {
			_graph.clear();
			_graph.update();
		}
		
		
	
	}
	
}
