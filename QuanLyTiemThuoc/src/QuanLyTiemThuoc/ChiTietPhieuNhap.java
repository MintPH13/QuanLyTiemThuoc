package QuanLyTiemThuoc;
import java.util.Scanner;

public class ChiTietPhieuNhap implements INhapXuat {
    private String mapn;
    private String mathuoc;
    private int soluong;
    private double gianhap;
    private double thanhtien;

    public ChiTietPhieuNhap() {}

    public ChiTietPhieuNhap(String mapn, String mathuoc, int soluong, double gianhap, double thanhtien) {
        this.mapn = mapn;
        this.mathuoc = mathuoc;
        this.soluong = soluong;
        this.gianhap = gianhap;
        this.thanhtien = thanhtien;
    }

    public String getMapn() { return mapn; }
    public void setMapn(String mapn) { this.mapn = mapn; }
    public String getMathuoc() { return mathuoc; }
    public void setMathuoc(String mathuoc) { this.mathuoc = mathuoc; }
    public int getSoluong() { return soluong; }
    public void setSoluong(int soluong) { this.soluong = soluong; }
    public double getGianhap() { return gianhap; }
    public void setGianhap(double gianhap) { this.gianhap = gianhap; }
    public double getThanhtien() { return thanhtien; }
    public void setThanhtien(double thanhtien) { this.thanhtien = thanhtien; }

    @Override
    public void nhap(Scanner sc) {
        System.out.print("Mã PN: ");
        mapn = sc.nextLine();
        System.out.print("Mã thuốc: ");
        mathuoc = sc.nextLine();
        System.out.print("Số lượng nhập: ");
        soluong = Integer.parseInt(sc.nextLine());
        System.out.print("Giá nhập: ");
        gianhap = Double.parseDouble(sc.nextLine());
        thanhtien = soluong * gianhap;
    }

    @Override
    public void xuat() {
        System.out.printf("Mã PN: %-6s | Mã thuốc: %-6s | SL nhập: %-4d | Giá nhập: %,12.2f | Thành tiền: %,14.2f VNĐ\n",
                mapn, mathuoc, soluong, gianhap, thanhtien);
    }

    public String toFileString() {
        return mapn + ";" + mathuoc + ";" + soluong + ";" + gianhap + ";" + thanhtien;
    }
}
