package com.myyy.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myyy.reggie.entity.Orders;

public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     * @param orders
     */
    public void submit(Orders orders);

    /**
     * 再来一单
     * @param orders
     */
    public void again(Orders orders);
}
