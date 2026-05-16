package kz.alibek.sharemate.users;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable long id){
        return userService.findById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id){
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable long id,@RequestBody User user){
        return userService.updateUser(id,user);
    }
}