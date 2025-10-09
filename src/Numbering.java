import java.util.ArrayList;

public class Numbering{
    private int[] d; // profondeur lors de la recherche
    private int[] f; //date de fin ( après l'appel récursif)
    private int nb;
    class Arc { int pdep; int pfin; }
    private ArrayList<Arc> tf_a; // tree forward arc
    private ArrayList<Arc> b_a; // back arc
    private ArrayList<Arc> c_a; // cross arc
    private String[] colors; 
    

    public Numbering(int n){
        d = new int[n];
        f = new int[n];
        tf_a = new ArrayList<Arc>();
        b_a = new ArrayList<Arc>();
        c_a = new ArrayList<Arc>();
        colors = new String[n];
        nb = 0;

        for(int i=0; i<n;i++){
            d[i] = 0;
            f[i] = 0;
            colors[i] = "noir";
        }
    }
    
    // D
    public int[] getD(){
        return d;
    }
    
    public void valD(int val, int i){
        d[i] = val;
    }

    // F
    public int[] getF(){
        return f;
    }

    public void valF(int val, int i){
        f[i] = val;
    }

    public void add_ft_arc(int val1, int val2){
        Arc a = new Arc();
        a.pdep = val1;
        a.pfin = val2;
        tf_a.add(a);
    }

    public void add_b_arc(int val1, int val2){
        Arc a = new Arc();
        a.pdep = val1;
        a.pfin = val2;
        b_a.add(a);
    }

    public void add_c_arc(int val1, int val2){
        Arc a = new Arc();
        a.pdep = val1;
        a.pfin = val2;
        c_a.add(a);
    }

    public ArrayList<Arc> get_c_arc(){
        return c_a;
    }

    public ArrayList<Arc> get_tf_arc(){
        return tf_a;
    }

    public ArrayList<Arc> get_b_arc(){
        return b_a;
    }

    // NB
    public int getNB(){
        return nb;
    }

    public void incrNB(){
        nb++;
    }

    public String[] getColors(){
        return colors;
    }

    public void setColor(int indice, String color){
        colors[indice] = color;
    }

    
}
