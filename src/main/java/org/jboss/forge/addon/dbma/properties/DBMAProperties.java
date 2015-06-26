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
    * sets the DB username used by DBMA
    * @param username
    */
   void setDbUsername(String username);
   
   /**
    * @return the DB username used by DBMA
    */
   String getDbUsername();
   
   /**
    * sets the DB password used by DBMA
    * @param password
    */
   void setDbPassword(String password);
   
   /**
    * @return the DB password used by DBMA
    */
   String getDbPassword();
   
   /**
    * @return the DB url used by DBMA
    */
   String getDbUrl();
   
   /**
    * sets the DB url used by DBMA
    * @param url
    */
   void setDbUrl(String url);
}
