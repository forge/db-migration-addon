/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.properties;

import org.jboss.forge.addon.database.tools.connections.ConnectionProfile;
import org.jboss.forge.addon.resource.FileResource;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 */
public class ConnectionPropertiesBuilder implements ConnectionProperties
{

   private ConnectionProfile connection;
   private String liquibaseVersion;

   public static ConnectionPropertiesBuilder create()
   {
      return new ConnectionPropertiesBuilder();
   }

   public ConnectionPropertiesBuilder setLiquibaseVersion(String version)
   {
      this.liquibaseVersion = version;
      return this;
   }

   public ConnectionPropertiesBuilder setConnection(ConnectionProfile connection)
   {
      this.connection = connection;
      return this;
   }

   @Override
   public ConnectionProfile getConnectionProfile()
   {
      return this.connection;
   }

   @Override
   public String getLiquibaseVersion()
   {
      return this.liquibaseVersion;
   }

   @Override
   public String toString()
   {
      return "driver: " + this.connection.getDriver() + "\n"
               + "classpath: " + this.getConnectionProfile().getPath() + "\n"
               + "url: " + this.getConnectionProfile().getUrl() + "\n"
               + "username: " + this.getConnectionProfile().getUser() + "\n"
               + "password: " + this.getConnectionProfile().getPassword() + "\n"
               + "liquibaseVersion " + this.getLiquibaseVersion();
   }

}
