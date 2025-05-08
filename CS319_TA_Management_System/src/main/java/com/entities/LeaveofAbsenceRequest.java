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

    public LeaveofAbsenceRequest(){
        super();
        setRequestType(RequestTypes.LEAVE_OF_ABSENCE_REQUEST);
        this.dates = null;
    }

    public LeaveofAbsenceRequest(int ownerID, String message, ArrayList<String> dates){
        super(RequestTypes.LEAVE_OF_ABSENCE_REQUEST, ownerID, message);
        this.dates = dates;
    }

    public ArrayList<String> getDates(){
        return this.dates;
    }

    public void setDates(ArrayList<String> dates){
        this.dates = dates;
    }
}
