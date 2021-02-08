package ru.mitya.service.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.mitya.service.customer.jpa.entity.Address;
import ru.mitya.service.customer.jpa.entity.Customer;
import ru.mitya.service.customer.service.ICustomerService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-address-before.sql", "/create-customer-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-address-after.sql", "/create-customer-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ICustomerService customerService;

    @Test
    public void shouldCreateCustomer() throws Exception {
        Address address = new Address.Builder()
                .withCity("London")
                .build();
        Customer customer = new Customer.Builder()
                .withFirstName("lo")
                .withLastName("li")
                .withMiddleName("la")
                .withRegisteredAddress(address)
                .withActualAddress(address)
                .withSex("male")
                .build();
        this.mockMvc.perform(post("/customers")
                .content(asJsonString(customer))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
        List<Customer> customerList = customerService.getAllCustomers();
        assert customerList.get(3) != null;
    }

    @Test
    public void shouldGetAllCustomers() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldSearchByFirstNameAndLastName() throws Exception{
       this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/search")
               .param("firstname", "Po").param("lastname", "Po"))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(status().isOk())
               .andReturn();
    }

    @Test
    public void shouldEditCustomer() throws Exception{
        Address address = new Address.Builder()
                .withCity("London")
                .build();
        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/2")
        .content(asJsonString(address))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}