package org.example.projectapi.Controller;

import org.example.projectapi.Service.CustomerService;
import org.example.projectapi.Service.EmployeeService;
import org.example.projectapi.model.Customer;
import org.example.projectapi.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllCustomer() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getCustomerById(@PathVariable Long id) {
        Optional<Employee> publisher = employeeService.findById(id);
        return publisher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Employee createCustomer(@RequestBody Employee publisher) {
        return employeeService.save(publisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateCustomer(@PathVariable Long id, @RequestBody Employee publisherDetails) {
        Optional<Employee> publisher = employeeService.findById(id);
        if (publisher.isPresent()) {
            Employee updatedPublisher = publisher.get();
            updatedPublisher.setFullName(publisherDetails.getFullName());
            updatedPublisher.setEmail(publisherDetails.getEmail());
            updatedPublisher.setPhone(publisherDetails.getPhone());
            updatedPublisher.setRoles(publisherDetails.getRoles());
            updatedPublisher.setPassword(publisherDetails.getPassword());
            updatedPublisher.setAge(publisherDetails.getAge());
            return ResponseEntity.ok(employeeService.save(updatedPublisher));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Optional<Employee> publisher = employeeService.findById(id);
        if (publisher.isPresent()) {
            employeeService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
