package reflection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import exceptions.NoPackageException;
import model.Model;

public class ReflectionPackage {
	
	
    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException, NoPackageException
     * @throws IOException
     */
	/** 
	 * Given a package name, attempts to reflect to find all classes within the package 
	 * on the local file system. 
	 *  
	 * @param packageName 
	 * @return 
	 */  
	public static ArrayList<Class<?>> getClassesInPackage(String packageName) throws NoPackageException, ClassNotFoundException {  
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();  
	    String packageNameSlashed = "/" + packageName.replace(".", "/");
	    // Get a File object for the package  
	    URL directoryURL = Thread.currentThread().getContextClassLoader().getResource(packageNameSlashed);  
	    if (directoryURL == null) {  
	        throw new NoPackageException("38");  //"Could not retrieve URL resource: " + packageNameSlashed
	    }  
	  
	    String directoryString = directoryURL.getFile();  
	    if (directoryString == null) {  
	    	throw new NoPackageException("39");  //"Could not find directory for URL resource: " + packageNameSlashed
	    }  
	  
	    File directory = new File(directoryString);  
	    if (directory.exists()) {  
	        // Get the list of the files contained in the package  
	        String[] files = directory.list();  
	        for (String fileName : files) {  
	            // We are only interested in .class files  
	            if (fileName.endsWith(".class")) {  
	                // Remove the .class extension  
	                fileName = fileName.substring(0, fileName.length() - 6);  
	                try {  
	                    classes.add(Class.forName(packageName+"."+fileName));  
	                } catch (ClassNotFoundException e) {  
	                	throw new ClassNotFoundException("34", e);  //packageName + "." + fileName + " does not appear to be a valid class."
	                }  
	            }  
	        }  
	    } else {  
	    	throw new NoPackageException("40");  //packageName + " does not appear to exist as a valid package on the file system."
	    }  
	    return classes;  
	}  
	
	
	
}
