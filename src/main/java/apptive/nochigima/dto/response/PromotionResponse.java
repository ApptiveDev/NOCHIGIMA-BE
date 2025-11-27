package apptive.nochigima.dto.response;

import java.time.LocalDate;

import apptive.nochigima.domain.Promotion;

public record PromotionResponse(String title, String description, String imageUrl, LocalDate startAt, LocalDate endAt) {
    public static PromotionResponse of(Promotion promotion) {
        return new PromotionResponse(
                promotion.getTitle(),
                promotion.getDescription(),
                promotion.getMainImageUrl(),
                promotion.getStartAt(),
                promotion.getEndAt());
    }
}
