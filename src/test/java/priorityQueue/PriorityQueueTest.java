package priorityQueue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import domain.TestDummy;

import java.util.List;

public class PriorityQueueTest {
    @Test
    public void test_queue_empty() {
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        assert (queue.getItems().size() == 0);
    }

    @Test
    public void test_AddItem() {
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        queue.add(new TestDummy(1, 1));
        assert (queue.getItems().size() == 1);
    }

    @Test
    void test_AddCorrectItem() {
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        queue.add(new TestDummy(1, 1));
        TestDummy td = queue.getItems().get(0);
        assert (td.getId() == 1);
        assert (td.getPriority() == 1);
    }

    @Test
    void test_AddMultipleItems() {
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        queue.add(new TestDummy(1, 1));
        queue.add(new TestDummy(3, 2));
        assert (queue.getItems().get(0).getId() == 2);
        assert (queue.getItems().get(1).getId() == 1);
    }

    @Test
    void test_getQueueItems() {
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        assert (queue.getItems().size() == 0);
        queue.add(new TestDummy(1, 1));
        assert (queue.getItems().size() == 1);
        queue.add(new TestDummy(2, 2));
        assert (queue.getItems().size() == 2);
    }

    @Test
    void test_popItem() {
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        queue.add(new TestDummy(1, 1));
        queue.add(new TestDummy(2, 2));
        queue.add(new TestDummy(5, 3));

        TestDummy td = queue.pop();
        List<TestDummy> list = queue.getItems();
        assert (list.get(0).getId() == 2);
        assert (list.get(1).getId() == 1);
        Assertions.assertEquals(3, td.getId(), "Expected different id");
    }

    @Test
    void test_popMultipleItems() {
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        queue.add(new TestDummy(3, 1));
        queue.add(new TestDummy(2, 2));
        queue.add(new TestDummy(1, 3));

        List<TestDummy> list = queue.getItems();

        TestDummy td = queue.pop();
        assert (list.size() == 2);
        Assertions.assertEquals(1, td.getId(), "id not expected");

        td = queue.pop();
        assert (list.size() == 1);
        Assertions.assertEquals(2, td.getId(), "id not expected");
    }

    @Test
    void test_popEmptyQueue() {
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        TestDummy td = queue.pop();

        Assertions.assertEquals(null, td, "Expected null");
    }

    @Test
    void test_updateIncresePriority() {
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        queue.add(new TestDummy(3, 1));
        queue.add(new TestDummy(2, 2));
        queue.add(new TestDummy(1, 3));

        List<TestDummy> list = queue.getItems();
        TestDummy td = list.get(2);
        Assertions.assertEquals(3, td.getId(), "Exepected least priority id");
        td.setPriority(5);
        queue.update(td);
        td = queue.pop();
        Assertions.assertEquals(3, td.getId(), "Expected most priority id");
        Assertions.assertEquals(5, td.getPriority(), "Expected Priority");
    }

    @Test
    void test_updateDecreasePriority() {
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        queue.add(new TestDummy(3, 1));
        queue.add(new TestDummy(2, 2));
        queue.add(new TestDummy(1, 3));

        List<TestDummy> list = queue.getItems();
        TestDummy td = list.get(0);
        Assertions.assertEquals(1, td.getId(), "Exepected most priority id");
        td.setPriority(1);
        queue.update(td);
        td = queue.pop();
        Assertions.assertEquals(2, td.getId(), "Expected most priority id");
        Assertions.assertEquals(2, td.getPriority(), "Expected Priority");
    }

    @Test
    void test_updateInexistent() {
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        queue.add(new TestDummy(3, 1));
        queue.add(new TestDummy(2, 2));
        queue.add(new TestDummy(1, 3));

        TestDummy td = new TestDummy(6, 6);
        assert (queue.getItems().size() == 3);
        for (TestDummy testDummy : queue.getItems()) {
            assert (testDummy.getPriority() != 6 && testDummy.getId() != 6);
        }
    }

    @Test
    void test_AddOnThreads() {
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        Thread t1 = new Thread() {
            PriorityQueue<TestDummy> q = queue;

            public void run() {
                queue.add(new TestDummy(1, 1));
                queue.add(new TestDummy(3, 5));
                queue.add(new TestDummy(2, 6));
            }
        };
        Thread t2 = new Thread() {
            PriorityQueue<TestDummy> q = queue;

            public void run() {
                queue.add(new TestDummy(3, 3));
                queue.add(new TestDummy(2, 4));
                queue.add(new TestDummy(4, 8));
            }
        };
        Thread t3 = new Thread() {
            PriorityQueue<TestDummy> q = queue;

            public void run() {
                queue.add(new TestDummy(4, 9));
                queue.add(new TestDummy(1, 2));
                queue.add(new TestDummy(3, 7));
            }
        };

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            Assertions.assertEquals(9, queue.getItems().size(), "Expected queue size");
            Assertions.assertEquals(4, queue.pop().getPriority(), "Expected item with most priority");
        } catch (InterruptedException e) {
            assert (false);
        }
    }

    @Test
    void test_updateOnThreads() {
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        Thread t1 = new Thread() {
            PriorityQueue<TestDummy> q = queue;

            public void run() {
                queue.add(new TestDummy(1, 1));
                queue.add(new TestDummy(3, 5));
                TestDummy td = queue.getItems().get(1);
                td.setPriority(7);
                queue.update(td);
                queue.add(new TestDummy(4, 6));
                queue.add(new TestDummy(3, 2));
                queue.add(new TestDummy(1, 7));
            }
        };
        Thread t2 = new Thread() {
            PriorityQueue<TestDummy> q = queue;

            public void run() {
                queue.add(new TestDummy(3, 3));
                queue.add(new TestDummy(2, 4));
                queue.add(new TestDummy(4, 8));
                queue.add(new TestDummy(3, 9));
                queue.add(new TestDummy(1, 10));
            }
        };

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            Assertions.assertEquals(10, queue.getItems().size(), "Expected queue size");
            Assertions.assertEquals(7, queue.pop().getPriority(), "Expected item with most priority");
        } catch (InterruptedException e) {
            assert (false);
        }
    }

    @Test
    void test_PopOnThreads() {
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        Thread t1 = new Thread() {
            PriorityQueue<TestDummy> q = queue;

            public void run() {
                queue.add(new TestDummy(1, 1));
                queue.add(new TestDummy(3, 5));
                queue.add(new TestDummy(7, 6));
                queue.pop();
                queue.add(new TestDummy(6, 2));
                queue.add(new TestDummy(1, 7));
            }
        };
        Thread t2 = new Thread() {
            PriorityQueue<TestDummy> q = queue;

            public void run() {
                queue.add(new TestDummy(3, 3));
                queue.add(new TestDummy(5, 4));
                queue.add(new TestDummy(4, 8));
                queue.add(new TestDummy(3, 9));
                queue.add(new TestDummy(6, 10));
            }
        };

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            Assertions.assertEquals(9, queue.getItems().size(), "Expected queue size");
            Assertions.assertEquals(6, queue.pop().getPriority(), "Expected item with most priority");
        } catch (InterruptedException e) {
            assert (false);
        }
    }
}
