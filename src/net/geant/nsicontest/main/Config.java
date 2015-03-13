/**
 * 
 */
package net.geant.nsicontest.main;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Utility class for reading configuration from property file.
 * Property file's keys must be provided as enumerations.
 */
public class Config {
	
private Properties properties;
	
	private Config(Properties properties) { 
		
		this.properties = properties;
	}
	
	public int keysCount() { return properties.size(); }
	
	/**
	 * Returns value part of property based on key, empty string if nothing assigned
	 * @param key
	 * @return
	 */
	public String getString(Enum<?> key) {
		
		return properties.getProperty(key.name().toLowerCase()).trim();
	}
	
	/**
	 * Returns Boolean value or null if value not give or incorrect
	 * @param key
	 * @return
	 */
	public Boolean getBoolean(Enum<?> key) {
				
		String bool = getString(key);
		if (bool.isEmpty()) return null;
		try {
			return Boolean.valueOf(bool);
		} catch (Exception e) {	return null; }
	}
	
	/**
	 * Returns Integer value or null if value not give or incorrect
	 * @param key
	 * @return
	 */
	public Integer getInteger(Enum<?> key) {
		
		String number = getString(key);
		if (number.isEmpty()) return null;
		try {
			return Integer.valueOf(number);
		} catch (Exception e) { return null; }
	}
	
	/**
	 * Returns Long value or null if value not give or incorrect
	 * @param key
	 * @return
	 */
	public Long getLong(Enum<?> key) {
		
		String number = getString(key);
		if (number.isEmpty()) return null;
		try { 
			return Long.valueOf(number);
		} catch (Exception e) { return null; }
	}
	
	/**
	 * Returns Float value or null if value not give or incorrect
	 * @param key
	 * @return
	 */
	public Float getFloat(Enum<?> key) {
		
		String number = getString(key);
		if (number.isEmpty()) return null;
		try {
			return Float.valueOf(number);
		} catch (Exception e) { return null; }
	}

	/**
	 * Loads property file and checks if keys are present
	 * @param filename
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	public static Config load(String filename, Enum<?>[] keys) throws Exception {
		
		if (keys == null) throw new IllegalArgumentException("keys cannot be null");

		FileInputStream in = new FileInputStream(filename);
		Properties props = new Properties();
		props.load(in);
		in.close();
		
		for (Enum<?> key : keys) {
			if (!props.containsKey(key.name().toLowerCase()))
				throw new IllegalArgumentException(String.format("key %s not found in %s property file", key.name().toLowerCase(), filename));			
		}
		return new Config(props);
	}
}
