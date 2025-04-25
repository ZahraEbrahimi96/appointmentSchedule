package com.learning.appointmentschedule.doctor;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //add doctorCode to the fields
//    @Column(name = "code", nullable = false)
//    private String doctorCode;


    @Column(name = "name", nullable = false)
    private String name;

//    @ManyToOne
//    @JoinColumn(name = "specialization_id", nullable = false)
//    private Specialization specialization;

    @Column(name = "experience_years", nullable = false)
    private int experienceYears;

    @Column(name = "contact_info", nullable = false)
    private String contactInfo;
}