package com.tp.tp1_simon.modele;

import java.util.ArrayList;
import java.util.List;

public class Simon {

    public static final int taille = 2;
    private String[][] cellules = new String[taille][taille];


    public Simon(){
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                cellules[i][j] = "";
            }
        }
    }





}