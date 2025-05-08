package com.entities;

import java.util.ArrayList;

import com.converters.IntegerArrayListToJsonConverter;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_of_absence_requests")
public class LeaveofAbsenceRequest extends Request {
    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayListToJsonConverter.class)
    ArrayList<Integer> dates;

    public LeaveofAbsenceRequest(){
        super();
        setRequestType(RequestTypes.LEAVE_OF_ABSENCE_REQUEST);
        this.dates = null;
    }

    public LeaveofAbsenceRequest(int ownerID, String message, ArrayList<Integer> dates){
        super(RequestTypes.LEAVE_OF_ABSENCE_REQUEST, ownerID, message);
        this.dates = dates;
    }

    public ArrayList<Integer> getDates(){
        return this.dates;
    }

    public void setDates(ArrayList<Integer> dates){
        this.dates = dates;
    }
}
