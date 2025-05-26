package com.learning.appointmentschedule.doctor;
import com.learning.appointmentschedule.doctor.Doctor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//@Service
@FeignClient(value = "scheduleService", url = "http://127.0.0.1:8000")
public interface DoctorService {

    @PostMapping("/doctors")
    ResponseEntity<String> save(@RequestBody Doctor doctor);

    @GetMapping("/doctors")
    ResponseEntity<String> getDoctors();

    @GetMapping("/doctors/{id}")
    ResponseEntity<?> getDoctorById(@PathVariable Long id);


}
