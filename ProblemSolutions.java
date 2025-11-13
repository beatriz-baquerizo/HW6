
/******************************************************************
 *
 *   BEATRIZ SANTOS / 002
 *
 *   This java file contains the problem solutions for the methods lastBoulder,
 *   showDuplicates, and pair methods. You should utilize the Java Collection
 *   Framework for these methods.
 *
 ********************************************************************/

import java.util.*;
import java.util.PriorityQueue;

public class ProblemSolutions {

    /**
     * Priority Queue (PQ) Game
     *
     * PQ1 Problem Statement:
     * -----------------------
     *
     * You are given an array of integers of boulders where boulders[i] is the
     * weight of the ith boulder.
     *
     * We are playing a game with the boulders. On each turn, we choose the heaviest
     * two boulders and smash them together. Suppose the heaviest two boulders have
     * weights x and y. The result of this smash is:
     *
     *    If x == y, both boulders are destroyed, and
     *    If x != y, the boulder of weight x is destroyed, and the boulder of
     *               weight y has new weight y - x.
     *
     * At the end of the game, there is at most one boulder left.
     *
     * Return the weight of the last remaining boulder. If there are no boulders
     * left, return 0.
     *
     *
     * Example 1:
     *
     * Input: boulders = [2,7,4,1,8,1]
     * Output: 1
     * Explanation:
     * We combine 7 and 8 to get 1 so the list converts to [2,4,1,1,1] then,
     * we combine 2 and 4 to get 2 so the list converts to [2,1,1,1] then,
     * we combine 2 and 1 to get 1 so the list converts to [1,1,1] then,
     * we combine 1 and 1 to get 0 so the list converts to [1] then that's the
     * value of the last stone.
     *
     * Example 2:
     *
     * Input: boulders = [1]
     * Output: 1
     *
     *
     *
     * RECOMMENDED APPROACH
     *
     * Initializing Priority Queue in reverse order, so that it gives
     * max element at the top. Taking top Elements and performing the
     * given operations in the question as long as 2 or more boulders;
     * returning the 0 if queue is empty else return pq.peek().
     *
     * * create a max heap with largest value at the top
     *          * while priority queue is not empty
     *          *  - x = root
     *          *  - y = max of left and right child
     *          *  if x == y, then return 0
     *          * if x > y,
     *          *      - remove y
     *          *      - x = x - y
     *          * recurse
     */

  public static int lastBoulder(int[] boulders) {

      // create priority queue in reverse order --> O(1)
      PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

      // populate pq --> O(n log n)
      for (int i = 0; i < boulders.length; i++){
          pq.add(boulders[i]);
      }

      // perform operations while we have two boulders --> O(n)
      while (pq.size() > 1){
          // get heaviest by removing top two elements --> O (log n) for each
          int x = pq.poll();
          int y = pq.poll();
          // if not equal, add difference of the two
          if (x != y){
              pq.add(x - y); // --> O (log n)
          }

      }
      // return last boulders weight --> O(1)
      if (pq.isEmpty()){
          return 0;
      } else {
          return pq.peek(); // total time complexity of O (n log n)
      }
  }


    /**
     * Method showDuplicates
     *
     * This method identifies duplicate strings in an array list. The list
     * is passed as an ArrayList<String> and the method returns an ArrayList<String>
     * containing only unique strings that appear more than once in the input list.
     * This returned array list is returned in sorted ascending order. Note that
     * this method should consider case (strings are case-sensitive).
     *
     * For example, if the input list was: "Lion", "Dog", "Cat", "Dog", "Horse", "Lion", "CAT"
     * the method would return an ArrayList<String> containing: "Dog", "Lion"
     *
     * @param  input an ArrayList<String>
     * @return       an ArrayList<String> containing only unique strings that appear
     *               more than once in the input list. They will be in ascending order.
     */

    public static ArrayList<String> showDuplicates(ArrayList<String> input) {
        // use a hashmap to keep count of repeated strings --> O(n)
        Map<String, Integer> counter = new HashMap<>();

        // count frequency of repeated strings --> O(n)
        for (int i = 0; i < input.size(); i++){
            String current = input.get(i);
            // if map contains current key, then add to count
            if (counter.containsKey(current)){
                counter.put(current, counter.get(current) + 1);
            } else {
                counter.put(current, 1);
            }
        }

        // create results arrayList --> O(n) time and space
        ArrayList<String> result = new ArrayList<>();
        // for a key in the key set, if the key is repeated more than one, add that frequency to result
       for (String key : counter.keySet()){
           if (counter.get(key) > 1){
               result.add(key);
           }
       }

       //ascending order
        Collections.sort(result);

       return result;
    }


    /**
     * Finds pairs in the input array that add up to k.
     *
     * @param input   Array of integers
     * @param k       The sum to find pairs for

     * @return an ArrayList<String> containing a list of strings. The ArrayList
     *        of strings needs to be ordered both within a pair, and
     *        between pairs in ascending order. E.g.,
     *
     *         - Ordering within a pair:
     *            A string is a pair in the format "(a, b)", where a and b are
     *            ordered lowest to highest, e.g., if a pair was the numbers
     *            6 and 3, then the string would be "(3, 6)", and NOT "(6, 3)".
     *         - Ordering between pairs:
     *            The ordering of strings of pairs should be sorted in lowest to
     *            highest pairs. E.g., if the following two string pairs within
     *            the returned ArraryList, "(3, 6)" and "(2, 7), they should be
     *            ordered in the ArrayList returned as "(2, 7)" and "(3, 6 )".
     *
     *         Example output:
     *         If the input array list was {2, 3, 3, 4, 5, 6, 7}, then the
     *         returned ArrayList<String> would be {"(2, 7)", "(3, 6)", "(4, 5)"}
     *
     *  HINT: Considering using any Java Collection Framework ADT that we have used
     *  to date, though HashSet. Consider using Java's "Collections.sort()" for final
     *  sort of ArrayList before returning so consistent answer. Utilize Oracle's
     *  Java Framework documentation in its use.
     *
     *  1. create hashset for quick lookups
     *  2. create arraylist to store result pairs
     *  3. for each number in input:
     *      - calculate complement
     *      - if complement exists in hashset and != the current num:
     *          create pair string with smaller num first
     *          add to result arraylist
     * 4. sort the results
     * 5. return result
     */

    public static ArrayList<String> pair(int[] input, int k) {
        // create hashset --> O(n)
        HashSet<Integer> hash = new HashSet<>();

        // create new array list
        ArrayList<String> result = new ArrayList<>();

        // another hashset to track pairs
        HashSet<String> seen = new HashSet<>();

        // populate hashset --> O(n)
        for (int i = 0; i < input.length; i++){
            hash.add(input[i]);
        }

        // find pairs
        for (Integer num : input){
            int complement = k - num;
            if (hash.contains(complement)){
                // order in pairs
                int small = Math.min(num, complement);
                int big = Math.max(num, complement);
                // create string for arraylist
                String pair = "(" + small + ", " + big + ")";

                // add if not seen before
                if (!seen.contains(pair)){
                    seen.add(pair);
                    result.add(pair);
                }
            }
        }
        Collections.sort(result);
        return result;  // Make sure returned lists is sorted as indicated above
    }
}