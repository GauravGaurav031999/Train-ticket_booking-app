package com.train.ticket.booking.service;

import com.train.ticket.booking.entity.Ticket;
import com.train.ticket.booking.entity.User;
import com.train.ticket.booking.model.Requstdtc;
import com.train.ticket.booking.repo.TicketRepository;
import com.train.ticket.booking.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Service
public class TicketService {


    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TicketRepository ticketRepository;


    public TicketService(UserRepository userRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    public Ticket purchaseTicket(Requstdtc dto) {
        logger.info("Attempting to purchase ticket for user: {} {}", dto.getFirstName(), dto.getLastName());
        User user = new User();
        if (dto.getFirstName() != null || dto.getLastName() != null || dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            logger.info("User data saved in database");
            userRepository.save(user);
        } else {
            logger.info("User data not saved in database");
        }
        logger.info("Entering to ticket selection selection");
        Ticket.Section section = Math.random() > 0.5 ? Ticket.Section.A : Ticket.Section.B;
        int seatNumber = ticketRepository.countBySection(section) + 1;
        logger.info("Total number of seats: {}", seatNumber);
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setFromLocation("London");
        ticket.setToLocation("France");
        ticket.setPrice(20);
        ticket.setSection(section);
        ticket.setSeatNumber(seatNumber);
        logger.info("Ticket data saved in database");
        Ticket savedTicket = ticketRepository.save(ticket);
        logger.info("Ticket purchased successfully: {}", savedTicket);

        return savedTicket;
    }

    public Ticket getTicketById(Long ticketId) {
        logger.info("Attempting to get ticket by id: {}", ticketId);
        return ticketRepository.findById(ticketId).orElse(null);
    }

    public List<Ticket> getTicketsBySection(Ticket.Section section) {
        logger.info("Attempting to get tickets by section: {}", section);
        return ticketRepository.findBySection(section);
    }


    public void removeUser(Long userId) {
        try {
            logger.info("Deleting user: {}", userId);
            ticketRepository.deleteByUserId(userId);
        } catch (Exception e) {
            logger.error("Failed to delete user with ID: {}", userId, e);
            throw new RuntimeException("Error occurred while deleting user", e);
        }
    }

    public Ticket updateSeat(Long userId, Ticket.Section newSection, int newSeatNumber) {
        logger.info("Attempting to update seat for userId: {}, newSection: {}, newSeatNumber: {}", userId, newSection, newSeatNumber);
        Ticket ticket = ticketRepository.findByUserId(userId).stream().findFirst().orElse(null);

        if (ticket != null) {
            ticket.setSection(newSection);
            ticket.setSeatNumber(newSeatNumber);
            Ticket updatedTicket = ticketRepository.save(ticket);
            logger.info("Seat updated successfully: {}", updatedTicket.getClass());
            return updatedTicket;
        } else {
            logger.warn("No ticket found for userId: {}", userId);
            return null;
        }

    }


}


