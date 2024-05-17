package com.restapistarter;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.restapi.model.Customer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

    private int port = 8081;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/test",
                String.class)).contains("test controller");
    }

    @Test
    public void getCliente() {
    ResponseEntity<Customer> responseEntity = restTemplate.getForEntity("/api/customers/{id}", Customer.class, 1);
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    
    Customer customer = responseEntity.getBody();
    assertThat(customer.getId()).isEqualTo(1);
    assertThat(customer.getName()).isEqualTo("Mario");
}
}
