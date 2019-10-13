import java.util.*;

/**
 * HackerRank Test Question 'Can You Sort?'
 * Sort in ascending order, first by frequency then by value.
 *
 * @author Trisha Funtanilla
 */
public class CanYouSort {

    public static void main(String[] args) {
        List<Integer> input = new ArrayList<>();
        input.add(3);
        input.add(2);
        input.add(1);
        input.add(4);
        input.add(4);
        input.add(5);
        customSort(input);
    }

    private static void customSort(List<Integer> arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        Comparator<Map.Entry<Integer, Integer>> customComparator = (e1, e2) -> {
            int compareByFrequency = e1.getValue().compareTo(e2.getValue());
            if (compareByFrequency != 0) {
                return compareByFrequency;
            } else {
                return e1.getKey().compareTo(e2.getKey());
            }
        };

        List<Map.Entry<Integer, Integer>> output = new ArrayList<>(map.entrySet());
        output.sort(customComparator);

        for (Map.Entry<Integer, Integer> entry : output) {
            for (int i = entry.getValue(); i > 0; i--) {
                System.out.println(entry.getKey());
            }
        }
    }
}
