package com.catdev.project.entity.flight;

import com.catdev.project.entity.aircraft.AircraftSeatEntity;
import com.catdev.project.entity.common.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "flight_seat_price")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightSeatPrice extends DateTimeEntity {

    @Column
    public BigDecimal price;

    @Column
    private String currency;
}

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class FlightSeatPriceId {
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private AircraftSeatEntity aircraftSeat;


}
