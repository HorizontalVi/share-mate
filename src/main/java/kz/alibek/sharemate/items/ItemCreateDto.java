package kz.alibek.sharemate.items;

import lombok.Data;

@Data
public class ItemCreateDto {
    String name;
    String description;
    Boolean available;
}
