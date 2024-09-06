package com.train.ticket.booking.repo;

import com.train.ticket.booking.entity.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findBySection(Ticket.Section section);

    List<Ticket> findByUserId(Long userId);

    int countBySection(Ticket.Section section);


    void deleteByUserId(Long userId);
}
