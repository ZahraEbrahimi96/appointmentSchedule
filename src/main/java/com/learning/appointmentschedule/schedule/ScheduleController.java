package com.learning.appointmentschedule.schedule;

import com.learning.appointmentschedule.dtos.AppointmentScheduleDto;
import com.learning.appointmentschedule.exception.NoContentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping()
    public ResponseEntity<List<Schedule>> getAllSchedule(){
        return ResponseEntity.ok(scheduleService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Schedule> getScheduleById (@RequestParam Long id){
        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<Optional<Schedule>> getScheduleByIdAndDates(@RequestParam Long doctorId, @RequestParam LocalDateTime endOfDay, @RequestParam LocalDateTime startOfDay) throws Exception {
        return ResponseEntity.ok(scheduleService.findScheduleByDoctorIdAndDate(doctorId,endOfDay,startOfDay));
    }

    @GetMapping("/doctorName")
    public ResponseEntity<List<Schedule>> getScheduleByDoctorName(@RequestParam String doctorName) throws Exception {
        return ResponseEntity.ok(scheduleService.findByDoctorNameLike(doctorName));
    }

    @PostMapping()
    public ResponseEntity<Schedule> save (@RequestParam AppointmentScheduleDto appointmentScheduleDto) throws NoContentException, Exception {
        Schedule schedule = appointmentScheduleDto.getSchedule();
        scheduleService.createSchedule(schedule);
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("id/{id}")
    public void deleteSchedule(@RequestParam Long id){
        scheduleService.delete(id);
    }
}
