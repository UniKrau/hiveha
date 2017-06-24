package concurrency.cookbook.chapter3;

/**
 * Created by lhao on 6/19/17.
 */
public class Main {

    public static void main(String[] args) {

        /*PrintQueue printQueue = new PrintQueue();
        Thread thread[]=new Thread[10];
        for (int i=0; i<10; i++){
            thread[i]=new Thread(new Job(printQueue),"Thread"+i);
        }
        for (int i=0; i<10; i++){
            thread[i].start();
        }*/

        Videoconference conference=new Videoconference(10);
        Thread threadConference=new Thread(conference);
        threadConference.start();

        for (int i=0; i<10; i++){
            Participant p=new Participant(conference, "Participant "+i);
            Thread t=new Thread(p);
            t.start();
        }

    }
}
