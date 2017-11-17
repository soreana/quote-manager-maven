package helpers;

public class Tag<T> {
    private T t;

    public Tag(T arg){
        this.t = arg;
    }

    @Override
    public boolean equals(Object o){
        return this.equals((Tag<T>) o);
    }

    private boolean equals(Tag<T> t){
        return false;
    }

    @Override
    public String toString(){
        return String.format("Tag is: %s", t.toString());
    }
}
