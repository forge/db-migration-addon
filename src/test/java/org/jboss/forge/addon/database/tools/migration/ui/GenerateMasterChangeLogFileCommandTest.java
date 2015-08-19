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
import org.jboss.forge.addon.database.tools.migration.facet.DatabaseMigrationFacet;
import org.jboss.forge.addon.database.tools.migration.resource.changelog.ChangeLogResource;
import org.jboss.forge.addon.database.tools.migration.util.Constants;
import org.jboss.forge.addon.facets.FacetFactory;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.facets.ResourcesFacet;
import org.jboss.forge.addon.ui.controller.CommandController;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.test.UITestHarness;
import org.jboss.forge.arquillian.AddonDependencies;
import org.jboss.forge.arquillian.archive.AddonArchive;
import org.jboss.forge.parser.xml.Node;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
@RunWith(Arquillian.class)
public class GenerateMasterChangeLogFileCommandTest
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

   @Inject
   private FacetFactory facetFactory;

   @Before
   public void setup() throws Exception
   {
      project = projectFactory.createTempProject();

      try (CommandController LiquibaseCommandController = testHarness.createCommandController(AddLiquibaseCommand.class,
               project.getRoot()))
      {
         LiquibaseCommandController.initialize();
         LiquibaseCommandController.setValueFor("liquibaseVersion", Constants.LIQUIBASE_DEFAULT_VERSION);
         LiquibaseCommandController.execute();
      }
   }

   @Test
   public void testEmptyMasterChangeLogGeneration() throws Exception
   {

      try (CommandController commandController = testHarness.createCommandController(
               GenerateMasterChangeLogFileCommand.class,
               project.getRoot()))
      {

         commandController.initialize();
         commandController.setValueFor("generationMode", Constants.MODE_EMPTY_CHANGELOG);
         Assert.assertTrue(commandController.isValid());

         Result result = commandController.execute();
         Assert.assertTrue(project.getFacet(ResourcesFacet.class).getResourceDirectory()
                  .getChildDirectory(Constants.DEFAULT_MIGRATION_DIRECTORY).getChild(Constants.MASTER_CHANGELOG).exists());

         DatabaseMigrationFacet migrationFacet = project.getFacet(DatabaseMigrationFacet.class);
         ChangeLogResource changeLog = migrationFacet.getMasterChangeLog();
         Assert.assertTrue(changeLog != null);
         Node resourceNode = changeLog.getXmlSource();
         Node testNode = new Node("databaseChangeLog");
         testNode.attribute("xmlns", "http://www.liquibase.org/xml/ns/dbchangelog");
         testNode.attribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
         testNode.attribute("xsi:schemaLocation", "http://www.liquibase.org/xml/ns/dbchangelog"
                  + "            http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd");

         Assert.assertEquals(resourceNode.getSingle("databaseChangeLog"), testNode);
         Assert.assertEquals(result.getMessage(), "Master ChangeLog File has been created");

      }
   }

   @After
   public void tearDown() throws Exception
   {
      project.getRoot().delete(true);
   }

}
