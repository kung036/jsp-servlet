package com.example.jsp.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
