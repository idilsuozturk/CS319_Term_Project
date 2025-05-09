package com.entities;

import java.util.ArrayList;

import com.converters.IntegerArrayListToJsonConverter;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_of_absence_requests")
public class LeaveofAbsenceRequest extends Request {
    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayListToJsonConverter.class)
    private ArrayList<String> dates;

    private Integer respondentID;

    public LeaveofAbsenceRequest(){
        super();
        setRequestType(RequestTypes.LEAVE_OF_ABSENCE_REQUEST);
        this.dates = null;
        this.respondentID = -1;
    }

    public LeaveofAbsenceRequest(int ownerID, String message, ArrayList<String> dates){
        super(RequestTypes.LEAVE_OF_ABSENCE_REQUEST, ownerID, message);
        this.dates = dates;
        this.respondentID = -1;
    }

    public ArrayList<String> getDates(){
        return this.dates;
    }

    public void setDates(ArrayList<String> dates){
        this.dates = dates;
    }

    public int getRespondentID(){
        return this.respondentID;
    }

    public void setRespondentID(int respondentID){
        this.respondentID = respondentID;
    }
}
