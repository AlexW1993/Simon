package com.tp.tp1_simon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


import com.tp.tp1_simon.modele.Simon;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements Cloneable {

    /**
     * Declaration des Attributs
     */
    private Simon jeu;

    private ImageButton boutonCommencer, boutonRecommencer;

    private TextView score, meilleurScore, texteCommencer, texteRecommencer;

    private final ImageButton[] boutons = new ImageButton[4];
    private final ImageButton[] formes = new ImageButton[4];

    private ImageView x;

    private RadioButton boutonFacile, boutonIntermédiaire, boutonDifficile;

    private boolean confirmationBoutons = true, finPartie;

    /**
     * Méthode onCreate
     * <p>
     * Perment d'intialiser touts les boutons et textView qui sont
     * dans activity_main(vue)
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intiliaser le jeu
        jeu = new Simon();
        // Bouton de commenzer
        boutonCommencer = findViewById(R.id.boutonCommencer);
        texteCommencer = findViewById(R.id.commencer);
        //Bouton de recommencer
        boutonRecommencer = findViewById(R.id.boutonRecommencer);
        texteRecommencer = findViewById(R.id.recommencer);
        //Textes score et Records
        score = findViewById(R.id.score);
        score.setText(String.valueOf(jeu.getPoints()));
        meilleurScore = findViewById(R.id.meilleurScore);
        meilleurScore.setText(String.valueOf(jeu.getRecord()));
        //noms des boutons
        jeu.ajouterBouttons("Carre");
        jeu.ajouterBouttons("Cercle");
        jeu.ajouterBouttons("Etoile");
        jeu.ajouterBouttons("Triangle");

        //image x
        x = findViewById(R.id.imageX);

        //Boutons de difficulté
        boutonFacile = findViewById(R.id.radioFacile);
        boutonIntermédiaire = findViewById(R.id.radioIntermédiaire);
        boutonDifficile = findViewById(R.id.radioDifficile);

        //Boutons forms
        for (int i = 0; i < 4; i++) {
            String buttonID = "bouton" + jeu.getNomButtons().get(i);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            boutons[i] = findViewById(resID);
            formes[i] = findViewById(resID);
            int finalI = i;
            boutons[i].setOnClickListener(v -> {
                gestionClick(finalI);
            });
        }

        boutonCommencer.setOnClickListener(v -> gestionClickCommencer());
        boutonRecommencer.setOnClickListener(v -> gestionClickRecommencer());
        boutonsInvisibles();
    }

    private void gestionClickCommencer() {
        boutonCommencer.setVisibility(View.INVISIBLE);
        texteCommencer.setVisibility(View.INVISIBLE);
        boutonRecommencer.setVisibility(View.VISIBLE);
        texteRecommencer.setVisibility(View.VISIBLE);
        validerDifficulté();
        activationBoutons();
        systèmeJeu();
    }

    private void activationBoutons(){
        if (Boolean.compare(confirmationBoutons,true)== 0){
            for (int i = 0; i < 4; i++) {
                boutons[i].setVisibility(View.VISIBLE);
            }
            confirmationBoutons = false;
            boutonFacile.setVisibility(View.INVISIBLE);
            boutonIntermédiaire.setVisibility(View.INVISIBLE);
            boutonDifficile.setVisibility(View.INVISIBLE);
            x.setVisibility(View.INVISIBLE);
        } else {
            for (int i = 0; i < 4; i++) {
                boutons[i].setVisibility(View.INVISIBLE);
            }
            confirmationBoutons = true;
            x.setVisibility(View.VISIBLE);
            boutonFacile.setVisibility(View.VISIBLE);
            boutonIntermédiaire.setVisibility(View.VISIBLE);
            boutonDifficile.setVisibility(View.VISIBLE);
        }
    }

    private void validerDifficulté(){
        if(boutonFacile.isChecked()==true){
            jeu.ajouterDifficulté(1500);
        } else if (boutonIntermédiaire.isChecked() == true) {
            jeu.ajouterDifficulté(750);
        } else if (boutonDifficile.isChecked() == true) {
            jeu.ajouterDifficulté(500);
        } else {
            jeu.ajouterDifficulté(1500);
        }
    }

    private void systèmeJeu(){
        finPartie = false;
        jeu.choixCouleurIA();
        délaiSéquence();
    }

    private void boutonsInvisibles() {
        boutonRecommencer.setVisibility(View.INVISIBLE);
        texteRecommencer.setVisibility(View.INVISIBLE);
        for (int i = 0; i < 4; i++) {
            boutons[i].setVisibility(View.INVISIBLE);
        }
        x.setVisibility(View.INVISIBLE);
    }

    private void gestionClick(int i) {
        if (boutons[i] == boutons[0]) {
            boutons[i].setBackgroundColor(0xFFFF0000);
            jeu.ajouterCouleur(0);
            attendreEtCacher(i);
            gestionTourJoueur();
        } else if (boutons[i] == boutons[1]) {
            boutons[i].setBackgroundColor(0xFF0000FF);
            jeu.ajouterCouleur(1);
            attendreEtCacher(i);
            gestionTourJoueur();
        } else if (boutons[i] == boutons[2]) {
            boutons[i].setBackgroundColor(0xFFFFFF00);
            jeu.ajouterCouleur(2);
            attendreEtCacher(i);
            gestionTourJoueur();
        } else if (boutons[i] == boutons[3]) {
            boutons[i].setBackgroundColor(0xFF00FF00);
            jeu.ajouterCouleur(3);
            attendreEtCacher(i);
            gestionTourJoueur();
        }
    }

    private void délaiSéquence(){
        for (int i = 0; i < 4; i++) {
            boutons[i].setEnabled(false);
        }
        new CountDownTimer(1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                attendreEtCacherSequence();
            }
        }.start();
    }

    private void gestionTourJoueur(){
        if(jeu.comparerSequenceChoix(jeu.getChoixCouleur().size()-1) == true){
            if(jeu.getChoixCouleur().size() == jeu.getChoixCouleurIA().size()){
                gestionScore();
                prochainePartie();
            }
        } else {
            activationBoutons();
            finPartie = true;
        }

    }

    private void gestionScore() {
        jeu.ajouterPoints();
        score.setText(String.valueOf(jeu.getPoints()));
        if (jeu.getPoints() > jeu.getRecord()) {
            meilleurScore.setText(String.valueOf(jeu.getPoints()));
            jeu.setRecord(jeu.getPoints());
        }
    }

    private void prochainePartie(){
        jeu.reinitialiserChoixCouleur();
        systèmeJeu();
    }

    private void gestionClickRecommencer() {
        jeu.reinitialiserPoints();
        score.setText(String.valueOf(jeu.getPoints()));
        for (int i = 0; i < 4; i++) {
            boutons[i].setBackgroundColor(0xFF00BCD4);
        }
        jeu.reinitialiserChoixCouleur();
        jeu.reinitialiserChoixCouleurIA();
        validerDifficulté();
        if(finPartie == true){
            activationBoutons();
        }
        systèmeJeu();
    }

    private void attendreEtCacherSequence() {
        new CountDownTimer(jeu.getDifficulté() * jeu.getChoixCouleurIA().size(), jeu.getDifficulté() / 2 ) {
            int i = 0;
            boolean couleurSimon = false;

            @Override
            public void onTick(long millisUntilFinished) {
                if (couleurSimon == false) {
                    if (jeu.getChoixCouleurIA().get(i) == 0) {
                        boutons[0].setBackgroundColor(0xFFFF0000);
                    } else if (jeu.getChoixCouleurIA().get(i) == 1) {
                        boutons[1].setBackgroundColor(0xFF0000FF);
                    } else if (jeu.getChoixCouleurIA().get(i) == 2) {
                        boutons[2].setBackgroundColor(0xFFFFFF00);
                    } else if (jeu.getChoixCouleurIA().get(i) == 3) {
                        boutons[3].setBackgroundColor(0xFF00FF00);
                    }
                    couleurSimon = true;
                } else {
                    boutons[jeu.getChoixCouleurIA().get(i)].setBackgroundColor(0xFF00BCD4);
                    couleurSimon = false;
                    if (i < jeu.getChoixCouleurIA().size() - 1) {
                        i++;
                    }
                }
            }

            @Override
            public void onFinish() {
                for (int i = 0; i < 4; i++) {
                    boutons[i].setEnabled(true );
                }
            }
        }.start();
    }

    private void attendreEtCacher(int i) {
        new CountDownTimer(500,500 ) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                boutons[i].setBackgroundColor(0xFF00BCD4);
            }
        }.start();
    }
}