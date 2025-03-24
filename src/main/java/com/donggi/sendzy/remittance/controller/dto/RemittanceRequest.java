package com.donggi.sendzy.remittance.controller.dto;

public record RemittanceRequest(
    Long receiverId,
    Long amount
) {
}
