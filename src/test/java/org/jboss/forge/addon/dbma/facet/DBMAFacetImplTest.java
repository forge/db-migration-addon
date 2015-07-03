/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.dbma.facet;

import java.util.Properties;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.addon.database.tools.connections.ConnectionProfile;
import org.jboss.forge.addon.dbma.util.Constants;
import org.jboss.forge.addon.facets.FacetFactory;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.facets.ResourcesFacet;
import org.jboss.forge.arquillian.AddonDependencies;
import org.jboss.forge.arquillian.archive.AddonArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
@RunWith(Arquillian.class)
public class DBMAFacetImplTest
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
   private FacetFactory facetFactory;


   @Test
   public void testDBMAFacet() throws Exception
   {
      Project project = projectFactory.createTempProject();
      DBMAFacet dbmaFacet = facetFactory.install(project, DBMAFacet.class);
      Assert.assertTrue((dbmaFacet.isInstalled()));

      ConnectionProfile connection = new ConnectionProfile();
      connection.setName("dbma-connection");
      connection.setUrl("dbma-url");
      connection.setUser("dbma-user");
      connection.setPassword("dbma-password");
      connection.setDialect("dbma-dialect");
      connection.setDriver("dbma-driver");
      connection.setPath("dbma-path");

      dbmaFacet.setPropertiesFile(connection);
      ResourcesFacet resourcesFacet = project.getFacet(ResourcesFacet.class);
      Assert.assertTrue(resourcesFacet.getResourceDirectory()
               .getChildDirectory(Constants.DBMA_MIGRATION_DIRECTORY_NAME)
               .getChild(Constants.DBMA_PROPERTIES_FILE_NAME).exists());
      
      Properties dbmaProperties = new Properties();
      dbmaProperties.load(resourcesFacet.getResourceDirectory()
               .getChildDirectory(Constants.DBMA_MIGRATION_DIRECTORY_NAME)
               .getChild(Constants.DBMA_PROPERTIES_FILE_NAME).getResourceInputStream());
      Assert.assertEquals(dbmaProperties.getProperty("driver"), "dbma-driver");
      Assert.assertEquals(dbmaProperties.getProperty("username"), "dbma-user");
      Assert.assertEquals(dbmaProperties.getProperty("classpath"), "dbma-path");
      Assert.assertEquals(dbmaProperties.getProperty("password"), "dbma-password");
      Assert.assertEquals(dbmaProperties.getProperty("url"), "dbma-url");
      Assert.assertEquals(dbmaProperties.getProperty("liquibaseVersion"), Constants.LIQUIBASE_DEFAULT_VERSION);
      
      //update the properties
      connection.setUser("this-is-me-again");
      dbmaFacet.setPropertiesFile(connection);
      dbmaProperties.load(resourcesFacet.getResourceDirectory()
               .getChildDirectory(Constants.DBMA_MIGRATION_DIRECTORY_NAME)
               .getChild(Constants.DBMA_PROPERTIES_FILE_NAME).getResourceInputStream());
      Assert.assertEquals(dbmaProperties.getProperty("username"), "this-is-me-again");

      

      
   }

}
