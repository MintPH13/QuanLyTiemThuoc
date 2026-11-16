package QuanLyTiemThuoc;

import java.util.Arrays;
import java.util.Scanner;

public class NhanVien implements INhapXuat {
    private String manv;
    private String hoTen;
    private String diaChi;
    private int gioiTinh; // 0: Nam, 1: Nữ
    private String ngaySinh;
    private String sdt;
    private String ngayVaoLam;
    private String email;
    private double luongCoBan;
    private double phuCap;
    public static int tongSoNhanVien = 0;

    public NhanVien() {
        tongSoNhanVien++;
    }

    public NhanVien(String manv, String hoTen, String diaChi, int gioiTinh, String ngaySinh,
                    String sdt, String ngayVaoLam, String email, double luongCoBan, double phuCap) {
        this.manv = manv;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.ngayVaoLam = ngayVaoLam;
        this.email = email;
        this.luongCoBan = luongCoBan;
        this.phuCap = phuCap;
        tongSoNhanVien++;
    }

    public String getManv() { return manv; }
    public void setManv(String manv) { this.manv = manv; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getDiaChi() { return diaChi; }
    public int getGioiTinhInt() { return gioiTinh; }
    public String getGioiTinh() { return gioiTinh == 1 ? "Nữ" : "Nam"; }
    public String getNgaySinh() { return ngaySinh; }
    public String getSdt() { return sdt; }
    public String getEmail() { return email; }

    public double tinhLuong() {
        return luongCoBan + phuCap;
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
        System.out.printf("Mã NV: %-5s | Họ tên: %-20s | GT: %-3s | NS: %-10s | SĐT: %-10s | ĐC: %-16s | Lương: %,11.0f VNĐ\n",
                manv, hoTen, getGioiTinh(), ngaySinh, sdt, diaChi, tinhLuong());
    }

    public String toFileString() {
        return manv + ";" + hoTen + ";" + diaChi + ";" + gioiTinh + ";" + ngaySinh + ";"
                + sdt + ";" + ngayVaoLam + ";" + email + ";" + luongCoBan + ";" + phuCap;
    }

    // ================= CÁC THAO TÁC QUẢN LÝ NHÂN VIÊN =================

    public static void menuQuanLy(Scanner sc, DanhSachNhanVien dsnv) {
        int chon;
        do {
            System.out.println("\n--- QUẢN LÝ NHÂN VIÊN ---");
            System.out.println("1. Thêm nhân viên mới");
            System.out.println("2. Xuất danh sách nhân viên");
            System.out.println("3. Tìm kiếm nhân viên");
            System.out.println("4. Sửa thông tin nhân viên");
            System.out.println("5. Xóa nhân viên");
            System.out.println("6. Sắp xếp danh sách nhân viên theo tên");
            System.out.println("0. Quay lại menu chính");
            System.out.print("Chọn: ");
            try { chon = Integer.parseInt(sc.nextLine()); }
            catch (Exception e) { chon = -1; }

            switch (chon) {
                case 1 -> themMoi(sc, dsnv);
                case 2 -> xuatDanhSach(dsnv);
                case 3 -> timKiem(sc, dsnv);
                case 4 -> sua(sc, dsnv);
                case 5 -> xoa(sc, dsnv);
                case 6 -> {
                    sapXepTheoTen(dsnv);
                    xuatDanhSach(dsnv);
                }
                case 0 -> System.out.println("Trở về menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    public static NhanVien timTheoMa(DanhSachNhanVien dsnv, String ma) {
        for (NhanVien nv : dsnv.getDs()) {
            if (nv.getManv().equalsIgnoreCase(ma)) return nv;
        }
        return null;
    }

    public static void xuatDanhSach(DanhSachNhanVien dsnv) {
        NhanVien[] ds = dsnv.getDs();
        System.out.println("\n========================= DANH SÁCH NHÂN VIÊN (" + ds.length + " NV) =========================");
        for (NhanVien nv : ds) nv.xuat();
    }

    public static void themMoi(Scanner sc, DanhSachNhanVien dsnv) {
        NhanVien nv = new NhanVien();
        nv.nhap(sc);
        while (timTheoMa(dsnv, nv.getManv()) != null) {
            System.out.print("Mã NV đã tồn tại! Nhập lại: ");
            nv.setManv(sc.nextLine());
        }
        NhanVien[] ds = Arrays.copyOf(dsnv.getDs(), dsnv.getDs().length + 1);
        ds[ds.length - 1] = nv;
        dsnv.setDs(ds);
        System.out.println("=> Đã thêm nhân viên thành công.");
    }

    public static void sua(Scanner sc, DanhSachNhanVien dsnv) {
        System.out.print("Nhập mã NV cần sửa: ");
        String ma = sc.nextLine();
        NhanVien nv = timTheoMa(dsnv, ma);
        if (nv == null) { System.out.println("Không tìm thấy!"); return; }
        nv.nhapCapNhat(sc);
        System.out.println("=> Đã cập nhật nhân viên.");
    }

    public static void xoa(Scanner sc, DanhSachNhanVien dsnv) {
        System.out.print("Nhập mã NV cần xóa: ");
        String ma = sc.nextLine();
        NhanVien[] ds = dsnv.getDs();
        int idx = -1;
        for (int i = 0; i < ds.length; i++) {
            if (ds[i].getManv().equalsIgnoreCase(ma)) { idx = i; break; }
        }
        if (idx != -1) {
            for (int i = idx; i < ds.length - 1; i++) ds[i] = ds[i + 1];
            dsnv.setDs(Arrays.copyOf(ds, ds.length - 1));
            System.out.println("=> Đã xóa nhân viên " + ma);
        } else {
            System.out.println("Không tìm thấy mã NV!");
        }
    }

    public static void timKiem(Scanner sc, DanhSachNhanVien dsnv) {
        System.out.print("Nhập mã hoặc tên NV cần tìm: ");
        String tk = sc.nextLine().toLowerCase();
        for (NhanVien nv : dsnv.getDs()) {
            if (nv.getManv().toLowerCase().contains(tk) || nv.getHoTen().toLowerCase().contains(tk)) {
                nv.xuat();
            }
        }
    }

    public static void sapXepTheoTen(DanhSachNhanVien dsnv) {
        Arrays.sort(dsnv.getDs(), (a, b) -> a.getHoTen().compareToIgnoreCase(b.getHoTen()));
        System.out.println("=> Đã sắp xếp danh sách nhân viên theo tên!");
    }
}