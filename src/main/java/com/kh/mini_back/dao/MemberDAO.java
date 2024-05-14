package com.kh.mini_back.dao;

import com.fasterxml.jackson.databind.ext.SqlBlobSerializer;
import com.kh.mini_back.utils.Common;
import com.kh.mini_back.vo.MemberVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MemberDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    // 아이디와 비밀번호를 입력받아 일치하면 true, 아니면 false 반환
    public boolean logIn(String id,String pw) {
        boolean isLogin = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "SELECT USER_ID,USER_PW FROM USER_INFO_TB WHERE USER_ID = '" + id +"' AND USER_PW = '" + pw + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                isLogin = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isLogin;
    }
    //아이디를 입력받아 이미존재하면 true 아니면 false 출력,
    public boolean checkId(String id) {
        boolean isId = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "SELECT USER_ID FROM USER_INFO_TB WHERE USER_ID = '" + id + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                isId = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isId;
    }
    //이메일을 입력받아 이미존재하면 true 아니면 false 출력,
    public boolean checkEmail(String email) {
        boolean isEmail = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "SELECT USER_EMAIL FROM USER_INFO_TB WHERE USER_EMAIL = '" + email + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("USER_EMAIL"));
                isEmail = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isEmail;
    }

    //이메일을 입력받아 아이디 비밀번호 찾기
    public String findInfo(String email, String category) {
        String id = null;
        String pw = null;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "SELECT USER_ID, USER_PW FROM USER_INFO_TB WHERE USER_EMAIL = '" + email + "'";
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                id = rs.getString("USER_ID");
                pw = rs.getString("USER_PW");
                if(category.contains("아이디")) return id;
                else return pw;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return null;
    }
}
