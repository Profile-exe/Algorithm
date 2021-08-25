package algorithm.theory.sort;

import java.util.ArrayList;

public class MergeSort {
    public ArrayList<Integer> mergeFunc(ArrayList<Integer> leftList, ArrayList<Integer> rightList) {
        ArrayList<Integer> mergedList = new ArrayList<>();
        int leftPoint = 0, rightPoint = 0;

        // Case1 : left/right 둘 다 있을 때
        while (leftList.size() > leftPoint && rightList.size() > rightPoint) {
            if (leftList.get(leftPoint) < rightList.get(rightPoint)) {
                mergedList.add(leftList.get(leftPoint++));
            } else {
                mergedList.add(rightList.get(rightPoint++));
            }
        }

        // Case2 : right 데이터가 없을 때
        while (leftList.size() > leftPoint) {
            mergedList.add(leftList.get(leftPoint++));
        }

        // Case3 : left 데이터가 없을 때
        while (rightList.size() > rightPoint) {
            mergedList.add(rightList.get(rightPoint++));
        }

        return mergedList;
    }

    public ArrayList<Integer> mergeSplitFunc(ArrayList<Integer> dataList) {
        if (dataList.size() <= 1) {
            return dataList;
        }
        int medium = dataList.size() / 2;

        ArrayList<Integer> leftArr = this.mergeSplitFunc(new ArrayList<>(dataList.subList(0, medium)));
        ArrayList<Integer> rightArr = this.mergeSplitFunc(new ArrayList<>(dataList.subList(medium, dataList.size())));

        return this.mergeFunc(leftArr, rightArr);
    }

    public static void main(String[] args) {
        ArrayList<Integer> testData = new ArrayList<>();

        for (int index = 0; index < 100; index++) {
            testData.add((int)(Math.random() * 100));
        }

        MergeSort mSort = new MergeSort();
        System.out.println(mSort.mergeSplitFunc(testData));
    }
}