package example.ordermanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * @author vasu on 28/01/23 - 11:55 pm
 * @Project OrderManagement
 */
@Getter
@Setter
@Entity
public class Quantity extends BaseEntity{

    private Double weight;

    @ManyToOne(targetEntity = ItemOrder.class, fetch = FetchType.LAZY)
    private ItemOrder itemOrder;

}
