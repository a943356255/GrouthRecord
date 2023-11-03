package generics;

import java.util.ArrayList;
import java.util.List;

public class SingleGenerics<T> {

    private T name;

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }
}
