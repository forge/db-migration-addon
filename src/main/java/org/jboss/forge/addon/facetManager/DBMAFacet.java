/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.facetManager;

import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.facets.AbstractFacet;
import org.jboss.forge.addon.facets.constraints.FacetConstraint;
import org.jboss.forge.addon.facets.constraints.FacetConstraints;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.projects.facets.DependencyFacet;
import org.jboss.forge.addon.projects.facets.MetadataFacet;
import org.jboss.forge.addon.projects.facets.ResourcesFacet;
import org.jboss.forge.addon.resource.FileResource;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */

@FacetConstraints({
         @FacetConstraint(DependencyFacet.class),
         @FacetConstraint(MetadataFacet.class),
         @FacetConstraint(ProjectFacet.class),
         @FacetConstraint(ResourcesFacet.class)
})
public class DBMAFacet extends AbstractFacet<Project> implements ProjectFacet
{
   public static final String DBMA_DIRECTORY_NAME = "migration";
   public static final String DBMA_CONFIGURATION_FILE = "dbma.properties";
   public static final String LIQUIBASE_DEFAULT_VERSION = "3.4.0  ";

   private DependencyBuilder createDependency(String version)
   {
      return DependencyBuilder.create()
               .setGroupId("org.liquibase")
               .setArtifactId("liquibase-maven-plugin")
               .setVersion(version)
               .setPackaging("pom")
               .setScopeType("import");
   }

   @Override
   public boolean install()
   {
      // TODO Auto-generated method stub
      return false;
   }

   public boolean isInstalled()
   {
      DependencyFacet dependencyFacet = getFaceted().getFacet(DependencyFacet.class);
      return dependencyFacet.hasDirectManagedDependency(createDependency(LIQUIBASE_DEFAULT_VERSION));
   }

}
