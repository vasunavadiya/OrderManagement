package example.ordermanagement.repo;

import example.ordermanagement.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author vasu on 28/01/23 - 7:55 pm
 * @Project OrderManagement
 */
@Repository
public interface ItemRepository extends JpaRepository<Item,String> {

    List<Item> findAllByStatusIsTrue();

    Optional<Item> findById(String id);
}
