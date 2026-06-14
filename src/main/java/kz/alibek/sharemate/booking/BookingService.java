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
            throw new BadRequestException("Start cannot be null");
        }
        if (dto.getEnd() == null) {
            throw new BadRequestException("End cannot be null");
        }
        if (dto.getStart().isAfter(dto.getEnd())) {
            throw new BadRequestException("start cannot be after than end");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new NotFoundException("Вещь не найдена"));
        if (item.getAvailable() == false) {
            throw new BadRequestException("Cannot book unavailable item");
        }
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

    public List<Booking> findBookingsByBookerId (long userId){
        return bookingRepository.findBookingsByBookerId(userId);
    }

    public List<Booking> findBookingsByOwnerId(long userId){
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не верный"));
        return bookingRepository.findBookingsByItem_Owner_Id(userId);
    }

    public BookingResponseDto findBookingById(long userId, long bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Бронирование не найдено"));
        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().getId().equals(userId)){
            throw new NotFoundException("Не найден");
        }
        BookingResponseDto bookingResponseDto = new BookingResponseDto();
        bookingResponseDto.setId(booking.getId());
        bookingResponseDto.setStart(booking.getStart());
        bookingResponseDto.setEnd(booking.getEnd());
        bookingResponseDto.setStatus(booking.getStatus());
        bookingResponseDto.setBooker(booking.getBooker());
        bookingResponseDto.setItem(booking.getItem());
        return bookingResponseDto;
    }

    public BookingResponseDto updateBookingById(long userId, long bookingId, boolean approved){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Броанивание не найдено"));
        if (!booking.getItem().getOwner().getId().equals(userId)){
            throw new BadRequestException("Вы не являетесь хозяином предмета");
        }
        if (approved){
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        bookingRepository.save(booking);
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setStart(booking.getStart());
        dto.setEnd(booking.getEnd());
        dto.setStatus(booking.getStatus());
        dto.setBooker(booking.getBooker());
        dto.setItem(booking.getItem());
        return dto;
    }
}
