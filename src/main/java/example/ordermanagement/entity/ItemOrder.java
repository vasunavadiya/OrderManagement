package example.ordermanagement.entity;


import example.ordermanagement.enums.DeliveryStatus;
import example.ordermanagement.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author vasu on 28/01/23 - 3:37 pm
 * @Project OrderManagement
 */

@Getter
@Setter
@Entity
public class ItemOrder extends  BaseEntity{

    private LocalDateTime orderDateTime;

    private String address;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
    private Customer customer;

    private Double weight;

    private String mobileNumber;

    private String alMobileNumber;

    private Double rate;

    @ManyToOne(targetEntity = Item.class, fetch = FetchType.EAGER)
    private Item item;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private Double grandTotal;

    @OneToMany(mappedBy = "itemOrder",cascade = CascadeType.ALL,fetch = FetchType.EAGER,targetEntity = Quantity.class)
    @OrderBy("createdAt asc")
    private List<Quantity> quantities;

    @OneToMany(mappedBy = "itemOrder",cascade = CascadeType.ALL,fetch = FetchType.LAZY,targetEntity = Payment.class)
    @OrderBy("createdAt asc")
    private List<Payment> payments;
}
