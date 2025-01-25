package ca.mcmaster.se2aa4.mazerunner;

public class PathFactorizer {
    public String factorizePath(String canonical_path) {
        StringBuilder factorized_path = new StringBuilder();

        int count = 1;
        char last_char = canonical_path.charAt(0);
        char c;

        for (int i=1; i<canonical_path.length(); i++) {
            c = canonical_path.charAt(i);

            if (c == last_char) {
                count++;
            }
            else {
                if (count != 1) {
                    factorized_path.append(count);
                }
                
                factorized_path.append(last_char);
                factorized_path.append(" ");
                
                last_char = c;
                count = 1;
            }
        }

        // final char
        factorized_path.append(count);
        factorized_path.append(last_char);

        return factorized_path.toString();
    }
}
