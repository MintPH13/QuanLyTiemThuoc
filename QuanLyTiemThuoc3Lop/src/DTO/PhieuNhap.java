package DTO;
import java.util.Scanner;

public class PhieuNhap implements INhapXuat {
    private String mapn;
    private String manv;
    private String mancc;
    private String ngaynhap;
    private double tongtien;

    public PhieuNhap() {}

    public PhieuNhap(String mapn, String manv, String mancc, String ngaynhap, double tongtien) {
        this.mapn = mapn;
        this.manv = manv;
        this.mancc = mancc;
        this.ngaynhap = ngaynhap;
        this.tongtien = tongtien;
    }

    public String getMapn() { return mapn; }
    public void setMapn(String mapn) { this.mapn = mapn; }
    public String getManv() { return manv; }
    public void setManv(String manv) { this.manv = manv; }
    public String getMancc() { return mancc; }
    public void setMancc(String mancc) { this.mancc = mancc; }
    public String getNgaynhap() { return ngaynhap; }
    public void setNgaynhap(String ngaynhap) { this.ngaynhap = ngaynhap; }
    public double getTongtien() { return tongtien; }
    public void setTongtien(double tongtien) { this.tongtien = tongtien; }

    @Override
    public void nhap(Scanner sc) {
        System.out.print("Mã phiếu nhập: ");
        mapn = sc.nextLine();
        System.out.print("Mã nhân viên: ");
        manv = sc.nextLine();
        System.out.print("Mã nhà cung cấp: ");
        mancc = sc.nextLine();
        System.out.print("Ngày nhập (dd/MM/yyyy): ");
        ngaynhap = sc.nextLine();
    }

    @Override
    public void xuat() {
        System.out.printf("Mã PN: %-6s | Mã NV: %-6s | Mã NCC: %-6s | Ngày nhập: %-10s | Tổng tiền: %,14.2f VNĐ\n",
                mapn, manv, mancc, ngaynhap, tongtien);
    }

    public String toFileString() {
        return mapn + ";" + manv + ";" + mancc + ";" + ngaynhap + ";" + tongtien;
    }
}
