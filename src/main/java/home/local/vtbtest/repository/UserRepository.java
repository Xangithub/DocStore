package home.local.vtbtest.repository;

import home.local.vtbtest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
