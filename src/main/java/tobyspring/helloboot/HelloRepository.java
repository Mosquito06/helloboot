package tobyspring.helloboot;

public interface HelloRepository
{
    Hello findHello(String name);

    void increaseCount(String name);

    // 인터페이스 내 default 메서드를 선언하여 사용 가능
    // 활용 방법을 자세히 알고 싶으면, Java 내 Comparator 인터페이스를 찾아서 분석하면 됨
    default int countOf(String name)
    {
        Hello hello = findHello(name);

        return hello == null ? 0 : hello.getCount();
    }
}
