package com.example.controller;

import com.example.dto.CreateCatalogsRequestDto;
import com.example.dto.GetCatalogsResponseDto;
import com.example.service.CatalogsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/catalogs-service")
@RestController
public class CatalogsController {
    private final CatalogsService catalogsService;
    private final Environment environment;
    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s",
                environment.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<GetCatalogsResponseDto>> getAllCatalogs() {
        return ResponseEntity.status(HttpStatus.OK).body(this.catalogsService.getAllCatalogs());
    }

    @PostMapping("/catalogs")
    public ResponseEntity<Long> createCatalogs(
            @RequestBody @Valid CreateCatalogsRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.catalogsService.createCatalogs(request));
    }

    @DeleteMapping("/catalogs/{id}")
    public ResponseEntity<Void> deleteCatalogs(
            @PathVariable Long id
    ) {
        this.catalogsService.deleteCatalogs(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
