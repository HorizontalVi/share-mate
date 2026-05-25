package kz.alibek.sharemate.items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    @Query(" select i from Item i " +
            "where i.available = true" +
            " and (upper(i.name) like upper(concat('%',?1,'%')) " +
            "or upper(i.description) like upper(concat('%',?1,'%'))) "
            )
    public List<Item> search(String text);

    public List<Item> findByOwnerId(long ownerId);
}
