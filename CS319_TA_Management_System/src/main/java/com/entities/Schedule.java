package com.entities;

import jakarta.persistence.*;

@Embeddable

public class Schedule {
    enum Days {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY
    }

    enum lectureHours   {
        FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH, EIGHTH
    }

    private Days day;
    private lectureHours lectureHourBegin;
    private lectureHours lectureHourEnd;

    public Schedule(Days day, lectureHours lectureHourBegin, lectureHours lectureHourEnd ) {
        this.day = day;
        this.lectureHourBegin = lectureHourBegin;
        this.lectureHourEnd = lectureHourEnd;
    }

    public Days getDay() {
        return day;
    }

    public void setDay(Days day) {
        this.day = day;
    }

    public lectureHours getLectureHourBegin() {
        return lectureHourBegin;
    }

    public void setLectureHourBegin(lectureHours lectureHourBegin) {
        this.lectureHourBegin = lectureHourBegin;
    }

    public lectureHours getLectureHourEnd() {
        return lectureHourEnd;
    }   

    public void setLectureHourEnd(lectureHours lectureHourEnd) {
        this.lectureHourEnd = lectureHourEnd;
    }   
}
