/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.liquibase;

import java.io.PrintStream;

import org.jboss.forge.addon.projects.Project;

import liquibase.CatalogAndSchema;
import liquibase.Liquibase;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.DiffToChangeLog;
import net.sourceforge.argparse4j.inf.Namespace;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public class GenerateChangeLogCommand extends LiquibaseAbstractCommand
{

   /**
    * @param project
    * @param namespace
    */
   public GenerateChangeLogCommand(Project project, Namespace namespace)
   {
      super(project, namespace);
   }

   @SuppressWarnings("unchecked")
   @Override
   public void run(Namespace namespace, Liquibase liquibase) throws Exception
   {
      String subChangeLogFile = namespace.getString("subChangeLogFile");
      liquibase.generateChangeLog(new CatalogAndSchema(null, null), new DiffToChangeLog(new DiffOutputControl()),
               new PrintStream(subChangeLogFile));
      }
}