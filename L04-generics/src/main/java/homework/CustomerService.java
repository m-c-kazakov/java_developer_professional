package homework;


import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final TreeMap<Customer, String> map = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Map.Entry<Customer, String> customerStringEntry = map.firstEntry();
//        return customerStringEntry;
        return customerStringEntry != null ? Map.entry(customerStringEntry.getKey().clone(), customerStringEntry.getValue()) : null; // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> customerStringEntry = map.higherEntry(customer);
        return customerStringEntry != null ? Map.entry(customerStringEntry.getKey().clone(), customerStringEntry.getValue()) : null; // это "заглушка, чтобы скомилировать"
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
