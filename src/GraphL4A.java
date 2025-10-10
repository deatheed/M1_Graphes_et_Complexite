
import java.util.Scanner;
import java.util.Stack;

public class GraphL4A {

    private int n;
    private int type; //0 if undirected, 1 if directed
    private int weighted; // 0 if unweighted, 1 otherwise
    private WeightedNode4A[] adjlistW; //one of adjlistW and adjlist is null, depending on the type of the graph
    private Node4A[] adjlist;

    public GraphL4A(Scanner sc) {
        String[] firstline = sc.nextLine().split(" ");
        this.n = Integer.parseInt(firstline[0]);
        System.out.println(this.n);
        if (firstline[1].equals("undirected")) {
            this.type = 0;
        } else {
            this.type = 1;
        }
        System.out.println("Type= " + this.type);
        if (firstline[2].equals("unweighted")) {
            this.weighted = 0;
        } else {
            this.weighted = 1;
        }
        System.out.println("Weighted= " + this.weighted);
        if (this.weighted == 0) {
            this.adjlist = new Node4A[this.n];
            for (int i = 0; i < this.n; i++) {
                adjlist[i] = null;
            }
            adjlistW = null;
        } else {
            this.adjlistW = new WeightedNode4A[this.n];
            for (int i = 0; i < this.n; i++) {
                adjlistW[i] = null;
            }
            adjlist = null;
        }

        for (int k = 0; k < this.n; k++) {
            String[] line = sc.nextLine().split(" : ");
            int i = Integer.parseInt(line[0]); //the vertex "source"
            if (weighted == 0) {
                if ((line.length > 1) && (line[1].charAt(0) != ' ')) {
                    String[] successors = line[1].split(", ");
                    for (int h = 0; h < successors.length; h++) {
                        Node4A node = new Node4A(Integer.parseInt(successors[h]) - 1, null);
                        node.setNext(adjlist[i - 1]);
                        adjlist[i - 1] = node;
                    }
                }
            } else {
                line = line[1].split(" // ");
                if ((line.length == 2) && (line[1].charAt(0) != ' ')) {// if there really are somme successors, then we must have something different from " " after "// "
                    String[] successors = line[0].split(", ");
                    String[] theirweights = line[1].split(", ");
                    for (int h = 0; h < successors.length; h++) {
                        WeightedNode4A nodeW = new WeightedNode4A(Integer.parseInt(successors[h]) - 1, null, Float.parseFloat(theirweights[h]));
                        nodeW.setNext(adjlistW[i - 1]);
                        adjlistW[i - 1] = nodeW;
                    }

                }
            }
        }
    }

    //method to be applied only when type=0 and weighted=0
    public int[] degree() {
        int[] tmp = new int[this.n];
        for (int i = 0; i < this.n; i++) {
            tmp[i] = 0;
        }
        for (int i = 0; i < this.n; i++) {
            Node4A p = adjlist[i];
            while (p != null) {
                tmp[i] = tmp[i] + 1;
                p = p.getNext();
            }
        }
        return (tmp);
    }

    //method to be applied only when type=0 and weighted=1
    public int[] degreeW() {
        int[] tmp = new int[this.n];
        for (int i = 0; i < this.n; i++) {
            tmp[i] = 0;
        }
        for (int i = 0; i < this.n; i++) {
            WeightedNode4A p = adjlistW[i];
            while (p != null) {
                tmp[i] = tmp[i] + 1;
                p = p.getNext();
            }
        }
        return (tmp);
    }

    //method to be applied only when type=1 and weighted=0
    public TwoArrays4A degrees() {
        int[] tmp1 = new int[this.n]; //indegrees
        int[] tmp2 = new int[this.n]; //outdegrees
        for (int i = 0; i < this.n; i++) {
            tmp1[i] = 0;
            tmp2[i] = 0;
        }
        for (int i = 0; i < this.n; i++) {
            Node4A p = adjlist[i];
            while (p != null) {
                tmp2[i] = tmp2[i] + 1;
                tmp1[p.getVal()] = tmp1[p.getVal()] + 1;
                p = p.getNext();
            }
        }
        return (new TwoArrays4A(tmp1, tmp2));
    }

