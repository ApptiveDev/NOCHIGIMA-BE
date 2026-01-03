package apptive.nochigima.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apptive.nochigima.dto.response.BrandResponse;
import apptive.nochigima.dto.response.CategoryResponse;
import apptive.nochigima.service.BrandService;
import apptive.nochigima.service.CategoryService;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final BrandService brandService;

    public CategoryController(CategoryService categoryService, BrandService brandService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping
    public List<CategoryResponse> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/{categoryId}/brands")
    public List<BrandResponse> getBrands(@PathVariable Long categoryId) {
        return brandService.getBrandsByCategory(categoryId);
    }
}
