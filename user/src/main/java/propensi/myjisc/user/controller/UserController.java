package propensi.myjisc.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import propensi.myjisc.user.dto.EditUserRequestDTO;
import propensi.myjisc.user.dto.RegisterRequest;
import propensi.myjisc.user.dto.UserResponseDTO;
import propensi.myjisc.user.model.User;
import propensi.myjisc.user.repository.UserRepository;
import propensi.myjisc.user.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getUserDetails(@RequestParam String email) {
        try {
            UserResponseDTO user = userService.getUserDetails(email);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
            User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
            return ResponseEntity.ok(user);
        }

        @PutMapping("/user/update/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody EditUserRequestDTO editRequest) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            System.out.println(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                // Update user properties
                user.setFirstname(editRequest.getFirstname());
                user.setLastname(editRequest.getLastname());

                userRepository.save(user);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // print the exception message to console
            System.err.println("Error updating user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


        
}

    // @PutMapping("/user/update/{id}")
    // public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody EditUserRequestDTO editRequest) {
    //     try {
    //         userService.updateUser(id, editRequest); // Pass the user ID to the service method
    //         return new ResponseEntity<>(HttpStatus.OK);
    //     } catch (RuntimeException e) {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }

