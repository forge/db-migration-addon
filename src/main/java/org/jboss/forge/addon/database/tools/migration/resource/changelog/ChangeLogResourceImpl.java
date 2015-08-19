/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.resource.changelog;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import liquibase.changelog.ChangeSet;

import org.jboss.forge.addon.resource.AbstractFileResource;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.parser.xml.resources.XMLResource;
import org.jboss.forge.parser.xml.Node;
import org.jboss.forge.parser.xml.XMLParser;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public class ChangeLogResourceImpl extends AbstractFileResource<XMLResource> implements ChangeLogResource
{

   public ChangeLogResourceImpl(final ResourceFactory factory, final File file)
   {
      super(factory, file);
   }

   @Override
   public ChangeLogResource setContents(Node node)
   {
      setContents(XMLParser.toXMLString(node));
      return this;
   }

   @Override
   public Node getXmlSource() throws FileNotFoundException
   {
      return XMLParser.parse(getResourceInputStream());
   }

   @Override
   protected List<Resource<?>> doListResources()
   {
      return Collections.emptyList();
   }

   @Override
   public ChangeLogResource includeFile(String fileName)
   {
      Node resourceNode;
      try
      {
         resourceNode = this.getXmlSource();
         resourceNode.getSingle("databaseChangeLog").getSingle("databaseChangeLog").createChild("include")
                  .attribute("file", fileName);
         setContents(resourceNode);
      }
      catch (FileNotFoundException e)
      {
         // ignore
      }
      return this;
   }

   @Override
   public ChangeLogResource createFrom(File file)
   {
      return new ChangeLogResourceImpl(getResourceFactory(), file);
   }
}
