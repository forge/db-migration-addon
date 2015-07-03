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
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.ui.AbstractProjectCommand;
import org.jboss.forge.addon.ui.command.UICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.context.UIValidationContext;
import org.jboss.forge.addon.ui.hints.InputType;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.input.UISelectOne;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.furnace.util.Strings;

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

public class SetPropertiesCommand extends AbstractProjectCommand implements UICommand
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
            description = "Enter the database password")
   private UIInput<String> connectionProfilePassword;

   @Inject
   private ConnectionProfileManagerProvider managerProvider;

   private Map<String, ConnectionProfile> profiles;

   @Override
   public UICommandMetadata getMetadata(UIContext context)
   {
      return Metadata.forCommand(SetPropertiesCommand.class).name(
               "get-properties");
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
      ConnectionProfileManager manager = managerProvider.getConnectionProfileManager();
      profiles = manager.loadConnectionProfiles();
      ArrayList<String> profileNames = new ArrayList<String>();
      profileNames.add("");
      profileNames.addAll(profiles.keySet());
      connectionProfile.setValueChoices(profileNames);
      connectionProfile.setValue("");

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
   public void validate(UIValidationContext validator)
   {
      super.validate(validator);
      if (connectionProfile.getValue().equals(""))
            validator.addValidationError(connectionProfile,
                  "Please select a database connection. If there is no connection, "
                  + "please use Forge to create a new connection profile");
   }

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      ConnectionProfileManager manager = managerProvider.getConnectionProfileManager();
      if (!connectionProfile.getValue().equals(""))
      {
         ConnectionProfile connection = manager.loadConnectionProfiles().get(connectionProfile.getValue());
         if (!connection.isSavePassword())
         {
            String password = connectionProfilePassword.getValue();
            if (password == null)
               password = "";
            connection.setPassword(password);
         }
         Project selectedProj = getSelectedProject(context);
         if (selectedProj.getFacet(DBMAFacet.class).isInstalled())
            selectedProj.getFacet(DBMAFacet.class).setPropertiesFile(connection);
      }
      return Results
               .success("Properties stored!");
   }

   @Override
   public boolean isEnabled(UIContext context)
   {
      return getSelectedProject(context).hasFacet(DBMAFacet.class)
               && getSelectedProject(context).getFacet(DBMAFacet.class).isInstalled();
   }

   @Override
   protected boolean isProjectRequired()
   {
      return true;
   }

   @Inject
   private ProjectFactory projectFactory;

   @Override
   protected ProjectFactory getProjectFactory()
   {
      return projectFactory;
   }
}