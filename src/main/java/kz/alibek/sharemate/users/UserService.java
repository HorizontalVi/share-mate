package kz.alibek.sharemate.users;

import jakarta.validation.constraints.Email;
import kz.alibek.sharemate.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id){
        return userRepository.findById(id).orElseThrow(()->new NotFoundException("Пользователь с id=%s не найден".formatted(id)));
    }


    public User createUser(UserCreateDto dto) {
        User user = new User();
        user.setName(dto.getName());
        if (userRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException("Конфликт.Email существует");
        }
        user.setEmail(dto.getEmail());

        return userRepository.save(user);
    }


    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(long id, User user) {
        User userUpdate = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (user.getEmail() != null) {
            userUpdate.setEmail(user.getEmail());
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Конфликт.Email существует");
        }
        if (user.getName() != null) {
            userUpdate.setName(user.getName());
        }

        return userRepository.save(userUpdate);
    }
}