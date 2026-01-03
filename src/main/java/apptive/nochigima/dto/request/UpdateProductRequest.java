package apptive.nochigima.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateProductRequest(
        @NotNull Long productId, @Min(0) Long discountValue, LocalDate discountStartAt, LocalDate discountEndAt) {}