    //method to be applied only when type=1 and weighted=1
    public TwoArrays4A degreesW() {
        int[] tmp1 = new int[this.n]; //indegrees
        int[] tmp2 = new int[this.n]; //outdegrees
        for (int i = 0; i < this.n; i++) {
            tmp1[i] = 0;
            tmp2[i] = 0;
        }
        for (int i = 0; i < this.n; i++) {
            WeightedNode4A p = adjlistW[i];
            while (p != null) {
                tmp2[i] = tmp2[i] + 1;
                tmp1[p.getVal()] = tmp1[p.getVal()] + 1;
                p = p.getNext();
            }
        }
        return (new TwoArrays4A(tmp1, tmp2));
    }

    public int getType() {
        return this.type;
    }

    public float getWeighted() {
        return this.weighted;
    }

    public int getN() {
        return n;
    }

    public Node4A[] getTranspose() {
        Node4A[] newtab = new Node4A[adjlist.length];
        for (int i = 0; i < adjlist.length; i++) {
            for (Node4A current = adjlist[i]; current != null; current = current.getNext()) {
                int v = current.getVal();
                Node4A newNode = new Node4A(i, newtab[v]);
                newtab[v] = newNode;
            }
        }
        return newtab;
    }

    /**
     * Transpose le graphe représenté par des listes d'adjacence vers des listes
     * d'adjacence Retourne les tableaux de listes transposées directement
     * Complexité: O(V + E) où V = nombre de sommets, E = nombre d'arêtes
     */
    public Node4A[] transposeToAdjList() {
        if (this.weighted != 0) {
            return null; // Cette méthode est pour les graphes non pondérés
        }

        Node4A[] transposed = new Node4A[this.n];

        // Initialiser toutes les listes à null
        for (int i = 0; i < this.n; i++) {
            transposed[i] = null;
        }

        // Parcourir chaque sommet et ses successeurs
        for (int i = 0; i < this.n; i++) {
            Node4A current = this.adjlist[i];
            while (current != null) {
                int successor = current.getVal();
                // Ajouter l'arc inverse dans le graphe transposé
                Node4A newNode = new Node4A(i, transposed[successor]);
                transposed[successor] = newNode;
                current = current.getNext();
            }
        }
        return transposed;
    }

    /**
     * Transpose le graphe pondéré représenté par des listes d'adjacence vers
     * des listes d'adjacence Retourne les tableaux de listes pondérées
     * transposées directement Complexité: O(V + E) où V = nombre de sommets, E
     * = nombre d'arêtes
     */
    public WeightedNode4A[] transposeToWeightedAdjList() {
        if (this.weighted != 1) {
            return null; // Cette méthode est pour les graphes pondérés
        }

        WeightedNode4A[] transposed = new WeightedNode4A[this.n];

        // Initialiser toutes les listes à null
        for (int i = 0; i < this.n; i++) {
            transposed[i] = null;
        }

        // Parcourir chaque sommet et ses successeurs
        for (int i = 0; i < this.n; i++) {
            WeightedNode4A current = this.adjlistW[i];
            while (current != null) {
                int successor = current.getVal();
                float weight = current.getWeight();
                // Ajouter l'arc inverse avec le même poids
                WeightedNode4A newNode = new WeightedNode4A(i, transposed[successor], weight);
                transposed[successor] = newNode;
                current = current.getNext();
            }
        }
        return transposed;
    }

