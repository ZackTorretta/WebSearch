
package com.torretta.lucene;
import java.io.File; 
import java.io.FileFilter;
public class filter implements FileFilter{
    @Override 
	public boolean accept(File pathname) 
	{ 
		return pathname.getName().toLowerCase().endsWith(".txt"); //searches for txt files only
	}
}
