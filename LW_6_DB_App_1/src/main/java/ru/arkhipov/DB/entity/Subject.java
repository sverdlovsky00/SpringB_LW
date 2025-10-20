package ru.arkhipov.DB.entity;
import jakarta.persistence.*;
import lombok.*;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SUBJECTS")

public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "hours")
    private int hours;

    @Column(name = "description")
    private String description;

}
