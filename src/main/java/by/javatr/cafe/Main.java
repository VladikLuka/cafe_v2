package by.javatr.cafe;

import by.javatr.cafe.dao.repository.AbstractRepository;
import by.javatr.cafe.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends AbstractRepository<User>{

    int calculate() {
        return Integer.MAX_VALUE - Integer.MIN_VALUE;
    }

    public static void mergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(a, l, r, mid, n - mid);
    }

    public static void merge(
            int[] a, int[] l, int[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

    Integer a = new Integer(2); Integer b = new Integer(2);

    public int shift(int value, int offset) {
        value += offset;
        return value;
    }
    
    public static void main(String[] args) throws SQLException, InterruptedException {

        final String[] uploads = "/upload/data/3".split("upload");

        System.out.println(Arrays.asList(uploads));

    }


    public static void merge(List<Integer> l1, List<Integer> l2) {
        for (int index1 = 0, index2 = 0; index2 < l2.size(); index1++) {
            if (index1 == l1.size() || l1.get(index1) > l2.get(index2)) {
                l1.add(index1, l2.get(index2++));
            }
        }
    }

    public void sort(ArrayList<Integer> a,ArrayList<Integer> b ){
        int k = 0;
        int j = 0;

        for (int i = 0; i < a.size() + b.size(); i++) {

            if(j == b.size()){
                return;
            }
            if(k == a.size()){
                for (int l = j; l < b.size(); l++) {
                    a.add(b.get(l));
                }
                return;
            }

            final int i1 = a.get(k).compareTo(b.get(j));

            if(i1 < 0){
                k++;
            }else {
                a.add(k, b.get(j));
                k++;
                j++;
            }
        }
    }

    public String coord(String str){


        str = str.replaceAll(" ", "\n");

        String res = "";
        int co = 0;
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == ','){
                res +="{ lng: " + str.substring(co+1, i) + ", ";
                co = i;
            }

            if(str.charAt(i) == '\n'){
                res += " lat: " + str.substring(co+1, i) + " }," + "\n";
                co = i;
            }

        }



        return res;
    }
}