package com.lazarev.flashcards.aspect;

import com.lazarev.flashcards.annotation.Validate;
import com.lazarev.flashcards.service.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidateAspect {
    private final Map<String, Validator<?>> validatorBeans;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerMethods() {}

    @Pointcut("@annotation(com.lazarev.flashcards.annotation.Validate)")
    public void validateMethods() {}

    @Before("restControllerMethods() && validateMethods()")
    public void validateRequestBody(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Parameter[] parameters = method.getParameters();
        for(int i=0; i<parameters.length; i++){
            Parameter param = parameters[i];
            if(param.isAnnotationPresent(RequestBody.class) || param.isAnnotationPresent(ModelAttribute.class)){
                Validate annotation = method.getAnnotation(Validate.class);
                Class<? extends Validator<?>>[] validators = annotation.value();
                validateRequestBody(validators, args[i]);
            }
        }
    }

    @SneakyThrows
    private void validateRequestBody(Class<? extends Validator<?>>[] validators, Object arg) {
        for(Class<? extends Validator<?>> validatorClass : validators){
            String validatorBeanName = resolveBeanName(validatorClass);

            Method validateMethod = validatorClass.getDeclaredMethod("validate", Object.class);
            Validator<?> validator = validatorBeans.get(validatorBeanName);

            try{
                validateMethod.invoke(validator, arg);
            }
            catch (Exception ex){
                throw ex.getCause();
            }
        }
    }

    private String resolveBeanName(Class<?> beanClass){
        String capitalizedBeanName = beanClass.getSimpleName();
        return capitalizedBeanName.substring(0, 1).toLowerCase() + capitalizedBeanName.substring(1);
    }
}
