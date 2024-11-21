package com.bits.oems.user.controller;

import com.bits.oems.commons.model.EventDTO;
import com.bits.oems.commons.model.PaymentDTO;
import com.bits.oems.commons.model.UserDTO;
import com.bits.oems.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("{username}/events")
    public EventDTO registerEvent(@PathVariable String username, @RequestBody EventDTO eventDTO) {
        return userService.registerEvent(username, eventDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{username}/payments")
    public void addPayment(@PathVariable String username, @RequestBody PaymentDTO paymentDTO) {
        userService.addPayment(username, paymentDTO);
    }

    @GetMapping("{username}/payments")
    public List<PaymentDTO> getPayments(@PathVariable String username) {
        return userService.getPayments(username);
    }

    @GetMapping("{username}/events")
    public List<EventDTO> getEventsByUsername(@PathVariable String username) {
        return userService.getEvents(username);
    }

    @GetMapping("{username}/events/{eventId}")
    public EventDTO getEvent(@PathVariable String username, @PathVariable String eventId) {
        return userService.getEvent(username, eventId);
    }

    @GetMapping("{username}/events/{eventId}/participants")
    public List<UserDTO> getParticipants(@PathVariable String username, @PathVariable String eventId) {
        return userService.getParticipants(username, eventId);
    }

}
