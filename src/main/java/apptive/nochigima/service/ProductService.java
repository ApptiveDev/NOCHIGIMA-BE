package apptive.nochigima.service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;

import apptive.nochigima.domain.Brand;
import apptive.nochigima.domain.Discount;
import apptive.nochigima.domain.Product;
import apptive.nochigima.dto.request.ProductUpdateRequest;
import apptive.nochigima.dto.response.ProductResponse;
import apptive.nochigima.repository.BrandRepository;
import apptive.nochigima.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final DiscountCalculator discountCalculator;
    private final Clock clock;

    public ProductService(
            ProductRepository productRepository,
            BrandRepository brandRepository,
            DiscountCalculator discountCalculator,
            Clock clock) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.discountCalculator = discountCalculator;
        this.clock = clock;
    }

    public List<ProductResponse> getProductsByBrand(Long brandId, boolean onlyDiscounted) {
        Brand brand = getBrand(brandId);
        List<Product> products;
        if (onlyDiscounted) {
            // Centralized Clock keeps date-based filtering deterministic and test-friendly.
            LocalDate today = LocalDate.now(clock);
            products = productRepository.findDiscountedByBrandId(brand.getId(), today);
        } else {
            products = productRepository.findByBrandId(brand.getId());
        }
        return products.stream().map(this::toResponse).toList();
    }

    public ProductResponse getProduct(Long productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));
        return toResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));
        Brand brand = getBrand(request.brandId());
        Discount discount = buildDiscount(request);
        product.update(request.name(), request.price(), request.imageUrl(), brand, discount);
        return toResponse(product);
    }

    private Brand getBrand(Long brandId) {
        return brandRepository
                .findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException("브랜드를 찾을 수 없습니다."));
    }

    private Discount buildDiscount(ProductUpdateRequest request) {
        boolean hasAnyDiscountField = request.discountValue() != null
                || request.discountStartAt() != null
                || request.discountEndAt() != null;
        if (!hasAnyDiscountField) {
            return null;
        }
        if (request.discountValue() == null
                || request.discountStartAt() == null
                || request.discountEndAt() == null) {
            throw new IllegalArgumentException("할인 정보는 value/start/end 모두 필요합니다.");
        }
        if (request.discountStartAt().isAfter(request.discountEndAt())) {
            throw new IllegalArgumentException("할인 시작일은 종료일보다 늦을 수 없습니다.");
        }
        validateDiscountValue(request.discountValue());
        return new Discount(request.discountValue(), request.discountStartAt(), request.discountEndAt());
    }

    private void validateDiscountValue(Long value) {
        if (value < 0) {
            throw new IllegalArgumentException("할인 값은 0 이상이어야 합니다.");
        }
        if (value > 100) {
            throw new IllegalArgumentException("퍼센트 할인 값은 100 이하여야 합니다.");
        }
    }

    private ProductResponse toResponse(Product product) {
        Discount discount = product.getDiscount();
        boolean isDiscountedNow = discountCalculator.isDiscountedNow(discount);
        Long discountedPrice = discountCalculator.calculateDiscountedPrice(product.getPrice(), discount);
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                isDiscountedNow,
                discount != null ? discount.getValue() : null,
                discount != null ? discount.getStartAt() : null,
                discount != null ? discount.getEndAt() : null,
                discountedPrice);
    }
}
