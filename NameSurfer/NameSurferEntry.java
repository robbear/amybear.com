/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	/* Private instance variables */
	private String _name;
	int[] _namesData = new int[NDECADES];
	
	/* Constructor: creates a new NameSurferEntry from a data line as it appears in the
	 * data file. Each line begins with the name, which is followed by integers giving
	 * the rank of that name for each decade. */
	public NameSurferEntry(String line) {
		int nameEnd = line.indexOf(" ");
		_name = line.substring(0, nameEnd); //Stores the name in the String _name
		String data = line.substring(nameEnd + 1); //Stores the rankings in a separate String data
		
		/* Breaks up data into separate tokens, converts each token to an int, and stores 
		 * each int in the array _namesData */
		StringTokenizer st = new StringTokenizer(data); 
		for (int i = 0; st.hasMoreTokens(); i++) {
			int entryInt = Integer.parseInt(st.nextToken());
			_namesData[i] = entryInt;
		}
	}

	/* Returns the name associated with this entry. */
	public String getName() {
		return _name;
	}

	/* Method: returns the rank associated with an entry for a particular decade. The decade
	 * value is an integer indicating how many decades have passed since the first year
	 * in the database, which is given by the constant START_DECADE. If a name does not 
	 * appear in a decade, the rank value is 0. */
	public int getRank(int decade) {
		return _namesData[decade];
	}

	/* Returns a string that makes it easy to see the value of a NameSurferEntry. */
	public String toString() {
		String dataString = _name + " [";
		for (int i = 0; i < NDECADES; i++) {
			dataString += _namesData[i];
			if (i < NDECADES - 1) {
				dataString += " ";
			}
		}
		dataString += "]";
		return dataString;
	}
}

