package com.myyy.reggie.dto;

import com.myyy.reggie.entity.OrderDetail;
import com.myyy.reggie.entity.Orders;

import java.util.ArrayList;
import java.util.List;

public class OrdersDto extends Orders {

    private List<OrderDetail> orderDetails = new ArrayList<>();
}
