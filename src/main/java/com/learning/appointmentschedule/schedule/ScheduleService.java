package com.learning.appointmentschedule.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.appointmentschedule.appointment.Appointment;
import com.learning.appointmentschedule.doctor.Doctor;
import com.learning.appointmentschedule.doctor.DoctorRepository;
import com.learning.appointmentschedule.doctor.DoctorService;
import com.learning.appointmentschedule.exception.NoContentException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final DoctorService doctorService;



    public ScheduleService(ScheduleRepository scheduleRepository, DoctorService doctorService) {
        this.scheduleRepository = scheduleRepository;
        this.doctorService = doctorService;
    }

    @Transactional
    public Schedule createSchedule(Schedule schedule) throws Exception, NoContentException {
//        Optional<Doctor> doctorOpt = doctorService.();
//        if (doctorOpt.isEmpty()) {
//            throw new Exception("Doctor not found: " + doctorCode);
//        }
//        if (startDateTime.isAfter(endDateTime)){
//            throw new Exception("Start time must be before end time");
//        }
        ResponseEntity<String> doctorResponses = doctorService.save(schedule.getDoctor());
        if (doctorResponses.getStatusCode().equals(HttpStatus.OK)){
            if (schedule.getStartDateTime().isAfter(schedule.getEndDateTime())){
                throw new Exception("Start time must be before end time");
            }
            ObjectMapper mapper = new ObjectMapper();
            Doctor doctor= mapper.readValue(doctorResponses.getBody(),Doctor.class);
            doctorService.save(doctor);
            schedule.setDoctor(doctor);
            scheduleRepository.save(schedule);
        }else {
            System.out.println("Error :" + doctorResponses.getStatusCode() + " : " + doctorResponses.getBody());
            throw new NoContentException();
        }
        return schedule;
    }


    public Optional<Schedule>findScheduleByDoctorIdAndDate(Long doctorId, LocalDateTime endOfDay, LocalDateTime startOfDay) throws Exception {

        ResponseEntity<?> doctorResponse = doctorService.getDoctorById(doctorId);
        if (!doctorResponse.getStatusCode().equals(HttpStatus.OK)){
            throw new Exception("Doctor not found: " + doctorId);
        }
        return scheduleRepository.findByDoctorIdAndDateTimes(doctorId, endOfDay, startOfDay);
    }

    public List<Schedule> findByDoctorNameLike(String doctorName){
        return scheduleRepository.findByDoctorNameLike(doctorName);
    }


//    public Schedule update(Long id, Schedule schedule) throws Exception, NoContentException {
//        ResponseEntity<String> doctorResponses = doctorService.save(schedule.getDoctor());
//        if (doctorResponses.getStatusCode().equals(HttpStatus.OK)){
//            if (schedule.getStartDateTime().isAfter(schedule.getEndDateTime())){
//                throw new Exception("Start time must be before end time");
//            }
//            ObjectMapper mapper = new ObjectMapper();
//            Doctor doctor= mapper.readValue(doctorResponses.getBody(),Doctor.class);
//            doctorRepository.save(doctor);
//            schedule.setDoctor(doctor);
//            scheduleRepository.save(schedule);
//        }else {
//            System.out.println("Error :" + doctorResponses.getStatusCode() + " : " + doctorResponses.getBody());
//            throw new NoContentException();
//        }
//        return schedule;
//    }


    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }


    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }


    public Schedule findById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));
    }
}
