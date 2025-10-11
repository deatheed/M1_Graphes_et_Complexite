
import java.util.Scanner;
import java.util.Stack;

public class GraphM4A {

    private int n;
    private int type; //0 if undirected, 1 if directed
    private int weighted; // 0 if unweighted, 1 otherwise
    private float[][] adjmat;

    public GraphM4A(Scanner sc) {
        String[] firstline = sc.nextLine().split(" ");
        this.n = Integer.parseInt(firstline[0]);
        System.out.println("Number of vertices " + this.n);
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

        this.adjmat = new float[this.n][this.n];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                adjmat[i][j] = 0; // replace 0 with something else if the weights can be 0
            }
        }
        for (int k = 0; k < this.n; k++) {
            String[] line = sc.nextLine().split(" : ");
            int i = Integer.parseInt(line[0]); //the vertex "source"
            if (weighted == 0) {
                if ((line.length > 1) && (line[1].charAt(0) != ' ')) {
                    String[] successors = line[1].split(", ");
                    for (int h = 0; h < successors.length; h++) {
                        this.adjmat[i - 1][Integer.parseInt(successors[h]) - 1] = 1;
                    }
                }
            } else {
                line = line[1].split(" // ");
                if ((line.length == 2) && (line[1].charAt(0) != ' ')) {// if there really are somme successors, then we must have something different from " " after "// "
                    String[] successors = line[0].split(", ");
                    String[] theirweights = line[1].split(", ");
                    for (int h = 0; h < successors.length; h++) {
                        this.adjmat[i - 1][Integer.parseInt(successors[h]) - 1] = Float.parseFloat(theirweights[h]);
                    }
                }
            }
        }
    }

//method to be applied only when type=0
    public int[] degree() {
        int[] tmp = new int[this.n];
        for (int i = 0; i < this.n; i++) {
            tmp[i] = 0;
        }
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.adjmat[i][j] != 0) {
                    tmp[i] = tmp[i] + 1;
                }
            }
        }
        return tmp;

    }

