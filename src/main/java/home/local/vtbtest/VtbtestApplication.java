package home.local.vtbtest;

import home.local.vtbtest.entity.Client;
import home.local.vtbtest.entity.User;
import home.local.vtbtest.repository.ClientRepository;
import home.local.vtbtest.repository.UserRepository;
import home.local.vtbtest.config.Role;
import home.local.vtbtest.storage.StorageProperties;
import home.local.vtbtest.storage.StorageService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


import java.util.Collections;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class VtbtestApplication {

	public static void main(String[] args) {
		SpringApplication.run(VtbtestApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT)
				.setFieldMatchingEnabled(true)
				.setSkipNullEnabled(true)
				.setFieldAccessLevel(PRIVATE);
		return mapper;
	}

	/**
	 * Наполнение для БД
	 */
	@Bean
	CommandLineRunner init(StorageService storageService, ClientRepository clientRepository, UserRepository userRepository) {
		return (args) -> {
			userRepository.save(new User().setUsername("xan").setPassword("$2a$10$.SrZu447yxrGaoTiFP.Cn.MAvWLd4AH8ZdX2nSD/.Pvo8GAe9o02G").setAuthorities(Collections.singletonList(Role.USER))).setFullName("UserFullName"); //pass
			userRepository.save(new User().setUsername("second").setPassword("$2a$10$.SrZu447yxrGaoTiFP.Cn.MAvWLd4AH8ZdX2nSD/.Pvo8GAe9o02G").setAuthorities(Collections.singletonList(Role.USER))).setFullName("UserFullName2");//pass
			clientRepository.save(new Client().setInn("6587907086").setName("ООО МММ").setOgrn("5128459615972"));
			clientRepository.save(new Client().setInn("7946719033").setName("ООО Рога и Копыта").setOgrn("1085611485200"));
			storageService.deleteAll();
			storageService.init();
		};
	}
}
