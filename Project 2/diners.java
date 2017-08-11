/**
 * @author  Stephen Taylor
 * @description  implement the dining philosophers in Java
 */

import java.util.Random;
import java.lang.Runnable;

public class diners extends Thread {
    final static Random rgen = new Random();
    final static int COUNT = 3;

    static int state[] = new int[COUNT]; // track progress of each philosopher
    static int [] chopsticks = new int[COUNT]; // used int array to notice bad synchronization

    public static void main(String [] joke){
	for (int i=0; i<chopsticks.length; i++){
	    chopsticks[i] = 1;
	}

        Thread [] philosophers = new Thread[COUNT];

	for (int i=0; i<philosophers.length; i++){
	    philosophers[i] = new diners(i);
	    philosophers[i].start();
	}
    }

/**
 * track progress and report
 */
    public static void progress(String display){
	String t = "";
	for (int j = 0; j<COUNT; j++){
	    t += state[j];
	}
	String u = " ";
	for (int j = 0; j<COUNT; j++){
	    u += chopsticks[j] + " ";
	}
	System.out.println(t + u + display);
    }


    int id;

    public diners(int i){
        id = i;
    }

    /**
     * @description code for individual philosopher
     */
    @Override
    public void run(){
	progress("Philosopher "+id+" sits down");
	possiblyYield();
        while (true){
            // think 
            progress("Philosopher "+id+" is thinking");

            progress("Philosopher "+id+" gets hungry");
            // take chopsticks
            takeChopsticks(id);
	    possiblyYield();

            // eat
            progress("Philosopher "+id+" is eating");
	    possiblyYield();
            // drop chopsticks
            dropChopsticks(id);
            progress("Philosopher "+id+" finishes eating");
	    possiblyYield();
	    // error check
	    for (int i=0; i<COUNT; i++){
		assert (chopsticks[i] == 0 || chopsticks[i] == 0);
	    }
        }
    }

    /**
     * @parameter p the philosopher index
     * @result      the chopstick index
     */
    int left(int p){ return p; }
    int right(int p){ return (p+1) % COUNT; }

    void takeChopstick(int i){
        while (chopsticks[i] <= 0){ // this is the busy-waiting part
            progress("Philosopher "+id+" is hungry, but can't eat");
	    possiblyYield();
	}
	possiblyYield();
        // okay, here's the problem part:
	int tmp = chopsticks[i];
	possiblyYield();
        chopsticks[i] = tmp -1;
	state[id] ++; // 0: no stick, 1: one stick 2: two sticks 3: 1 stick
        return;
    }
    void dropChopstick(int i){
	int tmp = chopsticks[i];
	possiblyYield();
        chopsticks[i] = tmp + 1;  // actually this is also a problem
	state[id] = (state[id]+1) % 4;
    }

    void takeChopsticks(int p){
        takeChopstick(left(p)); // and here is another!
	possiblyYield();
        takeChopstick(right(p));
    }

    void dropChopsticks(int p){
        dropChopstick(left(p));
	possiblyYield();
        dropChopstick(right(p));
    }

    void possiblyYield(){
        if (rgen.nextInt(3) > 1) { yield(); }
    }

}
