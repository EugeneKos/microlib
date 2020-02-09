package ru.ed.microlib.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import ru.ed.microlib.annotation.CacheKey;
import ru.ed.microlib.annotation.Cacheable;
import ru.ed.microlib.cache.CacheService;
import ru.ed.microlib.cache.CacheServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Class> beanClasses = new HashMap<>();

    private CacheService cacheService = new CacheServiceImpl();

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Method[] beanClassMethods = beanClass.getMethods();
        for (Method beanClassMethod : beanClassMethods){
            if(beanClassMethod.isAnnotationPresent(Cacheable.class)){
                beanClasses.put(beanName, beanClass);
                return bean;
            }
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = beanClasses.get(beanName);
        if(beanClass == null){
            return bean;
        }

        return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Method[] originClassMethods = beanClass.getDeclaredMethods();
                for (Method originClassMethod : originClassMethods){
                    if(originClassMethod.getName().equals(method.getName())
                            && originClassMethod.isAnnotationPresent(Cacheable.class)) {

                        int hash = 0;

                        Parameter[] parameters = originClassMethod.getParameters();
                        if(parameters.length == 0){
                            hash = originClassMethod.getName().hashCode();
                            //Стратегия 1. Берем хэш по имени метода
                        } else {
                            int numParametersOfMethod = 0;

                            List<Integer> numOfPositions = new ArrayList<>(parameters.length);

                            for (int i=0; i<parameters.length; i++){
                                Parameter parameter = parameters[i];

                                numParametersOfMethod++;

                                if(parameter.isAnnotationPresent(CacheKey.class)){
                                    numOfPositions.add(i);
                                }
                            }

                            for (Integer numOfPosition : numOfPositions){
                                if(hash == 0){
                                    hash = args[numOfPosition].hashCode();
                                } else {
                                    hash = hash * args[numOfPosition].hashCode();
                                }
                                //Стратегия 2. Берем хэш по аргументам
                            }

                            if(hash == 0){
                                hash = numParametersOfMethod * originClassMethod.getName().hashCode();
                                //Стратегия 3. Берем хэш по имени метода и умножаем на кол-во параметров метода
                            }
                        }

                        Object cacheValue = cacheService.getValue(
                                hash, originClassMethod.getAnnotation(Cacheable.class).timeToLive()
                        );

                        if(cacheValue != null){
                            return cacheValue;
                        }

                        cacheValue = method.invoke(bean, args);

                        cacheService.putValue(hash, cacheValue);

                        return cacheValue;
                    }
                }

                return method.invoke(bean, args);
            }
        });
    }
}
