package example.ordermanagement.enums;

/**
 * @author vasu on 28/01/23 - 3:59 pm
 * @Project OrderManagement
 */
public enum DeliveryStatus {
    DELEVERD("deleverd"),
    PENDING("pending");
    private String value;

    public String getValue() {
        return value;
    }

    DeliveryStatus(String value) {
        this.value = value;
    }
}
