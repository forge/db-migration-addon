/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.dbma.properties;

import org.jboss.forge.addon.database.tools.connections.ConnectionProfile;
import org.jboss.forge.addon.resource.FileResource;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 */
public class DBMAPropertiesBuilder implements DBMAProperties
{
   
   private ConnectionProfile connection;
   private String liquibaseVersion;
   
   public DBMAPropertiesBuilder create(){
      return new DBMAPropertiesBuilder();
   }
   
   public DBMAPropertiesBuilder setLiquibaseVersion(String version){
      this.liquibaseVersion = version;
      return this;
   }
   
   public DBMAPropertiesBuilder setConnection(ConnectionProfile connection){
      this.connection = connection;
      return this;
   }

   /* (non-Javadoc)
    * @see org.jboss.forge.addon.dbma.properties.DBMAProperties#getConnectionProfile()
    */
   @Override
   public ConnectionProfile getConnectionProfile()
   {
      return this.connection;
   }

   /* (non-Javadoc)
    * @see org.jboss.forge.addon.dbma.properties.DBMAProperties#getLiquibaseVersion()
    */
   @Override
   public String getLiquibaseVersion()
   {
      return this.liquibaseVersion;
   }

   
   /* (non-Javadoc)
    * @see org.jboss.forge.addon.dbma.properties.DBMAProperties#toPropertiesFile()
    */
   @Override
   public FileResource<?> toPropertiesFile(FileResource<?> propertiesFile )
   {
      propertiesFile.setContents("driver: "+this.connection.getDriver()
               +"classpath: "+this.getConnectionProfile().getPath()
               +"url: "+this.getConnectionProfile().getUrl()
               +"username: "+this.getConnectionProfile().getUser()
               +"password: "+this.getConnectionProfile().getPassword()
               +"liquibaseVersion "+this.getLiquibaseVersion());
      return propertiesFile;
   }
   
   
   
}
