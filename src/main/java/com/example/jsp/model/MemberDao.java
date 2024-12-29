package com.example.jsp.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDao {
    private static DataSource dataSource;

    static {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private void closeConnection(Connection con) {
        if(con == null) return;
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createMember(MemberDto member) {
        Connection con = null;
        try {
            con = getConnection();
            String sql = "insert into member (id, name, password, email, address) " +
                                "values (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getName());
            pstmt.setString(3, member.getPassword());
            pstmt.setString(4, member.getEmail());
            pstmt.setString(5, member.getAddress());

            int rowCount = pstmt.executeUpdate();
            System.out.println(rowCount + " row(s) inserted.");
        } catch (SQLException e) {
            throw new RuntimeException("Member insert error : " + e);
        } finally {
            closeConnection(con);
        }
    }

    public String readMemberPassword(String id, String password) {
        String result = "fail";
        Connection con = null;
        try {
            con = getConnection();
            String sql = "select id, name, password, email, address from member " +
                    "where id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                String findPw = rs.getString("password");
                if(findPw.equals(password)) {
                    result = "success";
                    System.out.println("로그인 성공");
                } else {
                    throw new RuntimeException("ID/PW가 없습니다.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Member select error : " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Member select error : " + e.getMessage());
        } finally {
            closeConnection(con);
        }

        return result;
    }

    public MemberDto readMember(String id) {
        Connection con = null;
        MemberDto member = null;

        try {
            con = getConnection();
            String sql = "select id, name, password, email, address from member " +
                    "where id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                member = new MemberDto(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("address")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Member select error : " + e);
        } finally {
            closeConnection(con);
        }

        return member;
    }

    public void updateMember(MemberDto member) {
        Connection con = null;
        try {
            con = getConnection();
            String sql = "update member set name = ?, password = ?, email = ?, address = ? " +
                    "where id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getEmail());
            pstmt.setString(4, member.getAddress());
            pstmt.setString(5, member.getId());

            int rowCount = pstmt.executeUpdate();
            System.out.println(rowCount + " row(s) updated.");
        } catch (SQLException e) {
            throw new RuntimeException("Member update error : " + e);
        } finally {
            closeConnection(con);
        }
    }

    public void deleteMember(String id, String password) {
        Connection con = null;
        try {
            con = getConnection();
            String sql = "delete from member where id = ? and password = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            int rowCount = pstmt.executeUpdate();
            System.out.println(rowCount + " row(s) deleted.");
        } catch (SQLException e) {
            throw new RuntimeException("Member delete error : " + e);
        } finally {
            closeConnection(con);
        }
    }
}