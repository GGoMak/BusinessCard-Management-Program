package org.edwith.webbe.cardmanager.dao;

import org.edwith.webbe.cardmanager.dto.BusinessCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusinessCardManagerDao {

    private static String dburl = "jdbc:mysql://localhost:3306/projectA?serverTimezone=UTC";
    private static String dbUser = "connectuser";
    private static String dbPasswd = "1234";

    public List<BusinessCard> searchBusinessCard(String keyword) {
        List<BusinessCard> businessCardList = new ArrayList<>();
        BusinessCard businessCard = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dburl, dbUser, dbPasswd);

            String sql = "SELECT * FROM businesscard WHERE businesscard.name = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, keyword);
            rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String companyName = rs.getString("companyName");
                Date createDate = rs.getDate("createDate");

                businessCard = new BusinessCard(name, phone, companyName);
                businessCard.setCreateDate(createDate);

                businessCardList.add(businessCard);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return businessCardList;
    }

    public BusinessCard addBusinessCard(BusinessCard businessCard){

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dburl, dbUser, dbPasswd);

            String sql = "INSERT INTO businesscard VALUE (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, businessCard.getName());
            ps.setString(2, businessCard.getPhone());
            ps.setString(3, businessCard.getCompanyName());
            ps.setDate(4, new java.sql.Date(businessCard.getCreateDate().getTime()));
            ps.executeUpdate();

        } catch(Exception e){
            e.printStackTrace();
        } finally {
            if(ps != null){
                try{
                    ps.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try{
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return businessCard;
    }
}
