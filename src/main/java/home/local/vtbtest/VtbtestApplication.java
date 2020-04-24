package home.local.vtbtest;

import home.local.vtbtest.entity.Client;
import home.local.vtbtest.model.Document;
import home.local.vtbtest.repository.ClientRepository;
import home.local.vtbtest.storage.StorageProperties;
import home.local.vtbtest.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class VtbtestApplication {

	public static void main(String[] args) {
		SpringApplication.run(VtbtestApplication.class, args);
	}
	/**
	 * Наполнение для БД
	 */
	@Bean
	CommandLineRunner init(StorageService storageService, ClientRepository clientRepository) {
		return (args) -> {
			clientRepository.save(new Client().setInn("6587907086").setName("ООО МММ").setOgrn("5128459615972").setRespawnDate(LocalDate.of(2000,10,2)));
			clientRepository.save(new Client().setInn("7946719033").setName("ООО Рога и Копыта").setOgrn("1085611485200").setRespawnDate(LocalDate.of(2010,11,1)));
//			storageService.deleteAll();
			storageService.init();
		};
	}
}
