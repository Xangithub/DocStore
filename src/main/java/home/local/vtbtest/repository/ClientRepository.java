package home.local.vtbtest.repository;

import home.local.vtbtest.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByInn(String inn);


}
