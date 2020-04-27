package home.local.vtbtest.repository;

import home.local.vtbtest.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {

//    List<Document> findByClientId(Long id);
//    int countDocumentsByClient_Id(Long id);
}
