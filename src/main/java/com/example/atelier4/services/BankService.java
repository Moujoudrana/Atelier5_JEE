package com.example.atelier4.services;

import com.example.atelier4.entities.BankAccount;
import com.example.atelier4.entities.CurrentAccount;
import com.example.atelier4.entities.SavingAccount;
import com.example.atelier4.repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter(){ //consulter fonctionne pour lazy parce que cest une methode transactionnel
        BankAccount bankAccount=
                bankAccountRepository.findById("06d84b4e-2264-4b03-9f13-6e7051f38fa3").orElse(null);
        if(bankAccount!=null) {
            System.out.println("**************");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName()); //affiche la classe de ce compte soit C OU S
            if (bankAccount instanceof CurrentAccount) {
                System.out.println("OVERDRAFT " + ((CurrentAccount) bankAccount).getOverDraft());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println("RATE " + ((SavingAccount) bankAccount).getInterestRate());
            }

            bankAccount.getAccountOperation().forEach(op -> {
                System.out.println("=================");
                System.out.println(op.getType());
                System.out.println(op.getAmount());
                System.out.println(op.getOperationDate());
            });
        }
    }
}
