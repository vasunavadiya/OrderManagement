package example.ordermanagement.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author vasu on 29/01/23 - 12:18 am
 * @Project OrderManagement
 */
@Getter
@Setter
public class QuantityDto {

    private String orderId;

    private Double weight;
}
