package apptive.nochigima.repository;

import java.time.LocalDate;
import java.util.List;

import apptive.nochigima.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByBrandId(Long brandId);

    @Query(
            """
            select p
            from Product p
            where p.brand.id = :brandId
                and p.discount.startAt is not null
                and p.discount.endAt is not null
                and p.discount.startAt <= :now
                and p.discount.endAt >= :now
            """)
    List<Product> findDiscountedByBrandId(@Param("brandId") Long brandId, @Param("now") LocalDate now);
}
