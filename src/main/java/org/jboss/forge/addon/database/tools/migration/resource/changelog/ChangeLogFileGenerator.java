/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.resource.changelog;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.argparse4j.inf.Namespace;

import org.jboss.forge.addon.database.tools.migration.liquibase.GenerateChangeLogCommand;
import org.jboss.forge.addon.database.tools.migration.util.Constants;
import org.jboss.forge.addon.database.tools.migration.util.Utils;
import org.jboss.forge.addon.maven.resources.MavenModelResource;
import org.jboss.forge.addon.parser.xml.resources.XMLResource;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.ResourcesFacet;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.resource.URLResource;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public class ChangeLogFileGenerator
{
   private final Project project;
   private final DirectoryResource migrationDirectory;
   private final ResourceFactory resourceFactory;

   public ChangeLogFileGenerator(Project project)
   {
      this.project = project;
      ResourcesFacet projectResourcesFacet = project.getFacet(ResourcesFacet.class);
      this.migrationDirectory = projectResourcesFacet.getResourceDirectory().getChildDirectory(
               Constants.DEFAULT_MIGRATION_DIRECTORY);
      this.resourceFactory = this.migrationDirectory.getResourceFactory();
   }

   public MavenModelResource getModelResource(String name)
   {
      return migrationDirectory.getChild(name).reify(MavenModelResource.class);
   }

   public ChangeLogResource generateEmptyChangeLog(boolean isMaster)
   {
      String changeLogFileName = isMaster ? Constants.MASTER_CHANGELOG : Utils.getChangeLogFileName(new Date());
      ChangeLogResource changeLogResource = migrationDirectory.getChild(changeLogFileName).reify(
               ChangeLogResource.class);
      changeLogResource.createNewFile();
      changeLogResource.setContents(getEmptyChangeLog().getContents());

      return changeLogResource;
   }

   public void generateEmptyMasterChangeLog()
   {
      generateEmptyChangeLog(true);
   }

   private Resource<?> getEmptyChangeLog()
   {
      return resourceFactory.create(URLResource.class, getClass().getResource(Constants.EMPTY_CHANGELOG_TEMPLATE));
   }

   public void generateChangeLogFromDatabase()
   {
      ChangeLogResource masterChangeLog = generateEmptyChangeLog(true);
      ChangeLogResource subChangeLog = generateEmptyChangeLog(false);

      Map<String, Object> attrs = new HashMap<String, Object>();
      attrs.put("subChangeLogFile", subChangeLog.getFullyQualifiedName());
      GenerateChangeLogCommand generateChangeLogCommand = new GenerateChangeLogCommand(this.project, new Namespace(
               attrs));
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
