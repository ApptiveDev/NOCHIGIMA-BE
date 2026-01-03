package apptive.nochigima.dto.response;

import apptive.nochigima.domain.Category;

public record CategoryResponse(Long id, String name) {

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
