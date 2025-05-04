package com.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "manuelswaprequests")
public class ManuelSwapRequest extends Request {
    private int receiverID;
    private int ownerCourseID;
    private int receiverCourseID;

    public ManuelSwapRequest(){
        super();
        this.receiverID = -1;
        this.ownerCourseID = -1;
        this.receiverCourseID = -1;
    }

    public ManuelSwapRequest(String requestDate, int ownerID, int receiverID, int ownerCourseID, int receiverCourseID){
        super(requestDate, "Manuel Swap", ownerID);
        this.receiverID = receiverID;
        this.ownerCourseID = ownerCourseID;
        this.receiverCourseID = receiverCourseID;
    }

    public int getReceiverID(){
        return this.receiverID;
    }

    public void setReceiverID(int receiverID){
        this.receiverID = receiverID;
    }

    public int getOwnerCourseID(){
        return this.ownerCourseID;
    }

    public void setOwnerCourseID(int ownerCourseID){
        this.ownerCourseID = ownerCourseID;
    }

    public int getReceiverCourseID(){
        return this.receiverCourseID;
    }

    public void setReceiverCourseID(int receiverCourseID){
        this.receiverCourseID = receiverCourseID;
    }
}
