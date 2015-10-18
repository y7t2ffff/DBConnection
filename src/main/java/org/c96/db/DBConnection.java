/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.c96.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 *
 * @author 96
 */
public class DBConnection {

    static DataSource datasource;
    static Future<java.sql.Connection> future;

    static {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/school");
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("1234");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        datasource = new DataSource();
        datasource.setPoolProperties(p);
    }

    static public Connection getConnection() throws InterruptedException, ExecutionException, SQLException {
        Future<java.sql.Connection> future = datasource.getConnectionAsync();
        while (!future.isDone()) {
            System.out.println("Connection 等待中....");
            try {
                Thread.sleep(100);
            } catch (InterruptedException x) {
                Thread.currentThread().interrupt();
            }
        }
        return  future.get();
    }

}
