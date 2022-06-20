package com.zinkwork.Atm.repository;

import com.zinkwork.Atm.entity.JpaAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<JpaAccount, String> {

    Optional<JpaAccount> findJpaAccountByAccountNumber(String accountNumber);

}
