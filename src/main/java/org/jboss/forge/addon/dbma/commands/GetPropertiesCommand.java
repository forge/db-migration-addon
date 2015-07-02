/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.dbma.commands;

import org.jboss.forge.addon.database.tools.connections.ConnectionProfileManager;
import org.jboss.forge.addon.database.tools.connections.ConnectionProfileManagerProvider;
import org.jboss.forge.addon.database.tools.connections.ConnectionProfile;
import org.jboss.forge.addon.dbma.facet.DBMAFacet;
import org.jboss.forge.addon.dbma.properties.DBMAPropertiesBuilder;
import org.jboss.forge.addon.dbma.util.Constants;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.facets.ResourcesFacet;
import org.jboss.forge.addon.projects.ui.AbstractProjectCommand;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.command.UICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.hints.InputType;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.input.UISelectOne;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.furnace.util.Strings;
import org.metawidget.swing.Facet;

import java.lang.Override;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */

public class GetPropertiesCommand extends AbstractProjectCommand implements UICommand
{
   @Inject
   @WithAttributes(
            label = "Connection Profile",
            description = "Select the database connection profile you want to use")
   private UISelectOne<String> connectionProfile;

   @Inject
   @WithAttributes(
            label = "Connection Profile Password",
            type = InputType.SECRET,
            description = "Enter the database connection profile password")
   private UIInput<String> connectionProfilePassword;

   @Inject
   private ConnectionProfileManagerProvider managerProvider;
   
   @Inject
   private DBMAFacet dbmaFacet;
   
   @Inject
   private ResourceFactory resourceFactory;

   private ConnectionProfileManager manager = managerProvider.getConnectionProfileManager();
   
   private Map<String, ConnectionProfile> profiles;

  

   @Override
   public UICommandMetadata getMetadata(UIContext context)
   {
      return Metadata.forCommand(GetPropertiesCommand.class).name(
               "get-properties");
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
      profiles = manager.loadConnectionProfiles();
      ArrayList<String> profileNames = new ArrayList<String>();
      profileNames.add("");
      profileNames.addAll(profiles.keySet());
      connectionProfile.setValueChoices(profileNames);
      connectionProfile.setValue("");
      // Enable password input only if profile does not store saved passwords
      connectionProfilePassword.setEnabled(new Callable<Boolean>()
      {
         @Override
         public Boolean call() throws Exception
         {
            String connectionProfileName = connectionProfile.getValue();
            if (Strings.isNullOrEmpty(connectionProfileName))
               return false;
            return !profiles.get(connectionProfileName).isSavePassword();
         }
      });
      builder.add(connectionProfile).add(connectionProfilePassword);
   }

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      ConnectionProfile connection = manager.loadConnectionProfiles().get(connectionProfile.getValue());
      Project selectedProj = getSelectedProject(context);
      if (selectedProj.getFacet(DBMAFacet.class).isInstalled())
         selectedProj.getFacet(DBMAFacet.class).setPropertiesFile(connection);
      
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