package main.java;

import java.util.ArrayList;

public class Helper {

    /**
     * Use binarySearch to find close elements to received input
     * @param aElements
     * @param low
     * @param high
     * @param x
     * @return
     */
    public static int findCrossOver(ArrayList<Element> aElements, int low, int high, long x) {
        if (aElements.get(high).getWordSum() <= x) // x is greater than all
            return high;
        if (aElements.get(low).getWordSum() > x)  // x is smaller than all
            return low;

        // Find the middle point
        int mid = (low + high) / 2;  /* low + (high - low)/2 */

        /* If x is same as middle element, then return mid */
        if (aElements.get(mid).getWordSum() <= x && aElements.get(mid + 1).getWordSum() > x)
            return mid;

        /* If x is greater than arr[mid], then either arr[mid + 1]
          is ceiling of x or ceiling lies in arr[mid+1...high] */
        if (aElements.get(mid).getWordSum() < x)
            return findCrossOver(aElements, mid + 1, high, x);

        return findCrossOver(aElements, low, mid - 1, x);
    }

    // This function prints k closest elements to x in arr.
    public static ArrayList<Element> getKclosest(ArrayList<Element> aElements, long x, int k) {
        // Find the crossover point
        ArrayList<Element> closetElements = new ArrayList<>();
        int l = findCrossOver(aElements, 0, aElements.size() - 1, x);
        int r = l + 1;   // Right index to search
        int count = 0; // To keep track of count of elements

        if (aElements.get(l).getWordSum() == x) l--;

        // Compare elements on left and right of crossover
        // point to find the k closest elements
        while (l >= 0 && r < aElements.size() && count < k) {
            if (x - aElements.get(l).getWordSum() < aElements.get(r).getWordSum() - x) {
                closetElements.add(aElements.get(l--));
            } else {
                closetElements.add(aElements.get(r++));
            }
            count++;
        }

        // If there are no more elements on right side, then
        // get left elements
        while (count < k && l >= 0) {
            closetElements.add(aElements.get(l--));
            count++;
        }


        // If there are no more elements on left side, then
        // get right elements
        while (count < k && r < aElements.size()) {
            closetElements.add(aElements.get(r++));
            count++;
        }
        return closetElements;
    }

    public static long ASCIIWordSum(String str) {
        long sum = 0;
        for (int i = 0; i < str.length(); i++) {
            sum += str.charAt(i);
        }
        return sum;
    }
}
