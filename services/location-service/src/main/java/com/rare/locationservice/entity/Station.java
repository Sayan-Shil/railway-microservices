package com.rare.locationservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rare.embeddable.Address;
import com.rare.embeddable.GeoCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    @Size(max = 5)
    private String stationCode;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Address address;

    @Embedded
    private GeoCode geoCode;

    @ManyToOne
    @JsonIgnore
    private City city;

    private String timeZone;

    @JsonIgnore
    @Transient
    public String getDetailedName(){
        if(city!=null && city.getCountryCode()!=null){
            return name.toUpperCase() +"/" +city.getCountryCode();
        }
        return name.toUpperCase();
    }

}
