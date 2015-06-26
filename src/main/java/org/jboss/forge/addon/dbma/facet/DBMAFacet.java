/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.dbma.facet;

import org.jboss.forge.addon.dbma.properties.DBMAPropertiesBuilder;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.facets.AbstractFacet;
import org.jboss.forge.addon.facets.constraints.FacetConstraint;
import org.jboss.forge.addon.facets.constraints.FacetConstraints;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.projects.facets.DependencyFacet;
import org.jboss.forge.addon.projects.facets.MetadataFacet;
import org.jboss.forge.addon.projects.facets.ResourcesFacet;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */

@FacetConstraints({
         @FacetConstraint(DependencyFacet.class),
         @FacetConstraint(MetadataFacet.class),
         @FacetConstraint(ResourcesFacet.class)
})
public class DBMAFacet extends AbstractFacet<Project> implements ProjectFacet
{
   public static final String DBMA_DIRECTORY_NAME = "migration";
   public static final String DBMA_CONFIGURATION_FILE = "dbma.properties";
   public static final String LIQUIBASE_DEFAULT_VERSION = "3.4.0";
   public static final String LIQUIBASE_VERSION_PROPERTY_NAME = "LIQUIBASE_VERSION";

   private DependencyBuilder createLiquibaseDependency()
   {
      return DependencyBuilder.create()
               .setGroupId("org.liquibase")
               .setArtifactId("liquibase-maven-plugin")
               .setVersion(LIQUIBASE_DEFAULT_VERSION)
               .setPackaging("pom")
               .setScopeType("provided");
   }

   public String getLiquibaseDefaultVersion(){
      return LIQUIBASE_DEFAULT_VERSION;
   }
   
   public String getInstalledVersion()
   {
      MetadataFacet metadataFacet = getFaceted().getFacet(MetadataFacet.class);
      return metadataFacet.getDirectProperty(LIQUIBASE_VERSION_PROPERTY_NAME);
   }
   
   @Override
   public boolean install()
   {
      DependencyFacet dependencyFacet = getFaceted().getFacet(DependencyFacet.class);
      MetadataFacet metadataFacet = getFaceted().getFacet(MetadataFacet.class);

      String version = metadataFacet.getDirectProperty(LIQUIBASE_VERSION_PROPERTY_NAME);
      if (version == null)
      {
         createMigrationDirectory();
         metadataFacet.setDirectProperty(LIQUIBASE_VERSION_PROPERTY_NAME, LIQUIBASE_DEFAULT_VERSION);
         dependencyFacet.addDirectManagedDependency(createLiquibaseDependency());
      }
      return true;
   }
   
   public void createMigrationDirectory(){
      ResourcesFacet resourcesFacet = getFaceted().getFacet(ResourcesFacet.class);
      resourcesFacet.getResourceDirectory().getOrCreateChildDirectory(DBMA_DIRECTORY_NAME);
   } 

   public boolean isInstalled()
   {
      DependencyFacet dependencyFacet = getFaceted().getFacet(DependencyFacet.class);
      return dependencyFacet.hasDirectManagedDependency(createLiquibaseDependency());
   }

   /**
    * @param pBuilder
    */
   public void addDBMAProperties(DBMAPropertiesBuilder pBuilder)
   {
      // TODO Auto-generated method stub
      
   }

}
