package apptive.nochigima.service;

import java.time.Clock;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import apptive.nochigima.domain.Discount;

@Component
public class DiscountCalculator {

    private final Clock clock;

    public DiscountCalculator(Clock clock) {
        this.clock = clock;
    }

    public boolean isDiscountedNow(Discount discount) {
        if (discount == null || discount.getStartAt() == null || discount.getEndAt() == null) {
            return false;
        }
        // Clock is injected so tests can fix the current date and avoid reliance on system clocks.
        LocalDate today = LocalDate.now(clock);
        return (!today.isBefore(discount.getStartAt())) && (!today.isAfter(discount.getEndAt()));
    }

    public Long calculateDiscountedPrice(Long price, Discount discount) {
        if (price == null) {
            return null;
        }
        if (!isDiscountedNow(discount)) {
            return price;
        }
        long discountAmount = calculateDiscountAmount(price, discount.getValue());
        long discountedPrice = price - discountAmount;
        if (discountedPrice < 0) {
            discountedPrice = 0;
        }
        return discountedPrice;
    }

    private long calculateDiscountAmount(Long price, Long value) {
        if (value == null) {
            return 0L;
        }
        return price * value / 100;
    }
}
