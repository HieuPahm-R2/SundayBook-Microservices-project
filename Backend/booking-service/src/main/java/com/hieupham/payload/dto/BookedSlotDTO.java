package com.hieupham.payload.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookedSlotDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
