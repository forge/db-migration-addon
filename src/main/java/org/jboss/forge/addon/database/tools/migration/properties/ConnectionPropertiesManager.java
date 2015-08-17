/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.properties;

import java.util.Properties;

import org.jboss.forge.addon.database.tools.connections.ConnectionProfile;
import org.jboss.forge.addon.projects.Project;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public interface ConnectionPropertiesManager
{  
   /**
    * 
    * @return properties used by Liquibase to perform migration 
    */
   public Properties loadConnectionProperties(Project project);
}
