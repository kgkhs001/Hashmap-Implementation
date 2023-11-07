import java.util.HashMap;
import java.util.LinkedList;

/**
 * handles all the functions that would happen to a collection of votes
 */
public class VotingData {
    private LinkedList<String> ballot = new LinkedList<String>();

    private HashMap<String, ListOfVotes> ballotsLikely = new HashMap<String, ListOfVotes>();

    VotingData(){}


    /**
     * gets the list of ballots from the field ballot
     * @return List of strings representing the candidates in the ballot
     */
    public LinkedList<String> getBallotData(){
        return this.ballot;
    }//gets the ballot data


    /**
     * sends a vote and makes sure that data entry is valid
     * @throws CandidateNotFoundException when a candidate does not exist in the hashmap
     * @throws CandidateChosenMoreThanOnceException when a candidate is voted for more than once in a single vote
     */
    public void submitVote(String first, String second, String third) throws CandidateNotFoundException, CandidateChosenMoreThanOnceException{
        if(!ballotsLikely.containsKey(first)){
            throw new CandidateNotFoundException(first);
        }
        else if(!ballotsLikely.containsKey(second)){
            throw new CandidateNotFoundException(second);
        }
        else if(!ballotsLikely.containsKey(third)){
            throw new CandidateNotFoundException(third);
        }

        else if(first.equals(second) || first.equals(third)){
            throw new CandidateChosenMoreThanOnceException(first);
        }
        else if(second.equals(third)){
            throw new CandidateChosenMoreThanOnceException(second);
        }
        else {
            ballotsLikely.get(first).first += 1;
            ballotsLikely.get(second).second +=1;
            ballotsLikely.get(third).third +=1;
        }
    }

    /**
     * Adds a candidate to the ballot and hashmap
     * @param cand the candidate you want to input
     * @throws RedundantCandidateException when the candidate you want to nominate already exists in the ballot
     */

    public void nominateCandidate(String cand) throws RedundantCandidateException {
        if (!ballotsLikely.containsKey(cand)) {
            ballot.add(cand);
            ballotsLikely.put(cand, new ListOfVotes(0, 0, 0));
        }
        else{
            throw new RedundantCandidateException(cand);
        }
    }

    /**
     * picks the winner based off of them having more than 50% of the first votes
     * @return a String signifying the winner
     */

    public String pickWinnerMostFirstChoice(){
        //finds the total first votes
        double totalFirstVotes = 0;
        for(String n : ballot){
            totalFirstVotes += ballotsLikely.get(n).first;
        }
        //finds out if an individuals first votes are greater than half of the total votes
        for(String n : ballot){
            if(ballotsLikely.get(n).first > totalFirstVotes/2){
                return n;
            }
        }
        return "*Requires Runoff Poll*";
    }


    /**
     * finds the biggest value in a list of integers
     * @param listOfInts a list of Integers
     * @return the biggest value in the list of Integers
     */
    public int findBiggest(LinkedList<Integer> listOfInts){
        Integer biggestValue = 0;
        for(Integer n : listOfInts){
            if(n >= biggestValue){
                biggestValue = n;
            }
        }
        return biggestValue;
    }


    /**
     * picks winner based off of the most votes in in either their first second or third vote tally
     * @return a String indicating the winner
     */

    public String pickWinnerMostAgreeable(){
        HashMap<String, Integer> bestValues = new HashMap<>();
        Integer highestVotes = 0;
        String winner = null;//this is a valid use of null because we have been told in the assignment that we are to assume that at least
        //one valid vote has been passed meaning that there are definetly candidates in the ballot meaning that this null is just a placeholder that
        //is sure to change
        //this makes a new hashmap with all the original candidates and their highest achieving votes
        for(String n : ballot){
            LinkedList<Integer> listOfInts = new LinkedList<>();
            listOfInts.add(ballotsLikely.get(n).first);
            listOfInts.add(ballotsLikely.get(n).second);
            listOfInts.add(ballotsLikely.get(n).third);
            int mostVotes = findBiggest(listOfInts);
            bestValues.put(n, mostVotes);
        }
        //finds the candidate with the highest votes in whatever section
        for(String n : ballot){
            if(bestValues.get(n) > highestVotes){
                highestVotes = bestValues.get(n);
                winner = n;
            }
        }
        return winner;
    }
}//END OF CODE