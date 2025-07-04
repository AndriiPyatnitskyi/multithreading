class RunnableImpl implements Runnable {

    @Override
    public void run() {
        System.out.println("From RunnableImpl...");
    }
}

public class Main {
    public static void main(String[] args) {
        new Thread(() -> System.out.println("From Runnable..."))
                .start();

//
//        // Тут видно що статичний клас можна створити без інстанса класса в який він вкладений
//        Outer.Nested nested = new Outer.Nested();
//
//
//
//
//        Outer outer = new Outer();
//        // Натомість нестатичний клас не можна створити без попереднього створення інстанса класа в який він вкладений.
//        // Тобто нестатичному класу з якихось причин потрібен інстанс Outer класу.
//        Outer.Inner inner = outer.new Inner();
//
//        // Тут бачимо що Outer може всердині користуватись полями які є внутнрішніми класами як nested так і inner
//        outer.nested.doSmthFromNested();
//        outer.inner.doSmthInner();
//
//        // Іще ключавий момент це те що із Nested не можна викликати метод із Outer, а із Inner можна
//        //---
//        //        public void doSmthFromNested() {
//        //
//        //        }
//        //---
//        //        public void doSmthInner() {
//        //            doSmthFromOuter();
//        //        }
//
//        // Для чого так зроблено на мою думку:
//        // Nested клас є частиною Outer класу.
//        // Натомість Outer класс є частиною логіки для Inner класу.
//    }
    }
}

//class Outer {
//    public Nested nested;
//    public Inner inner;
//    public void doSmthFromOuter() {
//    }
//
//    public static class Nested {
//        public void doSmthFromNested() {
//            doSmthFromOuter();
//        }
//    }
//
//    public class Inner {
//        public void doSmthInner() {
//            doSmthFromOuter();
//        }
//    }
//}
