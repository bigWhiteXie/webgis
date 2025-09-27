package com.ruoyi.framework.interceptor.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.filter.RepeatedlyRequestWrapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpHelper;
import com.ruoyi.framework.interceptor.RepeatSubmitInterceptor;

/**
 * 判断请求url和数据是否和上一次相同，
 * 如果和上次相同，则是重复提交表单。 有效时间为10秒内。
 * 
 * @author ruoyi
 */
@Component
public class SameUrlDataInterceptor extends RepeatSubmitInterceptor
{
    @Autowired
    private RedisCache redisCache;

    /**
     * 间隔时间，单位:秒 默认10秒
     */
    private int interval()
    {
        return 10;
    }

    /**
     * 判断是否是白名单
     */
    private boolean isWhiteIp(HttpServletRequest request)
    {
        return false;
    }

    @Override
    public boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation)
    {
        String nowParams = "";
        if (request instanceof RepeatedlyRequestWrapper)
        {
            RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
            nowParams = HttpHelper.getBodyString(repeatedlyRequest);
        }

        // body参数为空，获取Parameter的数据
        if (StringUtils.isEmpty(nowParams))
        {
            nowParams = JSON.toJSONString(request.getParameterMap());
        }

        // 请求地址
        String url = request.getRequestURI();

        // 请求IP
        String ip = request.getRemoteAddr();

        // 请求类型
        String requestType = request.getMethod();

        // 白名单IP
        if (isWhiteIp(request))
        {
            return false;
        }

        // 构建唯一缓存键
        String cacheKey = CacheConstants.REPEAT_SUBMIT_KEY + url + "_" + ip + "_" + requestType + "_" + nowParams;

        // 检查是否已存在
        Object sessionObj = redisCache.getCacheObject(cacheKey);
        if (sessionObj != null)
        {
            return true;
        }

        // 设置缓存，防止重复提交
        redisCache.setCacheObject(cacheKey, "1", interval(), TimeUnit.SECONDS);
        return false;
    }
}
