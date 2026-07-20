package kz.alibek.sharemate.item_request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request,Long> {
    public List<Request> findRequestsByRequester_Id(Long requesterId);
}
