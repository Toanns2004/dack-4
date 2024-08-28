package org.example.projectapi.Controller;

import org.example.projectapi.Service.AuthService;
import org.example.projectapi.dto.request.EmployeeRequest;
import org.example.projectapi.dto.request.SignInRequest;
import org.example.projectapi.dto.response.EmployeeResponse;
import org.example.projectapi.dto.response.MessageRespone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<EmployeeResponse> register(@RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(authService.registerEmployee(employeeRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<EmployeeResponse> login(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authService.loginUser(signInRequest));
    }

    @PostMapping("/refresh")
    public MessageRespone refresh() {
        return new MessageRespone("success");
    }


}
