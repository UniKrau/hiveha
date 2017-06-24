package concurrency.cookbook.chapter1;

/**
 * Created by lhao on 6/19/17.
 */
public class Writer implements Runnable {

    private PricesInfo pricesInfo ;
    public Writer(PricesInfo pricesInfo){

        this.pricesInfo = pricesInfo;
    }
    public void run() {
        for (int i=0; i<3; i++) {
            System.out.printf("Writer: Attempt to modify the prices.\n");

            pricesInfo.setPrices(Math.random()*10, Math.random()*8);
            System.out.printf("Writer: Prices have been modified.\n");
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
