package concurrency.cookbook.chapter6;

/**
 * Created by lhao on 6/21/17.
 */
public class Contact {

    private String name;
    private String phone;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
