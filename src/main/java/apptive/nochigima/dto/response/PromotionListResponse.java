package apptive.nochigima.dto.response;

import java.time.LocalDate;

import apptive.nochigima.domain.Promotion;

public record PromotionListResponse(Long id, String title, String imageUrl, LocalDate startAt, LocalDate endAt) {
    public static PromotionListResponse of(Promotion promotion) {
        return new PromotionListResponse(
                promotion.getId(),
                promotion.getTitle(),
                promotion.getListImageUrl(),
                promotion.getStartAt(),
                promotion.getEndAt());
    }
}
