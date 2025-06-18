package com.lekhraj.java.spring.database;

import com.lekhraj.java.spring.database.entities.Customer;
import com.lekhraj.java.spring.database.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shopping-app")
public class ShoppingAppController
{
    @Autowired  private ShoppingAppService srv;

    // ==== CUSTOMER ======
    @PostMapping("/add-customer")
    public String addCustomer(@RequestParam String name, @RequestParam String email, @RequestParam String address) {
        return srv.addCustomer(name,email,address);
    }

    @PostMapping("/view-customer")
    public List<Customer> viewCustomer() {  return srv.viewCustomer();}

    // ==== PRODUCT ======
    @PostMapping("/add-product")
    public String addProduct(@RequestBody Product product) {
        return srv.addProduct(product);
    }

    @PostMapping("/view-product")
    public List<Product> viewProduct() { return srv.viewProduct();  }

    // ==== Place order ======

    @PostMapping("/place-order-directly")
    public String placeOrder(@RequestParam Long customerId, @RequestParam Long[] productIds, @RequestParam short quantity) {
        return srv.placeOrder(customerId,productIds,quantity);
    }

    /*

    @PostMapping("/add-product-to-cart")
    public String addToCart(@RequestParam Long customerId, @RequestParam Long productId, @RequestParam int quantity)
    {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {            return "Customer not found!";        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {            return "Product not found!";        }

        // Fetch or create cart for the customer
        Cart cart = cartRepository.findByCustomer(customer).orElse(new Cart());
        cart.setCustomer(customer);
        cart.setQuantity(quantity);
        List temp_products = cart.getProducts(); temp_products.add(product);
        cart.setProducts(temp_products);

        cartRepository.save(cart);
        return "Product added to cart!";
    }

    */
}
