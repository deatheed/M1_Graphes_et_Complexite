public class Numbering{
    private int[] d;
    private int[] f;
    private int nb;

    public Numbering(int n){
        d = new int[n];
        f = new int[n];
        nb = 0;

        for(int i=0; i<n;i++){
            d[i] = 0;
            f[i] = 0;
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

    // NB
    public int getNB(){
        return nb;
    }

    public void incrNB(){
        nb++;
    }

    
}