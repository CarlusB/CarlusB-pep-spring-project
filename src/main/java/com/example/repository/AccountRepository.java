package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    
    @Query("select a FROM Account a WHERE a.username = :username AND a.password = :password")
    Optional<Account> findAccountByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("select a FROM Account a WHERE a.username = :username")
    Optional<Account> findAccountByUsername(@Param("username") String username);


}
