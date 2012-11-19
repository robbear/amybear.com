/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {
	
	/* Private instance variables */
	ArrayList<NameSurferEntry> _entriesGraphed;
	
	/* Creates a new NameSurferGraph object that displays the data. */
	public NameSurferGraph() { 
		addComponentListener(this);
		_entriesGraphed = new ArrayList<NameSurferEntry>(); 
	}
	
	/* Method: clears the list of NameSurferEntries stored inside this class by clearing the ArrayList _entriesGraphed. */
	public void clear() {
		_entriesGraphed.clear();
	}
	
	/* Method: adds a new NameSurferEntry to the list of entries on the display. 
	 * This method does not actually draw the graph, but simply stores the entry; 
	 * the graph is drawn by calling update.*/
	public void addEntry(NameSurferEntry entry) {
		_entriesGraphed.add(entry);
	}

	/* Method: updates the display image by deleting all the graphical objects from the 
	 * canvas and then reassembling the display according to the list of entries. */
	public void update() {
		removeAll();
		drawGrid();
		if (_entriesGraphed.size() != 0) { 
			for (int i = 0; i < _entriesGraphed.size(); i++) {
				NameSurferEntry babyName = _entriesGraphed.get(i);
				graphData(babyName, i);
			}
		}
	}
	 
	/* Method: draws the vertical and horizontal lines to form the grid and adds labels to mark each decade. */
	private void drawGrid() {
		drawVerticalLines();
		drawHorizontalLines();
		addDecades();
	}
	
	private void drawVerticalLines() {
		double lineSep = getWidth() / NDECADES;
		for (int i = 0; i < NDECADES; i++) {	
			GLine line = new GLine((lineSep * i), 0, (lineSep * i), getHeight());
			add(line);
		}
	}
	
	private void drawHorizontalLines() {
		GLine upperLine = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		add(upperLine);
		GLine lowerLine = new GLine(0, (getHeight() - GRAPH_MARGIN_SIZE), getWidth(), (getHeight() - GRAPH_MARGIN_SIZE));
		add(lowerLine);
	}
	
	private void addDecades() {
		double lineSep = getWidth() / NDECADES;
		for (int i = 0; i < NDECADES; i++) {
			int decade = START_DECADE + (10 * i);
			String decadeString = Integer.toString(decade);
			
			double xPosition = (lineSep * i) + LABEL_PADDING;
			double yPosition = getHeight() - LABEL_PADDING;
			
			GLabel label = new GLabel(decadeString, xPosition, yPosition);
			add(label);
		}
	}
	
	private void graphData(NameSurferEntry babyName, int babyNameIndex) {
		addNameAndRank(babyName, babyNameIndex);
		drawConnectingLines(babyName, babyNameIndex); 
	}
	
	private void addNameAndRank(NameSurferEntry babyName, int babyNameIndex) {
		double lineSep = getWidth() / NDECADES;
		for (int i = 0; i < NDECADES; i++) {
			double xPosition = (lineSep * i) + LABEL_PADDING;
			double yPosition = ((babyName.getRank(i) * (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK) + GRAPH_MARGIN_SIZE);
			String rank = Integer.toString(babyName.getRank(i));
			
			if (babyName.getRank(i) == 0) {
				yPosition = getHeight() - GRAPH_MARGIN_SIZE;
				rank = "*";
			}
			
			GLabel nameAndRank = new GLabel(babyName.getName() + " " + rank, xPosition, yPosition);
			
			if (((babyNameIndex + 1) + 3) % 4 == 0) { 
				nameAndRank.setColor(Color.BLACK);
			} else if (((babyNameIndex + 1) + 2) % 4 == 0) {
				nameAndRank.setColor(Color.RED);
			} else if (((babyNameIndex + 1) + 1) % 4 == 0) {
				nameAndRank.setColor(Color.BLUE);
			} else if ((babyNameIndex + 1) % 4 == 0) {
				nameAndRank.setColor(Color.MAGENTA);
			}
	
			add(nameAndRank);
		}
	}
	
	private void drawConnectingLines(NameSurferEntry babyName, int babyNameIndex) {
		double lineSep = getWidth() / NDECADES;
		double xStart;
		double yStart;
		double xFinish;
		double yFinish;
		
		for (int i = 0; i < NDECADES - 1; i++) {
			xStart = lineSep * i;
			xFinish = lineSep * (i + 1);
			
			/* Calculates the beginning and ending y-coordinates of each line */
			if (babyName.getRank(i) == 0) { //If the rank of the current index of babyName is 0
				yStart = getHeight() - GRAPH_MARGIN_SIZE;
				if (babyName.getRank (i + 1) == 0) { //If the rank of the next index of babyName is 0
					yFinish = getHeight() - GRAPH_MARGIN_SIZE;
				} else { //If the rank of the next index of babyName is not 0
					yFinish = ((babyName.getRank(i + 1) * (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK) + GRAPH_MARGIN_SIZE);
				}
			} else if (babyName.getRank(i + 1) == 0) { //If the rank of the next index of babyName is 0
				yStart = ((babyName.getRank(i) * (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK) + GRAPH_MARGIN_SIZE);
				yFinish = getHeight() - GRAPH_MARGIN_SIZE;
			} else { //If the rank of neither the current nor the next index of babyName is 0
				yStart = ((babyName.getRank(i) * (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK) + GRAPH_MARGIN_SIZE);
				yFinish = ((babyName.getRank(i + 1) * (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK) + GRAPH_MARGIN_SIZE);
			}
			
			GLine line = new GLine(xStart, yStart, xFinish, yFinish);
			
			if (((babyNameIndex + 1) + 3) % 4 == 0) { 
				line.setColor(Color.BLACK);
			} else if (((babyNameIndex + 1) + 2) % 4 == 0) {
				line.setColor(Color.RED);
			} else if (((babyNameIndex + 1) + 1) % 4 == 0) {
				line.setColor(Color.BLUE);
			} else if ((babyNameIndex + 1) % 4 == 0) {
				line.setColor(Color.MAGENTA);
			}
			
			add(line);
		}
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
	/* Constants */
	private static final int LABEL_PADDING = 5;
}
