/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.dbma.properties;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public interface DBMAProperties
{
   /**
    * sets the db username used by DBMA
    * @param username
    */
   void setDbUsername(String username);
   
   /**
    * @return the db username used by DBMA
    */
   String getDbUsername();
   
   /**
    * sets the db password used by DBMA
    * @param password
    */
   void setDbPassword(String password);
   
   /**
    * @return the db password used by DBMA
    */
   String getDbPassword();
   
   /**
    * @return the db url used by DBMA
    */
   Integer getDbUrl();
   
   /**
    * sets the db url used by DBMA
    * 
    * @param url
    */
   void setDbUrl(String url);
}
