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
// import apptive.nochigima.dto.response.BrandResponse;
// import apptive.nochigima.repository.BrandRepository;
//
// @Service
// @RequiredArgsConstructor
// @Transactional(readOnly = true)
// public class BrandService {
//
//    private final BrandRepository brandRepository;
//
//    public List<BrandResponse> getAllBrands() {
//        return brandRepository.findAll().stream().map(BrandResponse::of).collect(Collectors.toList());
//    }
// }
