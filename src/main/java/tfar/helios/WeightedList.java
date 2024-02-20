package tfar.helios;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class WeightedList<E> {

    private final ArrayList<Entry> list = new ArrayList<>();
    private int totalWeight;
    public void addEntry(E item,int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative!");
        } else {
            list.add(new Entry(item,weight));
            totalWeight += weight;
        }
    }

    public void clear() {
        list.clear();
        totalWeight = 0;
    }

    public E get(int index) {
        return list.get(index).item;
    }

    public int indexOf(E item) {
        for (int i = 0; i < list.size();i++) {
            if (Objects.equals(list.get(i),item)) {
                return i;
            }
        }
        return -1;
    }

    public E getWeightedRandomItem(Random random) {
        if (totalWeight > 0 && !list.isEmpty()) {
            int i = random.nextInt(totalWeight);
            int index = 0;
            Entry entry = list.get(index);
            while (i > 0) {
                index++;
                entry = list.get(index);
                i -= entry.weight;
            }
            return entry.item;
        } else {
            return null;
        }
    }

    public class Entry {
        private final E item;
        private final int weight;
        private Entry(E item, int weight) {
            this.item = item;
            this.weight = weight;
        }
    }
}
