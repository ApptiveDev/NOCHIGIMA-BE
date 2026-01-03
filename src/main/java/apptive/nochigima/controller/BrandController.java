package apptive.nochigima.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import apptive.nochigima.dto.response.ProductResponse;
import apptive.nochigima.service.ProductService;

@Tag(name = "브랜드 API")
@RestController
@RequestMapping("/v1/brands")
public class BrandController {

    private final ProductService productService;

    public BrandController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{brandId}/products")
    public List<ProductResponse> getProducts(
            @PathVariable Long brandId, @RequestParam(defaultValue = "false") boolean onlyDiscounted) {
        return productService.getProductsByBrand(brandId, onlyDiscounted);
    }
}
