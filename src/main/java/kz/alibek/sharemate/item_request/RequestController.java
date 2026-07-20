package kz.alibek.sharemate.item_request;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    public RequestResponseDto createRequest (@RequestHeader("X-Sharer-User-Id") long id, @RequestBody RequestCreateDto dto){
        return requestService.createRequest(dto,id);
    }

    @GetMapping
    public List<RequestResponseDto> findAllByAuthor (@RequestHeader("X-Sharer-User-Id") long id){
        return requestService.findAllByAuthor(id);
    }
}
