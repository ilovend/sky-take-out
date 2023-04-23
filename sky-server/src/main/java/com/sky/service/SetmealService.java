package com.sky.service;


import com.sky.dto.SetmealDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SetmealService {

    void saveWithDish(SetmealDTO setmealDTO);
}
