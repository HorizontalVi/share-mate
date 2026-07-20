package kz.alibek.sharemate.items;

import kz.alibek.sharemate.booking.Booking;
import kz.alibek.sharemate.booking.BookingRepository;
import kz.alibek.sharemate.booking.BookingService;
import kz.alibek.sharemate.booking.BookingStatus;
import kz.alibek.sharemate.exception.BadRequestException;
import kz.alibek.sharemate.exception.NotFoundException;
import kz.alibek.sharemate.item_request.RequestRepository;
import kz.alibek.sharemate.users.User;
import kz.alibek.sharemate.users.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ItemService {
    public final ItemRepository itemRepository;
    public final UserRepository userRepository;
    public final BookingRepository bookingRepository;
    public final CommentRepository commentRepository;
    public final RequestRepository requestRepository;


    public ItemResponseDto findById(long itemId, long userId){
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Инструмент не найден"));
        List<Comment> comments = commentRepository.findCommentsByItemId(itemId);
        ItemResponseDto dto = new ItemResponseDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setAvailable(item.getAvailable());
        dto.setDescription(item.getDescription());

        List<CommentResponseDto> list = new ArrayList<>();
        for (Comment comment: comments){
           CommentResponseDto commentResponseDto = new CommentResponseDto();
           commentResponseDto.setId(comment.getId());
           commentResponseDto.setText(comment.getText());
           commentResponseDto.setCreated(comment.getCreated());
           commentResponseDto.setAuthorName(comment.getAuthor().getName());
           list.add(commentResponseDto);
       }

        dto.setComments(list);
        return dto;
    }

    public Item createItem(long userId,ItemCreateDto itemDto){
        User owner = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("Пользователь не найден"));
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        if(requestRepository.findById())
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

    /*POST /items/{itemId}/comment*/

    public CommentResponseDto createComment(long userId, long itemId, CommentCreateDto comment){
        log.info("Create comment {}",LocalDateTime.now());
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Вещь не найдена"));
        List<Booking> bookingsByBookerIdAndItemId = bookingRepository.findBookingsByBookerIdAndItemId(userId, itemId);

        for (Booking booking:bookingsByBookerIdAndItemId){
           if (booking.getStart().isBefore(LocalDateTime.now()) && booking.getStatus().equals(BookingStatus.APPROVED)){
               Comment comment1 = new Comment();
                comment1.setCreated(LocalDateTime.now());
                comment1.setText(comment.getText());
                comment1.setAuthor(user);
                comment1.setItem(item);

                commentRepository.save(comment1);

                CommentResponseDto commentResponseDto = new CommentResponseDto();
                commentResponseDto.setId(comment1.getId());
                commentResponseDto.setCreated(comment1.getCreated());
                commentResponseDto.setText(comment.getText());
                commentResponseDto.setAuthorName(comment1.getAuthor().getName());
                return commentResponseDto;
           }

        }
        throw new BadRequestException("Не найден");
    }

    public List<Comment> findCommentsByItemId(long itemId){
            itemRepository.findById(itemId).orElseThrow(()-> new NotFoundException("Вещь не надена"));
        return commentRepository.findCommentsByItemId(itemId);
    }
}