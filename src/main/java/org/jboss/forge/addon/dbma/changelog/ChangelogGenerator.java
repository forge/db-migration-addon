/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.dbma.changelog;

import org.jboss.forge.addon.resource.FileResource;

/**
 * This  will handle initial generation of the changelog file
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public interface ChangelogGenerator
{
   /**
    * 
    * @return an empty {@link ChangelogFileResource}
    */
   public ChangelogFileResource generateEmptyChangelog();
   
   /**
    *    
    * @return a {@link ChangelogFileResource} from the database
    */
   public ChangelogFileResource generateChangelogFromDatabase();

   
   

}
