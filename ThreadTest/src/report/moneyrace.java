package report;

import java.util.Collections;

import kr.or.ddit.basic.T11_DisplayCharacterTest;

public class moneyrace {
	
	static String strRank = "";

	public static void main(String[] args) {

		DisplayCharacter[] disChars = new DisplayCharacter[] { 
				new DisplayCharacter("슈나우저"),
				new DisplayCharacter("로취"), 
				new DisplayCharacter("헤카림") };

		for (int i = 0; i < disChars.length; i++) {
			disChars[i].start();
		}
		for (DisplayCharacter dc : disChars) {
			try {
				dc.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("경기 끝");
		System.out.println("==============================");
		System.out.println();
		System.out.println("경기 결과");
		System.out.println("순위 : "+ strRank);
	}
}

// 영어 대문자를 출력하는 쓰레드 클래스
class DisplayCharacter extends Thread {
	private String name;

	// 생성자
	public DisplayCharacter(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		
		int  location = 0;		
		
		for (int ch = 1; ch <= 50; ch++) {
			if(ch<location) {
				location++;
				for (int i = 0; i >= location; i++) {
				System.out.print("-");
				}
			}
			else if(ch >= location) {
				System.out.print("-");
				
			}
		for (int i = 0; i <= location; i++) {
			System.out.println("도착");
		}
			
		for (int ch2 = 'A'; ch2 <= 'Z'; ch2++) {
			System.out.println(name + "의 출력문자 : " + ch2);

			
			
			

			try {
				// sleep()메서드의 값을 200~500사이의 나수로한다.
				Thread.sleep((int) (Math.random() * 3010 + 2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(name + "출력 끝");
		moneyrace.strRank += name + " ";
		}
	}
}

//말 클래스
class Horse2 extends Thread implements Comparable<Horse2> {
 private String name;
 private int rank = 0;
 private int location = 0;
 public volatile boolean goal = false; // 결승지점 통과 여부
 
 public Horse2(String name) {
		super();
		this.name = name;
	}
 @Override
 public int compareTo(Horse2 h1) {
 	return Integer.compare(this.rank, h1.rank);
 }
	@Override
 public void run() {
     int cnt = 0;
     while (true) {
         location += cnt;
         try {
             Thread.sleep(200 * (int) (Math.random() * 4));
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

         if (location == 50) {
             break;
         }
         cnt++;
     }
 }
 public String getHName() {
     return name;
 }
 // public void setName(String name) {
 // this.name = name;
 // }
 public boolean isGoal() {
     return goal;
 }
 public void setGoal(boolean goal) {
     this.goal = goal;
 }
 public int getRank() {
     return rank;
 }
 public void setRank(int rank) {
     this.rank = rank;
 }
 public int getLocation() {
     return location;  
 }
}  
		
	
