package com.example.demo.attribute;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="mes_attribute")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attributeId;

    private String name;

    @OneToMany(mappedBy = "attribute",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AttributeValue> attributeValues;


}
