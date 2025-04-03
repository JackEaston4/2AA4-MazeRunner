package ca.mcmaster.se2aa4.mazerunner;

public class PathFactorizer {
    public String factorizePath(String canonical_path) {
        StringBuilder factorized_path = new StringBuilder();

        canonical_path = canonical_path.replaceAll("\\s+",""); // remove all spaces 

        int repetitions = 1;
        char previous_char = canonical_path.charAt(0);

        for (int i=1; i < canonical_path.length(); i++) {
            char current_char = canonical_path.charAt(i);

            if (current_char == previous_char) repetitions++;

            else { // if current character is not the same as last character
                if (repetitions != 1) factorized_path.append(repetitions);
            
                factorized_path.append(previous_char);
                factorized_path.append(" ");
                
                previous_char = current_char;
                repetitions = 1;
            }
        }

        // final character
        if (repetitions != 1) factorized_path.append(repetitions);
        factorized_path.append(previous_char);

        return factorized_path.toString();
    }
}
