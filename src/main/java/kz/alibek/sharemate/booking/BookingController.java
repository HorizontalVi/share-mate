package kz.alibek.sharemate.booking;

import kz.alibek.sharemate.items.ItemRepository;
import kz.alibek.sharemate.items.ItemService;
import kz.alibek.sharemate.users.UserRepository;
import kz.alibek.sharemate.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final UserService userService;
    private final ItemService itemService;
    private final BookingService bookingService;

    @PostMapping
    public BookingResponseDto create (@RequestHeader("X-Sharer-User-Id") long userId, @RequestBody BookingCreateDto dto){
        return bookingService.create(userId,dto);
    }

    @GetMapping
    public List<Booking> findAll (){
        return bookingService.findAll();
    }
}
