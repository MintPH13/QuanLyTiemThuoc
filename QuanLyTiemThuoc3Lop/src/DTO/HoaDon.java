package DTO;
import java.util.Scanner;

public class HoaDon implements INhapXuat {
    private String mahd;
    private String manv;
    private String makh;
    private String ngaylap;
    private double tongtien;

    public HoaDon() {}

    public HoaDon(String mahd, String manv, String makh, String ngaylap, double tongtien) {
        this.mahd = mahd;
        this.manv = manv;
        this.makh = makh;
        this.ngaylap = ngaylap;
        this.tongtien = tongtien;
    }

    public String getMahd() { return mahd; }
    public void setMahd(String mahd) { this.mahd = mahd; }
    public String getManv() { return manv; }
    public void setManv(String manv) { this.manv = manv; }
    public String getMakh() { return makh; }
    public void setMakh(String makh) { this.makh = makh; }
    public String getNgaylap() { return ngaylap; }
    public void setNgaylap(String ngaylap) { this.ngaylap = ngaylap; }
    public double getTongtien() { return tongtien; }
    public void setTongtien(double tongtien) { this.tongtien = tongtien; }

    @Override
    public void nhap(Scanner sc) {
        System.out.print("Mã HD: ");
        mahd = sc.nextLine();
        System.out.print("Mã NV: ");
        manv = sc.nextLine();
        System.out.print("Mã KH: ");
        makh = sc.nextLine();
        System.out.print("Ngày lập (dd/MM/yyyy): ");
        ngaylap = sc.nextLine();
    }

    @Override
    public void xuat() {
        System.out.printf("Mã HD: %-6s | Mã NV: %-6s | Mã KH: %-6s | Ngày lập: %-10s | Tổng tiền: %,14.2f VNĐ\n",
                mahd, manv, makh, ngaylap, tongtien);
    }

    public String toFileString() {
        return mahd + ";" + manv + ";" + makh + ";" + ngaylap + ";" + tongtien;
    }
}
