package tw.alan.myproject.opp.thread;
//07/07 PM
//wait-notify機制
class MazeGameCore{
	private int x = 0, y = 0;
	
	public void move(int x, int y) {
		synchronized (this){         //鎖定this同步
		this.x = x;
		this.y = y;
		System.out.println("x:" + x + "y:" + y);
		this.notify();
		}
	}
	
	public void checkExit() {
		synchronized (this){			
			try {
				this.wait();
				
				if(x == 0 && y == 0){             //放入try和catch裡
					System.out.println("Game Exit. Bye Bye.");
					System.exit(-1);
				}
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
		}
	}
}
class MazeGameCheck implements Runnable{
	private MazeGameCore core;
	
	public MazeGameCheck(MazeGameCore core) {
		this.core = core;
	}

	@Override
	public void run() {
		while(true){
			core.checkExit();
			
			try {
				Thread.sleep((int)(Math.random()*5));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
			
	}
	
}
class Hero implements Runnable {
	private MazeGameCore core;
	
	public Hero(MazeGameCore core){
		this.core = core;
	}

	@Override
	public void run() {
			
		while(true){
			
			int x = (int)(Math.random()*5);
			int y = (int)(Math.random()*5);
			core.move(x, y);
			
			try {
				Thread.sleep((int)(Math.random()*300));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	} 
}

public class MazeGmae {

	public static void main(String[] args) {
		MazeGameCore core = new MazeGameCore();
		
		Hero thor = new Hero(core);
		Thread thread1 = new Thread(thor);
		thread1.start();
		
		MazeGameCheck check = new MazeGameCheck(core);
		Thread thread2 = new Thread(check);
		thread2.start();

	}

}
