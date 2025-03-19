import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class ElementFinder {

    public static int Kth_finder(String filename, int K, String operation) {
        // Comparator for min-heap (finding Kth largest)
        Comparator<Integer> minHeapComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };

        // Comparator for max-heap (finding Kth smallest)
        Comparator<Integer> maxHeapComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        };

        // Choose the right comparator based on the operation
        Comparator<Integer> comparator;
        if (operation.equals("largest")) {
            comparator = minHeapComparator;
        } else if (operation.equals("smallest")) {
            comparator = maxHeapComparator;
        } else {
            throw new IllegalArgumentException("Invalid operation: " + operation);
        }

        PriorityQueue<Integer> heap = new PriorityQueue<>(comparator);

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.split("\\s+");
                for (String number : numbers) {
                    int num = Integer.parseInt(number);
                    heap.add(num);
                    if (heap.size() > K) {
                        heap.poll();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        return heap.isEmpty() ? -1 : heap.peek();
    }

    // Main method for testing
    public static void main(String[] args) {
        String filename = "input.txt";
        int K = 4;
        String operation = "largest";
        int result = Kth_finder(filename, K, operation);
        System.out.println("The " + K + "th " + operation + " element is: " + result);
    }
}
