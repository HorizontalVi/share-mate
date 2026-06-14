package kz.alibek.sharemate.items;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    public List<Comment> findCommentsByItemId(long itemId);
}
