package DTO;
import BUS.KtraTinhHopLe;
import java.util.Scanner;

public class NhanVien extends ConNguoi {
    private String manv;
    private String ngayVaoLam;
    private String email;
    private double luongCoBan;
    private double phuCap;
    public static int tongSoNhanVien = 0;

    public NhanVien() {
        super();
        tongSoNhanVien++;
    }

    public NhanVien(String manv, String hoTen, String diaChi, int gioiTinh, String ngaySinh,
                    String sdt, String ngayVaoLam, String email, double luongCoBan,
                    double phuCap) {
        super(hoTen, diaChi, gioiTinh, ngaySinh, sdt);
        this.manv = manv;
        this.ngayVaoLam = ngayVaoLam;
        this.email = email;
        this.luongCoBan = luongCoBan;
        this.phuCap = phuCap;
        tongSoNhanVien++;
    }

    public String getManv() { return manv; }
    public void setManv(String manv) { this.manv = manv; }
    public String getEmail() { return email; }
    public String getNgayVaoLam() { return ngayVaoLam; }
    public double getLuongCoBan() { return luongCoBan; }
    public double getPhuCap() { return phuCap; }

    public double tinhLuong() {
        return luongCoBan + phuCap;
    }

    @Override
    public String getVaiTro() {
        return "Nhân viên";
    }

    @Override
    public void nhap(Scanner sc) {
        do {
            System.out.print("Mã nhân viên: ");
            manv = sc.nextLine();
        } while (!KtraTinhHopLe.isNotEmpty(manv));
        nhapCapNhat(sc);
    }

    public void nhapCapNhat(Scanner sc) {
        super.nhap(sc);
        do {
            System.out.print("Ngày vào làm (dd/MM/yyyy): ");
            ngayVaoLam = sc.nextLine();
        } while (!KtraTinhHopLe.isDate(ngayVaoLam));
        do {
            System.out.print("Email: ");
            email = sc.nextLine();
        } while (!KtraTinhHopLe.isEmail(email));
        System.out.print("Lương cơ bản: ");
        luongCoBan = Double.parseDouble(sc.nextLine());
        System.out.print("Phụ cấp / Thưởng: ");
        phuCap = Double.parseDouble(sc.nextLine());
    }

    @Override
    public void xuat() {
        System.out.printf("Mã NV: %-5s | ", manv);
        super.xuat();
        System.out.printf(" | Lương: %,11.0f VNĐ\n", tinhLuong());
    }

    public String toFileString() {
        return manv + ";" + hoTen + ";" + diaChi + ";" + gioiTinh + ";" + ngaySinh + ";"
                + sdt + ";" + ngayVaoLam + ";" + email + ";" + luongCoBan + ";" + phuCap;
    }
}
