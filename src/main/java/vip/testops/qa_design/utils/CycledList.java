package vip.testops.qa_design.utils;

import java.util.ArrayList;
import java.util.List;

public class CycledList<T> {
    private List<T> list;
    private final int startCycleIndex;

    private final int capacity;

    public CycledList(int capacity, int startCycleIndex) {
        this.list = new ArrayList<>();
        this.startCycleIndex = Math.min(startCycleIndex, capacity-1);
        this.capacity = capacity;
    }

    public CycledList<T> add(T element) {
        if (list.size() < capacity) {
            list.add(element);
        } else {
            for(int i = startCycleIndex; i < capacity - 1; i++) {
                list.set(i, list.get(i + 1));
            }
            list.set(capacity - 1, element);
        }
        return this;
    }

    public CycledList<T> addAll(List<T> elements) {
        if(list.size() + elements.size() <= capacity) {
            list.addAll(elements);
        } else {
            for(T element : elements) {
                add(element);
            }
        }
        return this;
    }

    public List<T> get() {
        return list;
    }

    public int getStartCycleIndex() {
        return this.startCycleIndex;
    }

    public int size() {
        return list.size();
    }
}