    /**
     * Transpose le graphe représenté par des listes d'adjacence et retourne une
     * matrice d'adjacence Complexité: O(V² + E) où V = nombre de sommets, E =
     * nombre d'arêtes
     */
    public float[][] transposeToMatrix() {
        float[][] transposed = new float[this.n][this.n];

        // Initialiser la matrice à 0
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                transposed[i][j] = 0;
            }
        }

        if (this.weighted == 0) {
            // Graphe non pondéré
            for (int i = 0; i < this.n; i++) {
                Node4A current = this.adjlist[i];
                while (current != null) {
                    int successor = current.getVal();
                    // Arc inverse: de successor vers i
                    transposed[successor][i] = 1;
                    current = current.getNext();
                }
            }
        } else {
            // Graphe pondéré
            for (int i = 0; i < this.n; i++) {
                WeightedNode4A current = this.adjlistW[i];
                while (current != null) {
                    int successor = current.getVal();
                    float weight = current.getWeight();
                    // Arc inverse: de successor vers i avec le même poids
                    transposed[successor][i] = weight;
                    current = current.getNext();
                }
            }
        }

        return transposed;
    }

    /**
     * Teste si un chemin existe dans le graphe non orienté représenté par des
     * listes d'adjacence
     *
     * @param vertices tableau des sommets du chemin (numérotés à partir de 1)
     * @return true si le chemin existe, false sinon Complexité: O(k * deg_max)
     * où k = longueur du chemin, deg_max = degré maximum
     */
    public boolean pathExists(int[] vertices) {
        if (vertices.length < 2) {
            return true; // Un chemin d'un seul sommet existe toujours
        }

        // Vérifier chaque arête du chemin
        for (int i = 0; i < vertices.length - 1; i++) {
            int from = vertices[i] - 1; // Conversion vers indexation 0
            int to = vertices[i + 1] - 1;

            // Vérifier que les sommets sont valides
            if (from < 0 || from >= this.n || to < 0 || to >= this.n) {
                return false;
            }

            // Chercher l'arête from -> to dans les listes d'adjacence
            boolean edgeFound = false;

            if (this.weighted == 0) {
                Node4A current = this.adjlist[from];
                while (current != null) {
                    if (current.getVal() == to) {
                        edgeFound = true;
                        break;
                    }
                    current = current.getNext();
                }
            } else {
                WeightedNode4A current = this.adjlistW[from];
                while (current != null) {
                    if (current.getVal() == to) {
                        edgeFound = true;
                        break;
                    }
                    current = current.getNext();
                }
            }

            if (!edgeFound) {
                return false;
            }
        }

        return true;
    }

    /**
     * TP2 - Exercice 2 : Détecte la présence d'un cycle dans le graphe Utilise
     * un système de couleurs (noir/rouge/bleu) pour détecter les back arcs
     *
     * @return true si le graphe contient un cycle, false sinon
     *
     * Complexité : O(n²) où n est le nombre de sommets
     */
    public boolean DFSCycle() {
        Numbering num = new Numbering(n);
        for (int s = 0; s < n; s++) {
            if ("noir".equals(num.getColors()[s])) {
                if (cycle(s, num)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * TP2 - Fonction auxiliaire récursive pour détecter un cycle
     *
     * @param s Sommet courant
     * @param num Objet Numbering pour stocker les couleurs
     * @return true si un cycle est détecté, false sinon
     *
     * Complexité : O(n²) où n est le nombre de sommets
     */
    public boolean cycle(int s, Numbering num) {
        num.setColor(s, "rouge");
        Node4A current = this.adjlist[s];
        while (current != null) {
            int successor = current.getVal();
            // Arc inverse: de successor vers i
            if ("noir".equals(num.getColors()[successor])) {
                if (cycle(successor, num)) { // CORRECTION: Ajouter if et retourner
                    return true;
                }
            }
            if ("rouge".equals(num.getColors()[successor])) {
                return true;
            }
            current = current.getNext();
        }
        num.setColor(s, "bleu");
        return false;
    }

    public Stack<Integer> DFSCycleP() {
        Numbering num = new Numbering(n);
        Stack<Integer> p = new Stack<Integer>();
        for (int s = 0; s < n; s++) {
            if ("noir".equals(num.getColors()[s])) {
                Stack<Integer> result = cycleP(s, num, p);
                if (!result.isEmpty()) {
                    return result;
                }
            }
        }
        return new Stack<Integer>(); // Aucun cycle trouvé
    }

    /**
     * TP2 - Fonction auxiliaire récursive pour détecter un cycle
     *
     * @param s Sommet courant
     * @param num Objet Numbering pour stocker les couleurs
     * @return true si un cycle est détecté, false sinon
     *
     * Complexité : O(n²) où n est le nombre de sommets
     */
    public Stack<Integer> cycleP(int s, Numbering num, Stack<Integer> p) {
        num.setColor(s, "rouge");
        p.push(s);
        Node4A current = this.adjlist[s];
        while (current != null) {
            int successor = current.getVal();
            // Arc inverse: de successor vers i
            if ("noir".equals(num.getColors()[successor])) {
                if (cycle(successor, num)) { // CORRECTION: Ajouter if et retourner
                    Stack<Integer> result = cycleP(successor, num, p);
                    if (!result.isEmpty()) {
                        return result;
                    }
                }
            }
            if ("rouge".equals(num.getColors()[successor])) {
                return p;
            }
            current = current.getNext();
        }
        num.setColor(s, "bleu");
        //p.pop();
        return new Stack<Integer>();
    }
}
