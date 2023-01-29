package example.ordermanagement.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author vasu on 28/01/23 - 3:40 pm
 * @Project OrderManagement
 */
@Getter
@Setter
@Entity
public class Customer extends BaseEntity{

    private String name;

    private String mobileNumber;

    @OneToMany(mappedBy = "customer",targetEntity = ItemOrder.class,fetch = FetchType.LAZY)
    private List<ItemOrder> itemOrders;
}
