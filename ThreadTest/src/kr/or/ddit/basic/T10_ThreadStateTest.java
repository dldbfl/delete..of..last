package kr.or.ddit.basic;
/*
  <쓰레드 상태>
  NEW : 쓰레드가 생성되고 아직 start()가 호출되지 않은 상태
  RUNNABLE : 실행 중 또는 실행 가능한 상태
  BLOCKED : 동기화 블럭에 의해 일시정지된 상태 (LOCK이 풀릴때 까지 기다리는 상태)
  WAITING, TIMED_WAITING : 쓰레드의 작업이 종료되지는 않았지만 실행가능 하지않은(UNRUNNABLE) 일시정지 상태
  TERMINATED : 쓰레드의 작업이 종료된 상태
 */
public class T10_ThreadStateTest {
	public static void main(String[] args) {
		StatePrintThread spt = new StatePrintThread(new TargetThread());
		
		spt.start();
		
	}

}
/**
 * 쓰레드의 상태를 출력하는 예제
 * @author PC-13
 *
 */
class StatePrintThread extends Thread{
	private Thread targetThread;  //상태를 출력할 쓰레드가 저장될 변수
	
	public StatePrintThread(Thread targetThread) {
		this.targetThread = targetThread;
	}
	@Override
	public void run() {
		while(true) {
			//Thread상태 구하기(getState()메서드 이용)
			Thread.State state = targetThread.getState();
			System.out.println("target thread 상태 값: " + state);
			
			//NEW상태인지 검사
			if(state == Thread.State.NEW) {
				targetThread.start();   //쓰레드 시작
			}
			if(state == Thread.State.TERMINATED) {
				break;
			}
			try {
				Thread.sleep(500);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
/*
 * target용 쓰레드
 */
class TargetThread extends Thread{
	@Override
	public void run() {
		for(long i=1; i<=100000000L; i++) {}  //시간 지연용
		try {
			Thread.sleep(1500);
		}catch (InterruptedException e) {
			e.printStackTrace();
			
		}
		for(long i = 1; i<=1000000000L; i++) {}  //시간지연용
	}
}