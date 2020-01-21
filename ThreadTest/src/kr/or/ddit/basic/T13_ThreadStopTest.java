package kr.or.ddit.basic;

public class T13_ThreadStopTest {
	public static void main(String[] args) {
		ThreadStopEx1 th = new ThreadStopEx1();
//		th.start();
//		
//		try {
//			Thread.sleep(1000);
//		}catch(InterruptedException e) {
//			e.printStackTrace();
//		}
//		
////		th.stop();
//		th.setStop(true); // 0. 뭔가 미묘한 0번쨰 방법
		
		//	1번째 방법. interrupt()메서드를 이용한 쓰레드 멈추기
		ThreadStopEx2 th2 = new ThreadStopEx2();
		th2.start();
		
		try {
			Thread.sleep(300);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		th2.interrupt(); // 인터럽트 걸기
	}
}
	
/**
	 stop 메서드 연습용 쓰레드
 */

class ThreadStopEx1 extends Thread{
	private boolean stop;
	
	public  void setStop(boolean stop) {
		this.stop = stop;
	}
	
	@Override
	public void run() {
		while(!stop) {
			System.out.println("쓰레드 처리중.. ... ... ");
		}
		System.out.println("자원 정리 중....");
		System.out.println("작업종료");
	}
}

/**
 	interrupt() 메서드를 이용하여 쓰레드를 멈추게 하는 방법
 	
 	트라이캐치하라고 빨간줄 그어지는건 다 인터럽티드 문제.
 */
class ThreadStopEx2 extends Thread {
	@Override
	public void run() {
//		//	방법1 => sleep()메서드나 join()메서드등을 사용했을 때
//		//					 interrupt()메서드를 호출하면 InterruptedException이 발생한다.
//		try {
//			while(true) {
//				System.out.println("쓰레드 처리중....");
//				Thread.sleep(1);	// interrupt가 걸릴수있다. 이건 join도 포함.
//			}
//		} catch (InterruptedException e) {
//		}
		
		
		
		// 방법2 => interrrupt()메서드가 호출되었는지를 검사하기
		while(true) {
			System.out.println("쓰레드 처리 중...");
			
			// 검사방법1 => 쓰레드의 인스턴스용 메서드를 이용하는 방법
			if(this.isInterrupted()) {//interrupt메서드가 호출되면 true
				System.out.println("인스턴스용 isInterrupted()");
				break;
			}
			
//			// 검사방법2 => 쓰레드의 정적 메서드를 이용하는 방법
//			if(Thread.interrupted()) {// interrupt()메서드 호출되면 true
//				System.out.println("정적 메서드 interrupted");
//				break;
//			}
		}
		System.out.println("자원 정리 중...");
		System.out.println("실행 종료");
	}
}