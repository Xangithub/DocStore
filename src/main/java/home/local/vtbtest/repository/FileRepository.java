package home.local.vtbtest.repository;

import home.local.vtbtest.model.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Integer> {
}
