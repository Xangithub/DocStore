package home.local.vtbtest.repository;

import home.local.vtbtest.entity.Client;
import home.local.vtbtest.entity.Document;
import home.local.vtbtest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
      List<Document> findAllByUser(User user);
      List<Document> findAllByClient(Client client);
}
