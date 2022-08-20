package com.vhp.code.repository;

import com.vhp.code.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Transactional
    @Modifying
    Integer deleteByToken(String username);

    Boolean existsByToken(String token);
}
