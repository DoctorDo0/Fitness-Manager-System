package org.example.demo05.common;

import org.example.demo05.utils.JsonResp;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.stream.Collectors;

/**
 * 功能：全局统一异常处理
 *
 * @author 千堆雪
 * @version 1.0.0
 * created on 2025/1/27 20:41, last modified on 2025/1/27 20:41
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 当控制器中的方法出现参数校验异常时，即会调用此方法响应值。
     *
     * @param ex 参数校验异常
     * @return 响应结果
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public JsonResp handle(HandlerMethodValidationException ex) {
        String msg = ex.getAllErrors().stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return JsonResp.error(msg);
    }

    /**
     * 需要同时监听HandlerMethodValidationException和MethodArgumentNotValidException，二者都可能会出现
     * 两个是完全不同的异常类型，继承体系结构也不一样，没办法合并为一个。只是恰巧都包含getAllErrors方法而已
     *
     * @param ex 参数校验异常
     * @return 响应结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResp handle(MethodArgumentNotValidException ex) {
        String msg = ex.getAllErrors().stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return JsonResp.error(msg);
    }
}