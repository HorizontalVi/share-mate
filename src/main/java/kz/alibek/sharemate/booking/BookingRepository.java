package kz.alibek.sharemate.booking;

import kz.alibek.sharemate.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    public List<Booking> findBookingsByBookerId(Long bookerId);

    List<Booking> findBookingsByItem_Owner_Id(Long itemOwnerId);

    public List<Booking> findBookingsByBookerIdAndItemId(Long bookerId,Long itemId);

    List<Booking> findBookingByBooker(User booker);


}
