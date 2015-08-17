/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.liquibase;


import java.sql.SQLException;

import org.jboss.forge.addon.database.tools.migration.datasource.ManagedDataSource;
import org.jboss.forge.addon.database.tools.migration.util.Constants;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public class AutoCloseableLiquibase extends Liquibase implements AutoCloseable {
   private final ManagedDataSource dataSource;

   
   public AutoCloseableLiquibase(ManagedDataSource dataSource) throws LiquibaseException, SQLException {
      super(Constants.DEFAULT_MIGRATIONS_FILE,
            new ClassLoaderResourceAccessor(),
            new JdbcConnection(dataSource.getConnection()));
      this.dataSource = dataSource;
  }
   
   public AutoCloseableLiquibase(ManagedDataSource dataSource, String fileName) throws LiquibaseException, SQLException {
       super(fileName,
             new FileSystemResourceAccessor(),
             new JdbcConnection(dataSource.getConnection()));
       this.dataSource = dataSource;
   }

   @Override
   public void close() throws Exception {
       try {
           database.close();
       } finally {
           dataSource.stop();
       }
   }
}