package DTO;
import BUS.KtraTinhHopLe;
import java.util.Scanner;

public abstract class ConNguoi implements INhapXuat {
    protected String hoTen;
    protected String diaChi;
    protected int gioiTinh; // 0: Nam, 1: Nữ
    protected String ngaySinh;
    protected String sdt;

    public ConNguoi() {}

    public ConNguoi(String hoTen, String diaChi, int gioiTinh, String ngaySinh, String sdt) {
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
    }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public int getGioiTinhInt() { return gioiTinh; }
    public String getGioiTinh() { return gioiTinh == 1 ? "Nữ" : "Nam"; }
    public void setGioiTinh(int gioiTinh) { this.gioiTinh = gioiTinh; }
    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    @Override
    public void nhap(Scanner sc) {
        do {
            System.out.print("Họ tên: ");
            hoTen = sc.nextLine();
        } while (!KtraTinhHopLe.isNotEmpty(hoTen));

        do {
            System.out.print("Địa chỉ: ");
            diaChi = sc.nextLine();
        } while (!KtraTinhHopLe.isNotEmpty(diaChi));

        do {
            System.out.print("Giới tính (0: Nam, 1: Nữ): ");
            try { gioiTinh = Integer.parseInt(sc.nextLine()); }
            catch (Exception e) { gioiTinh = -1; }
        } while (gioiTinh != 0 && gioiTinh != 1);

        do {
            System.out.print("Ngày sinh (dd/MM/yyyy): ");
            ngaySinh = sc.nextLine();
        } while (!KtraTinhHopLe.isDate(ngaySinh));

        do {
            System.out.print("SĐT (10 số bắt đầu bằng 0): ");
            sdt = sc.nextLine();
        } while (!KtraTinhHopLe.isSDT(sdt));
    }

    @Override
    public void xuat() {
        System.out.printf("Họ tên: %-20s | GT: %-3s | NS: %-10s | SĐT: %-10s | ĐC: %-16s",
                hoTen, getGioiTinh(), ngaySinh, sdt, diaChi);
    }

    public abstract String getVaiTro();
}
