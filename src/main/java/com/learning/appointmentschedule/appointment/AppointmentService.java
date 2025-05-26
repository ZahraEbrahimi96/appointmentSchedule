package com.learning.appointmentschedule.appointment;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.appointmentschedule.doctor.Doctor;
import com.learning.appointmentschedule.doctor.DoctorRepository;
import com.learning.appointmentschedule.doctor.DoctorService;
import com.learning.appointmentschedule.dtos.AppointmentScheduleDto;
import com.learning.appointmentschedule.patient.Patient;
import com.learning.appointmentschedule.patient.PatientRepository;
import com.learning.appointmentschedule.patient.PatientService;
import com.learning.appointmentschedule.schedule.Schedule;
import com.learning.appointmentschedule.schedule.ScheduleRepository;
import com.learning.appointmentschedule.schedule.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ScheduleService scheduleService;
    private final PatientService patientService;

    public AppointmentService(AppointmentRepository appointmentRepository, ScheduleService scheduleService, PatientService patientService) {
        this.appointmentRepository = appointmentRepository;
        this.scheduleService = scheduleService;
        this.patientService = patientService;
    }


    @Transactional
    public List<LocalDateTime> findAvailableTime(Long doctorId, LocalDateTime startDay, LocalDateTime endDay) throws Exception {
        Optional<Schedule> scheduleOpt = scheduleService.findScheduleByDoctorIdAndDate(doctorId, startDay, endDay);
        if (scheduleOpt.isEmpty()) {
            return Collections.emptyList();
        }

        Schedule schedule = scheduleOpt.get();

        LocalDateTime dayStart = schedule.getStartDateTime();
        LocalDateTime dayEnd = schedule.getEndDateTime();
        int appointmentDurationMin = schedule.getDurationMin();

        List<LocalDateTime> availableTimes = new ArrayList<>();

        while (dayStart.isBefore(dayEnd)){
            LocalDateTime endTime = dayStart.plusMinutes(appointmentDurationMin);
            if (endTime.isAfter(dayEnd)){
                break;
            }

            List<Appointment> overlapping = appointmentRepository.findOverlappingTime(schedule.getId(), dayStart, endTime);
            if (overlapping.isEmpty()){
                availableTimes.add(dayStart);
            }
            dayStart = dayStart.plusMinutes(appointmentDurationMin);
        }
        return availableTimes;
    }

    @Transactional
    public Appointment saveAppointment(Long doctorId, Appointment appointment) throws Exception{

        Optional<Schedule> scheduleOpt = scheduleService.findScheduleByDoctorIdAndDate( doctorId, appointment.getStartDateTime(), appointment.getEndDateTime());
        if (scheduleOpt.isEmpty()){
            throw new Exception("No schedule found for this doctor");
        }
        Schedule schedule = scheduleOpt.get();

        LocalDateTime startOfTime = appointment.getStartDateTime();
        int appointmentDurationMin= schedule.getDurationMin();
        LocalDateTime endOfTime = startOfTime.plusMinutes(appointmentDurationMin);

        if (startOfTime.isBefore(schedule.getStartDateTime()) || endOfTime.isAfter(schedule.getEndDateTime())){
            throw new Exception("Chosen time is not on the doctor's schedule");
        }

        if (appointmentRepository.existsByScheduleIdAndStartTime(schedule.getId(), startOfTime )){
            throw new Exception("This appointment is already booked");
        }
        ResponseEntity<String>patientResponse = patientService.save(appointment.getPatient());
        if(patientResponse.getStatusCode().equals(HttpStatus.OK)){
            ObjectMapper mapper = new ObjectMapper();
            Patient patient = mapper.readValue(patientResponse.getBody(), Patient.class);
            patientService.save(patient);
            appointment.setPatient(patient);
            appointmentRepository.save(appointment);
        }
        return appointment;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }


    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByPatientName(String lastName) {
        return appointmentRepository.findByPatientName(lastName);
    }

    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsByDoctorName(String doctorName) {
        return appointmentRepository.findByDoctorName(doctorName);
    }

}
