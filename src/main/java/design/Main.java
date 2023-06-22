package design;

import design.template.AbstractClass;
import design.template.Test1;
import design.template.Test2;

public class Main {

    public static void main(String[] args) {
        AbstractClass abstractClass = new Test1();
        abstractClass.templateMethod();

        AbstractClass demo2 = new Test2();
        demo2.templateMethod();
    }

}
