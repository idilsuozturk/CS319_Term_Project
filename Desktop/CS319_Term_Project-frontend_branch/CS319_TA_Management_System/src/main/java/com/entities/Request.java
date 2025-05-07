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
    private String requestType;
    private int ownerID;

    public Request(){
        this.requestDate = null;
        this.requestType = null;
        this.ownerID = -1;
    }

    public Request(String requestDate, String requestType, int ownerID){
        this.requestDate = requestDate;
        this.requestType = requestType;
        this.ownerID = ownerID;
    }

    public String getRequestDate(){
        return this.requestDate;
    }

    public void setRequestDate(String requestDate){
        this.requestDate = requestDate;
    }

    public String getRequestType(){
        return this.requestType;
    }

    public void setRequestType(String requestType){
        this.requestType = requestType;
    }

    public int getOwnerID(){
        return this.ownerID;
    }

    public void setOwnerID(int ownerID){
        this.ownerID = ownerID;
    }
}
