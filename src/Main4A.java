
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main4A {

    public static void main(String args[]) {
        try {
            File file = new File(args[0]);

            //If we choose the representation by adjacency matrix
            Scanner sc = new Scanner(file);
            GraphM4A graphM = new GraphM4A(sc);

            System.out.println("\n=== REPRESENTATION PAR MATRICE D'ADJACENCE ===");

            if (graphM.getType() == 0) { //undirected
                int[] degree = graphM.degree();
                System.out.println("(Matrix) Degrees for vertices from 1 to " + degree.length + " for the given undirected graph");
                Tools4A.printArray(degree);
            } else { //directed
                TwoArrays4A pair = graphM.degrees();
                int[] indegree = pair.in(); //the result of graphM.degrees() is a pair of arrays, indegree and outdegree
                int[] outdegree = pair.out();
                System.out.println("(Matrix)Indegrees for vertices from 1 to " + indegree.length + " for the given digraph");
                Tools4A.printArray(indegree);
                System.out.println("(Matrix)Outdegrees for vertices from 1 to " + indegree.length + " for the given digraph");
                Tools4A.printArray(outdegree);
            }

            // EXERCICE 1: Transposition (Matrice -> Matrice)
            System.out.println("\n--- Exercice 1: Transposition (Matrice -> Matrice) ---");
            float[][] transposedMatrix = graphM.transposeToMatrix();
            System.out.println("Graphe transposé créé (matrice -> matrice)");
            System.out.println("Taille: " + transposedMatrix.length + "x" + transposedMatrix[0].length);

            // EXERCICE 1: Transposition (Matrice -> Liste)
            System.out.println("\n--- Exercice 1: Transposition (Matrice -> Liste) ---");
            if (graphM.getWeighted() == 0) {
                Node4A[] transposedAdjList = graphM.transposeToAdjList();
                System.out.println("Graphe transposé créé (matrice -> liste non pondéré)");
                System.out.println("Nombre de sommets: " + transposedAdjList.length);
            } else {
                WeightedNode4A[] transposedWeightedAdjList = graphM.transposeToWeightedAdjList();
                System.out.println("Graphe transposé créé (matrice -> liste pondéré)");
                System.out.println("Nombre de sommets: " + transposedWeightedAdjList.length);
            }

            // EXERCICE 2: Test d'existence de chemin (avec matrice)
            System.out.println("\n--- Exercice 2: Test d'existence de chemin (Matrice) ---");
            if (graphM.getType() == 0) { // Graphe non orienté seulement
                int[] testPath = {1, 2, 3}; // Exemple de chemin à tester
                boolean pathExists = graphM.pathExists(testPath);
                System.out.print("Test du chemin: ");
                for (int i = 0; i < testPath.length; i++) {
                    System.out.print(testPath[i]);
                    if (i < testPath.length - 1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println(" : " + (pathExists ? "EXISTE" : "N'EXISTE PAS"));
            } else {
                System.out.println("Test de chemin disponible uniquement pour les graphes non orientés");
            }

            // If we choose the representation by adjacency lists
            sc = new Scanner(file);
            GraphL4A graphL = new GraphL4A(sc);

            System.out.println("\n=== REPRESENTATION PAR LISTES D'ADJACENCE ===");

            if (graphL.getType() == 0 && graphL.getWeighted() == 0) { //undirected and unweighted
                int[] degree = graphL.degree();
                System.out.println("(List) Degrees for vertices from 1 to " + degree.length + " for the given undirected graph");
                Tools4A.printArray(degree);
            }
            if (graphL.getType() == 0 && graphL.getWeighted() == 1) { //undirected and weighted
                int[] degree = graphL.degreeW();
                System.out.println("(List) Degrees for vertices from 1 to " + degree.length + " for the given undirected graph");
                Tools4A.printArray(degree);
            }
            if (graphL.getType() == 1 && graphL.getWeighted() == 0) { //directed and unweighted
                TwoArrays4A pair = graphL.degrees();
                int[] indegree = pair.in();
                int[] outdegree = pair.out();
                System.out.println("(List) Indegrees for vertices from 1 to " + indegree.length + " for the given digraph");
                Tools4A.printArray(indegree);
                System.out.println("(List) Outdegrees for vertices from 1 to " + indegree.length + " for the given digraph");
                Tools4A.printArray(outdegree);
            }
            if (graphL.getType() == 1 && graphL.getWeighted() == 1) { //directed and unweighted
                TwoArrays4A pair = graphL.degreesW();
                int[] indegree = pair.in();
                int[] outdegree = pair.out();
                System.out.println("(List)Indegrees for vertices from 1 to " + indegree.length + " for the given digraph");
                Tools4A.printArray(indegree);
                System.out.println("(List)Outdegrees for vertices from 1 to " + indegree.length + " for the given digraph");
                Tools4A.printArray(outdegree);
            }

            // EXERCICE 1: Transposition (Liste -> Liste)
            System.out.println("\n--- Exercice 1: Transposition (Liste -> Liste) ---");
            if (graphL.getWeighted() == 0) {
                Node4A[] transposedAdjList = graphL.transposeToAdjList();
                System.out.println("Graphe transposé créé (liste -> liste non pondéré)");
                System.out.println("Nombre de sommets: " + transposedAdjList.length);

                // Afficher quelques informations sur le graphe transposé
                System.out.println("Premiers voisins du graphe transposé:");
                for (int i = 0; i < Math.min(3, transposedAdjList.length); i++) {
                    System.out.print("Sommet " + (i + 1) + " -> ");
                    Node4A current = transposedAdjList[i];
                    if (current == null) {
                        System.out.println("aucun voisin");
                    } else {
                        while (current != null) {
                            System.out.print((current.getVal() + 1) + " ");
                            current = current.getNext();
                        }
                        System.out.println();
                    }
                }
            } else {
                WeightedNode4A[] transposedWeightedAdjList = graphL.transposeToWeightedAdjList();
                System.out.println("Graphe transposé créé (liste -> liste pondéré)");
                System.out.println("Nombre de sommets: " + transposedWeightedAdjList.length);

                // Afficher quelques informations sur le graphe pondéré transposé
                System.out.println("Premiers voisins du graphe transposé:");
                for (int i = 0; i < Math.min(3, transposedWeightedAdjList.length); i++) {
                    System.out.print("Sommet " + (i + 1) + " -> ");
                    WeightedNode4A current = transposedWeightedAdjList[i];
                    if (current == null) {
                        System.out.println("aucun voisin");
                    } else {
                        while (current != null) {
                            System.out.print((current.getVal() + 1) + "(" + current.getWeight() + ") ");
                            current = current.getNext();
                        }
                        System.out.println();
                    }
                }
            }

            // EXERCICE 1: Transposition (Liste -> Matrice)
            System.out.println("\n--- Exercice 1: Transposition (Liste -> Matrice) ---");
            float[][] transposedMatrixFromList = graphL.transposeToMatrix();
            System.out.println("Graphe transposé créé (liste -> matrice)");
            System.out.println("Taille: " + transposedMatrixFromList.length + "x" + transposedMatrixFromList[0].length);

            // EXERCICE 2: Test d'existence de chemin (avec listes)
            System.out.println("\n--- Exercice 2: Test d'existence de chemin (Liste) ---");
            if (graphL.getType() == 0) { // Graphe non orienté seulement
                int[] testPath = {1, 2, 3}; // Exemple de chemin à tester
                boolean pathExists = graphL.pathExists(testPath);
                System.out.print("Test du chemin: ");
                for (int i = 0; i < testPath.length; i++) {
                    System.out.print(testPath[i]);
                    if (i < testPath.length - 1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println(" : " + (pathExists ? "EXISTE" : "N'EXISTE PAS"));

                // Test avec un autre chemin si le graphe a assez de sommets
                if (graphL.getN() >= 4) {
                    int[] testPath2 = {1, 3, 2, 4};
                    boolean pathExists2 = graphL.pathExists(testPath2);
                    System.out.print("Test du chemin: ");
                    for (int i = 0; i < testPath2.length; i++) {
                        System.out.print(testPath2[i]);
                        if (i < testPath2.length - 1) {
                            System.out.print(" -> ");
                        }
                    }
                    System.out.println(" : " + (pathExists2 ? "EXISTE" : "N'EXISTE PAS"));
                }
            } else {
                System.out.println("Test de chemin disponible uniquement pour les graphes non orientés");
            }

            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
