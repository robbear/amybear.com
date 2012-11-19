import java.io.*;
import java.util.*;
import acm.util.*;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {
	
	/* Private instance variables */
	private Map<String, NameSurferEntry> _babyNames = new HashMap<String, NameSurferEntry>();

	/* Constructor: creates a new NameSurferDataBase and initializes it using the data
	 * in the specified file. The constructor throws an error exception if the requested
	 * file does not exist or if an error occurs as the file is being read. */
	public NameSurferDataBase(String filename) {
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = rd.readLine();
				if (line == null) break;
				NameSurferEntry entry = new NameSurferEntry(line);
				_babyNames.put(entry.getName(), entry); //Puts the entry in the HashMap _babyNames
			}
			rd.close();
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}
	
	/* Method: converts the String name so that the first letter is capitalized 
	 * and the rest are lowercase so the name can be looked up in the HashMap. 
	 * Returns the NameSurferEntry associated with this name, if one exists. 
	 * If the name does not appear in the database, this method returns null. */
	public NameSurferEntry findEntry(String name) {
		String firstLetter = name.substring(0, 1);
		firstLetter = firstLetter.toUpperCase();
		
		String endOfWord = name.substring(1);
		endOfWord = endOfWord.toLowerCase();
		
		String newName = firstLetter + endOfWord;
		if (_babyNames.containsKey(newName)) {
			return _babyNames.get(newName);
		} else {
			return null;
		}
		
	}
}

