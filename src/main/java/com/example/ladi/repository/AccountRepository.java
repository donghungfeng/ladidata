package com.example.ladi.repository;

import com.example.ladi.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseRepository<Account>{
    Account findByUserName(String userName);
}
