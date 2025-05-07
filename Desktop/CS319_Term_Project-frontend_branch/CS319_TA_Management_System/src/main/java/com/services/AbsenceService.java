package com.services;

import org.springframework.stereotype.Service;

import com.entities.Absence;
import com.entities.TA;
import com.entities.Staff;
import com.repositories.AbsenceRepository;
import com.repositories.TARepository;


import java.util.List;

@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final NotificationService notificationService;

    public AbsenceService(AbsenceRepository absenceRepository, TARepository taRepository,
            NotificationService notificationService) {
        this.absenceRepository = absenceRepository;
        this.notificationService = notificationService;
    }

    public List<Absence> getAllAbsences() {
        return absenceRepository.findAll();
    }

    public Absence getAbsenceById(Integer id) {
        return absenceRepository.findById(id).orElse(null);
    }

    public void deleteAbsenceById(Integer id) {
        absenceRepository.deleteById(id);
    }

    public Absence updateAbsence(Integer id, Absence absence) {
        Absence existingAbsence = absenceRepository.findById(id).orElse(null);
        if (existingAbsence != null) {
            existingAbsence.setDate(absence.getDate());
            existingAbsence.setTa(absence.getTa());
            existingAbsence.setReason(absence.getReason());
            existingAbsence.setStatus(absence.getStatus());
            return absenceRepository.save(existingAbsence);
        }
        return null;
    }

    public boolean requestAbsence(TA ta, String date, String reason) {
        try {
            Absence absence = new Absence();
            absence.setTa(ta);
            absence.setDate(date);
            absence.setReason(reason);
            absence.setStatus("PENDING");

            absenceRepository.save(absence);

            // Instructor gets notified reagarding the Absence request.
            if (ta.getAdvisor() != null) {
                notificationService.sendNotification(
                        ta.getAdvisor(),
                        "New absence request from TA ID " + ta.getId() + " for date " + date);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean approveAbsence(Staff staff, Absence absence) {
        try {
            Absence existingAbsence = absenceRepository.findById(absence.getId()).orElse(null);
            if (existingAbsence != null) {
                existingAbsence.setStatus("APPROVED");
                absenceRepository.save(existingAbsence);

                // APPROVAL Notification
                notificationService.sendNotification(
                        existingAbsence.getTa().getId(),"Your absence request for " + existingAbsence.getDate() + " has been approved.");

                return true;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean rejectAbsence(Staff staff, Absence absence) {
        try {Absence existingAbsence = absenceRepository.findById(absence.getId()).orElse(null);
            if (existingAbsence != null) {
                existingAbsence.setStatus("REJECTED");
                absenceRepository.save(existingAbsence);

                // REJECTION notification
                notificationService.sendNotification(existingAbsence.getTa().getId(), "Your absence request for " + existingAbsence.getDate() + " has been rejected.");

                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean viewAbsence(Staff staff, Absence absence) {
        try {
            // Shows visible absence t oavoid erros
            return absenceRepository.findById(absence.getId()).isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    public List<Absence> getAbsencesByTAId(Integer taId) {
        return absenceRepository.findByTaId(taId);
    }


    public List<Absence> getApprovedAbsences() {
        return absenceRepository.findByStatus("APPROVED");
    }

    public List<Absence> getRejectedAbsences() {
        return absenceRepository.findByStatus("REJECTED");
    }

}