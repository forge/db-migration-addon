/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.datasource;


import org.apache.tomcat.jdbc.pool.DataSourceProxy;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public class ManagedDataSourceImpl extends DataSourceProxy implements ManagedDataSource
{
   public ManagedDataSourceImpl(PoolConfiguration config){
      super(config);
   }
   
   @Override
   public void stop() throws Exception {
       close();
   }
   
}
