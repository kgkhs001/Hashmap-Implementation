import static org.junit.Assert.*;
import org.junit.Test;
import java.util.LinkedList;

public class Examples {
    public Examples(){}
    // method to set up a ballot and cast votes

    VotingData setup1() {
        VotingData vd = new VotingData();

        // put candidates on the ballot
        try {
            vd.nominateCandidate("gompei");
            vd.nominateCandidate("husky");
            vd.nominateCandidate("ziggy");

        } catch (RedundantCandidateException e) {}

        // cast votes

        try {
            vd.submitVote("husky", "gompei", "ziggy");
            vd.submitVote("gompei", "ziggy", "husky");
            vd.submitVote("husky", "gompei", "ziggy");

        } catch (CandidateChosenMoreThanOnceException | CandidateNotFoundException e) {}

        return(vd);
    }
    @Test
    public void testMostFirstWinner () {//husky has the most first votes
        assertEquals ("husky", setup1().pickWinnerMostFirstChoice());
    }
    @Test
    public void testAgreeableFirst () {//husky has the most first votes
        assertEquals ("husky", setup1().pickWinnerMostFirstChoice());
    }




    VotingData setup2() {
        VotingData vd = new VotingData();

        // put candidates on the ballot
        try {
            vd.nominateCandidate("gompei");
            vd.nominateCandidate("husky");
            vd.nominateCandidate("ziggy");

        } catch (RedundantCandidateException e) {}//this means that the nominate candidate also works

        // cast votes

        try {
            vd.submitVote("gompei", "husky", "ziggy");//and these seem to be working correctly
            vd.submitVote("ziggy", "husky", "gompei");
            vd.submitVote("gompei", "husky", "ziggy");

        } catch (CandidateChosenMoreThanOnceException | CandidateNotFoundException e) {}

        return(vd);
    }

    @Test
    public void testAgreeableWinner (){//this works
        assertEquals("husky", setup2().pickWinnerMostAgreeable());
    }
    @Test
    public void testFirst (){//this works
        assertEquals("gompei", setup2().pickWinnerMostFirstChoice());
    }
    //pick winner methods work



    @Test//helper method
    public void testFindBiggest(){
        LinkedList<Integer> listOfInts = new LinkedList<>();
        listOfInts.add(1);
        listOfInts.add(3);
        listOfInts.add(5);
        listOfInts.add(0);
        assertEquals(setup1().findBiggest(listOfInts), 5);
    }




    VotingData setupCheckWinners() {
        VotingData vd = new VotingData();

        // put candidates on the ballot
        try {
            vd.nominateCandidate("gompei");
            vd.nominateCandidate("husky");
            vd.nominateCandidate("ziggy");
            vd.nominateCandidate("krishna");

        } catch (RedundantCandidateException e) {}//this means that the nominate candidate also works

        // cast votes

        try {
            vd.submitVote("gompei", "husky", "ziggy");//and these seem to be working correctly
            vd.submitVote("husky", "gompei", "ziggy");
            vd.submitVote("ziggy", "husky", "gompei");
            vd.submitVote("krishna", "husky", "ziggy");
            //every candidate has 25 percent of the total first votes
        }
        catch (CandidateChosenMoreThanOnceException | CandidateNotFoundException e) {}

        return(vd);
    }


    @Test
    public void testRunoff(){
        assertEquals(setupCheckWinners().pickWinnerMostFirstChoice(), "*Requires Runoff Poll*");
    }
    @Test
    public void testAgreeableDefault(){
        assertEquals(setupCheckWinners().pickWinnerMostAgreeable(), "husky");
        //should be the first one that has the biggest number of votes in one position
    }

    //EXCEPTION CHECKS
    VotingData setup3() {
        VotingData vd = new VotingData();

        // put candidates on the ballot
        try {
            vd.nominateCandidate("gompei");
            vd.nominateCandidate("husky");//due to there being a repeat this should throw a RedundantCandidateException
            vd.nominateCandidate("ziggy");

        } catch (RedundantCandidateException e) {}//this means that the nominate candidate also works

        // cast votes

        try {
            vd.submitVote("gompei", "husky", "ziggy");//and these seem to be working correctly
            vd.submitVote("ziggy", "husky", "gompei");
            vd.submitVote("gompei", "husky", "ziggy");
        }
        catch (CandidateChosenMoreThanOnceException | CandidateNotFoundException e) {}

        return(vd);
    }

    //Happens when we try to add an existing candidate to the ballot
    @Test(expected=RedundantCandidateException.class)
    public void checkRedundantCandidateException() throws RedundantCandidateException {
        setup3().nominateCandidate("husky");
    }

    @Test(expected=RedundantCandidateException.class)
    public void checkRedundantCandidateException2() throws RedundantCandidateException {
        setup3().nominateCandidate("gompei");
    }

    @Test(expected=RedundantCandidateException.class)
    public void checkRedundantCandidateException3() throws RedundantCandidateException {
        setup3().nominateCandidate("ziggy");
    }



    //checks if a candidate is voted for more than once
    @Test(expected=CandidateChosenMoreThanOnceException.class)
    public void checkCandChosenMoreThanOnce() throws CandidateChosenMoreThanOnceException, CandidateNotFoundException {
        setup3().submitVote("husky", "husky", "gompei");
    }

    @Test(expected=CandidateChosenMoreThanOnceException.class)
    public void checkCandChosenMoreThanOnce2() throws CandidateChosenMoreThanOnceException, CandidateNotFoundException {
        setup3().submitVote("husky", "ziggy", "ziggy");
    }

    @Test(expected=CandidateChosenMoreThanOnceException.class)
    public void checkCandChosenMoreThanOnce3() throws CandidateChosenMoreThanOnceException, CandidateNotFoundException {
        setup3().submitVote("gompei", "ziggy", "gompei");
    }


    //checks if the voter voted for a non existent candidate
    @Test(expected=CandidateNotFoundException.class)
    public void checkCandNotFound() throws CandidateNotFoundException, CandidateChosenMoreThanOnceException {
        setup3().submitVote("krishna", "husky", "gompei");//krishna is not in the ballot
    }


    @Test(expected=CandidateNotFoundException.class)
    public void checkCandNotFound2() throws CandidateNotFoundException, CandidateChosenMoreThanOnceException {
        setup3().submitVote("husky", "husky", "krishna");//krishna is not in the ballot
    }


    @Test(expected=CandidateNotFoundException.class)
    public void checkCandNotFound3() throws CandidateNotFoundException, CandidateChosenMoreThanOnceException {
        setup3().submitVote("gompei", "krishna", "gompei");//krishna is not in the ballot
    }
}//END OF FILE
