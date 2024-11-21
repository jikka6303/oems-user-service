package com.bits.oems.user.service;

import com.bits.oems.commons.exception.NoSuchUserRuntimeException;
import com.bits.oems.commons.model.PaymentDTO;
import com.bits.oems.commons.model.UserDTO;
import com.bits.oems.user.exception.EventAlreadyRegisteredException;
import com.bits.oems.user.exception.EventNotPaidException;
import com.bits.oems.commons.model.EventDTO;
import com.bits.oems.user.exception.NoSuchEventFoundException;
import com.bits.oems.user.model.User;
import com.bits.oems.user.repository.EventRepository;
import com.bits.oems.user.repository.PaymentRepository;
import com.bits.oems.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final PaymentRepository paymentRepository;

    @Transactional
    public EventDTO registerEvent(String username, EventDTO eventDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(NoSuchUserRuntimeException::new);

        if (user.getRegisteredEvents().contains(eventDTO.getEventId())) {
            throw new EventAlreadyRegisteredException();
        }

        checkIfEventIsPaid(eventDTO.getPaymentId(), username, eventDTO.getEventId());

        EventDTO event = updateEvent(username, eventDTO.getEventId());

        user.getRegisteredEvents().add(event.getEventId());

        userRepository.save(user);

        return event;
    }

    private EventDTO updateEvent(String username, String eventId) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setParticipants(Set.of(username));
        eventDTO.setEventId(eventId);
        return eventRepository.updateEvent(eventDTO);
    }

    private void checkIfEventIsPaid(String paymentId, String username, String eventId) {
        PaymentDTO payment = paymentRepository.getPayment(paymentId, username, eventId);
        if (payment == null) {
            throw new EventNotPaidException();
        }
    }

    public void addPayment(String username, PaymentDTO paymentDTO) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(NoSuchUserRuntimeException::new);

        if (user.getPaymentIds().contains(paymentDTO.getPaymentId())) {
            throw new EventAlreadyRegisteredException();
        }

        ofNullable(eventRepository.getEvent(paymentDTO.getReferenceId()))
                .orElseThrow(() -> new RuntimeException("Event not found"));

        paymentDTO.setUsername(username);

        paymentRepository.addPayment(paymentDTO);

        user.getPaymentIds().add(paymentDTO.getPaymentId());

        userRepository.save(user);

    }

    public List<EventDTO> getEvents(String username) {
        return userRepository.findByUsername(username)
                .map(User::getRegisteredEvents)
                .filter(Predicate.not(CollectionUtils::isEmpty))
                .map(eventRepository::getEvents)
                .orElse(List.of());
    }

    public EventDTO getEvent(String username, String eventId) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getRegisteredEvents().contains(eventId))
                .map(user -> eventRepository.getEvent(eventId))
                .orElseThrow(NoSuchEventFoundException::new);
    }

    public List<UserDTO> getParticipants(String username, String eventId) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getRegisteredEvents().contains(eventId))
                .map(user -> eventRepository.getEvent(eventId).getParticipants())
                .map(participants -> participants.stream().map(UserDTO::fromUsername).toList())
                .orElseThrow(NoSuchEventFoundException::new);
    }

    public List<PaymentDTO> getPayments(String username) {
        return userRepository.findByUsername(username)
                .map(user -> paymentRepository.getPayments(username))
                .orElseThrow(NoSuchUserRuntimeException::new);
    }
}
