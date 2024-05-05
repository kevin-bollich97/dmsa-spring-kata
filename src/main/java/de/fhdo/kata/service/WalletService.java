package de.fhdo.kata.service;

import java.util.Comparator;
import java.util.List;

import de.fhdo.kata.dao.WalletDao;
import de.fhdo.kata.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service for {@link Wallet}s.
 * <p>
 * ToDo: Configure this class as as service.
 * <p>
 * ToDo: Use explicit constructor injection.
 */
@Service
public class WalletService {
    private final WalletDao walletDao;

    @Autowired
    public WalletService(@Qualifier("walletDao") WalletDao accountDao) {
        this.walletDao = accountDao;
    }

    public Wallet findRichest() {
        List<Wallet> accounts = walletDao.findAll();
        return accounts.stream().max(Comparator.comparing(Wallet::getBalance)).get();
    }

}
