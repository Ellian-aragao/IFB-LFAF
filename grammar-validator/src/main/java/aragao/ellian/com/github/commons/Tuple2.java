package aragao.ellian.com.github.commons;

public record Tuple2<O1, O2>(O1 obj1, O2 obj2) {
    public static <O1, O2> Tuple2<O1, O2> of(O1 obj1, O2 obj2) {
        return new Tuple2<>(obj1, obj2);
    }
}
