package eu.dev.test.models;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SortedResultDao extends CrudRepository<SortedResult, Long> {

    @Query( "SELECT ali from SortedResult ali ORDER BY ali.created DESC" )
    public List<SortedResult> findAllResults();

}
