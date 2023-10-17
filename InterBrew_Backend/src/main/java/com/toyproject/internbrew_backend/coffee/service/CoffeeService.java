package com.toyproject.internbrew_backend.coffee.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.internbrew_backend.coffee.dto.CoffeeDto;
import com.toyproject.internbrew_backend.coffee.entity.Coffee;
import com.toyproject.internbrew_backend.coffee.repository.CoffeeRepository;
import com.toyproject.internbrew_backend.common.SaveImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;

    private final SaveImage saveImage;

    public List<CoffeeDto> findCoffees() {
        return coffeeRepository.findAll().stream()
                .map(CoffeeDto::new)
                .collect(Collectors.toList());
    }

    public CoffeeDto findOneCoffee(int coffeeId) {
        return coffeeRepository.findById(coffeeId);
    }

    public void newCoffee(String newCoffeeItem, MultipartFile coffeeImage, String userId) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(newCoffeeItem);

            CoffeeDto newCoffeeDto = new CoffeeDto();
            newCoffeeDto.setCoffeeName(root.path("_value").path("coffeeName").asText());
            newCoffeeDto.setCoffeeInformation(root.path("_value").path("coffeeInformation").asText());
            newCoffeeDto.setCoffeeCount(Integer.parseInt(root.path("_value").path("coffeeCount").asText()));
            newCoffeeDto.setCoffeeCreatedAt(root.path("_value").path("coffeeCreatedAt").asText());
            newCoffeeDto.setCoffeeExpireDate(root.path("_value").path("coffeeExpireDate").asText());
            newCoffeeDto.setCoffeePrice(Integer.parseInt(root.path("_value").path("coffeePrice").asText()));
            newCoffeeDto.setCoffeeStatus(root.path("_value").path("coffeeStatus").asText());
            newCoffeeDto.setCoffeeUserCreate(userId);
            newCoffeeDto.setCoffeeImage(saveImage.saveImage(coffeeImage, "Coffee")); // 인스턴스 메서드 호출

            Coffee newCoffee = Coffee.on(newCoffeeDto);
            coffeeRepository.save(newCoffee);
        } catch (Exception e) {
            log.error("newCoffee Creation Error", e);
            throw new Exception("newCoffee Creation Error", e);
        }
    }

    public void modifyCoffee(CoffeeDto modifyCoffeeItem) throws Exception {
        try {
            modifyCoffeeItem.setCoffeeUpdatedAt(LocalDateTime.now());

            Coffee coffee = Coffee.on(modifyCoffeeItem);
            coffeeRepository.save(coffee);
        } catch (Exception e) {
            log.error("modifyCoffee Creation Exception Error", e);
            throw new Exception("modifyCoffee Creation Exception Error", e);
        }
    }
}
