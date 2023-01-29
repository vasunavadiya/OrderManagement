package example.ordermanagement.repo;

import example.ordermanagement.entity.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author vasu on 28/01/23 - 9:06 pm
 * @Project OrderManagement
 */
@Repository
public interface ItemOrderRepository extends JpaRepository<ItemOrder,String> {

    @Override
    Optional<ItemOrder> findById(String s);
}
