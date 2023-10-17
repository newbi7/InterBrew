package com.toyproject.internbrew_backend.coffee.repository;

import com.toyproject.internbrew_backend.coffee.dto.CoffeeDto;
import com.toyproject.internbrew_backend.coffee.entity.Coffee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Integer> {

    CoffeeDto findById(int coffeeId);

    Coffee findByCoffeeId(int coffeeId);
}

