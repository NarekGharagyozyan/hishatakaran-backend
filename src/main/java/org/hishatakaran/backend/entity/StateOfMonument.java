package org.hishatakaran.backend.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "state_of_monument")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StateOfMonument {

    @Id
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "stateOfMonument")
    private List<DescriptiveCharacteristicReference> descriptiveCharacteristics = new ArrayList<>();
}