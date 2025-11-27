package apptive.nochigima.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import apptive.nochigima.domain.Promotion;
import apptive.nochigima.dto.response.PromotionListResponse;
import apptive.nochigima.dto.response.PromotionResponse;
import apptive.nochigima.repository.BrandRepository;
import apptive.nochigima.repository.CategoryRepository;
import apptive.nochigima.repository.PromotionRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public List<PromotionListResponse> getPromotions(Long brandId, Long categoryId) {
        List<Promotion> promotions;

        if (brandId != null) {
            if (!brandRepository.existsById(brandId)) {
                throw new IllegalArgumentException("Brand not found with id: " + brandId);
            }
            promotions = promotionRepository.findAllByBrandId(brandId);
        } else if (categoryId != null) {
            if (!categoryRepository.existsById(categoryId)) {
                throw new IllegalArgumentException("Category not found with id: " + categoryId);
            }
            promotions = promotionRepository.findAllByCategoryId(categoryId);
        } else {
            promotions = promotionRepository.findAll();
        }

        return promotions.stream().map(PromotionListResponse::of).toList();
    }

    @Transactional
    public PromotionResponse getPromotionDetail(Long id) {
        Promotion promotion = promotionRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found with id: " + id));

        promotion.increaseViewCount();

        return PromotionResponse.of(promotion);
    }
}
