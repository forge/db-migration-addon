/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.dbma.commands;

import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.ui.AbstractProjectCommand;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.context.UINavigationContext;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.wizard.UIWizard;
import org.jboss.forge.addon.ui.result.NavigationResult;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;

import java.lang.Exception;

import javax.inject.Inject;


/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */

public class DBMASetupCommand extends AbstractProjectCommand implements UIWizard
{

   @Inject
   private ResourceFactory resourceFactory;
   


   @Override
   public UICommandMetadata getMetadata(UIContext context)
   {
      return Metadata.forCommand(DBMASetupCommand.class).name("DBMA: Setup")
               .description("This wizard will guide you through the initial DBMA setup")
               .category(Categories.create("Database/Migration"));
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
   }

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      return null;

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

   /* (non-Javadoc)
    * @see org.jboss.forge.addon.ui.wizard.UIWizard#next(org.jboss.forge.addon.ui.context.UINavigationContext)
    */
   @Override
   @SuppressWarnings("unchecked")
   public NavigationResult next(UINavigationContext arg0) throws Exception
   {
      return Results.navigateTo(
               AddLiquibaseCommand.class,
               GetPropertiesCommand.class,
               CreateInitialChangelogFileCommand.class);
   }
}