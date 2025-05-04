package com.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "automaticswaprequests")
public class AutomaticSwapRequest extends Request {
    private int firstTAID;
    private int secondTAID;

    public AutomaticSwapRequest(){
        super();
        this.firstTAID = -1;
        this.secondTAID = -1;
    }

    public AutomaticSwapRequest(String requestDate, int ownerID, int firstTAID, int secondTAID){
        super(requestDate, "Automatic Swap", ownerID);
        this.firstTAID = firstTAID;
        this.secondTAID = secondTAID;
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
}
