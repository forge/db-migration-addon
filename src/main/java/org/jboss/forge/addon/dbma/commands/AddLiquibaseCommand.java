/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.dbma.commands;

import org.jboss.forge.addon.dbma.facet.DBMAFacet;
import org.jboss.forge.addon.facets.FacetFactory;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.ui.AbstractProjectCommand;
import org.jboss.forge.addon.ui.command.UICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;

import java.lang.Exception;

import javax.inject.Inject;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */

public class AddLiquibaseCommand extends AbstractProjectCommand implements UICommand
{

   @Inject
   private FacetFactory facetFactory;

   @Inject
   private DBMAFacet dbmaFacet;

   @Override
   public UICommandMetadata getMetadata(UIContext context)
   {
      return Metadata.forCommand(AddLiquibaseCommand.class)
               .name("DBMA: Add")
               .description("This will add the Liquibase dependency and create the migration directory"
                        + "where we will store all DBMA Files");
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
   }

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      facetFactory.install(getSelectedProject(context), dbmaFacet);
      return Results.success("Created migration directory and added liquibase dependency");
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