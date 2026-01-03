package apptive.nochigima.dto.response;

import java.time.LocalDate;

public record ProductResponse(
        Long id,
        String name,
        Long price,
        String imageUrl,
        boolean isDiscountedNow,
        Long discountValue,
        LocalDate discountStartAt,
        LocalDate discountEndAt,
        Long discountedPrice) {}