//method to be applied only when type=1
    public TwoArrays4A degrees() {
        int[] tmp1 = new int[this.n]; //indegrees
        int[] tmp2 = new int[this.n]; //outdegrees
        for (int i = 0; i < this.n; i++) {
            tmp1[i] = 0;
            tmp2[i] = 0;
        }
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.adjmat[i][j] != 0) {
                    tmp2[i] = tmp2[i] + 1;
                    tmp1[j] = tmp1[j] + 1;
                }
            }
        }
        return (new TwoArrays4A(tmp1, tmp2));

    }

    public int getType() {
        return this.type;
    }

    public String getString() {
        String s = "";
        for (float[] i : this.adjmat) {
            for (float j : i) {
                s += j + " ";
            }
            s += "\n";
        }
        return s;
    }

    /**
     * Remplace adjmat par ca transposé
     *
     * Complexité : O(n²) où n le nb de sommets
     */
    public void transpose() {
        // Transposer uniquement la partie triangulaire supérieure
        // pour éviter de transposer deux fois les mêmes éléments
        for (int i = 0; i < this.n; i++) {
            for (int j = i + 1; j < this.n; j++) {
                // Échanger adjmat[i][j] avec adjmat[j][i]
                float temp = this.adjmat[i][j];
                this.adjmat[i][j] = this.adjmat[j][i];
                this.adjmat[j][i] = temp;
            }
        }
    }

    public int getWeighted() {
        return weighted;
    }

    public int getN() {
        return n;
    }

    /**
     * TP1 - Exercice 1 : Calcule le graphe transposé Gt (Matrice -> Matrice)
     * Crée une nouvelle matrice d'adjacence représentant le graphe transposé
     *
     * @return Nouvelle matrice d'adjacence transposée
     *
     * Complexité : O(n²) où n est le nombre de sommets
     */
    public float[][] transposeToMatrix() {
        float[][] transposed = new float[this.n][this.n];

        // Transposer la matrice
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                transposed[j][i] = this.adjmat[i][j];
            }
        }

        return transposed;
    }

    /**
     * TP1 - Exercice 1 : Calcule le graphe transposé Gt (Matrice -> Liste non
     * pondérée) Convertit la matrice d'adjacence en listes d'adjacence
     * transposées
     *
     * @return Tableau de listes d'adjacence non pondérées représentant Gt, null
     * si le graphe est pondéré
     *
     * Complexité : O(n²) où n est le nombre de sommets
     */
    public Node4A[] transposeToAdjList() {
        if (this.weighted != 0) {
            return null; // Cette méthode est uniquement pour les graphes non pondérés
        }

        Node4A[] transposed = new Node4A[this.n];
        // Initialiser toutes les listes à null
        for (int i = 0; i < this.n; i++) {
            transposed[i] = null;
        }

        // Parcourir la matrice et créer les listes d'adjacence transposées
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.adjmat[i][j] != 0) {
                    // Arc de i vers j devient arc de j vers i
                    // CORRECTION: Stocker avec indexation cohérente (0-based)
                    Node4A newNode = new Node4A(i, transposed[j]);
                    transposed[j] = newNode;
                }
            }
        }
        return transposed;
    }

    /**
     * TP1 - Exercice 1 : Transpose le matrice pondéré adjmat et retourne des
     * listes d'adjacence pondéré en tant que transposées.
     *
     * @return Un tableau de listes d'adjacence pondérées représentant Gt et
     * null si le graphe est non pondéré
     *
     * Complexité : O(n²) où n le nb de sommets
     */
    public WeightedNode4A[] transposeToWeightedAdjList() {
        if (this.weighted != 1) {
            return null; // Cette méthode est uniquement pour les graphes pondérés
        }

        WeightedNode4A[] transposed = new WeightedNode4A[this.n];

        // Initialiser toutes les listes à null
        for (int i = 0; i < this.n; i++) {
            transposed[i] = null;
        }

        // Parcourir la matrice et créer les listes d'adjacence pondérées transposées
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.adjmat[i][j] != 0) {
                    // Arc de i vers j avec poids w devient arc de j vers i avec même poids
                    // Ajouter i en tête de la liste d'adjacence de j avec le poids correspondant
                    WeightedNode4A newNode = new WeightedNode4A(i, transposed[j], this.adjmat[i][j]);
                    transposed[j] = newNode;
                }
            }
        }

        return transposed;
    }

    /**
     * Teste si un chemin existe dans le graphe non orienté représenté par une
     * matrice d'adjacence
     *
     * @param vertices tableau des sommets du chemin (numérotés à partir de 1)
     * @return true si le chemin existe, false sinon
     *
     * Complexité: O(k) où k = longueur du chemin
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

            // Vérifier l'existence de l'arête dans la matrice
            if (this.adjmat[from][to] == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * TP2 - Parcours en profondeur récursif à partir du sommet i Numérote les
     * sommets dans l'ordre de découverte (d) et de fin (f)
     *
     * @param i Sommet de départ (indexation 0)
     * @param num Objet Numbering pour stocker les numéros
     *
     * Complexité : O(n²) où n est le nombre de sommets
     */
    public void DFSNum(int i, Numbering num) {
        num.incrNB();
        num.valD(num.getNB(), i);
        for (int t = 0; t < n; t++) {
            if (adjmat[i][t] != 0) {
                if (num.getD()[t] == 0) {
                    DFSNum(t, num);
                }
            }
        }
        num.incrNB();
        num.valF(num.getNB(), i);
    }

    /**
     * TP2 - Parcours en profondeur complet de tous les sommets du graphe Lance
     * DFSNum sur tous les sommets pour numéroter tous les arcs
     *
     * @return Objet Numbering contenant les numérotations d et f
     *
     * Complexité : O(n³) où n est le nombre de sommets (n fois DFSNum)
     */
    public Numbering DFSNumAll() {
        Numbering num = new Numbering(n);
        for (int i = 0; i < this.n; i++) {
            if (num.getD()[i] == 0) {
                DFSNum(i, num);
            }
        }
        return num;
    }

    /**
     * TP2 - Parcours en profondeur avec numérotation des sommets non visités
     * Lance DFSNum uniquement sur les sommets non encore découverts
     *
     * @return Objet Numbering contenant les numérotations d et f
     *
     * Complexité : O(n³) où n est le nombre de sommets
     */
    public Numbering numbering() {
        Numbering num = new Numbering(n);
        for (int i = 0; i < n; i++) {
            if (num.getD()[i] == 0) {
                DFSNum(i, num);
            }
        }
        return num;
    }

    /**
     * TP2 - Exercice 1 : Identifie le type de chaque arc du graphe Classifie
     * les arcs en : tree/forward (tf), back (b), ou cross (c)
     *
     * @return Objet Numbering contenant les classifications des arcs
     *
     * Complexité : O(n³) où n est le nombre de sommets (DFSNumAll +
     * classification)
     */
    public Numbering recognizeArc() {
        Numbering num = this.DFSNumAll();
        for (int s = 0; s < n; s++) {
            for (int t = 0; t < n; t++) {
                if (adjmat[s][t] != 0.0) {
                    if (num.getD()[s] < num.getD()[t] && num.getD()[t] < num.getF()[t] && num.getF()[t] < num.getF()[s]) {
                        num.add_ft_arc(s, t);
                    }
                    if (num.getD()[t] < num.getD()[s] && num.getD()[s] < num.getF()[s] && num.getF()[s] < num.getF()[t]) {
                        num.add_b_arc(s, t);
                    }
                    if (num.getF()[t] < num.getD()[s]) {
                        num.add_c_arc(s, t);
                    }
                }
            }
        }
        return num;
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
        for (int t = 0; t < n; t++) {
            if (adjmat[s][t] != 0.0) {
                if ("noir".equals(num.getColors()[t])) {
                    if (cycle(t, num)) { // CORRECTION: Ajouter if et retourner
                        return true;
                    }
                }
                if ("rouge".equals(num.getColors()[t])) {
                    return true;
                }
            }
        }
        num.setColor(s, "bleu");
        return false;
    }

    /**
     * TP2 - Exercice 3 : Trouve un cycle dans le graphe et retourne ses sommets
     *
     * @return Stack contenant les sommets du cycle, pile vide si aucun cycle
     *
     * Complexité : O(n²) où n est le nombre de sommets
     */
    public Stack<Integer> DFSCycleP() {
        Numbering num = new Numbering(n);
        Stack<Integer> p = new Stack<Integer>();
        for (int s = 0; s < n; s++) {
            System.out.println(s);
            if ("noir".equals(num.getColors()[s])) {
                System.out.println("Noir\n");
                Stack<Integer> result = cycleP(s, num, p);
                if (!result.isEmpty()) {
                    return result;
                }
            }
        }
        return new Stack<Integer>(); // Aucun cycle trouvé
    }

    /**
     * TP2 - Fonction auxiliaire récursive pour trouver les sommets d'un cycle
     *
     * @param s Sommet courant
     * @param num Objet Numbering pour les couleurs
     * @param p Pile contenant le chemin courant
     * @return Stack contenant le cycle si trouvé, pile vide sinon
     *
     * Complexité : O(n²) où n est le nombre de sommets
     */
    public Stack<Integer> cycleP(int s, Numbering num, Stack<Integer> p) {
        num.setColor(s, "rouge");
        System.out.println("Rouge\n");
        p.push(s);
        for (int t = 0; t < n; t++) {
            System.out.println(t);
            if (adjmat[s][t] != 0.0) {
                if ("noir".equals(num.getColors()[t])) {
                    Stack<Integer> result = cycleP(t, num, p);
                    if (!result.isEmpty()) {
                        System.out.println(t + "\\ " + s);
                        return result;
                    }
                }
                if ("rouge".equals(num.getColors()[t])) {
                    System.out.println(t + "XXXXXXXXXXXXXX " + s);
                    p.push(t);
                    return p;
                }
            }
        }
        num.setColor(s, "bleu");
        System.out.println("Bleu\n");
        return new Stack<Integer>();
    }

