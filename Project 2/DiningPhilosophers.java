package p2;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiningPhilosophers extends Thread {

    static int num;
    static int[] chopsticks;
    static Thread[] philosophers;
    int count = 0;
    int sticks = 0;
    int s1 = 0, s2 = 0;  //left and right stick; even: 1-left,2-right;odd: 1-right,2-left
    int hungerLevel = 0;

    public static void main(String[] args) {
		// TODO Auto-generated method stub

        System.out.println("Enter the number of philosophers");
        Scanner s = new Scanner(System.in);

        num = s.nextInt();
        chopsticks = new int[num];
        philosophers = new Thread[num];

        for (int i = 0; i < num; i++) {
            chopsticks[i] = 1;
            philosophers[i] = new DiningPhilosophers();
        }

        for (int i = 0; i < num; i++) {
            philosophers[i].setName(i + "");
            philosophers[i].start();
        }
        s.close();
        
    }

    @SuppressWarnings("deprecation")
	@Override
    public void run() {
        int id = Integer.parseInt(this.getName());
        System.out.println("philosopher " + id + " sit down");
        System.out.flush();

        while (true) {
            if (this.hungerLevel >= 5) {
                System.out.println("Philosopher " + id + "starve to death");
                System.out.flush();
                this.stop();
            }

            try {
                Thread.sleep((long) Math.floor(Math.random() * 1000));
            } catch (InterruptedException ex) {
                Logger.getLogger(DiningPhilosophers.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("Philosopher " + id + " is thinking");
            System.out.flush();

            try {
                Thread.sleep((long) Math.floor(Math.random() * 1000));
            } catch (InterruptedException ex) {
                Logger.getLogger(DiningPhilosophers.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("Philosopher " + id + " is hungry");
            System.out.flush();

            this.count = 0;
            while (this.count < 5 && this.s1 == 0) {
                //trying to take the first chopstick
            	//left for even, right for odd
                if (id % 2 == 0) {
                    if (takechopstick1() == 1) {
                        this.s1 = 1;
                    }
                } else {
                    if (takechopstick2() == 1) {
                        this.s1 = 1;
                    }
                }
                this.count++;

            }
            if (this.count == 5 && this.sticks != 2) {
                //droping the stiks having after 5 failer atemps to get both sticks
                dropsticks();
                try {
                    Thread.sleep((long) Math.floor(Math.random() * 1000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }
            this.count = 0;
            while (this.count < 5 && this.s2 == 0) {
                //trying to take the second chopstick
            	//right for even, left for odd
                if (id % 2 == 0) {
                    if (takechopstick2() == 1) {
                        this.s2 = 1;
                    }
                } else {
                    if (takechopstick1() == 1) {
                        this.s2 = 1;
                    }
                }
                this.count++;
            }

            if (this.sticks == 2) {
                //eating
                System.out.println("Philosopher " + id + " is eating");
                System.out.flush();
                try {
                    Thread.sleep((long) Math.floor(Math.random() * 1000));
                } catch (InterruptedException ex) {
                    Logger.getLogger(DiningPhilosophers.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Philosopher " + id + " finish eating");
                System.out.flush();
                this.hungerLevel = 0;
                dropsticks();
                try {
                    Thread.sleep((long) Math.floor(Math.random() * 2000));
                } catch (InterruptedException ex) {
                    Logger.getLogger(DiningPhilosophers.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (this.count == 5 && this.sticks != 2) {
                //returning sticks cant eat
                System.out.println("Philosopher " + id + " is hungry but cant eat");
                System.out.flush();
                this.hungerLevel++;
                dropsticks();
                try {
                    Thread.sleep((long) Math.floor(Math.random() * 1000));
                } catch (InterruptedException ex) {
                    Logger.getLogger(DiningPhilosophers.class.getName()).log(Level.SEVERE, null, ex);
                }
                continue;
            }
        }

    }

    synchronized int takechopstick1() {
        int i = -1, id = Integer.parseInt(this.getName());
        if (chopsticks[id % num] == 1) {
            chopsticks[id % num]--;
            this.sticks++;
            i = 1;
        }
        return i;
    }

    synchronized int takechopstick2() {
        int i = -1, id = Integer.parseInt(this.getName());
        if (chopsticks[(id + 1) % num] == 1) {
            chopsticks[(id + 1) % num]--;
            this.sticks++;
            i = 1;
        }
        return i;
    }

    synchronized void dropsticks() {
        int id = Integer.parseInt(this.getName());
        if (id % 2 == 0 && this.sticks > 0) {
        	//even returning sticks
            if (chopsticks[(id + 1) % num] == 0 && this.s2 > 0) {
            	//even return right
                chopsticks[(id + 1) % num]++;
                this.sticks--;
                this.s2 = 0;
            }
            if (chopsticks[(id) % num] == 0 && this.s1 > 0) {
            	//even returning left
                chopsticks[(id) % num]++;
                this.sticks--;
                this.s1 = 0;
            }
        }
        if (id % 2 == 1 && this.sticks > 0) {
        	//odd returning sticks
            if (chopsticks[(id + 1) % num] == 0 && this.s1 > 0) {
            	//odd returning right
                chopsticks[(id + 1) % num]++;
                this.sticks--;
                this.s1 = 0;
            }
            if (chopsticks[(id) % num] == 0 && this.s2 > 0) {
            	//odd returning left
                chopsticks[(id) % num]++;
                this.sticks--;
                this.s2 = 0;
            }
        }
    }

}
