package com.lekhraj.java.spring.database;

import com.lekhraj.java.spring.database.entities.AppOrder;
import com.lekhraj.java.spring.database.entities.AppOrderDetail;
import com.lekhraj.java.spring.database.entities.Customer;
import com.lekhraj.java.spring.database.entities.Product;
import com.lekhraj.java.spring.database.repo.AppOrderRepository;
import com.lekhraj.java.spring.database.repo.CartRepository;
import com.lekhraj.java.spring.database.repo.CustomerRepository;
import com.lekhraj.java.spring.database.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Transactional(transactionManager = "transactionManager_for_postgres")
@Service
public class ShoppingAppService
{
    @Autowired    private CustomerRepository customerRepository;
    @Autowired    private ProductRepository productRepository;
    @Autowired    private AppOrderRepository orderRepository;
    @Autowired    private CartRepository cartRepository;

    // ==== CUSTOMER ======
    public String addCustomer(@RequestParam String name, @RequestParam String email, @RequestParam String address) {
        Customer customer = new Customer();
        customer.setName(name);  customer.setEmail(email); customer.setAddress(address);

        customerRepository.save(customer);

        //Cart cart = new Cart();  cart.setCustomer(customer); cartRepository.save(cart);

        return "Customer added successfully with empty cart";
    }

    public List<Customer> viewCustomer() {  return customerRepository.findAll(); }

    // ==== PRODUCT ======
    public String addProduct(@RequestBody Product product) {
        productRepository.save(product);
        return "Product added successfully!";
    }

    public List<Product> viewProduct() { return productRepository.findAll();   }

    // ==== Place order ======

    public String placeOrder(@RequestParam Long customerId, @RequestParam Long[] productIds, @RequestParam short quantity) {
        // Fetch the customer
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            return "Customer not found!";
        }

        // Create a new order for the customer
        AppOrder order = new AppOrder();
        order.setCustomer(customer);
        orderRepository.save(order);

        // Add order details
        List<AppOrderDetail> orderDetail_list = new ArrayList<>();
        for (Long productId : productIds) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                AppOrderDetail orderDetail = new AppOrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProductName(product.getProductName());
                orderDetail.setQuantity(quantity);
                orderDetail.setPrice(product.getPrice());
                orderDetail_list.add(orderDetail);
            }
        }
        order.setOrderDetails(orderDetail_list);

        orderRepository.save(order);
        return "Order placed successfully!";
    }
}
