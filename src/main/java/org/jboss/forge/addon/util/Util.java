package org.jboss.forge.addon.util;

public class Util {
	public static String getMigrationDirectoryName(String projectName){
		return projectName+"-migration";
	}
	
	public static String getChangeLogFileName(String projectName){
		return getMigrationDirectoryName(projectName)+"/dbChangeLog.xml";
	}

}
