package com.kh.mini.dao;

import com.kh.mini.common.Common;
import com.kh.mini.vo.NotBoVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotBoDao {
    private Connection conn = null;
    private Statement stmt = null;
    PreparedStatement psmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    // 게시판에 글쓰기
    public boolean notboinsert(NotBoVo vo){
        int result = 0;
        String query = "INSERT INTO BOARD_TB (BOARD_NO, BOARD_TITLE, BOARD_CATEGORY, BOARD_DE, USER_ID, BOARD_DATE, BOARD_VIEW) VALUES (BOARD_SEQ.NEXTVAL, ?, ?, ?, ?, SYSDATE, 0)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(query);
            pStmt.setString(1, vo.getBoard_title());
            pStmt.setString(2, vo.getBoard_category());
            pStmt.setString(3, vo.getBoard_de());
            pStmt.setString(4, "kimfjd");
            result = pStmt.executeUpdate();
            System.out.println("글이 올라갔습니다. : " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

        if(result == 1) return true;
        else return false;
    }
    public List<NotBoVo> boardSel(String getboard_category) {
        List<NotBoVo> list = new ArrayList<>();
        String sql = null;
        System.out.println("board_category : " + getboard_category);
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            sql = "SELECT BOARD_NO, BOARD_TITLE, USER_ID, TO_CHAR(BOARD_DATE, 'YYYY-MM-DD') AS board_date, BOARD_VIEW FROM BOARD_TB WHERE BOARD_CATEGORY = " + "'" + getboard_category + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int board_no = rs.getInt("BOARD_NO");
                String board_title = rs.getString("BOARD_TITLE");
                String user_id = rs.getString("USER_ID");
                String board_date = rs.getString("BOARD_DATE");
                int board_view = rs.getInt("BOARD_VIEW");

                NotBoVo vo = new NotBoVo();
                vo.setBoard_no(board_no);
                vo.setBoard_title(board_title);
                vo.setUser_id(user_id);
                vo.setBoard_date(Date.valueOf(board_date));
                vo.setBoard_view(board_view);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<NotBoVo> myboardSel(String getuser_id) {
        List<NotBoVo> list = new ArrayList<>();
        String sql = null;
        System.out.println("UserId : " + getuser_id);
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            sql = "SELECT BOARD_NO, BOARD_TITLE, USER_ID, TO_CHAR(BOARD_DATE, 'YYYY-MM-DD') AS board_date, BOARD_VIEW FROM BOARD_TB WHERE USER_ID = " + "'" + getuser_id + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int board_no = rs.getInt("BOARD_NO");
                String board_title = rs.getString("BOARD_TITLE");
                String user_id = rs.getString("USER_ID");
                String board_date = rs.getString("BOARD_DATE");
                int board_view = rs.getInt("BOARD_VIEW");

                NotBoVo vo = new NotBoVo();
                vo.setBoard_no(board_no);
                vo.setBoard_title(board_title);
                vo.setUser_id(user_id);
                vo.setBoard_date(Date.valueOf(board_date));
                vo.setBoard_view(board_view);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<NotBoVo> sersel(String searchType, String keyword) {
        List<NotBoVo> list = new ArrayList<>();
        String sql = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = Common.getConnection();
            if (searchType.equals("제목")) {
                sql = "SELECT BOARD_NO, BOARD_TITLE, USER_ID, TO_CHAR(BOARD_DATE, 'YYYY-MM-DD') AS board_date, BOARD_VIEW FROM BOARD_TB WHERE BOARD_TITLE LIKE ?";
            } else if (searchType.equals("작성자")) {
                sql = "SELECT BOARD_NO, BOARD_TITLE, USER_ID, TO_CHAR(BOARD_DATE, 'YYYY-MM-DD') AS board_date, BOARD_VIEW FROM BOARD_TB WHERE USER_ID = ?";
            }
            pStmt = conn.prepareStatement(sql);
            if (searchType.equals("제목")) {
                pStmt.setString(1, "%" + keyword + "%");
            } else if (searchType.equals("작성자")) {
                pStmt.setString(1, keyword);
            }
            rs = pStmt.executeQuery();
            while (rs.next()) {
                NotBoVo notBoVo = new NotBoVo();
                notBoVo.setBoard_no(rs.getInt("BOARD_NO"));
                notBoVo.setBoard_title(rs.getString("BOARD_TITLE"));
                notBoVo.setUser_id(rs.getString("USER_ID"));
                notBoVo.setBoard_date(rs.getDate("BOARD_DATE"));
                notBoVo.setBoard_view(rs.getInt("BOARD_VIEW"));
                list.add(notBoVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pStmt != null) pStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


}
