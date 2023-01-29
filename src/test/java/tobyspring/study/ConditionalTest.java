package tobyspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

public class ConditionalTest
{
    @Test
    void conditional()
    {
        // 테스트 전용 어플리케이션 컨텍스트
        ApplicationContextRunner contextRunner = new ApplicationContextRunner();
        contextRunner.withUserConfiguration( Config1.class ).run( context ->
        {
            Assertions.assertThat(context).hasSingleBean(MyBean.class);
            Assertions.assertThat(context).hasSingleBean(Config1.class);
        });

        new ApplicationContextRunner().withUserConfiguration( Config2.class ).run( context ->
        {
            Assertions.assertThat(context).doesNotHaveBean(MyBean.class);
            Assertions.assertThat(context).doesNotHaveBean(Config1.class);
        });

        // true
//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
//        ac.register(Config1.class);
//        ac.refresh();
//
//        MyBean bean = ac.getBean(MyBean.class);
//
//        // false
//        AnnotationConfigApplicationContext ac2 = new AnnotationConfigApplicationContext();
//        ac2.register(Config2.class);
//        ac2.refresh();
//
//        MyBean bean2 = ac2.getBean(MyBean.class);
    }

    @Retention( RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional( TrueCondition.class )
    @interface TrueConditional{}

    @Retention( RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional( BooleanCondition.class )
    @interface BooleanConditional
    {
        boolean value();
    }

    @Configuration
    @BooleanConditional(true)
    static class Config1
    {
        @Bean
        MyBean myBean()
        {
           return new MyBean();
        }
    }

    @Retention( RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @BooleanConditional( false )
    @interface FalseConditional{}

    @Configuration
    @FalseConditional
    static class Config2
    {
        @Bean
        MyBean myBean()
        {
            return new MyBean();
        }
    }

    static  class MyBean {}

    static class TrueCondition implements Condition
    {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata)
        {
            return true;
        }
    }

    static class FalseCondition implements Condition
    {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata)
        {
            return false;
        }
    }

    private static class BooleanCondition implements Condition
    {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata)
        {
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
            Boolean value = (Boolean) annotationAttributes.get("value");

            return value;
        }
    }
}
