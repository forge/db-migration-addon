/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.resource.changelog;

import org.jboss.forge.addon.parser.xml.resources.XMLResource;
import org.jboss.forge.parser.xml.Node;

/**
 * represents a liquibase changelog file
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public interface ChangeLogFileResource extends XMLResource
{

   /**
    * 
    * @param node
    * @return
    */
   ChangeLogFileResource addNewChangeset(Node node);
   
   /**
    * adds  new include statement to ChangeLog File 
    * @param fileName the name of the file which is going to be included
    * @return
    */
   ChangeLogFileResource includeFile(String fileName);
}
