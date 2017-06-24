package concurrency.cookbook.chapter1;

/**
 * Created by lhao on 6/17/17.
 */
public class Calculator implements Runnable{
    private int number;

    public Calculator(int number){
        this.number = number;
    }

    public void run() {

        for (int i = 0; i < 10; i++) {

        }

    }

    public static void main(String[] args) {
        Thread threads[] = new Thread[10];
        Thread.State [] states = new Thread.State[10];

        for (int i=1; i<=10; i++){

            threads[i] = new Thread(new Calculator(i));

            if ((i%2)==0){
                threads[i].setPriority(Thread.MAX_PRIORITY);
            } else {
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }
            threads[i].setName("Thread "+i);
        }

    }
}
