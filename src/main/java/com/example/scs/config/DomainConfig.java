package com.example.scs.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.dbms.scs.domain")
@EnableTransactionManagement
public class DomainConfig {
}
