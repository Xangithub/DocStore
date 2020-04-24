package home.local.vtbtest.repository;

import home.local.vtbtest.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

    List<Document> findByClientId(Integer id);
    int countDocumentsByClient_Id(Integer id);
}
