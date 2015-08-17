/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.resource.changelog;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.argparse4j.inf.Namespace;

import org.jboss.forge.addon.database.tools.migration.liquibase.GenerateChangeLogCommand;
import org.jboss.forge.addon.database.tools.migration.util.Constants;
import org.jboss.forge.addon.database.tools.migration.util.Utils;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.ResourcesFacet;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.resource.URLResource;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public class ChangeLogGenerator
{
   private ResourceFactory resourceFactory;
   private Project project;
   
   public ChangeLogGenerator(Project project){
      this.project = project;
   }

   public ChangeLogFileResource generateEmptyChangeLog(DirectoryResource migrationDir, boolean isMaster)
   {
      String changeLogName = isMaster ? Constants.MASTER_CHANGELOG : Utils.getChangeLogFileName(new Date());
      ChangeLogFileResource changeLogFileResource = migrationDir.getChild(changeLogName).reify(
               ChangeLogFileResource.class);

      changeLogFileResource.setContents(getEmptyChangeLogTemplate().getResourceInputStream());
      return changeLogFileResource;
   }

 
   public void generateEmptyMasterChangeLog()
   {
      ResourcesFacet projectResourcesFacet = project.getFacet(ResourcesFacet.class);
      DirectoryResource migrationDir = projectResourcesFacet.getResourceDirectory().getChildDirectory(
               Constants.MIGRATION_DIRECTORY_NAME);
      generateEmptyChangeLog(migrationDir, true);
   }

   private Resource<?> getEmptyChangeLogTemplate()
   {
      return resourceFactory.create(URLResource.class, getClass().getResource(Constants.DEFAULT_MIGRATIONS_FILE));
   }

   public void generateChangeLogFromDatabase()
   {
      ResourcesFacet projectResourcesFacet = project.getFacet(ResourcesFacet.class);
      DirectoryResource migrationDir = projectResourcesFacet.getResourceDirectory().getChildDirectory(
               Constants.MIGRATION_DIRECTORY_NAME);

      ChangeLogFileResource masterChangeLog = generateEmptyChangeLog(migrationDir, true);
      ChangeLogFileResource subChangeLog = generateEmptyChangeLog(migrationDir, false);

      Map<String, Object> attrs = new HashMap<String, Object>();
      attrs.put("subChangeLogFile", subChangeLog.getFullyQualifiedName());
      GenerateChangeLogCommand generateChangeLogCommand = new GenerateChangeLogCommand(project, new Namespace(attrs));
      try
      {
         generateChangeLogCommand.run();
         masterChangeLog.includeFile(subChangeLog.getName());
      }
      catch (Exception e)
      {
         // ignore exception
      }
   }

}
