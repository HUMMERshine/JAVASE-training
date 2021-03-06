package priv.lst.thinkinjava.interr;

class SleepJob implements Runnable {
	@Override
	public void run() {//run 方法不可以抛出异常，因为父类或者接口Runnable的run方法没有抛出异常。
		try {
			System.out.println("Started sleeping.");
			Thread.sleep(1000);
			System.out.println("Finished sleeping.");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // Re-set the interrupted flag.
			//由于不可以抛出异常因此需要重新set中断标志位。
		}
	}
}

public class MultiSleep {

	public static void main(String[] args) throws InterruptedException {
		Thread sleeperThread = new Thread() {
			public void run() {
				Runnable job1 = new SleepJob();
				Runnable job2 = new SleepJob();
				Runnable job3 = new SleepJob();

				job1.run();
				if (Thread.interrupted()) {
					System.out.println("Interrupted in job1. Stop.");
					return;
				}
				job2.run();
				if (Thread.interrupted()) {
					System.out.println("Interrupted in job2. Stop.");
					//return; 如果把改return注释掉，那么程序会继续运行，thread的中断状态不变
					return;
				}
				job3.run();
				if (Thread.interrupted()) {
					System.out.println("Interrupted in job3. Stop.");
					return;
				}
			}
		};

		sleeperThread.start();
		Thread.sleep(1500);
		sleeperThread.interrupt();
		sleeperThread.join();
	}

}
