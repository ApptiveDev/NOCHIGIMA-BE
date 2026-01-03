package apptive.nochigima.service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import apptive.nochigima.dto.response.BrandResponse;
import apptive.nochigima.repository.BrandRepository;
import apptive.nochigima.repository.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BrandService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final Clock clock;

    public BrandService(BrandRepository brandRepository, CategoryRepository categoryRepository, Clock clock) {
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.clock = clock;
    }

    public List<BrandResponse> getBrandsByCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("카테고리를 찾을 수 없습니다.");
        }
        // Injected Clock makes date-based queries testable and keeps server time centralized.
        LocalDate today = LocalDate.now(clock);
        return brandRepository.findByCategoryWithDiscountedCount(categoryId, today);
    }
}
