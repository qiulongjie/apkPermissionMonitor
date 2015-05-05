package com.zzcm.admonitor.rmi;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
	
	public static void main(String[] args){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring_rmi.xml");
        ac.registerShutdownHook();// 注册终止jvm钩子，jvm平滑退出时将调用所有实现了DisposableBean接口的destroy
        Logger.getLogger(Start.class).info("远程服务任务启动完成");
	}

}
