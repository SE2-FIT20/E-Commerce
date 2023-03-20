package com.example.ecommerce.service.security;

import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.domain.Store;
import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.response.LoginResponse;
import com.example.ecommerce.dto.request.auth.LoginRequest;
import com.example.ecommerce.dto.request.auth.RegistrationRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.RegistrationException;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.impl.CustomerService;
import com.example.ecommerce.service.impl.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomerService customerService;
    private final StoreService storeService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<Response> register(RegistrationRequest registrationRequest) {

        boolean emailAlreadyRegistered = userRepository.existsByEmailIgnoreCase(registrationRequest.getEmail());

        if (emailAlreadyRegistered) {
            //TODO: custom RegistrationException and handle it in ExceptionHandler
            return ResponseEntity.ok(Response.builder().status(400).message("Email already registered!").build());
        }

        String role = registrationRequest.getRole();

        if (role.equals("CUSTOMER")) {
            Customer customer = new Customer();
            customer.setEmail(registrationRequest.getEmail());
            customer.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            customer.setName(registrationRequest.getName());
            customer.setRole(registrationRequest.getRole());
            customer.setAvatar(generateAvatarLink(customer.getName()));
            customerService.save(customer);

        } else if (role.equals("STORE")) {
            Store store = new Store();
            store.setEmail(registrationRequest.getEmail());
            store.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            store.setName(registrationRequest.getName());
            store.setRole(registrationRequest.getRole());
            store.setAvatar(generateAvatarLink(store.getName()));
            storeService.save(store);
        } else {
            throw new RegistrationException("Invalid role!");
        }

        Response response = Response.builder()
                .status(200)
                .message("Registration successfully!")
                .build();
        return ResponseEntity.ok(response);

    }

    private String generateAvatarLink(String name) {
        return String.format("https://ui-avatars.com/api/?name=%s&background=random", name);
    }
    @Override
    public ResponseEntity<Response> login(LoginRequest loginRequest) {


        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        try {
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            // if username or password is wrong, the BadCredentialsException will be thrown

            User user = userRepository.findByEmailIgnoreCase(loginRequest.getEmail()).get();
            String jwtToken = jwtService.generateToken(user);

            LoginResponse token = LoginResponse.builder()
                    .token(jwtToken)
                    .role(user.getRole())
                    .build();

            Response response = Response.builder()
                    .status(200)
                    .message("Log in successfully!")
                    .data(token)
                    .build();

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            Response response = Response.builder()
                    .status(400)
                    .message("Wrong username or password!")
                    .build();


            return ResponseEntity.badRequest().body(response);
        }



    }
}
