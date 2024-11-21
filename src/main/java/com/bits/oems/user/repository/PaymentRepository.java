package com.bits.oems.user.repository;

import com.bits.oems.commons.model.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "PaymentRepository", url = "${payment.url}")
public interface PaymentRepository {

    @GetMapping(value = "/payments/{paymentId}/users/{username}/events/{eventId}")
    PaymentDTO getPayment(
            @PathVariable("paymentId") String paymentId,
            @PathVariable("username") String username,
            @PathVariable("eventId") String eventId
    );

    @PostMapping(value = "/payments")
    void addPayment(@RequestBody PaymentDTO payment);

    @GetMapping(value = "/payments")
    List<PaymentDTO> getPayments(@RequestParam String username);
}
