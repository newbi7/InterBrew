package com.toyproject.internbrew_backend.coffee.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.internbrew_backend.coffee.dto.CoffeeDto;
import com.toyproject.internbrew_backend.coffee.service.CoffeeService;
import com.toyproject.internbrew_backend.exception.DataNullException;
import com.toyproject.internbrew_backend.exception.DataRuntimeException;
import com.toyproject.internbrew_backend.exception.dto.RuntimeErrorCode;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.text.resources.FormatData;

import java.util.List;

import static com.toyproject.internbrew_backend.exception.dto.NullErrorCode.NULL_DATA;
import static com.toyproject.internbrew_backend.exception.dto.RuntimeErrorCode.FAILED_LOAD_DATA;


@RestController
@RequestMapping(value = "api/v1")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class CoffeeController {

    private final CoffeeService coffeeService;

    /**
     * 커피 품목 전체 조회
     *
     * @return
     */
    @GetMapping("/coffee")
    public ResponseEntity<Object> getCoffeeList() {
        try {
            log.debug("getCoffeeList");
            List<CoffeeDto> result = coffeeService.findCoffees();
            return ResponseEntity.ok().body(result);
        } catch (DataNullException e) {
            log.error("Load Data Null Exception");
            DataNullException response = new DataNullException(NULL_DATA);
            return ResponseEntity.ok(response);
        } catch (DataRuntimeException e) {
            log.error("Load Data Runtime Exception");
            DataRuntimeException response = new DataRuntimeException(FAILED_LOAD_DATA);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 커피 품목 단일 조회
     *
     * @param coffeeNo
     * @return
     */
    @GetMapping("/coffee/{coffeeNo}")
    public ResponseEntity<Object> getOneCoffee(@PathVariable int coffeeNo) {
        try {
            log.debug("getOneCoffee");
            CoffeeDto coffeeInfo = coffeeService.findOneCoffee(coffeeNo);
            return ResponseEntity.ok(coffeeInfo);
        } catch (DataNullException e) {
            log.error("Load Data Null Exception");
            DataNullException response = new DataNullException(NULL_DATA);
            return ResponseEntity.ok(response);
        } catch (DataRuntimeException e) {
            log.error("Load Data Runtime Exception");
            DataRuntimeException response = new DataRuntimeException(FAILED_LOAD_DATA);
            return ResponseEntity.ok(response);
        }
    }

    // 커피 수정

    /*
    2023-08-27
    커피품목추가
    */
    @PutMapping("/coffee")
    public ResponseEntity<?> newCoffee(@RequestPart("newCoffeeItem") String newCoffeeItem,
                                       @RequestPart("newCoffeeImage") MultipartFile newCoffeeImage,
                                       @RequestPart("userId") String userId) throws Exception {
        try {
            coffeeService.newCoffee(newCoffeeItem, newCoffeeImage, userId);
            String response = new String("coffee created successfully.");
            return ResponseEntity.ok().body(response);
        } catch (DataNullException e) {
            log.error("Coffee Creation Null Error");
            DataNullException response = new DataNullException(4103, "EF4103", "Coffee Creation Data Null");
            return ResponseEntity.ok().body(response);
        } catch (DataRuntimeException e) {
            log.error("Coffee Creation Runtime Error");
            DataRuntimeException response = new DataRuntimeException(4103, "EF4103", "Coffee Creation Data Runtime Error");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Coffee Creation Error", e);
            Exception response = new Exception("Coffee Creation Error", e);
            return ResponseEntity.ok().body(response);
        }
    }

    /*
    커피 수정
    수정시 수정 날자,유저 업데이트
     */
    @PostMapping("/coffee")
    public ResponseEntity<?> modifyCoffee(@RequestBody CoffeeDto modifyCoffeeItem) throws Exception {
        try {
            coffeeService.modifyCoffee(modifyCoffeeItem);
            String response = new String("coffee modify successfully.");
            return ResponseEntity.ok().body(response);
        } catch (DataNullException e) {
            log.error("Coffee Modify Null Error");
            DataNullException response = new DataNullException(4103, "EF4103", "Coffee Modify Data Null");
            return ResponseEntity.ok().body(response);
        } catch (DataRuntimeException e) {
            log.error("Coffee Modify Runtime Error");
            DataRuntimeException response = new DataRuntimeException(4103, "EF4103", "Coffee Modify Data Runtime Error");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Coffee Modify Error", e);
            Exception response = new Exception("Coffee Modify Error", e);
            return ResponseEntity.ok().body(response);
        }
    }

}
