package src;

public class TestDummy implements Comparable<TestDummy> {
    private int priority;
    private int id;

    public TestDummy() {
        priority = 1;
        id = -1;
    }

    public TestDummy(int priority, int id) {
        this.priority = priority;
        this.id = id;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {

        return priority;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(TestDummy o) {
        if(id == o.getId()){
            return 0;
        }
        else if(priority > o.getPriority()){
            return -1;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "TestDummy{" +
                "priority=" + priority +
                ", id=" + id +
                '}';
    }
}
