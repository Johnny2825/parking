package ru.example.micro.parking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
@Entity
@Table(name = "parking")
@NoArgsConstructor
@AllArgsConstructor
public class ParkingEntity {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parking_id_seq")
    @SequenceGenerator(name="parking_id_seq", sequenceName = "parking_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "parking_space_amount")
    private Integer parkingSpaceAmount;
}
