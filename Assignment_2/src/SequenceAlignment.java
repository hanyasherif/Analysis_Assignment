import java.util.HashMap;

public class SequenceAlignment {

    static HashMap<Character, HashMap<Character, Double>> scoringMatrix;
    static final double EPSILON = 1e-9;

    public static void buildScoringMatrix() {

    	scoringMatrix = new HashMap<>();
        scoringMatrix.put('A', new HashMap<>());
        scoringMatrix.put('C', new HashMap<>());
        scoringMatrix.put('G', new HashMap<>());
        scoringMatrix.put('T', new HashMap<>());
        scoringMatrix.put('-', new HashMap<>());

        scoringMatrix.get('A').put('A', 1.0);
        scoringMatrix.get('A').put('G', -0.8);
        scoringMatrix.get('A').put('T', -0.2);
        scoringMatrix.get('A').put('C', -2.3);
        scoringMatrix.get('A').put('-', -0.6);

        scoringMatrix.get('G').put('A', -0.8);
        scoringMatrix.get('G').put('G', 1.0);
        scoringMatrix.get('G').put('T', -1.1);
        scoringMatrix.get('G').put('C', -0.7);
        scoringMatrix.get('G').put('-', -1.5);

        scoringMatrix.get('T').put('A', -0.2);
        scoringMatrix.get('T').put('G', -1.1);
        scoringMatrix.get('T').put('T', 1.0);
        scoringMatrix.get('T').put('C', -0.5);
        scoringMatrix.get('T').put('-', -0.9);
       
        scoringMatrix.get('C').put('A', -2.3);
        scoringMatrix.get('C').put('G', -0.7);
        scoringMatrix.get('C').put('T', -0.5);
        scoringMatrix.get('C').put('C', 1.0);
        scoringMatrix.get('C').put('-', -1.0);

        scoringMatrix.get('-').put('A', -0.6);
        scoringMatrix.get('-').put('G', -1.5);
        scoringMatrix.get('-').put('T', -0.9);
        scoringMatrix.get('-').put('C', -1.0);
        scoringMatrix.get('-').put('-', 0.0);
    }
    

    public static boolean isEqual(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    public static String[] alignSequences(String x, String y) {
        int n = x.length();
        int m = y.length();

        double[][] dp = new double[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            dp[i][0] = dp[i - 1][0] + scoringMatrix.get(x.charAt(i - 1)).get('-');
        }
        for (int j = 1; j <= m; j++) {
            dp[0][j] = dp[0][j - 1] + scoringMatrix.get('-').get(y.charAt(j - 1));
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                double match = dp[i - 1][j - 1] + scoringMatrix.get(x.charAt(i - 1)).get(y.charAt(j - 1));
                double delete = dp[i - 1][j] + scoringMatrix.get(x.charAt(i - 1)).get('-');
                double insert = dp[i][j - 1] + scoringMatrix.get('-').get(y.charAt(j - 1));
                dp[i][j] = Math.max(Math.max(match, delete), insert);
            }
        }

        StringBuilder alignedX = new StringBuilder();
        StringBuilder alignedY = new StringBuilder();
        int i = n, j = m;
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && dp[i][j] == dp[i - 1][j - 1] + scoringMatrix.get(x.charAt(i - 1)).get(y.charAt(j - 1))) {
                alignedX.insert(0, x.charAt(i - 1));
                alignedY.insert(0, y.charAt(j - 1));
                i--;
                j--;
            } else if (i > 0 && dp[i][j] == dp[i - 1][j] + scoringMatrix.get(x.charAt(i - 1)).get('-')) {
                alignedX.insert(0, x.charAt(i - 1));
                alignedY.insert(0, '-');
                i--;
            } else {
                alignedX.insert(0, '-');
                alignedY.insert(0, y.charAt(j - 1));
                j--;
            }
        }

        String[] alignment = new String[2];
        alignment[0] = alignedX.toString();
        alignment[1] = alignedY.toString();

        String alignmentScore;
        if (isEqual(dp[n][m], 0.0)) {
            alignmentScore = "0.0";
        } else {
            alignmentScore = String.valueOf(dp[n][m]);
        }
        return new String[]{alignedX.toString(), alignedY.toString(), alignmentScore};
   }
    
    public static void main(String[] args) {
    	String a = "ATGCC";
    	String b = "TACGCA";
    	
        String x = "TCCCAGTTATGTCAGGGGACACGAGCATGCAGAGAC";
        String y = "AATTGCCGCCGTCGTTTTCAGCAGTTATGTCAGATC";
    	
        buildScoringMatrix();

        String[] result1 = alignSequences(a, b);
        String[] result2 = alignSequences(x, y);
        
        System.out.println("First example answer:");
        for (int i = 0; i < result1.length - 1; i++) {
            System.out.println(result1[i]);
        }
        System.out.println("Alignment Score: " + result1[result1.length - 1]);

        System.out.println();
        System.out.println("Second example answer:");
        for (int i = 0; i < result2.length - 1; i++) {
            System.out.println(result2[i]);
        }
        System.out.println("Alignment Score: " + result2[result2.length - 1]);
    }
}
