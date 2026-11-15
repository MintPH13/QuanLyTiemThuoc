package QuanLyTiemThuoc;
import java.util.Scanner;

public class ThuocKhongKeDon extends Thuoc {
    private String congDung;

    public ThuocKhongKeDon() { super(); }

    public ThuocKhongKeDon(String mathuoc, String tenthuoc, String maloai, String mahsx,
                            String dvt, double giagoc, int soluong, String hansudung,
                            String congDung) {
        super(mathuoc, tenthuoc, maloai, mahsx, dvt, giagoc, soluong, hansudung);
        this.congDung = congDung;
    }

    public ThuocKhongKeDon(ThuocKhongKeDon other) {
        super(other);
        this.congDung = other.congDung;
    }

    public String getCongDung() { return congDung; }
    public void setCongDung(String congDung) { this.congDung = congDung; }

    @Override
    public void nhap(Scanner sc) {
        super.nhap(sc);
        System.out.print("Nhập công dụng thuốc: ");
        congDung = sc.nextLine();
    }

    @Override
    public void nhapCapNhat(Scanner sc) {
        super.nhapCapNhat(sc);
        System.out.print("Nhập công dụng mới: ");
        congDung = sc.nextLine();
    }

    @Override
    public double tinhGiaBan() {
        return giagoc * 1.10;
    }

    @Override
    public String getPhanNhom() {
        return "Không Kê Đơn";
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.printf(" | [Không Kê Đơn] Công dụng: %s\n", congDung);
    }

    @Override
    public String toFileString() {
        return "KHONGKEDON;" + mathuoc + ";" + tenthuoc + ";" + maloai + ";" + mahsx + ";"
                + dvt + ";" + giagoc + ";" + soluong + ";" + hansudung + ";" + congDung;
    }
}
