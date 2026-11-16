package QuanLyTiemThuoc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Scanner;

public class PhieuNhap implements INhapXuat {
    private String mapn;
    private String manv;
    private String mancc;
    private String ngaynhap;
    private double tongtien;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

    // ================= CÁC THAO TÁC QUẢN LÝ PHIẾU NHẬP =================

    public static void menuQuanLy(Scanner sc, DanhSachPhieuNhap dspn, DanhSachChiTietPhieuNhap dsctpn,
                                  DanhSachThuoc dst, DanhSachNhanVien dsnv) {
        int chon;
        do {
            System.out.println("\n--- QUẢN LÝ NHẬP HÀNG ---");
            System.out.println("1. Lập phiếu nhập thuốc (Tự động cộng tồn kho)");
            System.out.println("2. Xem danh sách phiếu nhập & Chi tiết phiếu nhập");
            System.out.println("0. Quay lại menu chính");
            System.out.print("Chọn: ");
            try { chon = Integer.parseInt(sc.nextLine()); }
            catch (Exception e) { chon = -1; }

            switch (chon) {
                case 1 -> themPhieuNhapMoi(sc, dspn, dsctpn, dst, dsnv);
                case 2 -> {
                    xuatDanhSach(dspn);
                    System.out.print("Bạn có muốn xem Chi tiết phiếu nhập không? (1. Xem tất cả CTPN, 2. Tìm theo mã PN, 0. Bỏ qua): ");
                    String sub = sc.nextLine();
                    if (sub.equals("1")) xuatChiTiet(dsctpn);
                    else if (sub.equals("2")) timChiTietTheoMaPN(sc, dsctpn);
                }
                case 0 -> System.out.println("Trở về menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    public static PhieuNhap timTheoMa(DanhSachPhieuNhap dspn, String mapn) {
        for (PhieuNhap pn : dspn.getDs()) {
            if (pn.getMapn().equalsIgnoreCase(mapn)) return pn;
        }
        return null;
    }

    public static void xuatDanhSach(DanhSachPhieuNhap dspn) {
        PhieuNhap[] ds = dspn.getDs();
        if (ds.length == 0) {
            System.out.println("Danh sách phiếu nhập trống!");
            return;
        }
        System.out.println("\n========================= DANH SÁCH PHIẾU NHẬP (" + ds.length + " PN) =========================");
        for (PhieuNhap pn : ds) pn.xuat();
    }

    public static void xuatChiTiet(DanhSachChiTietPhieuNhap dsctpn) {
        ChiTietPhieuNhap[] ds = dsctpn.getDs();
        if (ds.length == 0) {
            System.out.println("Danh sách chi tiết phiếu nhập trống!");
            return;
        }
        System.out.println("\n========================= CHI TIẾT PHIẾU NHẬP (" + ds.length + " dòng) =========================");
        for (ChiTietPhieuNhap ct : ds) ct.xuat();
    }

    public static void timChiTietTheoMaPN(Scanner sc, DanhSachChiTietPhieuNhap dsctpn) {
        System.out.print("Nhập mã PN cần xem chi tiết: ");
        String ma = sc.nextLine();
        for (ChiTietPhieuNhap ct : dsctpn.getDs()) {
            if (ct.getMapn().equalsIgnoreCase(ma)) ct.xuat();
        }
    }

    public static void themPhieuNhapMoi(Scanner sc, DanhSachPhieuNhap dspn, DanhSachChiTietPhieuNhap dsctpn,
                                        DanhSachThuoc dst, DanhSachNhanVien dsnv) {
        System.out.println("\n=== THÊM PHIẾU NHẬP THUỐC ===");
        System.out.print("Nhập mã phiếu nhập (VD: PN23): ");
        String mapn = sc.nextLine();
        if (timTheoMa(dspn, mapn) != null) {
            System.out.println("Mã phiếu nhập đã tồn tại!");
            return;
        }
        System.out.print("Nhập mã nhân viên (VD: NV01): ");
        String manv = sc.nextLine();
        if (NhanVien.timTheoMa(dsnv, manv) == null) {
            System.out.println("Không tìm thấy nhân viên!");
            return;
        }
        System.out.print("Nhập mã nhà cung cấp (VD: NCC01): ");
        String mancc = sc.nextLine();
        System.out.print("Nhập ngày nhập (dd/MM/yyyy): ");
        String ngay = sc.nextLine();

        double tong = 0;
        System.out.print("Nhập số lượng chi tiết phiếu nhập: ");
        int k = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < k; i++) {
            System.out.println("\n-- Chi tiết nhập " + (i + 1) + " --");
            System.out.print("Nhập mã thuốc (VD: T01..T12): ");
            String mat = sc.nextLine();
            Thuoc sp = Thuoc.timTheoMa(dst, mat);
            if (sp == null) {
                System.out.println("Thuốc không tồn tại!");
                i--;
                continue;
            }
            System.out.print("Nhập số lượng nhập thêm: ");
            int sl = Integer.parseInt(sc.nextLine());
            double gn = sp.getGiagoc();
            double tt = sl * gn;

            ChiTietPhieuNhap[] arrCT = Arrays.copyOf(dsctpn.getDs(), dsctpn.getDs().length + 1);
            arrCT[arrCT.length - 1] = new ChiTietPhieuNhap(mapn, mat, sl, gn, tt);
            dsctpn.setDs(arrCT);

            Thuoc.capNhatSLSP(dst, mat, +sl);
            tong += tt;
        }

        PhieuNhap[] arrPN = Arrays.copyOf(dspn.getDs(), dspn.getDs().length + 1);
        arrPN[arrPN.length - 1] = new PhieuNhap(mapn, manv, mancc, ngay, tong);
        dspn.setDs(arrPN);

        System.out.printf("=> Đã thêm phiếu nhập và cộng tồn kho thành công! Tổng tiền: %,.2f VNĐ\n", tong);
    }

    // Các hàm tính chi phí nhập phục vụ thống kê
    public static double tinhTongChiTheoNgay(DanhSachPhieuNhap dspn, String ngayStr) {
        double tong = 0;
        try {
            LocalDate target = LocalDate.parse(ngayStr, FMT);
            for (PhieuNhap pn : dspn.getDs()) {
                if (LocalDate.parse(pn.getNgaynhap(), FMT).equals(target)) tong += pn.getTongtien();
            }
        } catch (Exception e) {}
        return tong;
    }

    public static double tinhTongChiTheoTuan(DanhSachPhieuNhap dspn, String ngayTrongTuanStr) {
        double tong = 0;
        try {
            LocalDate date = LocalDate.parse(ngayTrongTuanStr, FMT);
            LocalDate dauTuan = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate cuoiTuan = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            for (PhieuNhap pn : dspn.getDs()) {
                LocalDate d = LocalDate.parse(pn.getNgaynhap(), FMT);
                if (!d.isBefore(dauTuan) && !d.isAfter(cuoiTuan)) tong += pn.getTongtien();
            }
        } catch (Exception e) {}
        return tong;
    }

    public static double tinhTongChiTheoThang(DanhSachPhieuNhap dspn, int thang, int nam) {
        double tong = 0;
        for (PhieuNhap pn : dspn.getDs()) {
            try {
                LocalDate d = LocalDate.parse(pn.getNgaynhap(), FMT);
                if (d.getMonthValue() == thang && d.getYear() == nam) tong += pn.getTongtien();
            } catch (Exception e) {}
        }
        return tong;
    }

    public static double tinhTongChiTheoQuy(DanhSachPhieuNhap dspn, int quy, int nam) {
        double tong = 0;
        for (PhieuNhap pn : dspn.getDs()) {
            try {
                LocalDate d = LocalDate.parse(pn.getNgaynhap(), FMT);
                int q = (d.getMonthValue() - 1) / 3 + 1;
                if (d.getYear() == nam && q == quy) tong += pn.getTongtien();
            } catch (Exception e) {}
        }
        return tong;
    }

    public static double tinhTongChiTheoNam(DanhSachPhieuNhap dspn, int nam) {
        double tong = 0;
        for (PhieuNhap pn : dspn.getDs()) {
            try {
                LocalDate d = LocalDate.parse(pn.getNgaynhap(), FMT);
                if (d.getYear() == nam) tong += pn.getTongtien();
            } catch (Exception e) {}
        }
        return tong;
    }
}