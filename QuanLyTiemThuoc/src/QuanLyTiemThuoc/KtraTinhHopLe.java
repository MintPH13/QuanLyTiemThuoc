package QuanLyTiemThuoc;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class KtraTinhHopLe {
    public static boolean isEmail(String st) {
        return st != null && st.matches("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+");
    }

    public static boolean isSDT(String st) {
        return st != null && st.matches("0\\d{9}");
    }

    public static boolean isDate(String st) {
        if (st == null || !st.matches("^\\d{2}/\\d{2}/\\d{4}$")) return false;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(st, fmt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNotEmpty(String st) {
        return st != null && !st.trim().isEmpty();
    }
}
