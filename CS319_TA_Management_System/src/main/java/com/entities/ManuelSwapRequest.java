package com.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "manuel_swap_requests")
public class ManuelSwapRequest extends Request {
    private int receiverID;
    private int ownerProctoringAssignmentID;
    private int receiverProctoringAssignmentID;
    
    public ManuelSwapRequest(){
        super();
        setRequestType(RequestTypes.MANUEL_SWAP_REQUEST);
        this.receiverID = -1;
        this.ownerProctoringAssignmentID = -1;
        this.receiverProctoringAssignmentID = -1;
    }

    public ManuelSwapRequest(int ownerID, String message, int receiverID, int ownerProctoringAssignmentID, int receiverProctoringAssignmentID){
        super(RequestTypes.MANUEL_SWAP_REQUEST, ownerID, message);
        this.receiverID = receiverID;
        this.ownerProctoringAssignmentID = ownerProctoringAssignmentID;
        this.receiverProctoringAssignmentID = receiverProctoringAssignmentID;
    }

    public int getReceiverID(){
        return this.receiverID;
    }

    public void setReceiverID(int receiverID){
        this.receiverID = receiverID;
    }

    public int getOwnerProctoringAssignmentID(){
        return this.ownerProctoringAssignmentID;
    }

    public void setOwnerProctoringAssignmentID(int ownerProctoringAssignmentID){
        this.ownerProctoringAssignmentID = ownerProctoringAssignmentID;
    }

    public int getReceiverProctoringAssignmentID(){
        return this.receiverProctoringAssignmentID;
    }

    public void setReceiverProctoringAssignmentID(int receiverProctoringAssignmentID){
        this.receiverProctoringAssignmentID = receiverProctoringAssignmentID;
    }
}
