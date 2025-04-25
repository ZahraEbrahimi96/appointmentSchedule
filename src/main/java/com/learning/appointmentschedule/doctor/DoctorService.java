package com.learning.appointmentschedule.doctor;
import com.learning.appointmentschedule.doctor.Doctor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "doctorClient", url = "http://172.20.10.3")
public interface DoctorService {

    @PostMapping("/doctors")
    ResponseEntity<String> postDoctor(@RequestBody Doctor doctor);

    @GetMapping("/doctors")
    ResponseEntity<String> getDoctors();

    @GetMapping("/doctors/{id}")
    ResponseEntity<String> getDoctorId();


}
