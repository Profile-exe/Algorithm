package algorithm.theory.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class BinarySearch {
    public boolean searchFunc(ArrayList<Integer> dataList, Integer searchItem) {
        if (dataList.isEmpty())
            return false;
        if (dataList.size() == 1) {
            return Objects.equals(searchItem, dataList.get(0));
        }

        int medium = dataList.size() / 2;
        if (Objects.equals(searchItem, dataList.get(medium))) {
            return true;
        } else {
            if (searchItem < dataList.get(medium)) {
                return searchFunc(new ArrayList<>(dataList.subList(0, medium)), searchItem);
            } else {
                return searchFunc(new ArrayList<>(dataList.subList(medium, dataList.size())), searchItem);
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> testData = new ArrayList<>();

        for (int index = 0; index < 100; index++) {
            testData.add((int)(Math.random() * 100));
        }

        Collections.sort(testData);
        BinarySearch b = new BinarySearch();
        System.out.println(testData);
        System.out.println(b.searchFunc(testData, 4));
    }
}
