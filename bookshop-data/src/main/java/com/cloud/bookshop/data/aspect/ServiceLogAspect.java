package com.cloud.bookshop.data.aspect;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Collection;

@Component
@Aspect
public class ServiceLogAspect {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Around("@annotation(com.cloud.bookshop.data.aspect.ServiceLog)")
    public Object logServiceInvoke(ProceedingJoinPoint pjp) throws Throwable {
        return doLog(pjp);
    }

    protected Object doLog(ProceedingJoinPoint pjp) throws Throwable {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("*****invoke service:" + pjp.getSignature().toLongString() + "*****");
            for (Object arg : pjp.getArgs()) {
                printObj(arg, "invoke service parameters:");
            }
            try {
                Object retVal = pjp.proceed();
                printObj(retVal, "return value:");
                return retVal;
            } catch (Throwable e) {
                LOGGER.info("throw exception", e);
                throw e;
            } finally {
                LOGGER.info("*****service invoke finish*****");
            }
        }
        return pjp.proceed();
    }

    @SuppressWarnings("rawtypes")
    void printObj(Object arg, String prefix) {
        if (arg != null) {
            if (arg.getClass().isArray()) {
                if (ArrayUtils.isNotEmpty((Object[]) arg)) {
                    Object[] args = (Object[]) arg;
                    for (Object object : args) {
                        printObj(object, prefix);
                    }
                }
            } else if (arg instanceof Collection) {
                if (CollectionUtils.isNotEmpty((Collection) arg)) {
                    Collection collection = (Collection) arg;
                    for (Object object : collection) {
                        printObj(object, prefix);
                    }
                }
            }

            if (ClassUtils.isPrimitiveOrWrapper(arg.getClass())) {
                LOGGER.info(prefix + arg.toString());
            } else if (arg instanceof String) {
                LOGGER.info(prefix + (String) arg);
            } else {
                LOGGER.info(prefix + ReflectionToStringBuilder.toString(arg));
            }
        } else {
            LOGGER.info(prefix + " null");
        }
    }

}