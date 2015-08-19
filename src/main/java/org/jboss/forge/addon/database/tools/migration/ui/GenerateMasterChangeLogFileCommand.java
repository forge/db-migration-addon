package org.jboss.forge.addon.database.tools.migration.ui;

import org.jboss.forge.addon.database.tools.migration.facet.DatabaseMigrationFacet;
import org.jboss.forge.addon.database.tools.migration.resource.changelog.ChangeLogFileGenerator;
import org.jboss.forge.addon.database.tools.migration.util.Constants;
import org.jboss.forge.addon.facets.constraints.FacetConstraint;
import org.jboss.forge.addon.facets.constraints.FacetConstraints;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.ui.AbstractProjectCommand;
import org.jboss.forge.addon.resource.ResourceFactory;
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
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;

import java.lang.Override;
import java.lang.Exception;
import java.util.concurrent.Callable;

import javax.inject.Inject;

@FacetConstraints({
         @FacetConstraint(DatabaseMigrationFacet.class)
})
public class GenerateMasterChangeLogFileCommand extends AbstractProjectCommand implements UICommand
{
   @Inject
   @WithAttributes(shortName = 'm', label = "Master ChangeLog File generation", type = InputType.DROPDOWN)
   private UISelectOne<String> generationMode;

   @Inject
   ResourceFactory resourceFactory;
   
   @Inject
   DatabaseMigrationFacet databaseMigrationFacet;

   @Override
   public UICommandMetadata getMetadata(UIContext context)
   {
      return Metadata.forCommand(GenerateMasterChangeLogFileCommand.class)
               .name("generate-master-changelog-file");
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
      generationMode.setDefaultValue("");
      generationMode.setValueChoices(
               new Callable<Iterable<String>>()
               {
                  @Override
                  public Iterable<String> call() throws Exception
                  {
                     return databaseMigrationFacet.getGenerationModes();
                  }
               });
      builder.add(generationMode);
   }

   @Override
   public void validate(UIValidationContext validator)
   {
      super.validate(validator);
      if (generationMode.getValue().equals(""))
         validator.addValidationError(generationMode,
                  "Please select your method to initialize the changelog");
   }

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      Project selectedProj = getSelectedProject(context);
      ChangeLogFileGenerator generator = new ChangeLogFileGenerator(selectedProj);

      String mode = generationMode.getValue();
      if (mode.equals(Constants.MODE_EMPTY_CHANGELOG))
      {
         generator.generateEmptyMasterChangeLog();
      }
      else
      {
         //generator.generateChangeLogFromDatabase();
      }
      return Results
               .success("Master ChangeLog File has been created");
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