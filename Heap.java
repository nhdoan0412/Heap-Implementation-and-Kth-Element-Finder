import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<K, V> implements PriorityQueue<K, V> {
    public List<Entry<K, V>> entries;
    public Comparator<K> comparator;

    public Heap(Comparator<K> comparator) {
        this.entries = new ArrayList<>();
        this.comparator = comparator;
    }

    @Override
    public void add(K k, V v) {
        entries.add(new Entry<>(k, v));
        bubbleUp(entries.size() - 1);
    }

    @Override
    public Entry<K, V> poll() {
        if (entries.isEmpty()) {
            throw new NoSuchElementException();
        }
        Entry<K, V> root = entries.get(0);
        if (entries.size() == 1) {
            entries.remove(0);
        } else {
            entries.set(0, entries.remove(entries.size() - 1));
            bubbleDown(0);
        }
        return root;
    }

    @Override
    public Entry<K, V> peek() {
        if (entries.isEmpty()) {
            throw new NoSuchElementException();
        }
        return entries.get(0);
    }

    @Override
    public List<Entry<K, V>> toArray() {
        return new ArrayList<>(entries);
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public int parent(int index) {
        return (index - 1) / 2;
    }

    public int left(int index) {
        return 2 * index + 1;
    }

    public int right(int index) {
        return 2 * index + 2;
    }

    public void swap(int i1, int i2) {
        Entry<K, V> temp = entries.get(i1);
        entries.set(i1, entries.get(i2));
        entries.set(i2, temp);
    }

    public void bubbleUp(int index) {
        int parentIndex = parent(index);
        if (index > 0 && comparator.compare(entries.get(index).getKey(), entries.get(parentIndex).getKey()) > 0) {
            swap(index, parentIndex);
            bubbleUp(parentIndex);
        }
    }

    public void bubbleDown(int index) {
        int leftIndex = left(index);
        int rightIndex = right(index);
        int largestIndex = index;

        if (leftIndex < entries.size() && comparator.compare(entries.get(leftIndex).getKey(), entries.get(largestIndex).getKey()) > 0) {
            largestIndex = leftIndex;
        }

        if (rightIndex < entries.size() && comparator.compare(entries.get(rightIndex).getKey(), entries.get(largestIndex).getKey()) > 0) {
            largestIndex = rightIndex;
        }

        if (largestIndex != index) {
            swap(index, largestIndex);
            bubbleDown(largestIndex);
        }
    }

    public boolean existsAndGreater(int index1, int index2) {
        if (index1 < entries.size() && index2 < entries.size()) {
            return comparator.compare(entries.get(index1).getKey(), entries.get(index2).getKey()) > 0;
        }
        return false;
    }

    public int size() {
        return entries.size();
    }

    @Override
    public String toString() {
        return entries.toString();
    }
}
