package apptive.nochigima.domain;

import java.time.LocalDate;

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
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_id")
    @NotNull private Long brandId;

    @Column(name = "category_id")
    @NotNull private Long categoryId;

    @Column(name = "title")
    @NotNull private String title;

    @Column(name = "view_count", columnDefinition = "BIGINT DEFAULT 0")
    @NotNull private Long viewCount;

    @Column(name = "main_image_url")
    @Nullable private String mainImageUrl;

    @Column(name = "list_image_url")
    @Nullable private String listImageUrl;

    @Column(name = "description")
    @NotNull private String description;

    @Column(name = "start_date")
    @NotNull private LocalDate startAt;

    @Column(name = "end_date")
    @Nullable private LocalDate endAt;

    public void increaseViewCount() {
        this.viewCount += 1;
    }
}
