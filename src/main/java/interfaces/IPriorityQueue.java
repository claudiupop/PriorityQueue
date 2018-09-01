package interfaces;

import java.util.List;

public interface IPriorityQueue<T extends IPriorityItem>{
    List<T> getItems();
    void add(T item);
    T pop();
    void update(T item);
}
