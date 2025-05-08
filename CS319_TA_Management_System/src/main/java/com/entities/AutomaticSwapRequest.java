package com.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "automatic_swap_requests")
public class AutomaticSwapRequest extends Request {
    private int firstTAID;
    private int secondTAID;
    private int firstTAsProctoringAssignmentID;
    private int secondTAsProctoringAssignmentID;

    public AutomaticSwapRequest(){
        super();
        setRequestType(RequestTypes.AUTOMATIC_SWAP_REQUEST);
        setPending(false);
        this.firstTAID = -1;
        this.secondTAID = -1;
        this.firstTAsProctoringAssignmentID = -1;
        this.secondTAsProctoringAssignmentID = -1;
    }

    public AutomaticSwapRequest(int ownerID, String message, int firstTAID, int secondTAID, int firstTAsProctoringAssignmentID, int secondTAsProctoringAssignmentID){
        super(RequestTypes.AUTOMATIC_SWAP_REQUEST, ownerID, message);
        setPending(false);
        this.firstTAID = firstTAID;
        this.secondTAID = secondTAID;
        this.firstTAsProctoringAssignmentID = firstTAsProctoringAssignmentID;
        this.secondTAsProctoringAssignmentID = secondTAsProctoringAssignmentID;
    }

    public int getFirstTAID(){
        return this.firstTAID;
    }

    public void setFirstTAID(int firstTAID){
        this.firstTAID = firstTAID;
    }

    public int getSecondTAID(){
        return this.secondTAID;
    }

    public void setSecondTAID(int secondTAID){
        this.secondTAID = secondTAID;
    }

    public int getFirstTAsProctoringAssignmentID(){
        return this.firstTAsProctoringAssignmentID;
    }

    public void setFirstTAsProctoringAssignmentID(int firstTAsProctoringAssignmentID){
        this.firstTAsProctoringAssignmentID = firstTAsProctoringAssignmentID;
    }

    public int getSecondTAsProctoringAssignmentID(){
        return this.secondTAsProctoringAssignmentID;
    }

    public void setSecondTAsProctoringAssignmentID(int secondTAsProctoringAssignmentID){
        this.secondTAsProctoringAssignmentID = secondTAsProctoringAssignmentID;
    }
}
