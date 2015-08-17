/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.liquibase;



import java.sql.SQLException;
import java.util.Properties;

import net.sourceforge.argparse4j.inf.Namespace;

import org.jboss.forge.addon.database.tools.migration.datasource.DataSourceFactory;
import org.jboss.forge.addon.database.tools.migration.datasource.ManagedDataSource;
import org.jboss.forge.addon.database.tools.migration.facet.DatabaseMigrationFacet;
import org.jboss.forge.addon.projects.Project;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;
import liquibase.exception.ValidationFailedException;


/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public abstract class LiquibaseAbstractCommand
{
   private final Project project;
   private final Namespace namespace;

   protected LiquibaseAbstractCommand(Project project, Namespace namespace) {
       this.project = project;
       this.namespace = namespace;
   }

   public void run() throws Exception {
      
      DatabaseMigrationFacet dbMigrationFacet = this.project.getFacet(DatabaseMigrationFacet.class);
      Properties properties = dbMigrationFacet.getProperties();
      final DataSourceFactory dbConfig = new DataSourceFactory();
      String migrationFile = namespace.getString("migrationFile");

      try (final AutoCloseableLiquibase liquibase = openLiquibase(dbConfig, properties, migrationFile)) {
          run(namespace, liquibase);
      } catch (ValidationFailedException e) {
          e.printDescriptiveError(System.err);
          throw e;
      }
  }

  private AutoCloseableLiquibase openLiquibase(final DataSourceFactory dataSourceFactory, final Properties properties, String migrationFile)
          throws SQLException, LiquibaseException {
      final ManagedDataSource dataSource = dataSourceFactory.buildSingleConnectionPool(properties);
      final AutoCloseableLiquibase autoCloseableLiquibase = migrationFile!= null?new AutoCloseableLiquibase(dataSource, migrationFile):new AutoCloseableLiquibase(dataSource);      
      return autoCloseableLiquibase;
  }

  protected abstract void run(Namespace namespace, Liquibase liquibase) throws Exception;
}
