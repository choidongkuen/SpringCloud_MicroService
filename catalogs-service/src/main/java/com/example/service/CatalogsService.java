package com.example.service;

import com.example.domain.entity.Catalogs;
import com.example.domain.repository.CatalogsRepository;
import com.example.dto.CreateCatalogsRequestDto;
import com.example.dto.GetCatalogsResponseDto;
import com.example.exception.NotFoundCatalogsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CatalogsService {

    private final CatalogsRepository catalogsRepository;

    @Transactional(readOnly = true)
    public List<GetCatalogsResponseDto> getAllCatalogs() {
        return this.catalogsRepository.findAll().stream()
                .map(Catalogs::toGetCatalogsResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createCatalogs(CreateCatalogsRequestDto request) {
        // 이미 해당 productId 카탈로그 존재하는 경우
        if(this.catalogsRepository.findByProductId(request.getProductId()).isPresent()) {
            Catalogs catalogs = this.catalogsRepository
                    .findByProductId(request.getProductId()).get();
            catalogs.plusStock(request.getStock());
            return catalogs.getId();
        }
        // productId 카탈로그 존재하지 않는 경우
        return this.catalogsRepository.save(request.toEntity()).getId();
    }

    @Transactional
    public void deleteCatalogs(@PathVariable Long id) {
        Catalogs foundCatalogs = this.catalogsRepository.findById(id)
                .orElseThrow(() -> new NotFoundCatalogsException("일치하는 카탈로그 정보가 존재하지 않습니다."));
        this.catalogsRepository.delete(foundCatalogs);
    }
}
