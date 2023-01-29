package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD)
@UnitTest
@interface FastTest
{

}

@Retention( RetentionPolicy.RUNTIME )
@Target( {ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Test
@interface UnitTest
{

}

public class HelloServiceTest
{
    @Test
    void simpleHelloService()
    {
        SimpleHelloService helloService = new SimpleHelloService(helloRepositoryStub);

        String ret = helloService.sayHello("Test");

        Assertions.assertThat(ret).isEqualTo("Hello Test");
    }

    private static HelloRepository helloRepositoryStub = new HelloRepository()
    {
        @Override
        public Hello findHello(String name) {
            return null;
        }

        @Override
        public void increaseCount(String name) {

        }
    };

    @Test
    void helloDecoratorService()
    {
        HelloService decorator = new HelloDecorator( new SimpleHelloService(helloRepositoryStub) );

        String ret = decorator.sayHello("Test");

        Assertions.assertThat(ret).isEqualTo("*Test*");
    }
}
