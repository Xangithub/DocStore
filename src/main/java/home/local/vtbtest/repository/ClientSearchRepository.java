package home.local.vtbtest.repository;

import home.local.vtbtest.entity.Client;
import home.local.vtbtest.util.ClientSearchQueryCriteriaConsumer;
import home.local.vtbtest.util.SearchCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Getter
@Setter
@NoArgsConstructor
public class ClientSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Client> searchClient(List<SearchCriteria> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = builder.createQuery(Client.class);
        Root r = query.from(Client.class);

        Predicate predicate = builder.conjunction();

        ClientSearchQueryCriteriaConsumer searchConsumer =
                new ClientSearchQueryCriteriaConsumer(predicate, builder, r);
        params.stream().forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        List<Client> result = entityManager.createQuery(query).getResultList();
        return result;
    }

}
