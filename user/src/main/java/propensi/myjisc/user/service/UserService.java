package propensi.myjisc.user.service;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import propensi.myjisc.user.dto.RegisterRequest;
import propensi.myjisc.user.dto.UserResponseDTO;
import propensi.myjisc.user.dto.EditUserRequestDTO;
import propensi.myjisc.user.model.Role;
import propensi.myjisc.user.model.User;
import propensi.myjisc.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
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

    public List<Long> getAllMurid() throws NoSuchObjectException {
        List<User> listMurid = userRepository.findByRole(Role.MURID);
        List<Long> idMurid = new ArrayList<>();
        if (listMurid.isEmpty() || listMurid == null) {
            throw new NoSuchObjectException("No murid found");
        } else {
            for (User user : listMurid) {
                idMurid.add(user.getId());
            }
            return idMurid;
        }
    }

    public List<Long> getAllGuru() throws NoSuchObjectException {
        List<User> listGuru = userRepository.findByRole(Role.GURU);
        List<Long> idGuru = new ArrayList<>();
        if (listGuru.isEmpty() || listGuru == null) {
            throw new NoSuchObjectException("No murid found");
        } else {
            for (User user : listGuru) {
                idGuru.add(user.getId());
            }
            return idGuru;
        }
    }


}
