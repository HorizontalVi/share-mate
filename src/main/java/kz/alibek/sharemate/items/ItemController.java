package kz.alibek.sharemate.items;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping()
    public List<Item> findByOwnerId(@RequestHeader("X-Sharer-User-Id") long userId){
        return itemService.findByOwnerId(userId);
    }

    @GetMapping("/{itemId}")
    public Item findById (@PathVariable long itemId, @RequestHeader("X-Sharer-User-Id") long userId){
        return itemService.findById(itemId,userId);
    }

    @PostMapping
    public Item create(@RequestHeader("X-Sharer-User-Id") long userId, @Valid @RequestBody ItemCreateDto dto){
        return itemService.createItem(userId,dto);
    }

    @PatchMapping("/{itemId}")
    public Item update(@RequestHeader("X-Sharer-User-Id") long userId,@RequestBody ItemCreateDto dto,@PathVariable long itemId){
        return itemService.updateItem(userId,dto,itemId);
    }

    @GetMapping("/search")
    public List<Item> searchByNameOrDescription(@RequestParam String text){
        return itemService.search(text);
    }
}