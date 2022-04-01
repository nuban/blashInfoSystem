package vip.imagin.blast.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "vip.imagin.blast.modules.user.dao"})
public class PackageConfig {
}
