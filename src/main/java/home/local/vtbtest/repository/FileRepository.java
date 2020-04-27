package home.local.vtbtest.repository;

import home.local.vtbtest.entity.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {
}
