package kz.alibek.sharemate.item_request;

import io.swagger.v3.oas.annotations.servers.Server;
import kz.alibek.sharemate.exception.NotFoundException;
import kz.alibek.sharemate.items.ItemRepository;
import kz.alibek.sharemate.users.User;
import kz.alibek.sharemate.users.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public RequestResponseDto createRequest(RequestCreateDto dto, long userId)
    {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Request request = new Request();
        request.setDescription(dto.getDescription());
        request.setRequester(user);
        request.setCreated(LocalDateTime.now());
        requestRepository.save(request);
        RequestResponseDto requestResponseDto = new RequestResponseDto();
        requestResponseDto.setId(request.getId());
        requestResponseDto.setCreated(request.getCreated());
        requestResponseDto.setDescription(request.getDescription());
        return requestResponseDto;
    }

    public List<RequestResponseDto> findAllByAuthor(long requesterId) {
        userRepository.findById(requesterId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        List<Request> requests = requestRepository.findRequestsByRequester_Id(requesterId);
        List<RequestResponseDto> dto = new ArrayList<>();
        for (Request request:requests){
            RequestResponseDto requestResponseDto = new RequestResponseDto();
            requestResponseDto.setId(request.getId());
            requestResponseDto.setCreated(request.getCreated());
            requestResponseDto.setDescription(request.getDescription());
            dto.add(requestResponseDto);
        }
        return dto;
    }
}
