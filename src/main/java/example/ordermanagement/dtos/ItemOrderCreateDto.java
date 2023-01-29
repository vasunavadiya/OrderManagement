package example.ordermanagement.dtos;

import example.ordermanagement.entity.Customer;
import example.ordermanagement.entity.Item;
import example.ordermanagement.entity.ItemOrder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

/**
 * @author vasu on 28/01/23 - 8:05 pm
 * @Project OrderManagement
 */
@Getter
@Setter
public class ItemOrderCreateDto {

    private String name;

    private String orderDateTime;

    private String address;

    private String mobileNumber;

    private String alMobileNumber;

    private Double rate;

    private String itemId;

    private Double  weight;

    private Double advance;
}
