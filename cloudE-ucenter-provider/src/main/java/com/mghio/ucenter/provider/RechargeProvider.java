package com.mghio.ucenter.provider;

import com.alibaba.fastjson.JSON;
import com.mghio.dto.BaseResult;
import com.mghio.entity.User;
import com.mghio.pay.client.ApplePayClient;
import com.mghio.ucenter.manager.UserManager;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RechargeProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(RechargeProvider.class);

    @Resource
    private UserManager userManager;
    @Resource
    private ApplePayClient applePayClient;


    @HystrixCommand(fallbackMethod = "rechargeFallback")
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public BaseResult<Boolean> recharge(@RequestParam(value = "userId") Long userId,
                                        @RequestParam(value = "amount") Double amount,
                                        @RequestParam(value = "type") String type) {
        User user = userManager.getUserByUserId(userId);
        LOGGER.info("user {} recharge {},type:{}", user.getUsername(), amount, type);
        BaseResult<Boolean> baseResult = applePayClient.recharge(userId, amount);
        LOGGER.info("user {} recharge  res:{}", user.getUsername(), JSON.toJSONString(baseResult));
        return baseResult;

    }

    private BaseResult<Boolean> rechargeFallback(Long useId, Double amount, String type, Throwable throwable) {
        LOGGER.error("user:{} recharge,amount:{},type:{}, fail:{}", useId, amount, type, throwable.getMessage(), throwable);
        return new BaseResult<>(false, throwable.getMessage());
    }
}
