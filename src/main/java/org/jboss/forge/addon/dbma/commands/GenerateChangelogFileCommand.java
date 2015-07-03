package org.jboss.forge.addon.dbma.commands;

import org.jboss.forge.addon.dbma.facet.DBMAFacet;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.ui.AbstractProjectCommand;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.command.UICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.context.UIValidationContext;
import org.jboss.forge.addon.ui.hints.InputType;
import org.jboss.forge.addon.ui.input.UISelectOne;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;

import java.lang.Override;
import java.lang.Exception;
import java.util.concurrent.Callable;

import javax.inject.Inject;

public class GenerateChangelogFileCommand extends AbstractProjectCommand implements UICommand
{
   @Inject
   @WithAttributes(shortName = 'm', label = "Generation mode", type = InputType.DROPDOWN)
   private UISelectOne<String> generationMode;
   
   @Inject
   DBMAFacet dbmaFacet;

   @Override
   public UICommandMetadata getMetadata(UIContext context)
   {
      return Metadata.forCommand(GenerateChangelogFileCommand.class)
            .name("generate-initial-changelog-file");
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
      builder.add(generationMode);
      generationMode.setDefaultValue("");
      generationMode.setValueChoices(
      new Callable<Iterable<String>>() {
         @Override
         public Iterable<String> call() throws Exception {
            return dbmaFacet.getGenerationModes();
         }
      });
      
   }
   
   @Override
   public void validate(UIValidationContext validator)
   {
      super.validate(validator);
      if (generationMode.getValue().equals(""))
            validator.addValidationError(generationMode,
                  "Please select how you want to generate the changelog file");
   }

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      return Results
            .success("Initial changelog file has been created!");
   }
   
   @Override
   public boolean isEnabled(UIContext context) {
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