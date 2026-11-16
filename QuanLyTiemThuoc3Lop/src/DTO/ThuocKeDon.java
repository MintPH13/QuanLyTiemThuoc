package DTO;
import java.util.Scanner;

public class ThuocKeDon extends Thuoc {
    private String hoatChat;
    private String chiDinhBacSi;

    public ThuocKeDon() { super(); }

    public ThuocKeDon(String mathuoc, String tenthuoc, String maloai, String mahsx,
                      String dvt, double giagoc, int soluong, String hansudung,
                      String hoatChat, String chiDinhBacSi) {
        super(mathuoc, tenthuoc, maloai, mahsx, dvt, giagoc, soluong, hansudung);
        this.hoatChat = hoatChat;
        this.chiDinhBacSi = chiDinhBacSi;
    }

    public ThuocKeDon(ThuocKeDon other) {
        super(other);
        this.hoatChat = other.hoatChat;
        this.chiDinhBacSi = other.chiDinhBacSi;
    }

    public String getHoatChat() { return hoatChat; }
    public void setHoatChat(String hoatChat) { this.hoatChat = hoatChat; }
    public String getChiDinhBacSi() { return chiDinhBacSi; }
    public void setChiDinhBacSi(String chiDinhBacSi) { this.chiDinhBacSi = chiDinhBacSi; }

    @Override
    public void nhap(Scanner sc) {
        super.nhap(sc);
        System.out.print("Nhập hoạt chất chính: ");
        hoatChat = sc.nextLine();
        System.out.print("Nhập chỉ định bác sĩ: ");
        chiDinhBacSi = sc.nextLine();
    }

    @Override
    public void nhapCapNhat(Scanner sc) {
        super.nhapCapNhat(sc);
        System.out.print("Nhập hoạt chất chính mới: ");
        hoatChat = sc.nextLine();
        System.out.print("Nhập chỉ định bác sĩ mới: ");
        chiDinhBacSi = sc.nextLine();
    }

    @Override
    public double tinhGiaBan() {
        return giagoc * 1.05;
    }

    @Override
    public String getPhanNhom() {
        return "Thuốc Kê Đơn";
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.printf(" | [Kê Đơn] HC: %-18s | Lưu ý: %s\n", hoatChat, chiDinhBacSi);
    }

    @Override
    public String toFileString() {
        return "KEDON;" + mathuoc + ";" + tenthuoc + ";" + maloai + ";" + mahsx + ";"
                + dvt + ";" + giagoc + ";" + soluong + ";" + hansudung + ";" + hoatChat + ";" + chiDinhBacSi;
    }
}
