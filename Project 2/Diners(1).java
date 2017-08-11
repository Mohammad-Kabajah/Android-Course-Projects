/**
 * @author  Stephen Taylor
 * @description  implement the dining philosophers in Java
 */

import java.util.Random;
import java.lang.Runnable;

public class Diners extends Thread {
	final static Random rgen = new Random();
	final static int COUNT = 3;

	static int state[] = new int[COUNT]; // track progress of each philosopher
	static int[] chopsticks = new int[COUNT]; // used int array to notice bad
												// synchronization

	public static void main(String[] args) {
		for (int i = 0; i < chopsticks.length; i++) {
			chopsticks[i] = 1;
		}
		// creating and initializing the threads
		Thread[] philosophers = new Thread[COUNT];

		for (int i = 0; i < philosophers.length; i++) {
			philosophers[i] = new Diners(i);
			philosophers[i].start();
		}
	}

	/**
	 * track progress and report
	 */
	public static void progress(String display) {
		String t = "";
		for (int j = 0; j < COUNT; j++) {
			t += state[j];
		}
		String u = " ";
		for (int j = 0; j < COUNT; j++) {
			u += chopsticks[j] + " ";
		}
		System.out.println(t + u + display);
	}

	int id;

	public Diners(int i) {
		id = i;
	}

	/**
	 * @description code for individual philosopher
	 */
	@Override
	public void run() {
		progress("Philosopher " + id + " sits down");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {
			// think
			progress("Philosopher " + id + " is thinking");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			progress("Philosopher " + id + " gets hungry");
			// take chopsticks
			if (takeChopsticks(id) == 1) {
				// eat
				progress("Philosopher " + id + " is eating");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// drop chopsticks
				dropChopsticks(id);

				progress("Philosopher " + id + " finishes eating");
			}
			possiblyYield();
			// error check
			for (int i = 0; i < COUNT; i++) {
				assert (chopsticks[i] == 0 || chopsticks[i] == 0);
			}
		}
	}

	/**
	 * @parameter p the philosopher index
	 * @result the chopstick index
	 */
	int left(int p) {
		return p;
	}

	int right(int p) {
		return (p + 1) % COUNT;
	}

	void takeChopstick(int i) {
		int tmp = chopsticks[i];
		chopsticks[i] = tmp - 1;

		return;
	}

	void dropChopstick(int i) {
		int tmp = chopsticks[i];
		chopsticks[i] = tmp + 1; // actually this is also a problem

	}

	synchronized int takeChopsticks(int p) {
		if (chopsticks[left(p)] == 1 && chopsticks[right(p)] == 1) {
			takeChopstick(left(p)); // and here is another!
			takeChopstick(right(p));
			state[id]++; // 0: no stick, 1: two sticks
			state[id] %= 2;
			return 1;
		}
		return 0;
	}

	synchronized void dropChopsticks(int p) {
		dropChopstick(left(p));
		dropChopstick(right(p));
		state[id] = (state[id] + 1) % 2; // changing the state
	}

	void possiblyYield() {
		if (rgen.nextInt(3) > 1) {
			yield();
		}
	}

}