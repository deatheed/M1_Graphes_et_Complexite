
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
     * Transpose la matrice d'adjacence en place (modifie la matrice actuelle)
     * Complexité: O(V²) où V = nombre de sommets
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
     * Transpose le graphe représenté par une matrice d'adjacence et retourne
     * une nouvelle matrice Complexité: O(V²) où V = nombre de sommets
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
     * Transpose le graphe représenté par une matrice d'adjacence vers des
     * listes d'adjacence non pondérées Complexité: O(V²) où V = nombre de
     * sommets
     */
    public Node4A[] transposeToAdjList() {
        if (this.weighted != 0) {
            return null; // Cette méthode est pour les graphes non pondérés
        }

        Node4A[] transposed = new Node4A[this.n];

        // Initialiser les listes à null
        for (int i = 0; i < this.n; i++) {
            transposed[i] = null;
        }

        // Parcourir la matrice et créer les listes d'adjacence transposées
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.adjmat[i][j] != 0) {
                    // Arc de i vers j devient arc de j vers i
                    Node4A newNode = new Node4A(i, transposed[j]);
                    transposed[j] = newNode;
                }
            }
        }

        return transposed;
    }

    /**
     * Transpose le graphe pondéré représenté par une matrice d'adjacence vers
     * des listes d'adjacence pondérées Complexité: O(V²) où V = nombre de
     * sommets
     */
    public WeightedNode4A[] transposeToWeightedAdjList() {
        if (this.weighted != 1) {
            return null; // Cette méthode est pour les graphes pondérés
        }

        WeightedNode4A[] transposed = new WeightedNode4A[this.n];

        // Initialiser les listes à null
        for (int i = 0; i < this.n; i++) {
            transposed[i] = null;
        }

        // Parcourir la matrice et créer les listes d'adjacence pondérées transposées
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.adjmat[i][j] != 0) {
                    // Arc de i vers j devient arc de j vers i avec le même poids
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
     * @return true si le chemin existe, false sinon Complexité: O(k) où k =
     * longueur du chemin
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

    public void DFSNum(int i,Numbering num){
        num.incrNB();
        num.valD(num.getNB(), i);
        for(int t=0;t<n;t++){
            if(adjmat[i][t]==0){
                DFSNum(t, num);
            }
        }
    }

    public Numbering DFSNumAll(){
        Numbering num = new Numbering(n);
        for (int i = 0; i < this.n; i++) {
            DFSNum(i, num);
        }
        return num;
    }

    public Numbering numbering(){
        Numbering num = new Numbering(n); 
        for(int i=0;i<n;i++){
            if(num.getD()[i]==0){
                DFSNum(i, num);
            }
        }
        return num;
    }

    public Numbering recognizeArc(){
        Numbering num = this.DFSNumAll();
        for(int s=0; s<n;s++){
            for(int t=0; t<n;t++){
                if(adjmat[s][t] != 0.0){
                    if(num.getD()[s]<num.getD()[t] && num.getD()[t]<num.getF()[t] && num.getF()[t]<num.getF()[s]){
                        num.add_ft_arc(s, t);
                    }
                    if(num.getD()[t]<num.getD()[s] && num.getD()[s]<num.getF()[s] && num.getF()[s]<num.getF()[t]){
                        num.add_b_arc(s, t);
                    }
                    if(num.getF()[t]<num.getD()[s]){
                        num.add_c_arc(s, t);
                    }
                }
            }
        }
        return num;
    }

    public boolean DFSCycle(){
        Numbering num = new Numbering(n);
        for(int s=0; s<n;s++){
            if(num.getColors()[s] == "noir"){
                if(cycle(s, num)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean cycle(int s, Numbering num){
        num.setColor(s, "rouge");
        for(int t=0; t<n; t++){
            if(adjmat[s][t]!= 0.0){
                if(num.getColors()[t] == "noir"){
                    cycle(t, num);
                }
                if(num.getColors()[t] == "rouge"){
                    return true;
                }
            }
        }
        num.setColor(s, "bleu");
        return false;
    }

    public Stack<Integer> DFSCycleP(){
        Numbering num = new Numbering(n);
        Stack<Integer> p = new Stack<Integer>();
        for(int s=0; s<n;s++){
            if(num.getColors()[s] == "noir"){
                cycleP(s,num,p);
                return p;
            }
        }
        return new Stack<Integer>();
    }

    public Stack<Integer> cycleP(int s, Numbering num,Stack<Integer> p){
        num.setColor(s, "rouge");
        p.push(s);
        for(int t=0; t<n; t++){
            if(adjmat[s][t]!= 0.0){
                if(num.getColors()[t] == "noir"){
                    p=cycleP(t, num,p);
                }
                if(num.getColors()[t] == "rouge"){
                    return p;
                }
            }
        }
        num.setColor(s, "bleu");
        return new Stack<Integer>();
    }
}
