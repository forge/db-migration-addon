/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.dbma.util;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public class Constants
{
   public static final String DBMA_MIGRATION_DIRECTORY_NAME = "migration";
   public static final String DBMA_PROPERTIES_FILE_NAME = "dbma.properties";
   public static final String LIQUIBASE_VERSION_PROPERTY_NAME = "LIQUIBASE_VERSION";
   
   public static final String LIQUIBASE_DEFAULT_VERSION = "3.4.0";
   
   public static final String MODE_EMPTY_CHANGELOG = "Generate empty changelog";
   public static final String MODE_CHANGELOG_FROM_DB = "Generate changelog from specified database";
   
}
