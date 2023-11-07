import java.util.Scanner;

/**
 * This is the stuff that goes behind the user actually making a vote in the terminal
 */
public class PollingDevice {
    private VotingData holder;
    private Scanner keyboard = new Scanner(System.in);
    PollingDevice(VotingData holder){
        this.holder = holder;
    }

    /**
     * nominates a candidate to the ballot
     * @param candName the name of the candidate you want to nominate
     * @return a String indicating if the cnadidate was added to the ballot or not
     */
    public String addWrite(String candName) {
        try{
            holder.nominateCandidate(candName);
        }
        catch (RedundantCandidateException name){
            return name.getName() + " already exists in the ballot.";
        }
        return candName + " successfully added.";
    }//end of addWrite()

    /**
     * prints the ballot in the terminal
     */
    public void printBallot() {
        System.out.println("The candidates are ");
        System.out.println(holder.getBallotData());
    }//end of printBallot()

    /**
     * prints a set of instructions on the terminal and inputs the candidates that the voter wants to vote for
     * handles exceptions that could be thrown by referncing other functions
     */
    public void screen(){
        this.printBallot();
        //first choice
        System.out.println("Write your three choices on seperate lines in order from your favorite choice to your third choice");
        try{
            String candidate = keyboard.next();
            String candidate2 = keyboard.next();
            String candidate3 = keyboard.next();
            holder.submitVote(candidate, candidate2, candidate3);
            System.out.println("You voted for " + candidate + ", " + candidate2 + ", and " + candidate3);
        }

        catch (CandidateNotFoundException name){
            System.out.println("Would you like to add " + name.getName() + "  to the ballot?");
            String answer = keyboard.next();
            if(answer.equals("Y") || answer.equals("y")){
                addWrite(name.getName());
                screen();
            }
        }
        catch (CandidateChosenMoreThanOnceException name){
            System.out.println("You cannot vote for " + name.getName() + " twice");
            screen();
        }
    }//end of screen()
}//end of code
