package com.tp.tp1_simon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.TableLayout;
import android.widget.TextView;


import com.tp.tp1_simon.modele.Simon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Cloneable{

    private Simon jeu;
    private final ImageButton[][] buttons = new ImageButton[Simon.taille][Simon.taille];
    private final ImageButton[][] formes = new ImageButton[Simon.taille][Simon.taille];
    private ArrayList<String> nomButtons = new ArrayList<>();
    private int k = 0, points = 0;
    private Integer record = 0;
    private TextView score, meilleurScore;
    private ImageButton commencer, recommencer;
    private TextView texteCommencer, texteRecommencer;
    private List<Integer> choixCouleur = new ArrayList<Integer>();
    private int[]choixCouleurAI = new int[4];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jeu = new Simon();
        nomButtons.add("Carre");
        nomButtons.add("Cercle");
        nomButtons.add("Etoile");
        nomButtons.add("Triangle");
        butonsVisibles();
        commencer.setOnClickListener(v -> gestionClickCommencer());
        recommencer.setOnClickListener(v->gestionClickRecommencer());
    }

    private void butonsVisibles(){
        recommencer = findViewById(R.id.buttonRecommencer);
        recommencer.setVisibility(View.INVISIBLE);
        texteRecommencer = findViewById(R.id.recommencer);
        texteRecommencer.setVisibility(View.INVISIBLE);
        commencer = findViewById(R.id.buttonCommencer);
    }

    private void gestionClick(int i, int j) {
        if (buttons[i][j] == buttons[0][0]){
            buttons[i][j].setBackgroundColor(0xFFFF0000);
            choixCouleur.add(0);
        } else if (buttons[i][j] == buttons[0][1]){
            buttons[i][j].setBackgroundColor(0xFF0000FF);
            choixCouleur.add(1);
        } else if (buttons[i][j] == buttons[1][0]){
            buttons[i][j].setBackgroundColor(0xFFFFFF00);
            choixCouleur.add(2);
        } else  if (buttons[i][j] == buttons[1][1]){
            buttons[i][j].setBackgroundColor(0xFF00FF00);
            choixCouleur.add(3);
        }
        attendreEtCacher(i,j);
        gestionScore();
    }

    private void gestionScore(){
        points += 1;
        record = 0;
        score.setText(String.valueOf(points));
        if(points > record){
            meilleurScore.setText(String.valueOf(points));
                record = points;
        } else {
            meilleurScore.setText(String.valueOf(record));
        }
    }

    private void gestionClickCommencer(){
        sequnceASuivre();
        for (int i = 0; i < Simon.taille; i++) {
            for (int j = 0; j < Simon.taille; j++) {
                String buttonID = "button" + nomButtons.get(k);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                formes[i][j] = findViewById(resID);

                int finalI = i;
                int finalJ = j;
                buttons[i][j].setOnClickListener(v -> {
                    gestionClick(finalI, finalJ);
                });
                k += 1;
            }
        }
        score = findViewById(R.id.score);
        meilleurScore = findViewById(R.id.meilleurScore);
        commencer.setVisibility(View.INVISIBLE);
        texteCommencer = findViewById(R.id.commencer);
        texteCommencer.setVisibility(View.INVISIBLE);
        recommencer.setVisibility(View.VISIBLE);
        texteRecommencer.setVisibility(View.VISIBLE);
    }

    private void gestionClickRecommencer(){
        points = 0;
        score.setText(String.valueOf(points));
        for (int i = 0; i < Simon.taille ; i++) {
            for (int j = 0; j < Simon.taille ; j++) {
                buttons[i][j].setBackgroundColor(0xFF00BCD4);
            }
        }
        sequnceASuivre();
        choixCouleur.clear();
    }

    private void attendreEtCacher(int i, int j){
        new CountDownTimer(500,500){


            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                        buttons[i][j].setBackgroundColor(0xFF00BCD4);
            }
        }.start();
    }

    private void sequnceASuivre(){

        for (int i = 0; i < 4; i++) {
            choixCouleurAI[i] = (int) Math.floor((Math.random()*4));
            if(choixCouleurAI[i] == 0) {
                buttons[0][0] = findViewById(R.id.buttonCarre);
                buttons[0][0].setBackgroundColor(0xFFFF0000);
                attendreEtCacher(0,0);
            } else if(choixCouleurAI[i]==1){
                buttons[0][1] = findViewById(R.id.buttonCercle);
                buttons[0][1].setBackgroundColor(0xFF0000FF);
                attendreEtCacher(0,1);
            } else if(choixCouleurAI[i]==2) {
                buttons[1][0] = findViewById(R.id.buttonEtoile);
                buttons[1][0].setBackgroundColor(0xFFFFFF00);
                attendreEtCacher(1,0);
            }else if(choixCouleurAI[i]==3) {
                buttons[1][1] = findViewById(R.id.buttonTriangle);
                buttons[1][1].setBackgroundColor(0xFF00FF00);
                attendreEtCacher(1,1);
            }
        }
    }
}