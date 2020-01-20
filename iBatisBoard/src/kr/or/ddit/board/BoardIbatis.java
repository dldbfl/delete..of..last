package kr.or.ddit.board;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class BoardIbatis {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		//	iBatis를 이용하여 DB자료를 처리하는 작업 순서
		// 1. iBatis의 환경설정 파일을 읽어와 실행시킨다.
		try {
			//	1-1. xml문서 읽어오기
			// 설정파일의 인코딩 설정
			Charset charset = Charset.forName("UTF-8");
			Resources.setCharset(charset);
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			
			//	1-2. 위에서 읽어온 Reader객체를 이용하여 실제 작업을 계속할 객체 생성
			SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			
			rd.close(); // Reader 객체 닫기
			
			
			int choice;
			do{
				displayMenu(); //메뉴 출력
				choice = scan.nextInt(); // 메뉴번호 입력받기
				switch(choice){
					case 1 :  // 자료 입력
						// 2-1. insert 작업 연습
						System.out.println("Insert 작업 시작...");
						
						// 1) 저장할 데이터를  VO에 담는다.
						BoardVO bv = new BoardVO();
						System.out.println("제목을 적으세요.");
						bv.setBoard_title(scan.next());
						System.out.println("작성자를 적으세요.");
						bv.setBoard_writer(scan.next());
						System.out.println("내용을 적으세요.");
						bv.setBoard_content(scan.next());
						
						// 2)  SqlMapClient객체 변수를 이용하여 해당 쿼리문을 실행한다.
						// 형식) smc.insert("namespace값.id값", 파라미터 객체);
						// 				반환값: 성공하면 null이 반환된다.
						Object obj = smc.insert("boardTest.insertBoard",bv);
						if(obj == null) {
							System.out.println("Insert 작업 성공");
						}else {
							System.out.println("Insert 작업 실패!!!");
						}
						System.out.println("---------------------------------------------");
						break;
					case 2 :  // 자료 삭제
						// 2-3. delete 연습
						System.out.println("delete 작업 시작....");
						
						//	delete메서드의 반환값 : 성공한 레코드 수 
						System.out.println("삭제할 제목을 입력하세요.");
						int cnt2 = smc.delete("boardTest.deleteBoard", scan.next());
										
						if(cnt2 > 0) {
							System.out.println("delete 작업 성공");
						}else {
							System.out.println("delete 작업 실패!!!");
						}
						System.out.println("---------------------------------------------");
						break;
					case 3 :  // 자료 수정
						// 2-2. update작업 연습
						System.out.println("update작업 시작...");
						
						BoardVO bv2 = new BoardVO();
						System.out.println("수정할 번호을 적으세요.");
						bv2.setBoard_no(scan.next());
						System.out.println("제목을 적으세요.");
						bv2.setBoard_title(scan.next());
						System.out.println("작성자를 적으세요.");
						bv2.setBoard_writer(scan.next());
						System.out.println("내용을 적으세요.");
						bv2.setBoard_content(scan.next());
						
						
						//	update()메서드의 변환값은 성공한 레코드 수이다.
						int cnt = smc.update("boardTest.updateBoard",bv2);
						
						if(cnt > 0) {
							System.out.println("update 작업 성공");
						}else {
							System.out.println("update 작업 실패!!!");
						}
						System.out.println("---------------------------------------------");
						break;
					case 4 :  // 전체 자료 출력
//						2-4. select 연습
						//	1) 응답의 결과가 여러개일 경우
						System.out.println("select 연습시작 ( 결과가 여러개인 경우 )... ");
						System.out.println("=======================");
						List<BoardVO> boardList;
						
						// 응답 결과가 여러개일 경우에는 queryForList메서드를 사용한다.
						//	이 메서드는 여러개의 레코드를 VO에 담은 후 이 VO 데이터를 
						//	List에 추가해 주는 작업을 자동으로 수행한다.
						
						boardList = smc.queryForList("boardTest.getBoardAll");
						for(BoardVO bv4 : boardList) {
							System.out.println("번호 : "+bv4.getBoard_no());
							System.out.println("제목 : "+bv4.getBoard_title());
							System.out.println("작성자 : "+bv4.getBoard_writer());	
							System.out.println("날짜 : "+bv4.getBoard_date());
							System.out.println("내용 : "+bv4.getBoard_content());
							System.out.println("=======================");
						}
						System.out.println("end");
						break;
					case 5 :  // 서치
						// 2) 검색
						System.out.println("select 연습시작 ( 결과가 한개일 경우 )... ");
						
						//	응답결과가 1개가 확실할 경우에는 queryForObject메서드를 사용한다.
						System.out.println("검색할 제목을 정하세요.");
						BoardVO bv3 = (BoardVO) smc.queryForObject("boardTest.getBoard",scan.next());
						
						System.out.println("번호 : "+bv3.getBoard_no());
						System.out.println("제목 : "+bv3.getBoard_title());
						System.out.println("작성자 : "+bv3.getBoard_writer());	
						System.out.println("날짜 : "+bv3.getBoard_date());
						System.out.println("내용 : "+bv3.getBoard_content());
						System.out.println("=======================");
						break;
					case 6 :  // 작업 끝
						System.out.println("작업을 마칩니다.");
						break;
						
					default :
						System.out.println("번호를 잘못 입력했습니다. 다시입력하세요");
				}
			}while(choice!=6);
			
		}catch(IOException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
			
			
			
			/**
			 * 메뉴를 출력하는 메서드
			 */
			public static void displayMenu(){
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
			
}
