package apptive.nochigima.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import apptive.nochigima.domain.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    List<Promotion> findAllByBrandId(Long brandId);

    List<Promotion> findAllByCategoryId(Long categoryId);
}
