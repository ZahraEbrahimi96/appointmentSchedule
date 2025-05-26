package com.learning.appointmentschedule;

import com.learning.appointmentschedule.appointment.Appointment;
import com.learning.appointmentschedule.appointment.AppointmentService;
import com.learning.appointmentschedule.doctor.DoctorRepository;
import com.learning.appointmentschedule.dtos.AppointmentScheduleDto;
import com.learning.appointmentschedule.patient.Patient;
import com.learning.appointmentschedule.patient.PatientRepository;
import com.learning.appointmentschedule.schedule.Schedule;
import com.learning.appointmentschedule.schedule.ScheduleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableFeignClients
public class AppointmentScheduleApplication {

    private static ScheduleService scheduleService;
    private static AppointmentService appointmentService;
    private static PatientRepository patientRepository;
    private static DoctorRepository doctorRepository;
    private static AppointmentScheduleDto appointmentScheduleDto;

    public AppointmentScheduleApplication(ScheduleService scheduleService,AppointmentScheduleDto appointmentScheduleDto, AppointmentService appointmentService,PatientRepository patientRepository,DoctorRepository doctorRepository) {
        AppointmentScheduleApplication.appointmentScheduleDto =appointmentScheduleDto;
        AppointmentScheduleApplication.scheduleService = scheduleService;
        AppointmentScheduleApplication.appointmentService= appointmentService;
        AppointmentScheduleApplication.doctorRepository=doctorRepository;
        AppointmentScheduleApplication.patientRepository=patientRepository;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppointmentScheduleApplication.class, args);

        Patient patient = appointmentScheduleDto.getPatient();
        Schedule schedule = appointmentScheduleDto.getSchedule();
        Appointment appointment = new Appointment();
        appointment.setSchedule(schedule);
        appointment.setPatient(patient);
        appointment.setStartDateTime(LocalDateTime.of(2025, 2, 23, 12, 12));
        appointment.setEndDateTime(LocalDateTime.of(2025, 2, 25, 12, 12));

        appointmentService.saveAppointment(schedule.getDoctor().getId(),appointment);
        System.out.println("Appointments :"+appointment);
    }

}

