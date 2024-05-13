package com.kh.mini_back.dao;

import com.kh.mini_back.utils.Common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MemberDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    // 아이디와 비밀번호를 입력받아 일치하면 true, 아니면 false 반환
    public boolean logIn(String id,String pw) {
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "SELECT USER_ID,USER_PW FROM USER_INFO_TB WHERE USER_ID = '" + id +"' AND USER_PW = '" + pw + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return false;
    }
    //아이디를 입력받아 이미존재하면 false 아니면 true 출력,
    public boolean checkId(String id) {
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "SELECT USER_ID FROM USER_INFO_TB WHERE USER_ID = '" + id + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return true;
    }
    //이메일을 입력받아 이미존재하면 false 아니면 true 출력,
    public boolean checkEmail(String email) {
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "SELECT USER_EMAIL FROM USER_INFO_TB WHERE USER_EMAIL = '" + email + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return true;
    }
}
