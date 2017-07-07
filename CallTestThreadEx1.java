package tw.alan.myproject.opp.thread;
//07/07 PM
//執行緒多工
class MySimpleTask implements Runnable{

	@Override
	public void run() {
		for(int i = 1; i <= 10; i++){
//			這樣執行緒才不會插隊
			System.out.println(Thread.currentThread().getName() + " i=" + i);
//			System.out.println(Thread.currentThread().getName());
//			System.out.println("i=" + i);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
					
				e.printStackTrace();
			}
			
		}
		
	}
	
}

public class CallTestThreadEx1 {

	public static void main(String[] args) {
		MySimpleTask task = new MySimpleTask();
		
		Thread thread1 = new Thread(task);  //task必須放進來
		Thread thread2 = new Thread(task);
//	    start執行緒的啟動
		thread1.start();           //執行完就死掉了,若要執行必須再new一個
		thread2.start();           //不能用成run,這樣變成呼叫方法,要用.start才是使用Runnable的功能
		
		try {
			thread1.join();                  //加入join,讓Finished等前面執行完才處理
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Finished.");   //因為分配多工處理,所以Finished不一定會印在結尾,且每次順序不一定相同

	}

}
