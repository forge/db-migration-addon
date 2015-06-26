/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.dbma.commands;

import org.jboss.forge.addon.dbma.facet.DBMAFacet;
import org.jboss.forge.addon.dbma.properties.DBMAPropertiesBuilder;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.ui.AbstractProjectCommand;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.command.UICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;

import java.lang.Override;
import java.lang.Exception;

import javax.inject.Inject;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */

public class GetPropertiesCommand extends AbstractProjectCommand implements UICommand
{
   @Inject
   @WithAttributes(label = "Database URL", description = "Database URL")
   private UIInput<String> DbUrl;

   @Inject
   @WithAttributes(label = "Username", description = "Database Username")
   private UIInput<String> DbUsername;

   @Inject
   @WithAttributes(label = "Password", description = "Database Password")
   private UIInput<String> DbPassword;

   @Override
   public UICommandMetadata getMetadata(UIContext context)
   {
      return Metadata.forCommand(GetPropertiesCommand.class).name(
               "get-properties");
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
      builder.add(DbUrl).add(DbUsername).add(DbPassword);
   }

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      DBMAPropertiesBuilder pBuilder = DBMAPropertiesBuilder.create();
      pBuilder.setNewDbUrl(DbUrl.getValue())
      .setNewDbUsername(DbUsername.getValue())
      .setNewDbPassword(DbPassword.getValue());
      
      DBMAFacet dbmaFacet = getSelectedProject(context).getFacet(DBMAFacet.class);
      dbmaFacet.addDBMAProperties(pBuilder);
      return Results
               .success("Command 'get-properties' successfully executed!");
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.forge.addon.projects.ui.AbstractProjectCommand#isProjectRequired()
    */
   @Override
   protected boolean isProjectRequired()
   {
      return true;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.forge.addon.projects.ui.AbstractProjectCommand#getProjectFactory()
    */
   @Override
   protected ProjectFactory getProjectFactory()
   {
      // TODO Auto-generated method stub
      return null;
   }
}