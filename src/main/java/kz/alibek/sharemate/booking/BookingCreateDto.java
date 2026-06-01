package kz.alibek.sharemate.booking;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingCreateDto {
    private long itemId;
    private LocalDateTime start;
    private LocalDateTime end;
}
