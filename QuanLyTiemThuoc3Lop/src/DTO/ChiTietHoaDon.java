package DTO;
import java.util.Scanner;

public class ChiTietHoaDon implements INhapXuat {
    private String mahd;
    private String mathuoc;
    private int soluong;
    private double dongia;
    private double thanhtien;

    public ChiTietHoaDon() {}

    public ChiTietHoaDon(String mahd, String mathuoc, int soluong, double dongia, double thanhtien) {
        this.mahd = mahd;
        this.mathuoc = mathuoc;
        this.soluong = soluong;
        this.dongia = dongia;
        this.thanhtien = thanhtien;
    }

    public String getMahd() { return mahd; }
    public void setMahd(String mahd) { this.mahd = mahd; }
    public String getMathuoc() { return mathuoc; }
    public void setMathuoc(String mathuoc) { this.mathuoc = mathuoc; }
    public int getSoluong() { return soluong; }
    public void setSoluong(int soluong) { this.soluong = soluong; }
    public double getDongia() { return dongia; }
    public void setDongia(double dongia) { this.dongia = dongia; }
    public double getThanhtien() { return thanhtien; }
    public void setThanhtien(double thanhtien) { this.thanhtien = thanhtien; }

    @Override
    public void nhap(Scanner sc) {
        System.out.print("Mã HD: ");
        mahd = sc.nextLine();
        System.out.print("Mã thuốc: ");
        mathuoc = sc.nextLine();
        System.out.print("Số lượng: ");
        soluong = Integer.parseInt(sc.nextLine());
        System.out.print("Đơn giá: ");
        dongia = Double.parseDouble(sc.nextLine());
        thanhtien = soluong * dongia;
    }

    @Override
    public void xuat() {
        System.out.printf("Mã HD: %-6s | Mã thuốc: %-6s | Số lượng: %-4d | Đơn giá: %,12.2f | Thành tiền: %,14.2f VNĐ\n",
                mahd, mathuoc, soluong, dongia, thanhtien);
    }

    public String toFileString() {
        return mahd + ";" + mathuoc + ";" + soluong + ";" + dongia + ";" + thanhtien;
    }
}
