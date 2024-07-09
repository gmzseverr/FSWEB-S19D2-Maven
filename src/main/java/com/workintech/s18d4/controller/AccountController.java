package com.workintech.s18d4.controller;


import com.workintech.s18d4.dto.AccountResponse;
import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.exception.AccountException;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountService accountService;
    private CustomerService customerService;

    @GetMapping
    public List<Account> findAllAccounts(){
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id){
        return accountService.find(id);

    }

    @PostMapping("/{customerId}")
    public AccountResponse saveAccount(@PathVariable("customerId") Long customerId, @RequestBody Account account){
        Customer customer=customerService.find(customerId);
        if (customer != null){
            customer.getAccounts().add(account);
            account.setCustomer(customer);
            accountService.save(account);
        }else {
            throw new RuntimeException("No customer found!");
        }
        return new AccountResponse(account.getId(), account.getAccountName(), account.getMoneyAmount(),
                new CustomerResponse(customer.getId(), customer.getEmail(), customer.getSalary()));
    }

    @PutMapping("/{customerId}")
    public AccountResponse update(@RequestBody Account account, @PathVariable Long customerId) {
        Customer customer = customerService.find(customerId);
        Account toBeUpdatedAccount = null;

        for (Account account1 : customer.getAccounts()) {
            if (account.getId() == account1.getId()) {
                toBeUpdatedAccount = account1;
                break;
            }
        }

        if (toBeUpdatedAccount == null) {
            throw new RuntimeException("Account " + account.getId() + " not found for this customer: " + customerId);
        }

        int indexOfToBeUpdated = customer.getAccounts().indexOf(toBeUpdatedAccount);
        customer.getAccounts().set(indexOfToBeUpdated,account);
        account.setCustomer(customer);
        accountService.save(account);
        return new AccountResponse(account.getId(), account.getAccountName(), account.getMoneyAmount(),
                new CustomerResponse(customerId, customer.getEmail(), customer.getSalary()));


    }


    @DeleteMapping("/{id}")
    public AccountResponse delete(@PathVariable Long id){
        Account account = accountService.find(id);
        if (account == null){
            throw new AccountException("Account with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        accountService.delete(id);
        return new AccountResponse(account.getId(), account.getAccountName(), account.getMoneyAmount(),
                new CustomerResponse(account.getCustomer().getId(),account.getCustomer().getEmail(),account.getCustomer().getSalary()));
    }

}
