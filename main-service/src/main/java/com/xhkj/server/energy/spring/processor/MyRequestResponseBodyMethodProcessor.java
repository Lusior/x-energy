package com.xhkj.server.energy.spring.processor;

import com.xhkj.server.energy.returnvalue.ReturnValue;
import com.xhkj.server.energy.utils.JsonViewUtil;
import com.xhkj.server.energy.utils.RequestUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.io.IOException;
import java.util.List;

public class MyRequestResponseBodyMethodProcessor extends RequestResponseBodyMethodProcessor {

    public MyRequestResponseBodyMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return super.supportsReturnType(returnType) || RequestUtils.isAjaxRequest();
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {
        if (RequestUtils.isAjaxRequest()) {
            mavContainer.setRequestHandled(true);
            ServletServerHttpResponse outputMessage = createOutputMessage(webRequest);
            ReturnValue<Object> xReturnValue = JsonViewUtil.convertToReturnValue(mavContainer.getModel(), returnValue);
            handlerJsonReturnValue(xReturnValue, outputMessage);
        } else {
            super.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
        }
    }

    private void handlerJsonReturnValue(Object returnValue, ServletServerHttpResponse outputMessage) throws IOException {
        GenericHttpMessageConverter genericConverter = (GenericHttpMessageConverter) this.messageConverters.get(0);
        MediaType selectedMediaType = MediaType.APPLICATION_JSON_UTF8;
        genericConverter.write(returnValue, selectedMediaType, outputMessage);
    }
}
