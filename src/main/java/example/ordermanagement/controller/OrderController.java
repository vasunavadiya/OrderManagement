package example.ordermanagement.controller;


import example.ordermanagement.dtos.ItemOrderCreateDto;
import example.ordermanagement.dtos.PaymentDto;
import example.ordermanagement.dtos.QuantityDto;
import example.ordermanagement.entity.*;
import example.ordermanagement.enums.DeliveryStatus;
import example.ordermanagement.enums.PaymentStatus;
import example.ordermanagement.repo.CustomerRepository;
import example.ordermanagement.repo.ItemOrderRepository;
import example.ordermanagement.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author vasu on 28/01/23 - 3:58 pm
 * @Project OrderManagement
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemOrderRepository itemOrderRepository;

    @GetMapping("/create")
    private String creatOrder(Model model) {
        List<Item> items = itemRepository.findAllByStatusIsTrue();
        ItemOrderCreateDto createDto = new ItemOrderCreateDto();
        model.addAttribute("items", items);
        model.addAttribute("itemOrder", createDto);
        return "order/CreateOrder";
    }

    @PostMapping("/placeOrder")
    private String placeOrder(@ModelAttribute("itemOrder") ItemOrderCreateDto itemOrderCreateDto) {
        ItemOrder itemOrder = new ItemOrder();
        Optional<Item> item = itemRepository.findById(itemOrderCreateDto.getItemId());
        Customer customer = customerRepository.findByMobileNumber(itemOrderCreateDto.getMobileNumber());

        if (item.isPresent()){
            itemOrder.setItem(item.get());

            LocalDate orderDate = LocalDate.parse(itemOrderCreateDto.getOrderDateTime());
            itemOrder.setOrderDateTime(orderDate.atTime(LocalTime.now()));
            itemOrder.setAddress(itemOrderCreateDto.getAddress());
            if (customer == null) {
                customer = new Customer();
                customer.setName(itemOrderCreateDto.getName());
                customer.setMobileNumber(itemOrderCreateDto.getMobileNumber());
                customer = customerRepository.save(customer);
            }
            itemOrder.setCustomer(customer);
            itemOrder.setMobileNumber(itemOrderCreateDto.getMobileNumber());
            itemOrder.setAlMobileNumber(itemOrderCreateDto.getAlMobileNumber());
            if (itemOrderCreateDto.getRate() == null){
                itemOrder.setRate(item.get().getPrice());
            }else {
                itemOrder.setRate(itemOrderCreateDto.getRate());
            }
            itemOrder.setWeight(itemOrderCreateDto.getWeight());
            itemOrder.setGrandTotal(itemOrder.getRate() * itemOrderCreateDto.getWeight());
            if (itemOrderCreateDto.getAdvance() != null){
                List<Payment> payments = new ArrayList<>();
                Payment payment = new Payment();
                payment.setItemOrder(itemOrder);
                payment.setPaidAmount(itemOrderCreateDto.getAdvance());
                payments.add(payment);
                itemOrder.setPayments(payments);
            }

            itemOrder.setDeliveryStatus(DeliveryStatus.PENDING);
            itemOrder.setPaymentStatus(PaymentStatus.UN_PAID);
            itemOrderRepository.save(itemOrder);
            return "redirect:/order/list";
        }
        return "redirect:/order/create";
    }

    @GetMapping("/list")
    private String lisOfAllOrder(Model model){
        List<ItemOrder> itemOrders = itemOrderRepository.findAll();
        model.addAttribute("orders", itemOrders);
        return "order/list";
    }

    @GetMapping("/getOrder")
    private String getItemOrder(@RequestParam(value = "orderId")String orderId,Model model){
        Optional<ItemOrder> itemOrder = itemOrderRepository.findById(orderId);
        model.addAttribute("order",itemOrder.get());

        model.addAttribute("que",new QuantityDto());
        model.addAttribute("id",itemOrder.get().getId());
        model.addAttribute("delivered",itemOrder.get().getQuantities());
        return "order/orderDetail";
    }

    @PostMapping("/delivery")
    private String deliveryItem(@ModelAttribute("que") QuantityDto quantityDto,@RequestParam("id")String orderId,Model model){
        quantityDto.setOrderId(orderId);
        Optional<ItemOrder> itemOrder = itemOrderRepository.findById(quantityDto.getOrderId());
        if (itemOrder.isPresent()){
            Quantity quantity = new Quantity();
            quantity.setWeight(quantityDto.getWeight());
            if (!itemOrder.get().getQuantities().isEmpty()){
                itemOrder.get().getQuantities().add(quantity);
            }else {
                itemOrder.get().getQuantities().add(quantity);
            }
            quantity.setItemOrder(itemOrder.get());
            itemOrder.get().setDeliveryStatus(DeliveryStatus.DELEVERD);
            itemOrderRepository.save(itemOrder.get());

        }else {
            return "redirect:/order/list";
        }
        return "redirect:/order/getOrder?orderId="+quantityDto.getOrderId();
    }

    @GetMapping("/generate-payment")
    private String generatePayment(@RequestParam("id")String orderId,Model model){
        Optional<ItemOrder> itemOrder = itemOrderRepository.findById(orderId);
        if (itemOrder.isPresent()){
            Double totalQuantity = itemOrder.get().getQuantities().stream().mapToDouble(
                    value -> value.getWeight()).sum();
            Double totalPayment = totalQuantity * itemOrder.get().getRate();
            model.addAttribute("id", itemOrder.get().getId());
            model.addAttribute("order", itemOrder.get());
            model.addAttribute("totalPayment", totalPayment);
            model.addAttribute("totalQuantity", totalQuantity);
            model.addAttribute("payment", new PaymentDto());
            model.addAttribute("paidPayments",itemOrder.get().getPayments());
            model.addAttribute("paymentStatus",PaymentStatus.PAID);
        }else {
            return "redirect:/order/list";
        }
        return "order/paymentSummery";
    }

    @PostMapping("/payment")
    private String payment(@ModelAttribute("payment")PaymentDto paymentDto,@RequestParam("id") String orderId){
        Optional<ItemOrder> itemOrder = itemOrderRepository.findById(orderId);
        Payment payment = new Payment();
        if (itemOrder.isPresent()){
            Double totalQuantity = itemOrder.get().getQuantities().stream().mapToDouble(
                    value -> value.getWeight()).sum();
            Double totalPayment = totalQuantity * itemOrder.get().getRate();
            Double alredayPaid = itemOrder.get().getPayments().stream().mapToDouble(value -> value.getPaidAmount()).sum();
            double change =totalPayment- paymentDto.getPaidAmount() - alredayPaid ;
            if (change > 0){
                itemOrder.get().setPaymentStatus(PaymentStatus.UN_PAID);
                payment.setPaidAmount(paymentDto.getPaidAmount());
//                payment.setChange(change);
            }else{
                itemOrder.get().setPaymentStatus(PaymentStatus.PAID);
                payment.setPaidAmount(paymentDto.getPaidAmount());
//                payment.setChange(change);
            }
            payment.setItemOrder(itemOrder.get());
            itemOrder.get().getPayments().add(payment);
            itemOrderRepository.save(itemOrder.get());
            return "redirect:/order/generate-payment?id="+itemOrder.get().getId();
        }
        return "redirect:/order/list";
     }

}
