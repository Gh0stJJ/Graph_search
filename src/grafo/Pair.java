package grafo;

public class Pair<T,U> {
    private  T key;
    private  U value;

    public Pair(T key, U value) {
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public U getValue() {
        return value;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public void setValue(U value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("(%s)", key);
    }

    //override contains method






}
