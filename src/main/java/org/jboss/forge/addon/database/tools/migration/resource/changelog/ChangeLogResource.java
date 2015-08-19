/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.resource.changelog;

import java.io.FileNotFoundException;

import org.jboss.forge.addon.parser.xml.resources.XMLResource;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.parser.xml.Node;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public interface ChangeLogResource extends XMLResource
{
  /**
   * Add an include to tag to the changelog file using the provided path 
   * @param fileName
   * @return {@link ChangeLogResource}
   */
  public ChangeLogResource includeFile(String fileName);
}
