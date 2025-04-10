package edu.poly.assjava5banhang.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.poly.assjava5banhang.model.Account;



public interface AccountDAO extends JpaRepository<Account,String>  {

    Account findByUsername(String username);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsernameAndPassword(String username, String password);

     Account findByVerificationToken(String token);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    

    
} 