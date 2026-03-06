package com.hieupham.payload.res;

import com.hieupham.payload.dto.BookingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {
    BookingDTO booking;

    PaymentResponse payment;
}
