package com.learning.appointmentschedule.schedule;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from scheduleEntity s where s.doctor.id = :doctorId and s.startDateTime <= :endDateTime and s.endDateTime >= :startDateTime ")
    Optional<Schedule> findByDoctorIdAndDateTimes
            (Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime );


    List<Schedule> findByDoctorNameLike(String doctorName);
}
