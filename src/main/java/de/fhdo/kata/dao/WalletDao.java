package de.fhdo.kata.dao;

import de.fhdo.kata.model.Wallet;
import org.checkerframework.checker.compilermsgs.qual.CompilerMessageKey;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Defines an API for {@link Wallet} data access object (DAO).
 */
@Component
public interface WalletDao {
    List<Wallet> findAll();
}
