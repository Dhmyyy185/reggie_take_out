package com.myyy.reggie.dto;

import com.myyy.reggie.entity.OrderDetail;
import com.myyy.reggie.entity.Orders;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private List<OrderDetail> orderDetails = new ArrayList<>();
}
