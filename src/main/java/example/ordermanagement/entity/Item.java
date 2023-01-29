package example.ordermanagement.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author vasu on 28/01/23 - 3:38 pm
 * @Project OrderManagement
 */

@Getter
@Setter
@Entity
public class Item extends BaseEntity {

    private String name;

    private Double price;

    @OneToMany(mappedBy ="item",targetEntity = ItemOrder.class,fetch = FetchType.LAZY)
    private List<ItemOrder> itemOrders;

}
