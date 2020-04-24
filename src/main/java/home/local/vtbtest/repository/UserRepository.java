package home.local.vtbtest.repository;

import home.local.vtbtest.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
