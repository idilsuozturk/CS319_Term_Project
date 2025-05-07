package com.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.entities.Absence;
import com.entities.TA;
import com.entities.Staff;
import com.services.AbsenceService;
import com.services.TAService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class AbsenceController {

    private final AbsenceService absenceService;
    private final TAService taService;

    public AbsenceController(AbsenceService absenceService, TAService taService) {
        this.absenceService = absenceService;
        this.taService = taService;
    }

    /*@GetMapping("/test")
    public String test() {
        return "Absence controller works.";
    }*/

    @GetMapping("/list-absences")
    public List<Absence> listAbsences() {
        return absenceService.getAllAbsences();
    }



    @GetMapping("absence/{id}")
    public Absence getAbsence(@PathVariable Integer id) {
        return absenceService.getAbsenceById(id);
    }




    @DeleteMapping("/delete-absence/{id}")
    public void deleteAbsence(@PathVariable Integer id) {
        absenceService.deleteAbsenceById(id);
    }

    @PutMapping("/update-absence/{id}")
    public Absence updateAbsence(@PathVariable Integer id, @RequestBody Absence absence) {
        return absenceService.updateAbsence(id, absence);
    }
    @PostMapping("/request-absence")
    public boolean requestAbsence(
            @RequestParam Integer taId,
            @RequestParam String date,
            @RequestParam String reason) {
        TA ta = taService.getTAById(taId);



        if (ta != null) {
            return absenceService.requestAbsence(ta, date, reason);
                        } return false; }


    // have to deal with permissions
    @PostMapping("/approve-absence/{id}")
    public boolean approveAbsence(
            @PathVariable Integer id,
            @RequestParam Integer staffId) {

        Absence absence = absenceService.getAbsenceById(id);
        
        Staff staff = new Staff() {
            @Override
            public Integer getId() {return staffId;}
        };

        if (absence != null) {
            return absenceService.approveAbsence(staff, absence);
        }
        return false;
    }
    // TODO permissions
    @PostMapping("/reject-absence/{id}")
    public boolean rejectAbsence(
            @PathVariable Integer id,
            @RequestParam Integer staffId) {

        Absence absence = absenceService.getAbsenceById(id);
        
        Staff staff = new Staff() {
            @Override
            public Integer getId() {
                return staffId;
                                    }
        };

        if (absence != null) {
            return absenceService.rejectAbsence(staff, absence);
        }
        
        return false;
    }
    // basic structure for vieing
    @GetMapping("/view-absence/{id}")
    public boolean viewAbsence(
            @PathVariable Integer id,
            @RequestParam Integer staffId) {

        Absence absence = absenceService.getAbsenceById(id);
        Staff staff = new Staff() {
            @Override
            public Integer getId() {


                return staffId;
            }
        };

        if (absence != null) {
            return absenceService.viewAbsence(staff, absence);
        }
        return false;
    }
    @GetMapping("/absence-by-taId/{taId}")
    public List<Absence> getAbsencesByTA(@PathVariable Integer taId) {
        return absenceService.getAbsencesByTAId(taId);
    }


    @GetMapping("/absence-approved")
    public List<Absence> getApprovedAbsences() {
        return absenceService.getApprovedAbsences();
    }

    @GetMapping("/absence-rejected")
    public List<Absence> getRejectedAbsences() {
        return absenceService.getRejectedAbsences();
    }

}