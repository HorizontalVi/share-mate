package kz.alibek.sharemate.booking;

import kz.alibek.sharemate.exception.BadRequestException;
import kz.alibek.sharemate.exception.NotFoundException;
import kz.alibek.sharemate.items.Item;
import kz.alibek.sharemate.items.ItemRepository;
import kz.alibek.sharemate.users.User;
import kz.alibek.sharemate.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public BookingResponseDto create(long userId, BookingCreateDto dto) {
        if (dto.getStart() == null) {
            throw new BadRequestException("Status code is 400");
        }
        if (dto.getEnd() == null) {
            throw new BadRequestException("Status code is 400");
        }
        if (dto.getStart().isAfter(dto.getEnd())) {
            throw new BadRequestException("Status code is 400");
        }
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new NotFoundException("Вещь не найдена"));
        if (item.getAvailable() == false) {
            throw new BadRequestException("Status code is 400");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Booking booking = new Booking();
        booking.setBooker(user);
        booking.setItem(item);
        booking.setStart(dto.getStart());
        booking.setEnd(dto.getEnd());
        booking.setStatus(BookingStatus.WAITING);

        bookingRepository.save(booking);

        BookingResponseDto responseBookerDto = new BookingResponseDto();
        responseBookerDto.setId(booking.getId());
        responseBookerDto.setStart(booking.getStart());
        responseBookerDto.setEnd(booking.getEnd());
        responseBookerDto.setItem(booking.getItem());
        responseBookerDto.setBooker(booking.getBooker());
        responseBookerDto.setStatus(booking.getStatus());

        return responseBookerDto;
    }

    public List<Booking> findAll (){
        return bookingRepository.findAll();
    }
}
