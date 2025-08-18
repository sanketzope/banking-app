package com.sanket.banking.repository;

import com.sanket.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRespository  extends JpaRepository<Account, Long> {
}
