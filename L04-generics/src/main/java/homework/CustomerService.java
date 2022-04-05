package homework;


import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> map = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> customerStringEntry = map.firstEntry();
        return customerStringEntry != null ? Map.entry(customerStringEntry.getKey().clone(), customerStringEntry.getValue()) : null;
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> customerStringEntry = map.higherEntry(customer);
        return customerStringEntry != null ? Map.entry(customerStringEntry.getKey().clone(), customerStringEntry.getValue()) : null;
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
