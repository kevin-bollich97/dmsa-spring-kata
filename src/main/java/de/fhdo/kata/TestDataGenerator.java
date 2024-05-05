package de.fhdo.kata;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;

import de.fhdo.kata.model.Wallet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

public class TestDataGenerator {
	public Wallet generateAccount() {
		Fairy fairy = Fairy.create();
		Person person = fairy.person();
		Random random = new Random();

		Wallet fakeAccount = new Wallet();
		fakeAccount.setFirstName(person.getFirstName());
		fakeAccount.setLastName(person.getLastName());
		fakeAccount.setEmail(person.getEmail());
		fakeAccount.setBirthday(LocalDate.of(person.getDateOfBirth().getYear(),
				person.getDateOfBirth().getMonth(), person.getDateOfBirth().getDayOfMonth()));
		fakeAccount.setBalance(BigDecimal.valueOf(random.nextInt(200_000)));
		fakeAccount.setCreationTime(LocalDateTime.now());

		return fakeAccount;
	}
}
