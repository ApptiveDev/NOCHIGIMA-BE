package apptive.nochigima.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import apptive.nochigima.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
