/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.ui;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.addon.database.tools.migration.ui.AddLiquibaseCommand;
import org.jboss.forge.addon.database.tools.migration.util.Constants;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.facets.DependencyFacet;
import org.jboss.forge.addon.projects.facets.MetadataFacet;
import org.jboss.forge.addon.projects.facets.ResourcesFacet;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.ui.controller.CommandController;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.test.UITestHarness;
import org.jboss.forge.arquillian.AddonDependencies;
import org.jboss.forge.arquillian.archive.AddonArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
@RunWith(Arquillian.class)
public class AddLiquibaseCommandTest
{
   @Deployment
   @AddonDependencies
   public static AddonArchive getDeployment()
   {
      return ShrinkWrap.create(AddonArchive.class).addBeansXML();
   }

   @Inject
   private ProjectFactory projectFactory;

   @Inject
   private UITestHarness testHarness;

   private Project project;

   @Before
   public void setup() throws Exception
   {
      project = projectFactory.createTempProject();
   }

   
   @Test
   public void testAddLiquibaseSetup() throws Exception
   {
      try (CommandController commandController = testHarness.createCommandController(AddLiquibaseCommand.class,
               project.getRoot()))
      {

         commandController.initialize();
         commandController.setValueFor("liquibaseVersion", Constants.LIQUIBASE_DEFAULT_VERSION);
         Assert.assertTrue(commandController.isValid());
         
         Result result = commandController.execute();
         Assert.assertTrue(project.getFacet(ResourcesFacet.class).getResourceDirectory()
                  .getChildDirectory(Constants.MIGRATION_DIRECTORY_NAME).exists());

         DependencyFacet depFacet = project.getFacet(DependencyFacet.class);
         Assert.assertTrue(depFacet.hasDirectManagedDependency(DependencyBuilder.create()
                  .setGroupId("org.liquibase")
                  .setArtifactId("liquibase-maven-plugin")
                  .setVersion(Constants.LIQUIBASE_DEFAULT_VERSION)
                  .setPackaging("pom")
                  .setScopeType("provided")));
         
         MetadataFacet metadataFacet = project.getFacet(MetadataFacet.class);
         Assert.assertEquals(metadataFacet.getDirectProperty(Constants.LIQUIBASE_VERSION_PROPERTY_NAME),
                  Constants.LIQUIBASE_DEFAULT_VERSION);
      }
   }

   @After
   public void tearDown() throws Exception
   {
      project.getRoot().delete(true);
   }

}
