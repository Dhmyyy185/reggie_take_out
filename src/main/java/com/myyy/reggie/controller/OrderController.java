package com.myyy.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myyy.reggie.common.BaseContext;
import com.myyy.reggie.common.R;
import com.myyy.reggie.dto.DishDto;
import com.myyy.reggie.dto.OrdersDto;
import com.myyy.reggie.entity.Dish;
import com.myyy.reggie.entity.OrderDetail;
import com.myyy.reggie.entity.Orders;
import com.myyy.reggie.service.OrderDetailService;
import com.myyy.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 用户下单
     *
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("订单数据：{}", orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 订单分页查询
     *
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, Integer number, String beginTime, String endTime) {
        //构造分页构造器对象
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        // Page<OrdersDto> ordersDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(number != null, Orders::getId, number);
        queryWrapper.between(beginTime != null && endTime != null && endTime.compareTo(beginTime) > 0,
                Orders::getOrderTime, beginTime, endTime);
        //添加排序条件
        queryWrapper.orderByDesc(Orders::getOrderTime);

        //执行分页查询
        orderService.page(pageInfo, queryWrapper);

        // //对象拷贝
        // BeanUtils.copyProperties(pageInfo, ordersDtoPage, "records");
        // List<Orders> records = pageInfo.getRecords();
        //
        // List<OrdersDto> list = records.stream().map((item) -> {
        //     OrdersDto ordersDto = new OrdersDto();
        //     //对象拷贝
        //     BeanUtils.copyProperties(item, ordersDto);
        //     return ordersDto;
        // }).collect(Collectors.toList());
        //
        // ordersDtoPage.setRecords(list);
        // return R.success(ordersDtoPage);
        return R.success(pageInfo);
    }

    /**
     * 个人中心查看订单
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize) {
        //构造分页构造器对象
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        //添加排序条件
        queryWrapper.orderByDesc(Orders::getOrderTime);

        //执行分页查询
        orderService.page(pageInfo, queryWrapper);

        //对象拷贝
        List<Orders> list = orderService.list(queryWrapper);
        BeanUtils.copyProperties(pageInfo, ordersDtoPage, "records");

        List<OrdersDto> list2 = list.stream().map((item) -> {
            OrdersDto ordersDto = new OrdersDto();
            //对象拷贝
            BeanUtils.copyProperties(item, ordersDto);

            Long orderId = ordersDto.getId();
            //条件构造器
            LambdaQueryWrapper<OrderDetail> queryWrapperForDetail = new LambdaQueryWrapper<>();
            //添加过滤条件
            queryWrapperForDetail.eq(OrderDetail::getOrderId, orderId);

            List<OrderDetail> listForDetail = orderDetailService.list(queryWrapperForDetail);
            ordersDto.setOrderDetails(listForDetail);
            return ordersDto;
        }).collect(Collectors.toList());

        ordersDtoPage.setRecords(list2);
        return R.success(ordersDtoPage);
    }

    /**
     * 更改订单状态
     *
     * @param orders
     * @return
     */
    @PutMapping
    public R<String> updateStatus(@RequestBody Orders orders) {
        //更改订单状态
        LambdaUpdateWrapper<Orders> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Orders::getId, orders.getId());
        updateWrapper.set(Orders::getStatus, orders.getStatus());

        orderService.update(updateWrapper);
        return R.success("更新订单状态成功");
    }

    @PostMapping("/again")
    public R<String> again(@RequestBody Orders orders){
        log.info("再来啊一单的订单id为: {}", orders.getId());

        orderService.again(orders);

        return R.success("再来一单成功");
    }
}