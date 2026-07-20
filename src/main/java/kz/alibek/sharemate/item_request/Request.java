package kz.alibek.sharemate.item_request;

import jakarta.persistence.*;
import kz.alibek.sharemate.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_requests")
@Getter
@Setter
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "description")
    String description;

    @Column(name = "created")
    LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    User requester;
}
