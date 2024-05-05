package de.fhdo.kata.dao;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import de.fhdo.kata.TestDataGenerator;
import de.fhdo.kata.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Fake implementation for {@link WalletDao}.
 * <p>
 * ToDo: Configure this class as a spring component named "walletDao".
 * <p>
 * ToDo: Use explicit constructor injection.
 */
@Component("walletDao")
public class FakeWalletDao implements WalletDao {
    private List<Wallet> accounts;

    @Autowired
    public FakeWalletDao(@Qualifier("dataGenerator") TestDataGenerator testDataGenerator) {
        this.accounts = Stream.generate(testDataGenerator::generateAccount).limit(20).collect(toList());
    }

    @Override
    public List<Wallet> findAll() {
        return accounts;
    }
}
