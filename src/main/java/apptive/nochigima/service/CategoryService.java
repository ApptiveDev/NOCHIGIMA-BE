package apptive.nochigima.service;

import java.util.List;

import apptive.nochigima.dto.response.CategoryResponse;
import apptive.nochigima.repository.CategoryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream().map(CategoryResponse::from).toList();
    }
}
