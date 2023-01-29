package tobyspring.config;

import org.springframework.context.annotation.Import;
import tobyspring.config.autoconfig.ServerProperties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target(ElementType.TYPE)
@Import( MyConfigurationPropertiesImportSelector.class )
public @interface EnableMyConfiguraionProperties
{
    Class<?> value();
}
