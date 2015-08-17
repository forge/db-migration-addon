/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.util;

import java.util.Date;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public class Utils
{
   /**
    * generates a file name out of the date by following this pattern: db.changelog-{TIMESTAMP} 
    * @param date
    * @return the file name
    */
   public static String getChangeLogFileName(Date date)
   {
      return "db.changelog-" + date.toString();
   }

}
