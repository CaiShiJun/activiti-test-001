package com.senyint.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: SpringContextUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Cai ShiJun
 * @date 2018年2月19日 下午3:58:10
 *
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	public static Object getBean(Class c) {
		return applicationContext.getBean(c);
	}

}