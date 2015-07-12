/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.facet;

import java.util.List;
import java.util.Properties;

import org.jboss.forge.addon.database.tools.connections.ConnectionProfile;
import org.jboss.forge.addon.projects.ProjectFacet;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */


public interface DatabaseMigrationFacet extends ProjectFacet
{
   /**
    * set the properties file for Liquibase
    * @param connection
    */
   public void setPropertiesFile(ConnectionProfile connection);
   
   /**
    * 
    * @return properties used by Liquibase to perform migration 
    */
   public Properties getDBMAProperties();

   /**
    * @return a list of Liquibase available versions
    */
   public List<String> getLiquibaseVersions();

   /**
    * @return a list of possible generation modes for the changelog file
    */
   public List<String> getGenerationModes();

}
