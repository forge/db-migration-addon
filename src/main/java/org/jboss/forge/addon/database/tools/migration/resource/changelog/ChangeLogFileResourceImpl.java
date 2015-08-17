/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.resource.changelog;

import java.io.File;
import java.io.FileNotFoundException;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.parser.xml.resources.XMLResourceImpl;
import org.jboss.forge.parser.xml.Node;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public class ChangeLogFileResourceImpl extends XMLResourceImpl implements ChangeLogFileResource
{
   public Node databaseChangeLog;

   /**
    * @param factory
    * @param parent
    * @return 
    */   
   public ChangeLogFileResourceImpl(final ResourceFactory factory, final File file)
   {
      super(factory, file);
   }

   @Override
   public ChangeLogFileResource addNewChangeset(Node node)
   {
      // TODO Auto-generated method stub
      return null;
   }

   /* (non-Javadoc)
    * @see org.jboss.forge.addon.database.tools.migration.resource.changelog.ChangeLogFileResource#includeFile(java.lang.String)
    */
   @Override
   public ChangeLogFileResource includeFile(String fileName)
   {
      Node resourceNode;
      try
      {
         resourceNode = this.getXmlSource();
         Node databaseChangeLogNode = resourceNode.getSingle("databaseChangeLog");
         databaseChangeLogNode.createChild("include").attribute("file",fileName);
         setContents(databaseChangeLogNode);
      }
      catch (FileNotFoundException e)
      {
         // TODO Auto-generated catch block
         //e.printStackTrace();
      }
      return null;
   }

}
