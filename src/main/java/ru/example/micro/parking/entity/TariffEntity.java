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

import java.math.BigDecimal;

/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
@Entity
@Table(name = "tariff")
@NoArgsConstructor
@AllArgsConstructor
public class TariffEntity {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tariff_id_seq")
    @SequenceGenerator(name="tariff_id_seq", sequenceName = "tariff_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "minutes_amount")
    private Integer minutesAmount;
    @Column(name = "payment")
    private BigDecimal payment;
}
