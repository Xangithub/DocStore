package home.local.vtbtest.repository;

import home.local.vtbtest.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

//@RepositoryRestResource
//public interface ClientRepository extends CrudRepository<Client, Integer> {
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
