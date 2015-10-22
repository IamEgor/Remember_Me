package yegor_gruk.example.com.rememberme.WaiterPackage;

import java.util.ArrayList;
import java.util.List;

public class LikeStack<T> {

    private int counter;

    private List<T> list = new ArrayList<T>();

    public void push(T element) {
        list.add(element);
        counter++;
    }

    public void pop(T element) {
        list.remove(element);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean isSymmetric() {
        return counter % 2 == 0;
    }

}
