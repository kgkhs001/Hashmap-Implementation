/**
 * This is thrown when a candidate is nominated more than once
 */
public class RedundantCandidateException extends Exception{
    private String repeatingName;
    RedundantCandidateException(String name) {
            this.repeatingName = name;
    }

    public String getName(){
        return this.repeatingName;
    }
}

