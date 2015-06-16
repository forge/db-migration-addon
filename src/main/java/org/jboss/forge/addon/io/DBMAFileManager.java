package org.jboss.forge.addon.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jboss.forge.addon.util.Util;

public class DBMAFileManager {

	public static boolean createDirectory(String directoryName){
	boolean result = false;
	try{
		File migrationDir = new File(directoryName);
		result = migrationDir.mkdir();
	} catch(SecurityException se){
		throw(se);    
	}     
	return result;
	}
	
	public static void addXMLEncoding(String fileName) throws IOException{
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.close();
		} catch (IOException e){
			throw e;
		}
		
	}
	
	public static boolean createEmptyChangeLogFile(String fileName) throws IOException{
		boolean result = false;
		try{
			File changeLogFile = new File(fileName);
			result = changeLogFile.createNewFile();
			addXMLEncoding(fileName);
		} catch(SecurityException se){
			throw(se);    
		} catch(IOException e){
			throw(e);
		}
		return result;
   }

}