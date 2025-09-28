
public class Tools4A {

    public static void printArray(int[] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(" " + tab[i]);
        }
        System.out.println();
    }

    public static void printMatrix(float[][] mat) {
        String maString = "\n";
        for (float[] line : mat) {
            for (float val : line) {
                maString += val + " ";
            }
            maString += "\n";
        }
        System.out.println(maString + "\n");
    }

}
