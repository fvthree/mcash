package com.fvthree.micromcashusers.api.config.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy
@EnableTransactionManagement(proxyTargetClass = true)
@Import({DatabaseConfig.class, JdbcTemplateConfig.class})
public class AppConfig {
}
