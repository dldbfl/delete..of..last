package report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.util.DBUtil;
import kr.or.ddit.util.DBUtil3;

public class BoardDaoImpl implements IBoardDao {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	/**
	  자원 반납용 메서드
	 */
	private void disConnect() {
		// 사용했던 자원 반납
		if(rs!=null)try{ rs.close(); }catch(SQLException ee){}
		if(stmt!=null)try{ stmt.close(); }catch(SQLException ee){}
		if(pstmt!=null)try{ pstmt.close(); }catch(SQLException ee){}
		if(conn!=null)try{ conn.close(); }catch(SQLException ee){}
	}
	
	@Override
	public int insertBoard(BoardVO bv) {
		
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			String sql =" INSERT INTO jdbc_board (board_no, board_title, board_writer, board_date, board_content) "+
					" VALUES (board_seq.NEXTVAL,?,?,SYSDATE, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  bv.getBoard_title());
			pstmt.setString(2,  bv.getBoard_writer());
			pstmt.setString(3,  bv.getBoard_content());
			
			cnt = pstmt.executeUpdate();
			
			
		}catch(SQLException e)	{
			e.printStackTrace();
		}finally {
			disConnect(); // 자원 반납
		}
		return cnt;
	}

	

	@Override
	public List<BoardVO> displayBoardAll() {
		
		List<BoardVO> memList = new ArrayList<BoardVO>();
		
		try {
			conn = DBUtil.getConnection();
			
			String sql = "select * from jdbc_board";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String board_no = rs.getString("board_no");
				String board_title = rs.getString("board_title");
				String board_writer = rs.getString("board_writer");
				String board_date = rs.getString("board_date");
				String board_content = rs.getString("board_content");
				
				System.out.println(board_no+"\t"+board_title+"\t"+board_writer+"\t"+board_date+"\t"+board_content);
			}
			System.out.println("------------------------------------------------------");
			System.out.println("출력작업 끝");
		}catch(SQLException e) {
			System.out.println("회원 자료 가져오기 실패");
			e.printStackTrace();
		}finally {
			disConnect();
		}
		return memList;
	}

	@Override
	public int updateBoard(BoardVO bv) {
		
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			String sql = "update jdbc_board set board_title = ?, board_writer = ?, board_content =?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bv.getBoard_title());
			pstmt.setString(2, bv.getBoard_writer());
			pstmt.setString(3, bv.getBoard_content());
			
			cnt = pstmt.executeUpdate();
			
		
		}catch (SQLException e) {
	
			e.printStackTrace();
		}finally {
			disConnect();
		}
		return cnt;
	}

	@Override
	public int deleteBoard(String board_title) {
		
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			String sql = "delete from jdbc_board where board_title = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board_title);
			
			cnt = pstmt.executeUpdate();
			if(cnt>0) {
				System.out.println(board_title + "삭제 성공....");
			}else {
				System.out.println(board_title+" 삭제 실패!!!");
			}
		}catch(SQLException e) {
			System.out.println(board_title+" 삭제 실패!!!");
			e.printStackTrace();
		}finally {
			disConnect();
		}
		return cnt;
	}

	@Override
	public List<BoardVO> search(BoardVO bv) {
		List<BoardVO> memList = new ArrayList<>();
		
		try { 
			conn = DBUtil3.getConnection();
			if((bv.getBoard_no() == null || bv.getBoard_no().equals(""))&&
					(bv.getBoard_title() == null || bv.getBoard_title().equals(""))&&
							(bv.getBoard_writer() == null || bv.getBoard_writer().equals(""))&&
									(bv.getBoard_date() == null || bv.getBoard_date().equals(""))&&
											(bv.getBoard_content() == null || bv.getBoard_content().equals(""))) {
				System.out.println("아무것도 작성하지않으셨습니다. 다시 입력해주세요.");
			}
			else {	
			String sql =" select * from jdbc_board where 1=1 ";
			if(bv.getBoard_no() != null && !bv.getBoard_no().equals("")) {
				sql += " and Board_no = ? ";
			}
			if(bv.getBoard_title() != null && !bv.getBoard_title().equals("")) {
				sql += " and Board_title = ? ";
			}
			if(bv.getBoard_writer() != null && !bv.getBoard_writer().equals("")) {
				sql += " and Board_writer = ? ";
			}
			if(bv.getBoard_date() != null && !bv.getBoard_date().equals("")) {
				sql += " and Board_date = like '%'  || ? || '%' ";
			}
			if(bv.getBoard_content() != null && !bv.getBoard_content().equals("")) {
				sql += " and Board_content = like '%'  || ? || '%' ";
			}
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			
			if(bv.getBoard_no() != null && !bv.getBoard_no().equals("")) {
				pstmt.setString(index++, bv.getBoard_no());
			}
			if(bv.getBoard_title() != null && !bv.getBoard_title().equals("")) {
				pstmt.setString(index++, bv.getBoard_title());
			}
			if(bv.getBoard_writer() != null && !bv.getBoard_writer().equals("")) {
				pstmt.setString(index++, bv.getBoard_writer());
			}
			if(bv.getBoard_date() != null && !bv.getBoard_date().equals("")) {
				pstmt.setString(index++, bv.getBoard_date());
			}
			if(bv.getBoard_content() != null && !bv.getBoard_content().equals("")) {
				pstmt.setString(index++, bv.getBoard_content());
			}			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO boaVO = new BoardVO();
				boaVO.setBoard_no(rs.getString("board_no"));
				boaVO.setBoard_title(rs.getString("board_title"));
				boaVO.setBoard_writer(rs.getString("board_writer"));
				boaVO.setBoard_date(rs.getString("board_date"));
				boaVO.setBoard_content(rs.getString("board_content"));
				
				memList.add(boaVO);
			}
			}
		}catch(SQLException e) {
			memList = null;
			e.printStackTrace();
		}finally {
			disConnect();
		}
					
		return memList;
	}

}
