package concurrency.cookbook.chapter6;

import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by lhao on 6/21/17.
 */
public class ContactTask implements Runnable {

    private ConcurrentSkipListMap<String, Contact> map;

    private String id;

    public ContactTask(ConcurrentSkipListMap<String, Contact> map, String id) {
        this.map = map;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i=0; i<1000; i++) {
            Contact contact=new Contact(id, String.valueOf(i+1000));
            map.put(id+contact.getPhone(), contact);
        }

    }


    public static void main(String[] args) {

        ConcurrentSkipListMap<String, Contact> map;
        map=new ConcurrentSkipListMap<>();
        Thread threads[]=new Thread[25];
        int counter=0;

        for (char i = 'A' ;i < 'Z'; i++){

            ContactTask contactTask = new ContactTask(map, String.valueOf(i));
            threads[counter]=new Thread(contactTask);
            threads[counter].start();
            counter++;
        }

        for (int i=0; i<25; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Main: Size of the map: %d\n",map.size());
        Map.Entry<String, Contact> element;
        Contact contact;
        element=map.firstEntry();
        contact=element.getValue();
        System.out.printf("Main: First Entry: %s: %s\n",contact.
                getName(),contact.getPhone());

        element=map.lastEntry();
        contact=element.getValue();
        System.out.printf("Main: Last Entry: %s: %s\n",contact.
                getName(),contact.getPhone());

        System.out.printf("Main: Submap from A1996 to B1002: \n");
        ConcurrentNavigableMap<String, Contact> submap=map.
                subMap("A1996", "B1002");
        do {
            element=submap.pollFirstEntry();
            if (element!=null) {
                contact=element.getValue();
                System.out.printf("%s: %s\n",contact.getName(),contact.
                        getPhone());
            }
        } while (element!=null);

    }
}
