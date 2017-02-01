package department.model.bo;

import lombok.Value;

/**
 * Created by Максим on 2/1/2017.
 */
@Value
public class Master {

    static class A {

        private final int a;

        A(int a) {
            this.a = a;
        }

        A(Builder b) {
            this.a = b.a;
        }

        static class Builder {

            private int a;

            Builder(A a) {
                setA(a.a);
            }

            public int getA() {
                return a;
            }

            public Builder setA(int a) {
                this.a = a;
                return this;
            }

            A build() {
                return new A(this);
            }

        }

    }

    {
        A a = new A(1);
        A b = new A.Builder(a).setA(12).setA(32).build();
    }

}
