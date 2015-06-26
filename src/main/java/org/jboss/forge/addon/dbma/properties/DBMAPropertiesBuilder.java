/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.dbma.properties;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 */
public class DBMAPropertiesBuilder implements DBMAProperties
{
   private String DbUsername;
   private String DbPassword;
   private String DbUrl;

   /**
    * @return {@link DBMAPropertiesBuilder}
    */
   public static DBMAPropertiesBuilder create()
   {
      return new DBMAPropertiesBuilder();
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.forge.addon.dbma.properties.DBMAProperties#setDbUsername(java.lang.String)
    */
   @Override
   public void setDbUsername(String username)
   {
      this.DbUsername = username;
   }
   
   /**
    * 
    * @param username
    * @return {@link DBMAPropertiesBuilder}
    */
   public DBMAPropertiesBuilder setNewDbUsername(String username)
   {
      this.DbUsername = username;
      return this;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.forge.addon.dbma.properties.DBMAProperties#getDbUsername()
    */
   @Override
   public String getDbUsername()
   {
      return this.DbUsername;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.forge.addon.dbma.properties.DBMAProperties#setDbPassword(java.lang.String)
    */
   @Override
   public void setDbPassword(String password)
   {
      this.DbPassword = password;
   }

   /**
    * 
    * @param password
    * @return {@link DBMAPropertiesBuilder}
    */
   public DBMAPropertiesBuilder setNewDbPassword(String password)
   {
      this.DbPassword = password;
      return this;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.forge.addon.dbma.properties.DBMAProperties#getDbPassword()
    */
   @Override
   public String getDbPassword()
   {
      return this.DbPassword;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.forge.addon.dbma.properties.DBMAProperties#getDbUrl()
    */
   @Override
   public String getDbUrl()
   {
      return this.DbUrl;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.forge.addon.dbma.properties.DBMAProperties#setDbUrl(java.lang.String)
    */
   @Override
   public void setDbUrl(String url)
   {
      this.DbUrl = url;
   }

   /**
    * 
    * @param url
    * @return {@link DBMAPropertiesBuilder}
    */
   public DBMAPropertiesBuilder setNewDbUrl(String url)
   {
      this.DbUrl = url;
      return this;
   }
}
