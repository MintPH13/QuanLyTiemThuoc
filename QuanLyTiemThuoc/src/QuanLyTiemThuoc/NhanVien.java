package QuanLyTiemThuoc;
import java.util.Scanner;

public class NhanVien extends ConNguoi {
    private String manv;
    private String chucVu; // Dược sĩ / Thu ngân
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
                    String chucVu, double phuCap) {
        super(hoTen, diaChi, gioiTinh, ngaySinh, sdt);
        this.manv = manv;
        this.ngayVaoLam = ngayVaoLam;
        this.email = email;
        this.luongCoBan = luongCoBan;
        this.chucVu = chucVu;
        this.phuCap = phuCap;
        tongSoNhanVien++;
    }

    public String getManv() { return manv; }
    public void setManv(String manv) { this.manv = manv; }
    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }

    public double tinhLuong() {
        return luongCoBan + phuCap;
    }

    @Override
    public String getVaiTro() {
        return chucVu;
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
        System.out.print("Chức vụ (Dược sĩ / Thu ngân): ");
        chucVu = sc.nextLine();
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
        System.out.printf(" | CV: %-12s | Lương: %,11.0f VNĐ\n", chucVu, tinhLuong());
    }

    public String toFileString() {
        return "DUOCSI;" + manv + ";" + hoTen + ";" + diaChi + ";" + gioiTinh + ";" + ngaySinh + ";"
                + sdt + ";" + ngayVaoLam + ";" + email + ";" + luongCoBan + ";" + chucVu + ";" + phuCap;
    }
}
