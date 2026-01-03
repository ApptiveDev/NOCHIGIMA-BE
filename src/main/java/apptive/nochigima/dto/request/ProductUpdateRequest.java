package apptive.nochigima.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductUpdateRequest(
        @NotBlank String name,
        @NotNull @Min(0) Long price,
        @NotNull Long brandId,
        @NotBlank String imageUrl,
        @Min(0) Long discountValue,
        LocalDate discountStartAt,
        LocalDate discountEndAt) {}
