package IC.SmartPark.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteAdmin(int id){
        userRepository.deleteById(id);
    }
    public ArrayList<User> getAdmins(){
        return userRepository.findByRole(Role.ADMIN);
    };
    public void updateAdmin(int id,String firstName,String lastName,String email){
        Optional<User> o=userRepository.findById(id);
        if(o.isPresent()){
            User u=o.get();
            u.setFirstName(firstName);
            u.setLastName(lastName);
            u.setEmail(email);
            userRepository.save(u);
        }
    }
    public void addAdmin(User u){
        userRepository.save(u);
    }
    public boolean checkAdmin(String email){
        Optional<User> o=userRepository.findByEmail(email);
        if(o.isPresent()) {
            User u=o.get();
            if (u.getRol()==Role.ADMIN){
                return true;
            }
            return false;
        }
        return false;
    }
    public Optional<User> findAdminById(int id){
        return userRepository.findById(id);
    }
}

