package kr.or.ddit.board;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import kr.or.ddit.service.BoardServiceImpl;
import kr.or.ddit.vo.BoardVO;


public class BoardMain {
	// 서비스 객체 변수를 선언
	private BoardServiceImpl bsi;
	
	public BoardMain() {

		bsi = BoardServiceImpl.getInstance();

	}

	private Scanner scan = new Scanner(System.in);
	
	
	
	/**
	 * 메뉴를 출력하는 메서드
	 */
	public void displayMenu() {
	
		System.out.println("----------------------");
		System.out.println("  === 게시판 만들기 ===");
		System.out.println("  1. 게시글 작성");
		System.out.println("  2. 게시글 삭제");
		System.out.println("  3. 게시글 수정");
		System.out.println("  4. 전체 게시글 출력");
		System.out.println("  5. 검색");
		System.out.println("  6. 프로그램 종료");
		System.out.println("----------------------");
		System.out.println("원하는 작업 선택 >> ");
	}

	/**
	 * 프로그램 시작메서드
	 * @throws SQLException 
	 */
	public void start() throws SQLException {
		int choice;
		do {
			displayMenu(); // 메뉴 출력
			choice = Integer.parseInt(scan.nextLine()); // 메뉴번호 입력받기
			switch (choice) {
			case 1: // 자료 입력
				insertContent();
				break;
			case 2: // 자료 삭제
				deleteBoard();
				break;
			case 3: // 자료 수정
				updateBoard();
				break;
			case 4: // 전체 자료 출력
				displayBoardAll();
				break;
			case 5: // 자료 검색
				searchBoard();
				break;
			case 6: // 작업 끝
				System.out.println("작업을 마칩니다.");
				break;
			default:
				System.out.println("번호를 잘못 입력했습니다. 다시입력하세요");
			}
		} while (choice != 6);
	}

	/**
	 * 회원정보를 수정하는 메서드
	 * @throws SQLException 
	 */

	private void updateBoard() throws SQLException {

		System.out.println();
		System.out.println("수정할 게시글의 번호를 입력하세요");
		String bdNo = scan.nextLine();
	
		
		System.out.println("수정할 내용을 입력하세요");
		System.out.println("새로운 제목 >> ");
		String bdTitle = scan.nextLine();

		System.out.println("새로운 작성자>>");
		String bdWriter = scan.nextLine();

		System.out.println("새로운 내용>>");
		String bdContent = scan.nextLine();

		BoardVO bv = new BoardVO();

		
		bv.setBoard_no(bdNo);
		bv.setBoard_title(bdTitle);
		bv.setBoard_writer(bdWriter);
		bv.setBoard_content(bdContent);
		
		
		//update()메서드의 반환값은 성공한 레코드 수이다.
		int cnt = bsi.updateBoard(bv);
		if(cnt>0) {
			System.out.println("업데이트 작업 성공");
		}else {
			System.out.println("업데이트 작업 실패");
		}
		System.out.println("---------------------------------");

	}

	private void deleteBoard() throws SQLException {
		System.out.println();
		System.out.println("삭제할 게시글의 번호를 입력해 주세요");
		String bdno = scan.nextLine();
		int cnt = bsi.deleteBoard(bdno);
		
		if(cnt > 0) {
			System.out.println("삭제 성공");
		}else {
			System.out.println("삭제 실패");
		}
		System.out.println("---------------------------------");
	}

	private void displayBoardAll() throws SQLException {

		List<BoardVO> boardList = bsi.displayBoardAll();

		

		for(BoardVO bv : boardList) {
			
			System.out.println("---------------------------------------------------------------------");
			System.out.println("번호 :" + bv.getBoard_no());
			System.out.println("제목 :" + bv.getBoard_title());
			System.out.println("작성자 :" + bv.getBoard_writer());
			System.out.println("날 짜 :" + bv.getBoard_date());
			System.out.println("내 용 :" + bv.getBoard_content());
			System.out.println("---------------------------------------------------------------------");
		}
		System.out.println("출력 끝...");
		
	}

	/**
	 * 
	 * 게시글을 추가하는 메서드
	 * @throws SQLException 
	 */

	private void insertContent() throws SQLException {

		
		System.out.println("정보를 입력하세요");
		System.out.println("게시글 제목 >>");
		String bdTitle = scan.nextLine();

		System.out.println("게시글 작성자 >>");
		String bdWriter = scan.nextLine();


		System.out.println("게시글 내용>>");
		String bdContent = scan.nextLine();
		
		BoardVO bv = new BoardVO();
		bv.setBoard_title(bdTitle);
		bv.setBoard_writer(bdWriter);
		bv.setBoard_content(bdContent);

		int cnt = bsi.insertContent(bv);	
		
		if(cnt == 1) {
			
			System.out.println("추가 작업 성공");
			
		}else {
			
			System.out.println("추가 작업 실패");
			
		}

}

	public void searchBoard() throws SQLException {
		
		System.out.println();
		System.out.println("검색할 정보를 입력하세요.");
		System.out.println("게시판 번호 >>");
		String bdno = scan.nextLine();

		System.out.println("제 목 >>");
		String bdtitle = scan.nextLine();

		System.out.println("작성자 >>");
		String bdwriter = scan.nextLine();


		BoardVO bv = new BoardVO();
		bv.setBoard_no(bdno);
		bv.setBoard_title(bdtitle);
		bv.setBoard_writer(bdwriter);


		List<BoardVO> bdlist = bsi.searchBoard(bv);

		if (bdlist.size() == 0) {
			System.out.println("출력할 회원 정보가 없습니다.");
		} else {
			for (BoardVO bv1 : bdlist) {

				System.out.println("---------------------------------------------------------------------");
				System.out.println("번호 :" + bv1.getBoard_no());
				System.out.println("제목 :" + bv1.getBoard_title());
				System.out.println("작성자 :" + bv1.getBoard_writer());
				System.out.println("날 짜 :" + bv1.getBoard_date());
				System.out.println("내 용 :" + bv1.getBoard_content());
				System.out.println("---------------------------------------------------------------------");

			}
		}
		System.out.println("검색 작업 끝....");

	}

	public static void main(String[] args) throws SQLException {
		BoardMain bdObj = new BoardMain();
		bdObj.start();
	}

}
