package ru.otus.crm.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "client_name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();


    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this(id, name);
        this.address = address;
        this.addPhones(phones);
    }

    private void addPhones(List<Phone> phones) {
        phones.forEach(phone -> {
            this.phones.add(phone);
            phone.setClient(this);
        });
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name,
                ofNullable(this.address).map(Address::new).orElse(null),
                this.phones);
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
