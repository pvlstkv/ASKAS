package com.example.javaserver.conference.service.dto;

public class ConferenceRequestBody {
    private final Long roomId;

    public ConferenceRequestBody(Long roomId) {
        this.roomId = roomId;
    }

    public Long getRoomId() {
        return roomId;
    }
}
