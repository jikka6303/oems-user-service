package com.bits.oems.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = "com.bits")
@EnableFeignClients(basePackages = "com.bits")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class OEMSUserService {

	public static void main(String[] args) {
		SpringApplication.run(OEMSUserService.class, args);
	}

}
