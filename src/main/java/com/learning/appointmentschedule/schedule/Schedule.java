package com.learning.appointmentschedule.schedule;

import com.learning.appointmentschedule.doctor.Doctor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "schedules-tbl")
@Entity(name = "scheduleEntity")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start-time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end-time", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "appointment-length", nullable = false)
    private int durationMin;

    @ManyToOne
    @JoinColumn(name = "doctor", nullable = false)
    private Doctor doctor;
}