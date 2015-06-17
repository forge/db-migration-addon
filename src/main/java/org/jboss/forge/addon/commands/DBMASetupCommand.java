package org.jboss.forge.addon.commands;

import org.jboss.forge.addon.io.DBMAFileManager;
import org.jboss.forge.addon.parser.xml.resources.XMLResource;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.facets.MetadataFacet;
import org.jboss.forge.addon.projects.ui.AbstractProjectCommand;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.util.Util;
import org.jboss.forge.furnace.Furnace;
import org.jboss.forge.parser.xml.Node;

import java.io.File;
import java.lang.Exception;

public class DBMASetupCommand extends AbstractProjectCommand
{

   private ResourceFactory factory;

   @Override
   public UICommandMetadata getMetadata(UIContext context)
   {
      return Metadata.forCommand(DBMASetupCommand.class).name("dbma: Setup")
               .category(Categories.create("Database/Migration"));
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
   }

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      String projectName = getSelectedProject(context).getFacet(MetadataFacet.class).getProjectName();

      // If migration directory creation is successful then create an empty changeLog file
      if (DBMAFileManager.createDirectory(Util.getMigrationDirectoryName(projectName)))
      {
         DBMAFileManager.createEmptyChangeLogFile(Util.getChangeLogFileName(projectName));

         XMLResource resource = factory.create(new File(Util.getChangeLogFileName(projectName))).reify(
                  XMLResource.class);
         Node node = resource.getXmlSource();
         Node changeLogChild = node.getOrCreate("databaseChangeLog");
         changeLogChild.attribute("xmlns", "http://www.liquibase.org/xml/ns/dbchangelog");
         changeLogChild.attribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
         changeLogChild.attribute("xsi:schemaLocation", "http://www.liquibase.org/xml/ns/dbchangelog "
                  + "http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd");
         resource.setContents(node);
      }
      return Results.success("Command 'dbma: Setup' successfully executed!");
   }

   @Override
   protected ProjectFactory getProjectFactory()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   protected boolean isProjectRequired()
   {
      // TODO Auto-generated method stub
      return true;
   }
}