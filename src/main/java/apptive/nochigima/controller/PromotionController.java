package apptive.nochigima.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import apptive.nochigima.dto.response.PromotionListResponse;
import apptive.nochigima.dto.response.PromotionResponse;
import apptive.nochigima.service.PromotionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping
    public ResponseEntity<List<PromotionListResponse>> getPromotions(
            @RequestParam(required = false) Long brandId, @RequestParam(required = false) Long categoryId) {
        var response = promotionService.getPromotions(brandId, categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionResponse> getPromotionDetail(@PathVariable Long id) {
        var response = promotionService.getPromotionDetail(id);
        return ResponseEntity.ok(response);
    }
}
