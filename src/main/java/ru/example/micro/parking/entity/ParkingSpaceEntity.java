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
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
@Entity
@Table(name = "parking_space")
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpaceEntity {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parking_space_id_seq")
    @SequenceGenerator(name="parking_space_id_seq", sequenceName = "parking_space_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "parking_id")
    private Long parkingId;
    @Column(name = "level")
    private Integer level;
    @Column(name = "space")
    private Integer space;
    @Column(name = "user_id")
    private Long userId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "last_update")
    private ZonedDateTime lastUpdate;
}
