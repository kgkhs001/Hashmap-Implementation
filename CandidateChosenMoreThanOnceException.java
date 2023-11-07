/**
 * Thrown when the voter votes for the same candidate more than once
 */
public class CandidateChosenMoreThanOnceException extends Exception {
    private String chosenMoreThanOnce;

    CandidateChosenMoreThanOnceException(String chosenMoreThanOnce) {
        this.chosenMoreThanOnce = chosenMoreThanOnce;
    }

    public String getName(){
        return this.chosenMoreThanOnce;
    }
}
