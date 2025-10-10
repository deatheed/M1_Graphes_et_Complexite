
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Stack;

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
                int[] indegree = pair.in();
                int[] outdegree = pair.out();
                System.out.println("(Matrix) Indegrees for vertices from 1 to " + indegree.length + " for the given digraph");
                Tools4A.printArray(indegree);
                System.out.println("(Matrix) Outdegrees for vertices from 1 to " + indegree.length + " for the given digraph");
                Tools4A.printArray(outdegree);
            }

            // EXERCICE 1: Transposition (Matrice -> Matrice)
            System.out.println("\n--- TP1 Exercice 1: Transposition (Matrice -> Matrice) ---");
            float[][] transposedMatrix = graphM.transposeToMatrix();
            System.out.println("Graphe transposé créé (matrice -> matrice)");
            System.out.println("Taille: " + transposedMatrix.length + "x" + transposedMatrix[0].length);

            // EXERCICE 1: Transposition (Matrice -> Liste)
            System.out.println("\n--- TP1 Exercice 1: Transposition (Matrice -> Liste) ---");
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
            System.out.println("\n--- TP1 Exercice 2: Test d'existence de chemin (Matrice) ---");
            if (graphM.getType() == 0) {
                int[] testPath = {1, 2, 3};
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

            // TP2 EXERCICE 1: DFS et reconnaissance des arcs
            System.out.println("\n--- TP2 Exercice 1: Parcours DFS et reconnaissance des arcs ---");
            Numbering num = graphM.recognizeArc();
            System.out.println("Parcours DFS effectué avec numérotation:");
            System.out.print("Numéros de découverte (d): ");
            for (int i = 0; i < graphM.getN(); i++) {
                System.out.print(num.getD()[i] + " ");
            }
            System.out.println();
            System.out.print("Numéros de fin (f): ");
            for (int i = 0; i < graphM.getN(); i++) {
                System.out.print(num.getF()[i] + " ");
            }
            System.out.println();
            System.out.println("Arcs tree/forward: " + num.get_tf_arc().size());
            System.out.println("Arcs back: " + num.get_b_arc().size());
            System.out.println("Arcs cross: " + num.get_c_arc().size());

            // TP2 EXERCICE 2: Détection de cycle
            System.out.println("\n--- TP2 Exercice 2: Détection de cycle ---");
            boolean hasCycle = graphM.DFSCycle();
            System.out.println("Le graphe contient un cycle: " + (hasCycle ? "OUI" : "NON"));

            // TP2 EXERCICE 3: Affichage du cycle
            System.out.println("\n--- TP2 Exercice 3: Affichage d'un cycle ---");
            Stack<Integer> cycle = graphM.DFSCycleP();
            if (!cycle.isEmpty()) {
                System.out.print("Cycle trouvé: ");
                Object[] cycleArray = cycle.toArray();
                for (int i = 0; i < cycleArray.length; i++) {
                    System.out.print(cycleArray[i]);
                    if (i < cycleArray.length - 1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println();
            } else {
                System.out.println("Aucun cycle trouvé");
            }

            // NOUVELLES FONCTIONNALITÉS
            // BFS - Parcours en largeur
            System.out.println("\n=== NOUVELLES FONCTIONNALITES ===");
            System.out.println("\n--- BFS: Parcours en largeur depuis le sommet 0 ---");
            graphM.printBFS(0);

            // Arbre BFS
            System.out.println("\n--- BFS: Arbre de parcours depuis le sommet 0 ---");
            int[] bfsTree = graphM.BFSTree(0);
            System.out.println("Arbre BFS (parents):");
            for (int i = 0; i < bfsTree.length; i++) {
                if (bfsTree[i] == -1) {
                    System.out.println("Sommet " + i + " : racine ou non atteignable");
                } else {
                    System.out.println("Sommet " + i + " -> parent: " + bfsTree[i]);
                }
            }

            // Matrice d'incidence
            System.out.println("\n--- Matrice d'incidence ---");
            graphM.printIncidenceMatrix();

            // Tri topologique (seulement pour graphes orientés)
            System.out.println("\n--- Tri topologique ---");
            if (graphM.getType() == 1) {
                graphM.printTopologicalSort();
            } else {
                System.out.println("Le tri topologique n'est disponible que pour les graphes orientés");
            }

            // Composantes connexes
            System.out.println("\n--- Composantes connexes ---");
            graphM.printConnectedComponents();

            // Test de connexité
            System.out.println("\n--- Test de connexité ---");
            boolean connected = graphM.isConnected();
            System.out.println("Le graphe est connexe: " + (connected ? "OUI" : "NON"));
            System.out.println("Nombre de composantes connexes: " + graphM.numberOfConnectedComponents());

            // If we choose the representation by adjacency lists
            sc = new Scanner(file);
            GraphL4A graphL = new GraphL4A(sc);

            System.out.println("\n\n=== REPRESENTATION PAR LISTES D'ADJACENCE ===");

            if (graphL.getType() == 0 && graphL.getWeighted() == 0) {
                int[] degree = graphL.degree();
                System.out.println("(List) Degrees for vertices from 1 to " + degree.length + " for the given undirected graph");
                Tools4A.printArray(degree);
            }

            if (graphL.getType() == 0 && graphL.getWeighted() == 1) {
                int[] degree = graphL.degreeW();
                System.out.println("(List) Degrees for vertices from 1 to " + degree.length + " for the given undirected graph");
                Tools4A.printArray(degree);
            }

            if (graphL.getType() == 1 && graphL.getWeighted() == 0) {
                TwoArrays4A pair = graphL.degrees();
                int[] indegree = pair.in();
                int[] outdegree = pair.out();
                System.out.println("(List) Indegrees for vertices from 1 to " + indegree.length + " for the given digraph");
                Tools4A.printArray(indegree);
                System.out.println("(List) Outdegrees for vertices from 1 to " + indegree.length + " for the given digraph");
                Tools4A.printArray(outdegree);
            }

            if (graphL.getType() == 1 && graphL.getWeighted() == 1) {
                TwoArrays4A pair = graphL.degreesW();
                int[] indegree = pair.in();
                int[] outdegree = pair.out();
                System.out.println("(List) Indegrees for vertices from 1 to " + indegree.length + " for the given digraph");
                Tools4A.printArray(indegree);
                System.out.println("(List) Outdegrees for vertices from 1 to " + indegree.length + " for the given digraph");
                Tools4A.printArray(outdegree);
            }

            // EXERCICE 1: Transposition (Liste -> Liste)
            System.out.println("\n--- TP1 Exercice 1: Transposition (Liste -> Liste) ---");
            if (graphL.getWeighted() == 0) {
                Node4A[] transposedAdjList = graphL.transposeToAdjList();
                System.out.println("Graphe transposé créé (liste -> liste non pondéré)");
                System.out.println("Nombre de sommets: " + transposedAdjList.length);
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
            System.out.println("\n--- TP1 Exercice 1: Transposition (Liste -> Matrice) ---");
            float[][] transposedMatrixFromList = graphL.transposeToMatrix();
            System.out.println("Graphe transposé créé (liste -> matrice)");
            System.out.println("Taille: " + transposedMatrixFromList.length + "x" + transposedMatrixFromList[0].length);

            // EXERCICE 2: Test d'existence de chemin (avec listes)
            System.out.println("\n--- TP1 Exercice 2: Test d'existence de chemin (Liste) ---");
            if (graphL.getType() == 0) {
                int[] testPath = {1, 2, 3};
                boolean pathExists = graphL.pathExists(testPath);
                System.out.print("Test du chemin: ");
                for (int i = 0; i < testPath.length; i++) {
                    System.out.print(testPath[i]);
                    if (i < testPath.length - 1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println(" : " + (pathExists ? "EXISTE" : "N'EXISTE PAS"));

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

            System.out.println("\n=== /////////////////////////////// ===");

            // === NOUVELLES FONCTIONNALITES POUR GRAPHL4A ===
            System.out.println("\n=== NOUVELLES FONCTIONNALITES POUR GRAPHL4A ===");

            // TP2: Détection de cycle avec listes d'adjacence
            System.out.println("\n--- TP2 Exercice 2: Détection de cycle (Listes d'adjacence) ---");
            if (graphL.getWeighted() == 0) {
                boolean hasCycleL = graphL.DFSCycle();
                System.out.println("(Liste) Le graphe contient un cycle: " + (hasCycleL ? "OUI" : "NON"));
                System.out.println("Note: Ce test utilise DFS avec coloration (noir/rouge/bleu)");
            } else {
                System.out.println("Détection de cycle actuellement disponible uniquement pour graphes non pondérés");
            }

            // TP2 EXERCICE 3: Affichage du cycle avec listes d'adjacence
            System.out.println("\n--- TP2 Exercice 3: Affichage d'un cycle (Listes d'adjacence) ---");
            if (graphL.getWeighted() == 0) {
                Stack<Integer> cycleL = graphL.DFSCycleP();
                if (!cycleL.isEmpty()) {
                    System.out.print("(Liste) Cycle trouvé: ");
                    Object[] cycleArrayL = cycleL.toArray();
                    for (int i = 0; i < cycleArrayL.length; i++) {
                        System.out.print(cycleArrayL[i]);
                        if (i < cycleArrayL.length - 1) {
                            System.out.print(" -> ");
                        }
                    }
                    System.out.println();
                    System.out.println("Taille du cycle: " + cycleL.size() + " sommets");
                } else {
                    System.out.println("(Liste) Aucun cycle trouvé");
                }
            } else {
                System.out.println("Affichage de cycle actuellement disponible uniquement pour graphes non pondérés");
            }

            System.out.println("\n=== FIN DES TESTS ===");

            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
