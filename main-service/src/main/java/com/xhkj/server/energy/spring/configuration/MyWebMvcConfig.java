package com.xhkj.server.energy.spring.configuration;

import com.xhkj.server.energy.spring.processor.MyRequestMappingHandlerAdapter;
import com.xhkj.server.energy.spring.processor.MyRequestResponseBodyMethodProcessor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyWebMvcConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
                handlers.add(myRequestResponseBodyMethodProcessor());
            }

            //重写父类提供的跨域请求处理的接口
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //添加映射路径
                registry.addMapping("/**")
                        //放行哪些原始域
                        .allowedOrigins("*")
                        //是否发送Cookie信息
                        .allowCredentials(true)
                        //放行哪些原始域(请求方式)
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        //放行哪些原始域(头部信息)
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    public MyRequestResponseBodyMethodProcessor myRequestResponseBodyMethodProcessor() {
        ArrayList<HttpMessageConverter<?>> messageConverters = new ArrayList<>(1);
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        return new MyRequestResponseBodyMethodProcessor(messageConverters);
    }

    @Bean
    public WebMvcRegistrations webMvcRegistrations() {
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return createRequestMappingHandlerMapping();
            }

            @Override
            public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
                return myRequestMappingHandlerAdapter();
            }
        };
    }

    @Bean
    public MyRequestMappingHandlerAdapter myRequestMappingHandlerAdapter() {
        return new MyRequestMappingHandlerAdapter();
    }

    private RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping() {
            @Override
            protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
                RequestMappingInfo info = createRequestMappingInfo(method);
                if (info != null) {
                    RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
                    if (typeInfo != null) {
                        info = typeInfo.combine(info);
                    }
                }
                return info;
            }

            private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
                RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
                RequestCondition<?> condition = (element instanceof Class ? getCustomTypeCondition((Class<?>) element) : getCustomMethodCondition((Method) element));
                return (requestMapping != null ? createRequestMappingInfo(requestMapping, element, condition) : null);
            }

            RequestMappingInfo createRequestMappingInfo(RequestMapping annotation, AnnotatedElement element, RequestCondition<?> customCondition) {
                String[] patterns = null;
                // 可以不配置 requestMapping value 直接实用方法名做为 mapping value
                if (annotation.value().length == 0 && element != null) {
                    if (element instanceof Method) {
                        patterns = new String[]{((Method) element).getName()};
                    } else /*if (element instanceof Class)*/ {
                        patterns = new String[]{((Class) element).getSimpleName()};
                    }
                } else {
                    patterns = resolveEmbeddedValuesInPatterns(annotation.value());
                }
                return new RequestMappingInfo(annotation.name(),
                        new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), this.useSuffixPatternMatch(), this.useTrailingSlashMatch(), this.getFileExtensions()),
                        new RequestMethodsRequestCondition(annotation.method()),
                        new ParamsRequestCondition(annotation.params()),
                        new HeadersRequestCondition(annotation.headers()),
                        new ConsumesRequestCondition(annotation.consumes(), annotation.headers()),
                        new ProducesRequestCondition(annotation.produces(), annotation.headers(), this.getContentNegotiationManager()), customCondition);
            }
        };
    }
}
