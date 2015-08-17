/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.database.tools.migration.datasource;


import java.util.Map;
import java.util.Properties;

import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 * @author <a href="mailto:wicem.zrelly@gmail.com">Wissem Zrelli</a>
 *
 */
public class DataSourceFactory {
   static final  PoolProperties poolConfig = new PoolProperties();
   
   public PoolProperties getPoolCongig(){
      return poolConfig;
   }

   public DataSourceFactory(){
      poolConfig.setMaxActive(1);
      poolConfig.setMaxIdle(1);
      poolConfig.setMinIdle(1);
   }
   
   public void setDbProperties(Properties properties) {
      this.poolConfig.setDriverClassName(properties.getProperty("driver"));
      this.poolConfig.setUrl(properties.getProperty("url"));
      this.poolConfig.setUsername(properties.getProperty("username"));
      this.poolConfig.setPassword(properties.getProperty("password"));
   }
      
   public ManagedDataSource buildSingleConnectionPool(Properties properties) {

       final PoolProperties poolConfig = new PoolProperties();
       poolConfig.setDriverClassName(properties.getProperty("driver"));
       poolConfig.setUrl(properties.getProperty("url"));
       poolConfig.setUsername(properties.getProperty("username"));
       poolConfig.setPassword(properties.getProperty("password"));

       return new ManagedDataSourceImpl(poolConfig);
   }
}
