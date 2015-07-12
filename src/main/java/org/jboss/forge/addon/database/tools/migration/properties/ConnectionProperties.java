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
 *
 */
public interface ConnectionProperties
{
   /**
    * get {@link ConnectionProfile}
    */
   ConnectionProfile getConnectionProfile();
   
   /**
    * get LiquibaseVersion
    */
   String getLiquibaseVersion();

   /**
    * @return a String representing {@link DBMAProperties}
    */
   String toString();
}
