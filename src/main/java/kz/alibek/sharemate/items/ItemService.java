package kz.alibek.sharemate.items;

import kz.alibek.sharemate.exception.NotFoundException;
import kz.alibek.sharemate.users.User;
import kz.alibek.sharemate.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {
    public final ItemRepository itemRepository;
    public final UserRepository userRepository;

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public Item findById(long itemId, long userId){
        return itemRepository.findById(itemId).orElseThrow(()-> new NotFoundException("Инструмент не найден"));
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

    public Item updateItem(long userId, ItemCreateDto item, long itemId){
        /*User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("Ползователь не найден"));*/
        Item itemUpdate = itemRepository.findById(itemId).orElseThrow(()-> new NotFoundException("Инструмент не найден"));
        if (!itemUpdate.getOwner().getId().equals(userId)){
            throw new NotFoundException("Предмет не найден");
        }
        if(item.getName()!= null){
            itemUpdate.setName(item.getName());
        }
        if (item.getDescription()!=null){
            itemUpdate.setDescription(item.getDescription());
        }
       if (item.getAvailable() != null){
           itemUpdate.setAvailable(item.getAvailable());
       }
       itemRepository.save(itemUpdate);
        return itemUpdate;
    }

    public List<Item> findByOwnerId(long userId) {
        return itemRepository.findByOwnerId(userId);
    }

    public List<Item> search (String text){
        if (text.isBlank()){
            return Collections.emptyList();
        }
        return itemRepository.search(text);
    }
}