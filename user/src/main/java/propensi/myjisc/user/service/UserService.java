package propensi.myjisc.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import propensi.myjisc.user.dto.RegisterRequest;
import propensi.myjisc.user.dto.UserResponseDTO;
import propensi.myjisc.user.dto.EditUserRequestDTO;
import propensi.myjisc.user.model.User;
import propensi.myjisc.user.repository.UserRepository;

@Service
public class UserService {

    private  UserRepository userRepository;
    
    public void updateUser(Long id, EditUserRequestDTO editRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        // Update user properties
        user.setFirstname(editRequest.getFirstname());
    
        userRepository.save(user);
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

    public void changePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    // public void userDetails(String email) {
    //     User user = userRepository.findByEmail(email)
    //     .orElseThrow(() -> new RuntimeException("User not found"));

        
    // }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponseDTO getUserDetails(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    
        UserResponseDTO response = new UserResponseDTO();
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }

    


}
