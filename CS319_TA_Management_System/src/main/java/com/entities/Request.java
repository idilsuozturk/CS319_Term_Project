package com.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "requests")
@Inheritance(strategy = jakarta.persistence.InheritanceType.JOINED)
public class Request {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    private String requestDate;
    private RequestTypes requestType;
    private int ownerID;
    private String message;

    public Request(){
        this.requestDate = null;
        this.requestType = RequestTypes.UNKNOWN;
        this.ownerID = -1;
        this.message = null;
    }

    public Request(String requestDate, RequestTypes requestType, int ownerID, String message){
        this.requestDate = requestDate;
        this.requestType = requestType;
        this.ownerID = ownerID;
        this.message = message;
    }

    public String getRequestDate(){
        return this.requestDate;
    }

    public void setRequestDate(String requestDate){
        this.requestDate = requestDate;
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
}
