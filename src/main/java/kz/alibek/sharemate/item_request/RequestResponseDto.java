package kz.alibek.sharemate.item_request;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Setter
public class RequestResponseDto {
    Long id;
    String description;
    LocalDateTime created;
    List<ItemResponseDto> items;
    @Data
    public static class ItemResponseDto{
        private Long id;
        private String name;
        private String description;
        private boolean available;
    }
}