// Ajouter ces fonctions à la classe GraphM4A
    /**
     * Parcours en largeur (BFS) à partir d'un sommet source
     *
     * @param s Sommet de départ (indexation 0)
     * @return Tableau des distances depuis s (-1 si non atteignable) Complexité
     * : O(n²) où n est le nombre de sommets (pour matrice d'adjacence)
     */
    public int[] BFS(int s) {
        int[] distances = new int[n];
        boolean[] visited = new boolean[n];

        // Initialisation
        for (int i = 0; i < n; i++) {
            distances[i] = -1;
            visited[i] = false;
        }

        // File implémentée avec un tableau circulaire
        int[] queue = new int[n];
        int front = 0;
        int rear = 0;
        int size = 0;

        // Démarrer depuis le sommet s
        visited[s] = true;
        distances[s] = 0;
        queue[rear] = s;
        rear = (rear + 1) % n;
        size++;

        while (size > 0) {
            int current = queue[front];
            front = (front + 1) % n;
            size--;

            // Explorer tous les voisins
            for (int neighbor = 0; neighbor < n; neighbor++) {
                if (adjmat[current][neighbor] != 0 && !visited[neighbor]) {
                    visited[neighbor] = true;
                    distances[neighbor] = distances[current] + 1;
                    queue[rear] = neighbor;
                    rear = (rear + 1) % n;
                    size++;
                }
            }
        }

        return distances;
    }

    /**
     * Parcours BFS complet qui retourne l'arbre de parcours
     *
     * @param s Sommet de départ (indexation 0)
     * @return Tableau des parents dans l'arbre BFS (-1 si racine ou non
     * atteignable) Complexité : O(n²) où n est le nombre de sommets
     */
    public int[] BFSTree(int s) {
        int[] parent = new int[n];
        boolean[] visited = new boolean[n];

        // Initialisation
        for (int i = 0; i < n; i++) {
            parent[i] = -1;
            visited[i] = false;
        }

        // File implémentée avec un tableau circulaire
        int[] queue = new int[n];
        int front = 0;
        int rear = 0;
        int size = 0;

        visited[s] = true;
        queue[rear] = s;
        rear = (rear + 1) % n;
        size++;

        while (size > 0) {
            int current = queue[front];
            front = (front + 1) % n;
            size--;

            for (int neighbor = 0; neighbor < n; neighbor++) {
                if (adjmat[current][neighbor] != 0 && !visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = current;
                    queue[rear] = neighbor;
                    rear = (rear + 1) % n;
                    size++;
                }
            }
        }

        return parent;
    }

    /**
     * Calcule la matrice d'incidence du graphe Lignes = sommets, Colonnes =
     * arêtes
     *
     * @return Matrice d'incidence Complexité : O(n² * m) où n est le nombre de
     * sommets et m le nombre d'arêtes
     */
    public int[][] incidenceMatrix() {
        // Compter le nombre d'arêtes
        int edgeCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (adjmat[i][j] != 0) {
                    if (this.type == 0) { // Non orienté
                        if (i <= j) { // Compter chaque arête une seule fois
                            edgeCount++;
                        }
                    } else { // Orienté
                        edgeCount++;
                    }
                }
            }
        }

        // Créer la matrice d'incidence
        int[][] incidence = new int[n][edgeCount];

        // Initialiser à 0
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < edgeCount; j++) {
                incidence[i][j] = 0;
            }
        }

        // Remplir la matrice
        int edgeIndex = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (adjmat[i][j] != 0) {
                    if (this.type == 0) { // Non orienté
                        if (i <= j) {
                            incidence[i][edgeIndex] = 1;
                            incidence[j][edgeIndex] = 1;
                            edgeIndex++;
                        }
                    } else { // Orienté
                        incidence[i][edgeIndex] = -1; // Départ
                        incidence[j][edgeIndex] = 1;  // Arrivée
                        edgeIndex++;
                    }
                }
            }
        }

        return incidence;
    }

    /**
     * Calcule un tri topologique du graphe orienté acyclique (DAG) Utilise DFS
     * et empile les sommets dans l'ordre de fin de traitement
     *
     * @return Tableau représentant l'ordre topologique, null si le graphe
     * contient un cycle Complexité : O(n²) où n est le nombre de sommets
     */
    public int[] topologicalSort() {
        // Vérifier si le graphe est orienté
        if (this.type == 0) {
            System.out.println("Le tri topologique n'est possible que pour les graphes orientés");
            return null;
        }

        // Vérifier s'il y a un cycle
        if (DFSCycle()) {
            System.out.println("Le graphe contient un cycle, tri topologique impossible");
            return null;
        }

        // Utiliser une pile pour stocker l'ordre
        Stack<Integer> stack = new Stack<Integer>();
        boolean[] visited = new boolean[n];

        // Initialiser visited
        for (int i = 0; i < n; i++) {
            visited[i] = false;
        }

        // Appeler DFS pour chaque sommet non visité
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }

        // Dépiler pour obtenir l'ordre topologique
        int[] result = new int[n];
        int index = 0;
        while (!stack.isEmpty()) {
            result[index++] = stack.pop();
        }

        return result;
    }

    /**
     * Fonction auxiliaire récursive pour le tri topologique
     *
     * @param v Sommet courant
     * @param visited Tableau de visite
     * @param stack Pile pour stocker l'ordre Complexité : O(n²) où n est le
     * nombre de sommets
     */
    private void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;

        // Parcourir tous les voisins
        for (int i = 0; i < n; i++) {
            if (adjmat[v][i] != 0 && !visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }

        // Empiler le sommet après avoir visité tous ses descendants
        stack.push(v);
    }

    /**
     * Teste la connexité du graphe et retourne les composantes connexes
     *
     * @return Tableau 2D où chaque ligne est une composante (terminée par -1)
     * Complexité : O(n²) où n est le nombre de sommets
     */
    public int[][] connectedComponents() {
        boolean[] visited = new boolean[n];
        int[][] components = new int[n][n]; // Maximum n composantes de n sommets
        int componentCount = 0;

        // Initialiser visited
        for (int i = 0; i < n; i++) {
            visited[i] = false;
        }

        // Pour chaque sommet non visité, lancer un DFS
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int[] component = new int[n];
                int size = 0;
                exploreComponent(i, visited, component, size);

                // Copier la composante dans le tableau de résultats
                for (int j = 0; j < n; j++) {
                    components[componentCount][j] = component[j];
                }
                componentCount++;
            }
        }

        // Créer un tableau de la bonne taille
        int[][] result = new int[componentCount][];
        for (int i = 0; i < componentCount; i++) {
            // Compter les sommets dans cette composante (jusqu'au premier -1)
            int compSize = 0;
            for (int j = 0; j < n && components[i][j] != -1; j++) {
                compSize++;
            }
            result[i] = new int[compSize];
            for (int j = 0; j < compSize; j++) {
                result[i][j] = components[i][j];
            }
        }

        return result;
    }

    /**
     * Fonction auxiliaire pour explorer une composante connexe avec DFS
     *
     * @param v Sommet de départ
     * @param visited Tableau de visite
     * @param component Tableau pour stocker les sommets de la composante
     * @param index Index courant dans le tableau component
     * @return Nouvel index après ajout Complexité : O(n²) où n est le nombre de
     * sommets
     */
    private int exploreComponent(int v, boolean[] visited, int[] component, int index) {
        visited[v] = true;
        component[index] = v;
        index++;

        // Marquer la fin si on atteint la fin du tableau
        if (index < n) {
            component[index] = -1;
        }

        // Explorer tous les voisins (sans considérer la direction pour les graphes orientés)
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                // Pour graphe non orienté ou orienté (connexité forte considérée comme faible ici)
                if (adjmat[v][i] != 0 || adjmat[i][v] != 0) {
                    index = exploreComponent(i, visited, component, index);
                }
            }
        }

        return index;
    }

    /**
     * Teste si le graphe est connexe
     *
     * @return true si le graphe est connexe, false sinon Complexité : O(n²) où
     * n est le nombre de sommets
     */
    public boolean isConnected() {
        int[][] components = connectedComponents();
        return components.length == 1;
    }

    /**
     * Retourne le nombre de composantes connexes
     *
     * @return Nombre de composantes connexes Complexité : O(n²) où n est le
     * nombre de sommets
     */
    public int numberOfConnectedComponents() {
        return connectedComponents().length;
    }

    /**
     * Affiche les composantes connexes du graphe Complexité : O(n²) où n est le
     * nombre de sommets
     */
    public void printConnectedComponents() {
        int[][] components = connectedComponents();
        System.out.println("Nombre de composantes connexes : " + components.length);

        for (int i = 0; i < components.length; i++) {
            System.out.print("Composante " + (i + 1) + " : {");
            for (int j = 0; j < components[i].length; j++) {
                System.out.print(components[i][j]);
                if (j < components[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("}");
        }
    }

    /**
     * Affiche la matrice d'incidence Complexité : O(n * m) où n est le nombre
     * de sommets et m le nombre d'arêtes
     */
    public void printIncidenceMatrix() {
        int[][] incidence = incidenceMatrix();
        if (incidence == null) {
            System.out.println("Impossible de créer la matrice d'incidence");
            return;
        }

        System.out.println("Matrice d'incidence :");
        for (int i = 0; i < incidence.length; i++) {
            for (int j = 0; j < incidence[i].length; j++) {
                System.out.print(incidence[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Affiche l'ordre topologique Complexité : O(n²) où n est le nombre de
     * sommets
     */
    public void printTopologicalSort() {
        int[] order = topologicalSort();
        if (order == null) {
            return;
        }

        System.out.print("Ordre topologique : ");
        for (int i = 0; i < order.length; i++) {
            System.out.print(order[i]);
            if (i < order.length - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    /**
     * Affiche les distances depuis un sommet (résultat BFS)
     *
     * @param s Sommet source Complexité : O(n²) où n est le nombre de sommets
     */
    public void printBFS(int s) {
        int[] distances = BFS(s);
        System.out.println("Distances depuis le sommet " + s + " :");
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] == -1) {
                System.out.println("Sommet " + i + " : non atteignable");
            } else {
                System.out.println("Sommet " + i + " : " + distances[i]);
            }
        }
    }

}
