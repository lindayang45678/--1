package com.lakala.util;

import java.io.InputStreamReader;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertyLoader {
	private static final boolean THROW_ON_LOAD_FAILURE = true;
    private static final boolean LOAD_AS_RESOURCE_BUNDLE = false;
    private static final String SUFFIX = ".properties";
    public final static String FILE_SEPARATOR="/";
    
    public static Properties loadProperties (String name, ClassLoader loader)
    {
        if (name == null)
            throw new IllegalArgumentException ("null input: name");
        
        if (name.startsWith (FILE_SEPARATOR))
            name = name.substring (1);
            
        if (name.endsWith (SUFFIX))
            name = name.substring (0, name.length () - SUFFIX.length ());
        
        Properties result = null;
        
        InputStreamReader in = null;
        try
        {
            if (loader == null) loader = ClassLoader.getSystemClassLoader ();
            
            if (LOAD_AS_RESOURCE_BUNDLE)
            {   
                name = name.replace ('/', '.');
                // Throws MissingResourceException on lookup failures:
                final ResourceBundle rb = ResourceBundle.getBundle (name );
                
                result = new Properties ();
                for (Enumeration keys = rb.getKeys (); keys.hasMoreElements ();)
                {
                    final String key = (String) keys.nextElement ();
                    final String value = rb.getString (key);
                    
                    result.put (key, value);
                } 
            }
            else
            {
                name = name.replace ('.', '/');
                
                if (! name.endsWith (SUFFIX))
                    name = name.concat (SUFFIX);
                                
                // Returns null on lookup failures:
                //in = loader.getResourceAsStream (name);
                in = new InputStreamReader(loader.getResourceAsStream (name), "UTF-8");
                if (in != null)
                {
                    result = new Properties ();
                    result.load (in); // Can throw IOException
                }
            }
        }
        catch (Exception e)
        {
            result = null;
        }
        finally
        {
            if (in != null) try { in.close (); } catch (Throwable ignore) {}
        }
        
        if (THROW_ON_LOAD_FAILURE && (result == null))
        {
            throw new IllegalArgumentException ("could not load [" + name + "]"+
                " as " + (LOAD_AS_RESOURCE_BUNDLE
                ? "a resource bundle"
                : "a classloader resource"));
        }
        
        return result;
    }
    
 
    public static Properties loadProperties (final String name)
    {
        return loadProperties (name,
            Thread.currentThread ().getContextClassLoader ());
    }
}
