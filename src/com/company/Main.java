package com.company;


import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //out.print("asdasd\n");

        //beolvasas

        //taska meretenek beolvasasa
        String s = br.readLine();
        int magassag = Integer.parseInt(s.split("\t")[0]);
        int szelesseg = Integer.parseInt(s.split("\t")[1]);

        //darabszam beolvasasa
        s = br.readLine();
        int darabszam = Integer.parseInt(s);

        //targyak beolvasasa
        ArrayList<targy> targyak = new ArrayList<targy>();
        for (int i = 0; i < darabszam; i++) {
            s = br.readLine();
            targy temp = new targy();
            temp.m = Integer.parseInt(s.split("\t")[0]);
            temp.sz = Integer.parseInt(s.split("\t")[1]);
            temp.name = i + 1;
            targyak.add(temp);
        }
        //targyak rendezese
        Collections.sort(targyak);

        long startTime = System.nanoTime();

        //matrix letrehozasa
        int[][] matrix = new int[magassag][szelesseg];
        //printm(matrix);
        //out.print(s);

        //targyak elnevezese


        //targyak elhelyezese
        for (targy elem :
                targyak) {
            //az elem nevu targy mozgatasa
            boolean success = false;
            int lastcolumn = 0;

            //eredeti matrix mentese
            int[][] originalmatrix = new int[matrix.length][];
            for (int i = 0; i < matrix.length; i++)
                originalmatrix[i] = matrix[i].clone();

            while (!success) {
                if (lastcolumn == szelesseg) {
                    System.out.print("not solvable at "+elem.name+"\n");
                    break;
                }

                try {
                    //tárgy elhelyezése

                    //a bal felso sarok keresese
                    int mag = 0;
                    int szel = lastcolumn;
                    while (matrix[mag][szel] != 0) {
                        if (mag == magassag - 1) {
                            szel++;
                            mag = 0;
                        } else
                            mag++;

                    }
                    lastcolumn = szel + 1;
                    //targy beirasa a bal felso sarokba
                    for (int i = 0; i < elem.m; i++) {
                        int[] row = matrix[i + mag];
                        for (int j = 0; j < elem.sz; j++) {
                            if (row[j + szel] == 0)
                                row[j + szel] = elem.name;
                            else
                                throw new Exception("van mar itt valami");
                        }
                    }

                    //out.print("\n");
                    //printm(matrix);
                    //out.print("\n");


                    //ha sikerrel jart
                    success = true;
                } catch (Exception ex) {
                    //ha nem sikerült, visszaallitja a matrixot, es ujraprobalja
                    for (int i = 0; i < originalmatrix.length; i++)
                        matrix[i] = originalmatrix[i].clone();
                }

            }
        }

        //el lett helyezve minden
        printm(matrix);
        long endTime = System.nanoTime();
        //out.print("\n");
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        //out.print(duration / 1000000.0 + " ms");
    }

    public static void printm(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            int[] row = matrix[i];
            for (int j = 0; j < row.length; j++) {
                out.print(row[j]);
                if (!(j == row.length - 1))
                    out.print("\t");
            }
            out.print("\n");
        }
    }
}

class targy implements Comparable<targy> {
    public int m;
    public int sz;
    public int name;

    @Override
    public int compareTo(targy o) {
        if (this.sz > o.sz)
            return -1;
        else if (this.sz == o.sz) {
            if (this.m*this.sz > o.m*o.sz) {
                return -1;
            } else
                return 1;
        } else return 1;
    }
}