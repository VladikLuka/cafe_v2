package by.javatr.cafe;

import by.javatr.cafe.aspectj.log.annotation.LogException;

public class Main {


    public static void main(String[] args) {

        Main main = new Main();
            main.devide(1,0);

    }

    @LogException
    public int devide(int a, int b) {
        return a/b;
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