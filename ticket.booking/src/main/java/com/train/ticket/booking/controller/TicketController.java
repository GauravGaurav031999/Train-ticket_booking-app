package com.train.ticket.booking.controller;

import com.train.ticket.booking.entity.Ticket;
import com.train.ticket.booking.model.Requstdtc;
import com.train.ticket.booking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/purchase")
    public Ticket purchaseTicket(@RequestBody Requstdtc dto) {
        return ticketService.purchaseTicket(dto);
    }

    @GetMapping("/receipt/{ticketId}")
    public Ticket getTicket(@PathVariable Long ticketId) {
        return ticketService.getTicketById(ticketId);
    }

    @GetMapping("/section/{section}")
    public List<Ticket> getTicketsBySection(@PathVariable Ticket.Section section) {
        return ticketService.getTicketsBySection(section);
    }

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<Map<String, Object>> removeUser(@PathVariable Long userId) {
        Map<String, Object> message = new HashMap<>();
        try {
            ticketService.removeUser(userId);
            message.put("message", "Data is deleted successfully " + userId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            message.put("error", "Failed to delete data for userId " + userId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PutMapping("/modify_seat")
    public Map<String, Object> updateSeat(@RequestParam Long userId, @RequestParam Ticket.Section newSection, @RequestParam int newSeatNumber) {
        Map<String, Object> message = new HashMap<>();
        try {
            Ticket ticket = ticketService.updateSeat(userId, newSection, newSeatNumber);
            message.put("message", " user Data Updated successfully ");
            return message;
        } catch (Exception e) {
            message.put("error", "Failed to update data for user  " + userId);
            return message;
        }

    }
}

