package com.tp.tp1_simon.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simon {

    /**
     * Declaration des Attributs
     */
    private int points, record, difficulté;

    private ArrayList<String> nomBouttons;

    private List<Integer> choixCouleur, choixCouleurIA;

    /**
     * Constructeur du jeu Simon.
     */
    public Simon() {

        nomBouttons = new ArrayList<>();
        points = 0;
        record = 0;
        choixCouleur = new ArrayList<Integer>();
        choixCouleurIA = new ArrayList<Integer>();

    }

    /**
     * Accesseurs des points du jeu
     *
     * @return Les points qui ont été gagné dans le jeu.
     */
    public int getPoints() {
        return points;
    }

    public int getDifficulté() {
        return difficulté;
    }

    /**
     * Accesseurs du record du jeu
     *
     * @return Le record maximum de points obtenus
     * dans le jeu.
     */
    public int getRecord() {
        return record;
    }

    /**
     * Mutateur de record
     *
     * @param record, le nouveau record du jeu.
     */
    public void setRecord(int record) {
        this.record = record;
    }

    /**
     * Accesseurs du nom de chaque bouton du jeu.
     *
     * @return Le nom du chaque bouton du jeu.
     */
    public List<String> getNomButtons() {

        return nomBouttons;
    }

    /**
     * Accesseurs d'une liste de couleurs.
     *
     * @return Les couleurs choisies par le joueur.
     */
    public List<Integer> getChoixCouleur() {

        return choixCouleur;
    }

    /**
     * Accesseurs d'une liste de couleurs.
     *
     * @return Les couleurs choisies par l'IA.
     */
    public List<Integer> getChoixCouleurIA() {

        return choixCouleurIA;
    }

    /**
     * Permit d'ajouter 1 points à chaque fois que
     * le joueur click sur la bonne couleur
     */
    public void ajouterPoints() {

        this.points += 1;
    }

    /**
     * Permit d'ajouter reinitialiser les points à 0
     */
    public void reinitialiserPoints() {

        this.points = 0;
    }

    /**
     * Permet ajouter des noms aux boutons
     *
     * @param forme, le nom du bouton.
     */
    public void ajouterBouttons(String forme) {

        this.nomBouttons.add(forme);
    }

    /**
     * Permet ajouter la couleur que le joueur a choisit
     *
     * @param couleur, la couleur choisit.
     */
    public void ajouterCouleur(int couleur) {

        this.choixCouleur.add(couleur);
    }

    /**
     * Permet effacer la liste des couleurs du joueur
     */
    public void reinitialiserChoixCouleur() {

        this.choixCouleur.clear();
    }

    /**
     * Permet effacer la liste des couleurs de l'IA
     */
    public void reinitialiserChoixCouleurIA() {

        this.choixCouleurIA.clear();
    }

    /**
     * Permet ajouter la couleur / couleurs que l'IA a choisit
     */
    public void choixCouleurIA() {
        Random random = new Random();
        choixCouleurIA.add(random.nextInt(4));
    }

    /**
     * Permet de comparer le choix entre le joueur et l'IA
     *
     * @return true si les choix sont égals ou false si les choix
     * ne sont pas égals
     */
    public boolean comparerSequenceChoix(int i) {
        boolean confirmation = true;

        if (choixCouleurIA.get(i) == choixCouleur.get(i)) {
            confirmation = true;
        } else {
            confirmation = false;
        }

        return confirmation;
    }

    public void ajouterDifficulté(int i) {
            difficulté = i;
    }

}