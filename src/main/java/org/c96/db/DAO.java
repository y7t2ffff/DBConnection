/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.c96.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author 96
 */
public class DAO {

    static Connection con = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;
    static String sql;
    static String Status;
    static String Message;
    static JSONArray jParameters;

    /**
     * 查詢(多筆)資料
     *
     * @return 取得回傳JSONARRAY DATA
     * @throws SQLException
     */
    static public JSONArray getJsonArrayData() throws SQLException {
        JSONArray jData = null;

        try {
            //取得連線
            con = DBConnection.getConnection();

            if (sql.equals("")) {
                throw new Exception("SQL 未設定");
            }

            ps = con.prepareStatement(sql);
            //設定參數
            for (int i = 0; i < jParameters.length(); i++) {
                ps.setObject(i + 1, jParameters.get(i));
            }
            rs = ps.executeQuery();

            jData = new JSONArray();
            JSONObject jtmp = null;
            while (rs.next()) {
                jtmp = new JSONObject();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    jtmp.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
                }
                jData.put(jtmp);
            }
            Status = "Success";
        } catch (Exception ex) {
            System.out.println("SQL Error : " + ex.getMessage());
        } finally {
            if (con != null) {
                con.close();
            }
        }

        return jData;
    }

    /**
     * 查詢(單一)資料
     *
     * @return 取得回傳JSONARRAY DATA
     * @throws SQLException
     */
    static public JSONObject getJsonObjectData() throws SQLException {
        JSONObject jData = null;

        try {
            //取得連線
            con = DBConnection.getConnection();

            if (sql.equals("")) {
                throw new Exception("SQL 未設定");
            }

            ps = con.prepareStatement(sql);
            //設定參數
            for (int i = 0; i < jParameters.length(); i++) {
                ps.setObject(i + 1, jParameters.get(i));
            }
            rs = ps.executeQuery();

            while (rs.next()) {
                jData = new JSONObject();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    jData.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
                }
            }
            Status = "Success";
        } catch (Exception ex) {
            System.out.println("SQL Error : " + ex.getMessage());
        } finally {
            if (con != null) {
                con.close();
            }
        }

        return jData;
    }

    static public boolean execute() throws SQLException {
        boolean flag = false;
        try {
            //取得連線
            con = DBConnection.getConnection();

            if (sql.equals("")) {
                throw new Exception("SQL 未設定");
            }

            ps = con.prepareStatement(sql);
            //設定參數
            for (int i = 0; i < jParameters.length(); i++) {
                ps.setObject(i + 1, jParameters.get(i));
            }
            flag = ps.execute();

        } catch (Exception ex) {

        } finally {
            if (con != null) {
                con.close();
            }
        }

        return flag;
    }

    ;
    
    /**
     * 設定參數
     *
     * @param obj
     */
    public static void setParameter(Object obj) {
        jParameters.put(obj);
    }

    /**
     * 設定SQL，並重新設置參數
     *
     * @param sql
     */
    public static void setSql(String sql) {
        jParameters = new JSONArray();
        DAO.sql = sql;
    }
}
