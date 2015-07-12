/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.properties;

import org.jboss.forge.addon.database.tools.connections.ConnectionProfile;
import org.jboss.forge.addon.database.tools.migration.util.Constants;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public class ConnectionPropertiesBuilderTest
{
      @Test
      public void testCreate()
      {
         ConnectionProfile connection = new ConnectionProfile();
         connection.setName("dbma-connection");
         connection.setUrl("dbma-url");
         connection.setUser("dbma-user");
         connection.setPassword("dbma-password");
         connection.setDialect("dbma-dialect");
         connection.setDriver("dbma-driver");
         connection.setPath("dbma-path");
         
         ConnectionPropertiesBuilder builder = ConnectionPropertiesBuilder.create();
         Assert.assertNotNull(builder);
         builder.setConnection(connection);
         builder.setLiquibaseVersion(Constants.LIQUIBASE_DEFAULT_VERSION);
         
         Assert.assertEquals(Constants.LIQUIBASE_DEFAULT_VERSION, builder.getLiquibaseVersion());
         ConnectionProfile testConnection = builder.getConnectionProfile();
         Assert.assertNotNull(testConnection);
         Assert.assertEquals("dbma-connection", testConnection.getName());
         Assert.assertEquals("dbma-url", testConnection.getUrl());
         Assert.assertEquals("dbma-user", testConnection.getUser());
         Assert.assertEquals("dbma-password", testConnection.getPassword());
         Assert.assertEquals("dbma-dialect", testConnection.getDialect());
         Assert.assertEquals("dbma-driver", testConnection.getDriver());
         Assert.assertEquals("dbma-path", testConnection.getPath());

      }
}
