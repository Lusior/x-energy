package com.xhkj.server.energy.spring.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 * 把自定义的 MyRequestResponseBodyMethodProcessor 放到 HandlerMethodReturnValueHandlerComposite 中的list的第一个
 */
@Slf4j
public class MyRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        try {
            Field returnValueHandlers = RequestMappingHandlerAdapter.class.getDeclaredField("returnValueHandlers");
            returnValueHandlers.setAccessible(true);
            HandlerMethodReturnValueHandlerComposite o = (HandlerMethodReturnValueHandlerComposite) returnValueHandlers.get(this);
            Field returnValueHandlers1 = HandlerMethodReturnValueHandlerComposite.class.getDeclaredField("returnValueHandlers");
            returnValueHandlers1.setAccessible(true);
            List<HandlerMethodReturnValueHandler> o1 = (List<HandlerMethodReturnValueHandler>) returnValueHandlers1.get(o);
            Iterator<HandlerMethodReturnValueHandler> iterator = o1.iterator();

            HandlerMethodReturnValueHandler remove = null;
            while (iterator.hasNext()) {
                HandlerMethodReturnValueHandler next = iterator.next();
                if (next instanceof MyRequestResponseBodyMethodProcessor) {
                    iterator.remove();
                    remove = next;
                    break;
                }
            }
            // todo 需要了解为什么这里没有加载 MyRequestResponseBodyMethodProcessor
            if (remove != null) {
                o1.add(0, remove);
            } else {
                MyRequestResponseBodyMethodProcessor bean = getApplicationContext().getBean(MyRequestResponseBodyMethodProcessor.class);
                if (bean != null) {
                    o1.add(0, bean);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
