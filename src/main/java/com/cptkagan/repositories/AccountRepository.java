package com.cptkagan.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cptkagan.models.Account;
import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    Optional<Account> findByUserName(String userName);
}
