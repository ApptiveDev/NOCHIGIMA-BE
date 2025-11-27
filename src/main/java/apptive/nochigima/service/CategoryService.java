// package apptive.nochigima.service;
//
// import java.util.List;
// import java.util.stream.Collectors;
//
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
//
// import lombok.RequiredArgsConstructor;
//
// import apptive.nochigima.dto.response.CategoryResponse;
// import apptive.nochigima.repository.CategoryRepository;
//
// @Service
// @RequiredArgsConstructor
// @Transactional(readOnly = true)
// public class CategoryService {
//
//    private final CategoryRepository categoryRepository;
//
//    public List<CategoryResponse> getAllCategories() {
//        return categoryRepository.findAll().stream().map(CategoryResponse::of).collect(Collectors.toList());
//    }
// }
