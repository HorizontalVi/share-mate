package kz.alibek.sharemate.booking;

import kz.alibek.sharemate.items.Item;
import kz.alibek.sharemate.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponseDto {

    long id;
    LocalDateTime start;
    LocalDateTime end;
    BookingStatus status;
    User booker;
    Item item;
}
