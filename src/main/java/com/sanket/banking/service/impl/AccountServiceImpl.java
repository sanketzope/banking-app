package com.sanket.banking.service.impl;

import com.sanket.banking.dto.AccountDto;
import com.sanket.banking.entity.Account;
import com.sanket.banking.mapper.AccountMapper;
import com.sanket.banking.repository.AccountRespository;
import com.sanket.banking.service.AccountService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRespository accountRespository;

    public AccountServiceImpl(AccountRespository accountRespository) {
        this.accountRespository = accountRespository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRespository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account =
                accountRespository.findById(id).orElseThrow(()->new RuntimeException(
                "Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRespository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exist"));
        double total = account.getBalance()+ amount;
        account.setBalance(total);
        Account savedAccount = accountRespository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRespository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exist"));
        if (account.getBalance()<amount){
            throw new RuntimeException("Insufficient amount");
        }
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRespository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
       List<Account> accounts =  accountRespository.findAll();
       return accounts.stream().map(AccountMapper::mapToAccountDto)
               .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRespository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exist"));
        accountRespository.deleteById(id);
    }


}
