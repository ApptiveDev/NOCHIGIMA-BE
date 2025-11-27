package apptive.nochigima.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_id")
    @NotNull private Long categoryId;

    @Column(name = "name")
    @NotNull private String name;

    @Column(name = "logo_image_url")
    @Nullable private String logoImageUrl;

    @Column(name = "promotion_count", columnDefinition = "BIGINT DEFAULT 0")
    @NotNull private Long promotionCount;
}
