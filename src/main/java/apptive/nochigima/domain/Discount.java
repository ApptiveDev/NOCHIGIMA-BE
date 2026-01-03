package apptive.nochigima.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Discount {

    // If discount history or promotions are needed later, extract this to a separate entity.

    @Column(name = "discount_value")
    private Long value;

    @Column(name = "start_at")
    private LocalDate startAt;

    @Column(name = "end_at")
    private LocalDate endAt;

    public Discount(Long value, LocalDate startAt, LocalDate endAt) {
        this.value = value;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
