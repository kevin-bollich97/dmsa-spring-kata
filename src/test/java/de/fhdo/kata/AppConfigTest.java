package de.fhdo.kata;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import de.fhdo.kata.config.AppConfig;
import de.fhdo.kata.dao.FakeWalletDao;
import de.fhdo.kata.dao.WalletDao;
import de.fhdo.kata.model.Wallet;
import de.fhdo.kata.service.WalletService;

@SpringJUnitConfig
public class AppConfigTest {
	@Configuration
	@ComponentScan(basePackages = { "de.fhdo.kata" })
	static class TestConfig {
	}

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private WalletService walletService;

	@Autowired
	private WalletDao walletDao;

	@Test
	public void testConfigClassIsMarkedAsConfiguration() {
		Configuration configuration = AppConfig.class.getAnnotation(Configuration.class);

		assertThat(configuration, notNullValue());
	}

	@Test
	public void testComponentScanIsEnabled() {
		ComponentScan componentScan = AppConfig.class.getAnnotation(ComponentScan.class);

		assertThat(componentScan, notNullValue());
	}

	@Test
	public void testComponentScanPackagesAreSpecified() {
		ComponentScan componentScan = AppConfig.class.getAnnotation(ComponentScan.class);
		String[] packages = componentScan.basePackages();
		if (packages.length == 0) {
			packages = componentScan.value();
		}
		assertThat(packages, arrayContainingInAnyOrder("de.fhdo.kata.dao", "de.fhdo.kata.service"));
	}

	@Test
	public void testDataGeneratorHasOnlyOneBean() {
		Map<String, TestDataGenerator> testDataGeneratorMap = applicationContext
				.getBeansOfType(TestDataGenerator.class);

		assertThat(testDataGeneratorMap.size(), is(1));
	}

	@Test
	public void testDataGeneratorBeanIsConfiguredExplicitly() {
		Method[] methods = AppConfig.class.getMethods();
		Method testDataGeneratorBeanMethod = findTestDataGeneratorBeanMethod(methods);

		assertThat(testDataGeneratorBeanMethod, notNullValue());
	}

	@Test
	public void testDataGeneratorBeanName() {
		Map<String, TestDataGenerator> dataGeneratorBeanMap = applicationContext
				.getBeansOfType(TestDataGenerator.class);

		assertThat(dataGeneratorBeanMap.keySet(), hasItem("dataGenerator"));
	}

	@Test
	public void testDataGeneratorBeanNameIsNotSpecifiedExplicitly() {
		Method[] methods = AppConfig.class.getMethods();
		Method testDataGeneratorBeanMethod = findTestDataGeneratorBeanMethod(methods);
		Bean bean = testDataGeneratorBeanMethod.getDeclaredAnnotation(Bean.class);

		assertThat(bean.name(), arrayWithSize(0));
		assertThat(bean.value(), arrayWithSize(0));
	}

	private Method findTestDataGeneratorBeanMethod(Method[] methods) {
		for (Method method : methods) {
			if (method.getReturnType().equals(TestDataGenerator.class)
					&& method.getDeclaredAnnotation(Bean.class) != null) {
				return method;
			}
		}
		return null;
	}

	@Test
	public void testFakewalletDaoIsConfiguredAsComponent() {
		Component component = FakeWalletDao.class.getAnnotation(Component.class);

		assertThat(component, notNullValue());
	}

	@Test
	public void testWalletDaoHasOnlyOneBean() {
		Map<String, WalletDao> walletDaoBeanMap = applicationContext.getBeansOfType(WalletDao.class);

		assertThat(walletDaoBeanMap.size(), is(1));
	}

	@Test
	public void testWalletDaoBeanName() {
		Map<String, WalletDao> walletDaoBeanMap = applicationContext.getBeansOfType(WalletDao.class);

		assertThat(walletDaoBeanMap.keySet(), hasItem("walletDao"));
	}

	@Test
	public void testWalletDaoConstructorIsMarkedWithAutowired() throws NoSuchMethodException {
		Autowired autowired = FakeWalletDao.class.getConstructor(TestDataGenerator.class)
				.getAnnotation(Autowired.class);

		assertThat(autowired, notNullValue());
	}

	@Test
	public void testWalletServiceHasOnlyOneBean() {
		Map<String, WalletService> walletServiceMap = applicationContext.getBeansOfType(WalletService.class);

		assertThat(walletServiceMap.size(), is(1));
	}

	@Test
	public void testWalletServiceIsConfiguredAsService() {
		Service service = WalletService.class.getAnnotation(Service.class);

		assertThat(service, notNullValue());
	}

	@Test
	public void testWalletServiceBeanName() {
		Map<String, WalletService> walletServiceMap = applicationContext.getBeansOfType(WalletService.class);

		assertThat(walletServiceMap.keySet(), hasItem("walletService"));
	}

	@Test
	public void testWalletServiceBeanNameIsNotSpecifiedExplicitly() {
		Service service = WalletService.class.getAnnotation(Service.class);

		assertThat(service.value(), equalTo(""));
	}

	@Test
	public void testWalletServiceIsMarkedWithAutowired() throws NoSuchMethodException {
		Autowired autowired = WalletService.class.getConstructor(WalletDao.class).getAnnotation(Autowired.class);

		assertThat(autowired, notNullValue());
	}

	@Test
	public void testFindRichestWallet() {
		Wallet richest = walletService.findRichest();

		Wallet actualRichest = walletDao.findAll().stream().max(Comparator.comparing(Wallet::getBalance)).get();

		assertThat(richest, equalTo(actualRichest));
	}

}
