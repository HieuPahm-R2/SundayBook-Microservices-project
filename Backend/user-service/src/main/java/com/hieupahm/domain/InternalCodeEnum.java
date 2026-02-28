package com.hieupahm.domain;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum InternalCodeEnum {
    PROPERTY_MANAGEMENT_CODE_001(1, "Request performed successfully", HttpStatus.OK),
    PROPERTY_MANAGEMENT_CODE_002(2, "Property not found", HttpStatus.NOT_FOUND),
    PROPERTY_MANAGEMENT_CODE_003(3, "Property created successfully", HttpStatus.CREATED),
    PROPERTY_MANAGEMENT_CODE_004(4, "Property updated successfully", HttpStatus.OK),
    PROPERTY_MANAGEMENT_CODE_005(5, "Property deleted successfully", HttpStatus.OK),
    PROPERTY_MANAGEMENT_CODE_006(6, "User properties retrieved successfully", HttpStatus.OK),
    PROPERTY_MANAGEMENT_CODE_007(7, "Booking not found", HttpStatus.NOT_FOUND),
    PROPERTY_MANAGEMENT_CODE_008(8, "Booking created successfully", HttpStatus.CREATED),
    PROPERTY_MANAGEMENT_CODE_009(9, "Booking updated successfully", HttpStatus.OK),
    PROPERTY_MANAGEMENT_CODE_010(10, "Booking deleted successfully", HttpStatus.OK),
    PROPERTY_MANAGEMENT_CODE_011(11, "User booking data retrieved successfully", HttpStatus.OK),
    REVIEW_MANAGEMENT_CODE_001(1, "Request performed successfully", HttpStatus.OK),
    REVIEW_MANAGEMENT_CODE_002(2, "Review not found", HttpStatus.NOT_FOUND),
    REVIEW_MANAGEMENT_CODE_003(3, "Review created successfully", HttpStatus.CREATED),
    REVIEW_MANAGEMENT_CODE_004(4, "Review updated successfully", HttpStatus.OK),
    REVIEW_MANAGEMENT_CODE_005(5, "Review deleted successfully", HttpStatus.OK),

    BOOKING_MANAGEMENT_CODE_001(1, "Booking Modification Created Successfully", HttpStatus.CREATED),
    BOOKING_MANAGEMENT_CODE_002(2, "Booking Modification fetched Successfully", HttpStatus.OK),
    BOOKING_MANAGEMENT_CODE_003(3, "Booking Modification is now confirmed", HttpStatus.OK),
    BOOKING_MANAGEMENT_CODE_004(4, "Booking Modification is now rejected", HttpStatus.OK);

    private final String codeDescription;
    private final String codeNumber;
    private final HttpStatus httpStatus;

    InternalCodeEnum(int codeNumber, String codeDescription, HttpStatus status) {
        this.codeNumber = String.format("%03d", codeNumber);
        this.codeDescription = codeDescription;
        this.httpStatus = status;
    }
}
