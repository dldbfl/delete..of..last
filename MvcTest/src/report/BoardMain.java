package report;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import kr.or.ddit.basic.IMemberService;
import kr.or.ddit.basic.MemberMain;
import kr.or.ddit.basic.MemberServiceImpl;
import kr.or.ddit.basic.MemberVO;
import kr.or.ddit.util.DBUtil;


public class BoardMain {
	
	private IBoardService BoardService;

	public BoardMain() {
		BoardService = new BoardServiceImpl();
	}
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Scanner scan = new Scanner(System.in); 
	
	/**
	 * 메뉴를 출력하는 메서드
	 */
	public void displayMenu(){
		System.out.println();
		System.out.println("----------------------");
		System.out.println("  === 작 업 선 택 ===");
		System.out.println("  1. 자료 입력");
		System.out.println("  2. 자료 삭제");
		System.out.println("  3. 자료 수정");
		System.out.println("  4. 전체 자료 출력");
		System.out.println("  5. 검색.");		
		System.out.println("  6. 작업 끝.");
		System.out.println("----------------------");
		System.out.print("원하는 작업 선택 >> ");
	}
	
	/**
	 * 프로그램 시작메서드
	 */
	public void start(){
		int choice;
		do{
			displayMenu(); //메뉴 출력
			choice = scan.nextInt(); // 메뉴번호 입력받기
			switch(choice){
				case 1 :  // 자료 입력
					insertBoard();
					break;
				case 2 :  // 자료 삭제
					deleteBoard();
					break;
				case 3 :  // 자료 수정
					updateBoard();
					break;
				case 4 :  // 전체 자료 출력
					displayBoardAll();			
					break;
				case 5 :  // 서치
					search();
					break;
				case 6 :  // 작업 끝
					System.out.println("작업을 마칩니다.");
					break;
					
				default :
					System.out.println("번호를 잘못 입력했습니다. 다시입력하세요");
			}
		}while(choice!=6);
	}
	
	
	/**
	 	회원정보를 삭제하는 메서드(입력받은 회원 ID를 이용하여 삭제한다.
	 */
	
	private void deleteBoard() {
		System.out.println();
		System.out.println("삭제할 게시글제목을 입력하세요 >> ");
		String board_title = scan.next();
		
		int cnt = BoardService.deleteBoard(board_title);
		
		if(cnt > 0) {
			System.out.println(board_title +"회원 삭제 성공...");
		}else {
			System.out.println(board_title +"회원 삭제 실패!!!");
		}
		
	}

	/**
	 	글 수정하는 메서드
	 */
	
	private void updateBoard() {
		System.out.println();
		String board_title = "";
		boolean chk = true;
		board_title = scan.next();
		
		System.out.println("수정할 내용을 입력하세요.");
		System.out.println("새로운 작성자 이름 >> ");
		String board_writer = scan.next();
		
		scan.nextLine();//버퍼 비우기
		System.out.println("새로운 내용>> ");
		String board_content = scan.nextLine();
		
		BoardVO bv = new BoardVO();
		bv.setBoard_title(board_title);
		bv.setBoard_writer(board_writer);
		bv.setBoard_content(board_content);
		
		int cnt = BoardService.updateBoard(bv);
		
		if(cnt > 0) {
			System.out.println(board_writer +"회원글 수정 작업 성공...");
		}else {
			System.out.println(board_writer +"회원 수정 작업 실패!!!");
		}
	}

	/**
	  전체 게시글을 출력하는 메서드
	 */
	
	private void displayBoardAll() {
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println(" 번호\t제목\t작성자\t날짜\t내용");
		System.out.println("-----------------------------------------------");
		
		List<BoardVO>memList = BoardService.displayBoardAll();
		if(memList.size() == 0) {
			System.out.println("출력할 회원정보가 없습니다.");
		}else {
			for(BoardVO bv : memList) {
				System.out.println(bv.getBoard_no()+"\t"+bv.getBoard_title()
				+"\t"+bv.getBoard_writer()+"\t"+bv.getBoard_date()+"\t"+bv.getBoard_content());
			}			
		}
	}

	/**
	 	글 추가하는 메서드
	 */
	
	private void insertBoard() {
		boolean chk = false;	// 중복체크의 목적을 가진다. check==chk
		String memId = "";
				
		System.out.println("제목>> ");
		String board_title = scan.next();
		
		System.out.println("작성자>> ");
		String board_writer = scan.next();
		
		scan.nextLine(); // 버퍼 비우기
		System.out.print("내용>> ");
		String board_content = scan.nextLine();
		
		// 입력받은 정보를 VO객체에 넣는다.
				BoardVO bv = new BoardVO();
				bv.setBoard_title(board_title);
				bv.setBoard_writer(board_writer);
				bv.setBoard_content(board_content);
				
				int cnt = BoardService.insertBoard(bv);
				
				if(cnt > 0) {
					System.out.println(memId +"회원 추가 작업 성공...");
				}else {
					System.out.println(memId +"회원 추가 작업 실패!!!");
				}
		
	}
	
	/**
	 * 회원 ID를 이용하여 회원이 있는지 알려주는 메서드
	 * @param memId
	 * @return true : 이미 존재함, false: 신규회원
	 */

	public void search() {
		// 검색할 회원 ID, 회원 이름, 전화번호, 주소등을 입력하면
		// 입력한 정보만 사용하여 검색하는 기능을 구현하시오.
		// 주소는 입력한 값이 포함만 되어도 검색되도록 한다.
		// 입력을 하지 않을 자료는 엔터키로 다음 입력으로 넘긴다.
		scan.nextLine(); // 입력버퍼 지우기
		System.out.println();
		System.out.println("검색할 정보를 입력하세요.");
		System.out.println("게시글 번호 >>");
		String board_no = scan.nextLine().trim();
		
		System.out.println("새로운 제목 >> ");
		String board_title = scan.nextLine().trim();
		
		System.out.println("새로운 이름>> ");
		String board_writer = scan.nextLine().trim();
		
		
		System.out.println("새로운 날짜>> ");
		String board_date = scan.nextLine().trim();
		
		scan.nextLine();//버퍼 비우기
		System.out.println("새로운 내용>> ");
		String board_content = scan.nextLine().trim();
		
		BoardVO bv = new BoardVO();
		bv.setBoard_no(board_no);
		bv.setBoard_title(board_title);
		bv.setBoard_writer(board_writer);
		bv.setBoard_date(board_date);
		bv.setBoard_content(board_content);
		
		List<BoardVO> memList =
				BoardService.search(bv);
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println(" ID\t이름\t전화번호\t주소");
		System.out.println("-----------------------------------------------");
	
		if(memList.size() == 0) {
			System.out.println("출력할 회원정보가 없습니다.");
		}else {
			for(BoardVO mv2 : memList) {
				System.out.println(mv2.getBoard_no()+"\t"+mv2.getBoard_title()
				+"\t"+mv2.getBoard_writer()+"\t"+mv2.getBoard_date()+"\t"+mv2.getBoard_content());
			}			
		}
	}

	public static void main(String[] args) {
		BoardMain memObj = new BoardMain();
		memObj.start();
	}

}






