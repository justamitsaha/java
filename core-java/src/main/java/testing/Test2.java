package testing;

public class Test2 {
    private int f = 9;
    private static int l=8;

    public void method() {
        NestedClass nc = new NestedClass();
        NestedClass.i++;
        nc.j++;
        NestedClass2 nc2 = new NestedClass2();
        nc2.j++;
    }

    static class NestedClass {
        private static int i;
        private int j;

        public void test(){
            Test2 test2 = new Test2();
            test2.f++;
            l++;
        }
    }

    class NestedClass2 {
        private int j =9;
        public void test(){
            f++;
            l++;
        }
    }
}