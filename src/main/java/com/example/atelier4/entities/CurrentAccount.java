package com.example.atelier4.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("CA") //pour la strategie single table si je cree un compte current dans le champ type il stockera CA
@Entity
public class CurrentAccount extends BankAccount{
    private double overDraft;
}
