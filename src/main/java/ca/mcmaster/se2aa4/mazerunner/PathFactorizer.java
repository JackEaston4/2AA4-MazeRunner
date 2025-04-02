package ca.mcmaster.se2aa4.mazerunner;

public class PathFactorizer {
    public String factorizePath(String canonical_path) {
        StringBuilder factorized_path = new StringBuilder();

        canonical_path = canonical_path.replaceAll("\\s+",""); // remove all spaces 

        int count = 1;
        char last_char = canonical_path.charAt(0);

        for (int i=1; i<canonical_path.length(); i++) {
            char c = canonical_path.charAt(i);

            if (c == last_char) count++;

            else { // if current character is not the same as last character
                if (count != 1) factorized_path.append(count);
            
                factorized_path.append(last_char);
                factorized_path.append(" ");
                
                last_char = c;
                count = 1;
            }
        }

        // final char
        if (count != 1) factorized_path.append(count);
        factorized_path.append(last_char);

        return factorized_path.toString();
    }
}
