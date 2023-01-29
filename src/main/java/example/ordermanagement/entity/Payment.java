package example.ordermanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * @author vasu on 29/01/23 - 1:42 am
 * @Project OrderManagement
 */
@Entity
@Getter
@Setter
public class Payment extends BaseEntity{

    private Double paidAmount;

//    private Double change;
//
    @ManyToOne(targetEntity = ItemOrder.class, fetch = FetchType.EAGER)
    private ItemOrder itemOrder;
}
