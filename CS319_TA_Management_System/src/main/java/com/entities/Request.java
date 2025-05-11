package com.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "requests")
@Inheritance(strategy = jakarta.persistence.InheritanceType.JOINED)
public class Request {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;
    
    private RequestTypes requestType;
    private int ownerID;
    private String message;
    private boolean pending;

    public Request(){
        this.requestType = RequestTypes.UNKNOWN;
        this.ownerID = -1;
        this.message = null;
        this.pending = true;
    }

    public Request(RequestTypes requestType, int ownerID, String message){;
        this.requestType = requestType;
        this.ownerID = ownerID;
        this.message = message;
        this.pending = true;
    }

    public int getID(){
        return this.id;
    }

    public RequestTypes getRequestType(){
        return this.requestType;
    }

    public void setRequestType(RequestTypes requestType){
        this.requestType = requestType;
    }

    public int getOwnerID(){
        return this.ownerID;
    }

    public void setOwnerID(int ownerID){
        this.ownerID = ownerID;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public boolean getPending(){
        return this.pending;
    }

    public void setPending(boolean pending){
        this.pending = pending;
    }
}
