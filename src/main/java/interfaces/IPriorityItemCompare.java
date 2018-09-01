package interfaces;

public interface IPriorityItemCompare<T extends IPriorityItem> {
    int compare(T first, T second);
}
