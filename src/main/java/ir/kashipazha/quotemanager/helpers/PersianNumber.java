package ir.kashipazha.quotemanager.helpers;

public interface PersianNumber {
    static String toPersianNumber(String arg ){
        return arg.replaceAll("1","۱")
            .replaceAll("2","۲")
            .replaceAll("3","۳")
            .replaceAll("4","۴")
            .replaceAll("5","۵")
            .replaceAll("6","۶")
            .replaceAll("7","۷")
            .replaceAll("8","۸")
            .replaceAll("9","۹")
            .replaceAll("0","۰");
    }

    static String toPersianNumber(int arg ){
        return toPersianNumber(String.valueOf(arg));
    }
}
