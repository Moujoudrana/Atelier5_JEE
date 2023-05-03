package com.example.atelier4;

import com.example.atelier4.entities.*;
import com.example.atelier4.enums.AccountStatus;
import com.example.atelier4.enums.OperationType;
import com.example.atelier4.repositories.AccountOperationRepository;
import com.example.atelier4.repositories.BankAccountRepository;
import com.example.atelier4.repositories.CustomerRepository;
import com.example.atelier4.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class Atelier4Application {

    public static void main(String[] args) {

        SpringApplication.run(Atelier4Application.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankService bankService){
        return args->{
            bankService.consulter();
        };
    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            AccountOperationRepository accountOperationRepository,
                            BankAccountRepository bankAccountRepository){
        return args -> {
            Stream.of( "Moujoud","Rana", "Salma").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{ //chaque client a 2 compte C ET S
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString()); //parceque lid nest pas autoincrement UUID genere une chainedec aleatoire sous forme dun format uuid
                currentAccount.setBalance((Math.random()*90000));
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED); //va stocker la valeur 0, actv 1, susp 2
                                    //si on veut que le status secrit en string ajouter lannotation dans lentite bankacccount
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString()); //il est tjrs unique parce quil est generer selon la date systeme
                savingAccount.setBalance((Math.random()*90000));
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });

            bankAccountRepository.findAll().forEach(acc->{ //pour chaque compte on va creer 10operation
                for(int i=0;i<10 ;i++){
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT); //soit debit soit crdit cest aleatoire
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });

        };

    }
}
