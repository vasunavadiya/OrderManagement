package example.ordermanagement.repo;

import example.ordermanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author vasu on 28/01/23 - 8:16 pm
 * @Project OrderManagement
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {

    Customer findByMobileNumber(String mobileNumber);
}
