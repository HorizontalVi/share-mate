package kz.alibek.sharemate.items;

import lombok.Data;

import java.util.List;

@Data
public class ItemResponseDto {
    private Long id;
    private String name;
    private String description;
    private boolean available;
    List<CommentResponseDto> comments;
}
