/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.c96.main;

import org.c96.db.DAO;
import org.json.JSONArray;

/**
 *
 * @author 96
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String sql = "SELECT\n"
                    + "school.ID,\n"
                    + "school.SchName,\n"
                    + "school.SchCode\n"
                    + "FROM\n"
                    + "school\n"
                    + "WHERE ID = ?";
            //設定SQL
            DAO.setSql(sql);
            //設定參數
            DAO.setParameter("1");
            //取得資料
            JSONArray jData = DAO.getJsonArrayData();

            System.out.println(jData);

            sql = "INSERT INTO `School` (`SchName`, `SchCode`) VALUES (?,?)";
            //設定SQL
            DAO.setSql(sql);
            //設定參數
            DAO.setParameter("OPSCHOOL");
            DAO.setParameter("213454");
            DAO.execute();
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
