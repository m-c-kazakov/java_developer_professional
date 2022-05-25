package ru.otus.crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @JoinColumn(name = "client_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Client clients;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }
}
