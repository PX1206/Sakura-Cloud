package com.sakura.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sakura.order.feign.ProductFeignService;
import com.sakura.order.feign.StockFeignService;
import com.sakura.order.param.AddOrderParam;
import com.sakura.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sakura
 * @date 2023/7/19 11:25
 */
@RestController
@RequestMapping("/order")
@RefreshScope
public class OrderController {

    @Autowired
    StockFeignService stockFeignService;
    @Autowired
    ProductFeignService productFeignService;

    @Value("${user.name}")
    private String userName;

    @Value("${user.age}")
    private String userAge;

    @Value("${user.sex}")
    private String userSex;

    @Autowired
    OrderService orderService;

    @RequestMapping("/add")
    public String add(){
        System.out.println("下单成功");
        String msg = stockFeignService.reduct();
        String productMsg = productFeignService.get(1);
        return userName + userAge + userSex +  "下单成功" + msg + "-" + productMsg;
    }

    @RequestMapping("/get")
    @SentinelResource(value = "get", blockHandler = "getBlockHandler")
    public String get(){
        return "获取订单成功";
    }

    // 流控方法必须和原方法类型一致参数一致
    // 一定要加上BlockException
    public String getBlockHandler(BlockException blockException){
        // 我们可以在这个方法里面处理流控后的业务逻辑
        return "get接口被流控";
    }

    @RequestMapping("/flow")
    public String flow() throws Exception {
        Thread.sleep(3000);
        return "正常访问";
    }

    @RequestMapping("/addOrder")
    @ResponseBody
    public String addOrder(@Validated @RequestBody AddOrderParam addOrderParam){
        return orderService.addOrder(addOrderParam);
    }

}
