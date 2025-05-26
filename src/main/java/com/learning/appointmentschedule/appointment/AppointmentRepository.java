package com.learning.appointmentschedule.appointment;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


    @Query("select a from appointmentEntity a where a.patient.patientId = :patientId")
    List<Appointment> findByPatientId(Long patientId);

    @Query("select a from appointmentEntity a where a.patient.lastName =:lastName")
    List<Appointment> findByPatientName(String lastName);

    @Query("select a from appointmentEntity a where a.schedule.doctor.id = :doctorId")
    List<Appointment> findByDoctorId(Long doctorId);

    @Query("select a from appointmentEntity a where a.schedule.doctor.name =:doctorName")
    List<Appointment> findByDoctorName(String doctorName);

    @Query ("select a from appointmentEntity a where a.schedule.id = :scheduleId and a.startDateTime <= :endTime and a.endDateTime >= :startTime")
    List<Appointment> findOverlappingTime(
      @Param("scheduleId") Long scheduleId,
      @Param("startTime") LocalDateTime startTime,
      @Param("endTime") LocalDateTime endTime
    );

    @Query("select case when count(a) > 0 then true else false end " +
            "from appointmentEntity a " +
            "where a.schedule.id = :scheduleId " +
            "and a.startDateTime = :startTime")
    boolean existsByScheduleIdAndStartTime(
            @Param("scheduleId") Long scheduleId,
            @Param("startTime") LocalDateTime startTime
    );

//    @Query("select a from appointmentEntity a where :startTime between a.startDateTime and a.endDateTime or :endTime between a.startDateTime and a.endDateTime" )
//    List<Appointment> findByAvailableTime(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}
