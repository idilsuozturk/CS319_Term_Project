package com.entities;

import java.util.ArrayList;

import com.converters.IntegerArrayListToJsonConverter;

import jakarta.persistence.*;

@Entity
@Table(name = "leaveofabsencerequests")
public class LeaveofAbsenceRequest extends Request {
    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayListToJsonConverter.class)
    ArrayList<Integer> dates;

    public LeaveofAbsenceRequest(){
        super();
        this.dates = null;
    }

    public LeaveofAbsenceRequest(String requestDate, int ownerID, ArrayList<Integer> dates){
        super(requestDate, "Leave of Absence", ownerID);
        this.dates = dates;
    }

    public ArrayList<Integer> getDates(){
        return this.dates;
    }

    public void setDates(ArrayList<Integer> dates){
        this.dates = dates;
    }
}
