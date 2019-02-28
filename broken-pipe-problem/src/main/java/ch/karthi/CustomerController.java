package ch.karthi;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * To reproduce the broken pipe mysql last packet sent .. problem, just set wait_timeout in mysql to 50 seconds 
 * and comment out the hibernate.connection.release_mode=AFTER_STATEMENT in hibernate.properties
 * and calls this endpoint 
 
 * @author k.bolluganesh
 *
 */

@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/customers")
	public List<Customer> getCustomer() throws InterruptedException {
		Customer customer = new Customer();
		customer.setName("myCustomer");
		customer.setId(UUID.randomUUID().getLeastSignificantBits());
		customerRepository.save(customer);

		TimeUnit.SECONDS.sleep(80);

		return customerRepository.findAll();
	}

}
