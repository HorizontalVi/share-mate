package kz.alibek.sharemate.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    public List<Booking> findBookingsByBookerId(Long bookerId);

    List<Booking> findBookingsByItem_Owner_Id(Long itemOwnerId);

    public Booking findBookingByBo
}
