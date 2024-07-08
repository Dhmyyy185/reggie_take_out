package com.myyy.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myyy.reggie.dto.SetmealDto;
import com.myyy.reggie.entity.Setmeal;
import com.myyy.reggie.entity.SetmealDish;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);

    /**
     * 修改套餐，同时修改套餐信息和菜品关联管理
     * @param setmealDto
     */
    public void updateWithSetMealDish(SetmealDto setmealDto);

    /**
     * 查询套餐对应信息
     * @param id
     */
    public SetmealDto getByIdWithSetMealDish(Long id);
}
