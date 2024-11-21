package com.bits.oems.user.repository;

import com.bits.oems.commons.model.EventDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@FeignClient(value = "EventRepository", url = "${events.url}")
public interface EventRepository {

    @PutMapping("/events")
    EventDTO updateEvent(@RequestBody EventDTO eventDTO);

    @GetMapping(value = "/events/{eventId}")
    EventDTO getEvent(@PathVariable("eventId") String eventId);

    @GetMapping(value = "/events")
    List<EventDTO> getEvents(@RequestParam("eventIds") Set<String> eventIds);
}
