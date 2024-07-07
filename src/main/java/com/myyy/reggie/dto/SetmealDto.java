package com.myyy.reggie.dto;

import com.myyy.reggie.entity.Setmeal;
import com.myyy.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
