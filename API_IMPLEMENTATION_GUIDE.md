# Brand, Category, Promotion API 구현 가이드

이 문서는 브랜드(Brand), 카테고리(Category), 행사(Promotion) 정보를 제공하는 API 구현을 위한 가이드라인입니다.

## 1. API 명세 (API Specifications)

RESTful 원칙에 따라 리소스를 조회하는 엔드포인트를 설계합니다.

| Method | Endpoint | Description | Query Params (Optional) |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/promotions` | 행사 목록 조회 (필터링 및 페이징 포함) | `brandId`, `categoryId`, `page`, `size`, `sort` |
| `GET` | `/api/v1/promotions/{id}` | 행사 상세 조회 | - |
| `GET` | `/api/v1/brands` | 브랜드 목록 조회 | - |
| `GET` | `/api/v1/categories` | 카테고리 목록 조회 | - |

---

## 2. DTO 설계 (Data Transfer Objects)

Entity를 직접 반환하지 않고, 클라이언트에 최적화된 DTO를 정의합니다.

### 2.1 PromotionResponse (행사 응답)
`Promotion` 엔티티가 `Brand`와 `Category` 객체를 참조하고 있으므로, 클라이언트에는 ID나 이름 등 필요한 정보만 평탄화(Flatten)하여 전달하는 것이 좋습니다.

```java
package apptive.nochigima.dto.response;

import apptive.nochigima.domain.Promotion;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PromotionResponse {
    private Long id;
    private String title;
    private String description;
    private String mainImageUrl;
    private String listImageUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    // Brand Info
    private Long brandId;
    private String brandName; // Brand 엔티티의 name 타입 수정 필요 고려 (현재 Long)

    // Category Info
    private Long categoryId;
    private String categoryName;

    public static PromotionResponse of(Promotion promotion) {
        return PromotionResponse.builder()
                .id(promotion.getId())
                .title(promotion.getTitle())
                .description(promotion.getDescription())
                .mainImageUrl(promotion.getMainImageUrl())
                .listImageUrl(promotion.getListImageUrl())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                // 연관관계 매핑
                .brandId(promotion.getBrandId().getId())
                .brandName(String.valueOf(promotion.getBrandId().getName())) // 임시 변환
                .categoryId(promotion.getCategoryId().getId())
                .categoryName(promotion.getCategoryId().getName())
                .build();
    }
}
```

### 2.2 BrandResponse & CategoryResponse
```java
// BrandResponse.java
@Getter
@Builder
public class BrandResponse {
    private Long id;
    private String name;
    private String logoImageUrl;
    private Long promotionCount;
    
    public static BrandResponse of(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(String.valueOf(brand.getName()))
                .logoImageUrl(brand.getLogoImageUrl())
                .promotionCount(brand.getPromotionCount())
                .build();
    }
}

// CategoryResponse.java
@Getter
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    
    public static CategoryResponse of(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
```

---

## 3. Repository 계층 구현

필터링(브랜드별, 카테고리별)과 페이징(Pagination) 기능을 지원하기 위해 JPA 메서드를 추가합니다.

### PromotionRepository 수정
```java
package apptive.nochigima.repository;

import apptive.nochigima.domain.Brand;
import apptive.nochigima.domain.Category;
import apptive.nochigima.domain.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    
    // 1. 전체 조회 (페이징)
    Page<Promotion> findAll(Pageable pageable);

    // 2. 브랜드별 조회
    Page<Promotion> findAllByBrandId(Brand brand, Pageable pageable);

    // 3. 카테고리별 조회
    Page<Promotion> findAllByCategoryId(Category category, Pageable pageable);
    
    // 4. 브랜드 + 카테고리 복합 조회 (선택적 필터링을 위해 @Query 사용 권장)
    @Query("SELECT p FROM Promotion p " +
           "WHERE (:brand IS NULL OR p.brandId = :brand) " +
           "AND (:category IS NULL OR p.categoryId = :category)")
    Page<Promotion> findAllWithFilters(
            @Param("brand") Brand brand, 
            @Param("category") Category category, 
            Pageable pageable
    );
}
```

---

## 4. Service 계층 구현

비즈니스 로직을 처리하고 Entity를 DTO로 변환합니다.

```java
package apptive.nochigima.service;

import apptive.nochigima.domain.Brand;
import apptive.nochigima.domain.Category;
import apptive.nochigima.domain.Promotion;
import apptive.nochigima.dto.response.PromotionResponse;
import apptive.nochigima.repository.PromotionRepository;
// BrandRepository, CategoryRepository 필요
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromotionService {

    private final PromotionRepository promotionRepository;
    // private final BrandRepository brandRepository;
    // private final CategoryRepository categoryRepository;

    public Page<PromotionResponse> getPromotions(Long brandId, Long categoryId, Pageable pageable) {
        // Brand, Category 엔티티 조회 로직 (ID가 있는 경우)
        Brand brand = null; // brandRepository.findById(brandId)...
        Category category = null; // categoryRepository.findById(categoryId)...

        // Repository 메서드 호출
        Page<Promotion> promotions = promotionRepository.findAllWithFilters(brand, category, pageable);

        // DTO 변환
        return promotions.map(PromotionResponse::of);
    }
    
    public PromotionResponse getPromotionDetail(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("행사를 찾을 수 없습니다."));
        return PromotionResponse.of(promotion);
    }
}
```

---

## 5. 주요 고려사항 (Key Considerations)

### 5.1 N+1 문제 방지 (Performance)
현재 `Promotion` 엔티티는 `Brand`와 `Category`를 `@ManyToOne`으로 참조하고 있습니다. 목록 조회 시, 각 `Promotion`마다 `Brand`와 `Category`를 조회하는 쿼리가 추가로 발생할 수 있습니다.

*   **해결책**: `Fetch Join` 또는 `@EntityGraph`를 사용하여 한 번의 쿼리로 연관된 엔티티를 함께 가져와야 합니다.

```java
// PromotionRepository.java 예시
@EntityGraph(attributePaths = {"brandId", "categoryId"})
Page<Promotion> findAll(Pageable pageable);
```
*(참고: 현재 엔티티 필드명이 `brandId`, `categoryId`로 되어 있어 attributePaths에도 동일하게 작성해야 합니다.)*

### 5.2 페이징 (Pagination)
대량의 데이터를 한 번에 내려주는 것은 서버와 클라이언트 모두에 부담입니다. 반드시 `Pageable`을 적용하여 필요한 만큼만 데이터를 조회하세요.

### 5.3 데이터 타입 검토 (Review Needed)
*   **Brand Entity**: `name` 필드가 현재 `Long` 타입으로 선언되어 있습니다. 브랜드 이름이라면 `String`이어야 할 가능성이 높습니다. 확인 후 수정이 필요합니다.
*   **Naming Convention**: `Promotion` 엔티티에서 연관관계 필드명이 `brandId`, `categoryId`로 되어 있습니다. 보통 객체 자체를 참조할 때는 `brand`, `category`로 명명하고 `@JoinColumn(name="brand_id")`를 쓰는 것이 관례입니다.

### 5.4 예외 처리
존재하지 않는 ID로 조회할 경우에 대한 예외 처리(`GlobalExceptionHandler`)가 필요합니다.
