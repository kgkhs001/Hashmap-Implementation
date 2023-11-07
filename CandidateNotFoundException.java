/**
 * Thrown when the voter votes for a non existent candidate
 */
public class CandidateNotFoundException extends Exception{
    private String name;

    CandidateNotFoundException(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
