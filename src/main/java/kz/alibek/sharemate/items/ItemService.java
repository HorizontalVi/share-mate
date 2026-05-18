package kz.alibek.sharemate.items;

import kz.alibek.sharemate.exception.NotFoundException;
import kz.alibek.sharemate.users.User;
import kz.alibek.sharemate.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {
    public final ItemRepository itemRepository;
    public final UserRepository userRepository;

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public Item createItem(long userId,ItemCreateDto itemDto){
        User owner = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("Пользователь не найден"));
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());

        item.setOwner(owner);

        return itemRepository.save(item);
    }

    public Item updateItem(long itemId, Item item, long userId){
        Item itemUpdate = itemRepository.findById(itemId).orElseThrow(()-> new NotFoundException("Инструмент не найден"));
        return null;
    }

}
