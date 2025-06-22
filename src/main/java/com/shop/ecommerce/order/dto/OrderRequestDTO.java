package com.shop.ecommerce.order.dto;

import java.util.List;

public class OrderRequestDTO {
    private String name;
    private String address;
    private String paymentMethod;
    private String creditCardNumber;
    private String creditCardExpiry;
    private String creditCardCVC;
    private List<OrderItemDTO> items;

    // Getter und Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getCreditCardNumber() { return creditCardNumber; }
    public void setCreditCardNumber(String creditCardNumber) { this.creditCardNumber = creditCardNumber; }

    public String getCreditCardExpiry() { return creditCardExpiry; }
    public void setCreditCardExpiry(String creditCardExpiry) { this.creditCardExpiry = creditCardExpiry; }

    public String getCreditCardCVC() { return creditCardCVC; }
    public void setCreditCardCVC(String creditCardCVC) { this.creditCardCVC = creditCardCVC; }

    public List<OrderItemDTO> getItems() { return items; }
    public void setItems(List<OrderItemDTO> items) { this.items = items; }
}
