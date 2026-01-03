package apptive.nochigima.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import apptive.nochigima.domain.Brand;
import apptive.nochigima.dto.response.BrandResponse;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("""
            select new apptive.nochigima.dto.response.BrandResponse(
                b.id,
                b.name,
                count(p.id)
            )
            from Brand b
            left join b.products p
                on p.discount.startAt is not null
                and p.discount.endAt is not null
                and p.discount.startAt <= :now
                and p.discount.endAt >= :now
            where b.category.id = :categoryId
            group by b.id, b.name
            """)
    List<BrandResponse> findByCategoryWithDiscountedCount(
            @Param("categoryId") Long categoryId, @Param("now") LocalDate now);
}
