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
     *
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

    /**
     * La méthode permet changer  la visibilité des certaines boutons
     * (boutonCommencer et boutonRecommencer avec ses textes), valider
     * la difficulté du jeu, activer certains boutons et ejecuter
     * la logique du jeu.
     */
    private void gestionClickCommencer() {
        boutonCommencer.setVisibility(View.INVISIBLE);
        texteCommencer.setVisibility(View.INVISIBLE);
        boutonRecommencer.setVisibility(View.VISIBLE);
        texteRecommencer.setVisibility(View.VISIBLE);
        validerDifficulté();
        activationBoutons();
        systèmeJeu();
    }

    /**
     * La méthode permet de réinitialiser le jeu.
     */
    private void gestionClickRecommencer() {
        jeu.réinitialiserPoints();
        score.setText(String.valueOf(jeu.getPoints()));
        for (int i = 0; i < 4; i++) {
            boutons[i].setBackgroundColor(0xFF00BCD4);
        }
        jeu.réinitialiserChoixCouleur();
        jeu.réinitialiserChoixCouleurIA();
        validerDifficulté();
        if(finPartie == true){
            activationBoutons();
        }
        systèmeJeu();
    }

    /**
     * La méthode permet de rendre invisible le boutonRecommencer,son texte
     * et l'image X avant que le joueur commence le jeu.
     */
    private void boutonsInvisibles() {
        boutonRecommencer.setVisibility(View.INVISIBLE);
        texteRecommencer.setVisibility(View.INVISIBLE);
        for (int i = 0; i < 4; i++) {
            boutons[i].setVisibility(View.INVISIBLE);
        }
        x.setVisibility(View.INVISIBLE);
    }

    /**
     * La méthode permet l'activation ou desactivation des boutons (boutonFacile,
     * boutonIntermédiaire, boutonDifficile, boutons generals et l'image X quand
     * le joueur perd la partie.
     */
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

    /**
     * La méthode permet valider le choix de la difficulté du joueur.
     */
    private void validerDifficulté(){
        if(boutonFacile.isChecked()==true){
            jeu.setDifficulté(1500);
        } else if (boutonIntermédiaire.isChecked() == true) {
            jeu.setDifficulté(750);
        } else if (boutonDifficile.isChecked() == true) {
            jeu.setDifficulté(500);
        } else {
            jeu.setDifficulté(1500);
        }
    }

    /**
     * La méthode permet de faire la séquence du couleur de l'IA.
     */
    private void systèmeJeu(){
        finPartie = false;
        jeu.choixCouleurIA();
        délaiSéquence();
    }

    /**
     * La méthode permet de faire une délai de temps entre le dernièr tour et
     * le nouveau. Aussi les boutons seront desactivés pendant que la nouveau séquence de l'IA
     * s'affiche.
     */
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

    /**
     * La méthode permet afficher la séquence de l'IA dans une intervalle du temps.
     * Aussi les boutons seront activés
     */
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

    /**
     * La méthode permet changer la couleur du bouton sélectioner et
     * faire la logique de séquence entre la séquence du joueur et l'IA
     *
     * @param i, le code du bouton qui a été sélectionè.
     */
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

    /**
     * La méthode permet de retourner la couleur du background du fond de l'application
     * après que le bouton que le jouer a sélectioné est allumé.
     *
     * @param i, le code du bouton qui a été sélectionè.
     */
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

    /**
     * La méthode permet de vérifier las séquence du joueur avec la séquence de l'IA
     * pour savoir si la partie continue ou pas.
     */
    private void gestionTourJoueur(){
        if(jeu.comparerSéquenceChoix(jeu.getChoixCouleur().size()-1) == true){
            if(jeu.getChoixCouleur().size() == jeu.getChoixCouleurIA().size()){
                gestionScore();
                prochainePartie();
            }
        } else {
            activationBoutons();
            finPartie = true;
        }

    }

    /**
     * La méthode permet de préparer le joueur pour une nouvelle partie.
     */
    private void prochainePartie(){
        jeu.réinitialiserChoixCouleur();
        systèmeJeu();
    }

    /**
     * La méthode permet de ajouter 1 points chaque fois que le joueur
     * fait une séquence correct. Aussi, si les points sont plus haut que l'actuel record,
     * le record sera modifier..
     */
    private void gestionScore() {
        jeu.ajouterPoints();
        score.setText(String.valueOf(jeu.getPoints()));
        if (jeu.getPoints() > jeu.getRecord()) {
            meilleurScore.setText(String.valueOf(jeu.getPoints()));
            jeu.setRecord(jeu.getPoints());
        }
    }
}