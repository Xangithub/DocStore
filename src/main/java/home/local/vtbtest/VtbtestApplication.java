package home.local.vtbtest;

import home.local.vtbtest.entity.Client;
import home.local.vtbtest.entity.Document;
import home.local.vtbtest.entity.File;
import home.local.vtbtest.entity.User;
import home.local.vtbtest.repository.ClientRepository;
import home.local.vtbtest.repository.DocumentRepository;
import home.local.vtbtest.repository.FileRepository;
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


import java.time.LocalDate;
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
    CommandLineRunner init(StorageService storageService, ClientRepository clientRepository, UserRepository userRepository, DocumentRepository documentRepository, FileRepository fileRepository) {
        return (args) -> {

            final User xan = new User().setUsername("xan").setPassword("$2a$10$.SrZu447yxrGaoTiFP.Cn.MAvWLd4AH8ZdX2nSD/.Pvo8GAe9o02G").setAuthorities(Collections.singletonList(Role.USER)).setFullName("UserFullName");
            userRepository.save(xan);//pass

            userRepository.save(new User().setUsername("second").setPassword("$2a$10$.SrZu447yxrGaoTiFP.Cn.MAvWLd4AH8ZdX2nSD/.Pvo8GAe9o02G").setAuthorities(Collections.singletonList(Role.USER)).setFullName("UserFullName2"));//pass

            final Client melkosoft = new Client().setInn("6587907086").setName("Melkosoft").setOgrn("5128459615972").setDocumentsList(Collections.EMPTY_LIST);
            clientRepository.save(melkosoft);

            clientRepository.save(new Client().setInn("7946719033").setName("Oracle").setOgrn("1085611485200").setDocumentsList(Collections.EMPTY_LIST));

            final File ablaabla = new File().setFileKey("ablaabla").setFileName("pupsik.dat").setData("This DATA in file.".getBytes());
//            fileRepository.save(ablaabla);
            fileRepository.save(new File().setFileKey("vvv").setFileName("zed.dat").setData("my win".getBytes()));

            documentRepository.save(new Document()
                    .setClient(melkosoft)
                    .setUser(xan)
                    .setDocDate(LocalDate.of(2011, 11, 02))
                    .setDocNumber("999")
                    .setFile(ablaabla)
            );


            storageService.init();
            storageService.deleteAll();
        };
    }
}
