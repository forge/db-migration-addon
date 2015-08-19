/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.properties;

import java.io.IOException;
import java.util.Properties;

import org.jboss.forge.addon.database.tools.migration.util.Constants;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.ResourcesFacet;
import org.jboss.forge.addon.resource.DirectoryResource;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public class ConnectionPropertiesManagerImpl implements ConnectionPropertiesManager
{

   /*
    * (non-Javadoc)
    * 
    * @see
    * org.jboss.forge.addon.database.tools.migration.properties.ConnectionPropertiesManager#loadConnectionProperties
    * (org.jboss.forge.addon.projects.Project)
    */
   @Override
   public Properties loadConnectionProperties(Project project)
   {
      Properties dbmaProperties = new Properties();

      ResourcesFacet resourcesFacet = project.getFacet(ResourcesFacet.class);
      DirectoryResource migrationDir = resourcesFacet.getResourceDirectory().getChildDirectory(
               Constants.DEFAULT_MIGRATION_DIRECTORY);
      try
      {
         dbmaProperties.load(migrationDir.getChild(Constants.PROPERTIES_FILE).getResourceInputStream());
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return dbmaProperties;
   }

}
