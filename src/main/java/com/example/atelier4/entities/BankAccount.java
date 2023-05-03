package com.example.atelier4.entities;

import com.example.atelier4.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //pour la strategie de table dheritage (ST dans une seule classe)
@DiscriminatorColumn(name="TYPE", length = 4, discriminatorType = DiscriminatorType.STRING) //pour le champ type qui sajoute s ST

//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) //pour la strategie de table dheritage (chaque classe a une table separement)

//@Inheritance(strategy = InheritanceType.JOINED) //pour la strategie de table dheritage (chaque classe a une table qui ne contient que les attributs ajouter (daft/rate) + la table backaccount
        //dans les 2tables C et S il contiennent draft et rate + id du compte foreign key dans bankaccount

public abstract class BankAccount { //abstract pour le type Table per class pour ne pas creer la table bank account dans bd
    @Id
    private String id;
    private double balance;
    private Date createdAt;

    @Enumerated(EnumType.STRING) //pour que dans la bd secrit en format string, ordinal cest le par defaut 012..
    private AccountStatus status;

    @ManyToOne //(un client peut avoir plusieurs compte) un compte concerne un client //plusieur compte pour un client
    private Customer customer;

    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY) //un compte peut avoir plusieurs operation
            //fetch soit lazy(par defaut): charge que les info du compte mais pas la liste des op jusqua la demande (mais ne peut travailler duavec service)
            //ou eager : a chque fois quon demande de charger un compte tout les operations de ce compte sont aussi charger
            //sans fetch si on souhaite afficher les op dun compte il affiche ERREUR
            //linconvenient deager cest quil peut charger beaucoup trop de donnee en memoire quon utilisera pas
    private List<AccountOperation> accountOperation;
}
