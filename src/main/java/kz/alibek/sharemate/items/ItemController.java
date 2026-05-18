package kz.alibek.sharemate.items;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<Item> findAll(){
        return itemService.findAll();
    }

    @PostMapping
    public Item create(@RequestHeader("X-Sharer-User-Id") long userId, @RequestBody ItemCreateDto dto){
        return itemService.createItem(userId,dto);
    }

    @PatchMapping
    public Item update(@RequestHeader("X-Sharer-User-Id") long userId,@RequestBody ItemCreateDto dto){
        return itemService.updateItem(userId,dto);
    }
}