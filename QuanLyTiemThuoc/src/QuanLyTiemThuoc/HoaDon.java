package QuanLyTiemThuoc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Scanner;

public class HoaDon implements INhapXuat {
    private String mahd;
    private String manv;
    private String makh;
    private String ngaylap;
    private double tongtien;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

    // ================= CÁC THAO TÁC QUẢN LÝ HÓA ĐƠN =================

    public static void menuQuanLy(Scanner sc, DanhSachHoaDon dshd, DanhSachChiTietHoaDon dscthd,
                                  DanhSachThuoc dst, DanhSachNhanVien dsnv, DanhSachKhachHang dskh) {
        int chon;
        do {
            System.out.println("\n--- QUẢN LÝ HÓA ĐƠN & BÁN HÀNG ---");
            System.out.println("1. Lập hóa đơn bán thuốc (Tự động trừ tồn kho)");
            System.out.println("2. Xem danh sách hóa đơn & Chi tiết hóa đơn");
            System.out.println("0. Quay lại menu chính");
            System.out.print("Chọn: ");
            try { chon = Integer.parseInt(sc.nextLine()); }
            catch (Exception e) { chon = -1; }

            switch (chon) {
                case 1 -> themHoaDonMoi(sc, dshd, dscthd, dst, dsnv, dskh);
                case 2 -> {
                    xuatDanhSach(dshd);
                    System.out.print("Bạn có muốn xem Chi tiết hóa đơn không? (1. Xem tất cả CTHD, 2. Tìm theo mã HĐ, 0. Bỏ qua): ");
                    String sub = sc.nextLine();
                    if (sub.equals("1")) xuatChiTiet(dscthd);
                    else if (sub.equals("2")) timChiTietTheoMaHD(sc, dscthd);
                }
                case 0 -> System.out.println("Trở về menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    public static HoaDon timTheoMa(DanhSachHoaDon dshd, String mahd) {
        for (HoaDon h : dshd.getDs()) {
            if (h.getMahd().equalsIgnoreCase(mahd)) return h;
        }
        return null;
    }

    public static void xuatDanhSach(DanhSachHoaDon dshd) {
        HoaDon[] ds = dshd.getDs();
        if (ds.length == 0) {
            System.out.println("Danh sách hóa đơn trống!");
            return;
        }
        System.out.println("\n========================= DANH SÁCH HÓA ĐƠN (" + ds.length + " HĐ) =========================");
        for (HoaDon h : ds) h.xuat();
    }

    public static void xuatChiTiet(DanhSachChiTietHoaDon dscthd) {
        ChiTietHoaDon[] ds = dscthd.getDs();
        if (ds.length == 0) {
            System.out.println("Danh sách chi tiết HĐ trống!");
            return;
        }
        System.out.println("\n========================= CHI TIẾT HÓA ĐƠN (" + ds.length + " dòng) =========================");
        for (ChiTietHoaDon ct : ds) ct.xuat();
    }

    public static void timChiTietTheoMaHD(Scanner sc, DanhSachChiTietHoaDon dscthd) {
        System.out.print("Nhập mã HĐ cần xem chi tiết: ");
        String ma = sc.nextLine();
        int count = 0;
        for (ChiTietHoaDon ct : dscthd.getDs()) {
            if (ct.getMahd().equalsIgnoreCase(ma)) {
                ct.xuat();
                count++;
            }
        }
        if (count == 0) System.out.println("Không tìm thấy chi tiết cho mã HĐ: " + ma);
    }

    public static void themHoaDonMoi(Scanner sc, DanhSachHoaDon dshd, DanhSachChiTietHoaDon dscthd,
                                     DanhSachThuoc dst, DanhSachNhanVien dsnv, DanhSachKhachHang dskh) {
        System.out.println("\n=== THÊM HÓA ĐƠN BÁN THUỐC ===");
        System.out.print("Nhập mã hóa đơn (VD: HD23): ");
        String mahd = sc.nextLine();
        if (timTheoMa(dshd, mahd) != null) {
            System.out.println("Mã hóa đơn đã tồn tại!");
            return;
        }

        System.out.print("Nhập mã nhân viên (VD: NV01): ");
        String manv = sc.nextLine();
        if (NhanVien.timTheoMa(dsnv, manv) == null) {
            System.out.println("Không tìm thấy nhân viên!");
            return;
        }

        System.out.print("Nhập mã khách hàng (VD: KH01): ");
        String makh = sc.nextLine();
        KhachHang kh = KhachHang.timTheoMa(dskh, makh);
        if (kh == null) {
            System.out.println("Không tìm thấy khách hàng!");
            return;
        }

        System.out.print("Nhập ngày lập (dd/MM/yyyy): ");
        String ngaylap = sc.nextLine();
        if (!KtraTinhHopLe.isDate(ngaylap)) {
            System.out.println("Định dạng ngày không hợp lệ!");
            return;
        }

        double tongtien = 0;
        System.out.print("Nhập số lượng chi tiết hóa đơn: ");
        int k = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < k; i++) {
            System.out.println("\n-- Chi tiết " + (i + 1) + " --");
            System.out.print("Nhập mã thuốc (VD: T01..T12): ");
            String mathuoc = sc.nextLine();

            Thuoc sp = Thuoc.timTheoMa(dst, mathuoc);
            if (sp == null) {
                System.out.println("Thuốc không tồn tại!");
                i--;
                continue;
            }

            double dongia = sp.getDongia();
            System.out.print("Nhập số lượng mua (Tồn kho hiện tại: " + sp.getSoluong() + "): ");
            int soluong = Integer.parseInt(sc.nextLine());

            if (soluong <= 0 || soluong > sp.getSoluong()) {
                System.out.println("Số lượng không hợp lệ hoặc vượt quá tồn kho!");
                i--;
                continue;
            }

            double thanhtien = soluong * dongia;
            ChiTietHoaDon[] arrCT = Arrays.copyOf(dscthd.getDs(), dscthd.getDs().length + 1);
            arrCT[arrCT.length - 1] = new ChiTietHoaDon(mahd, mathuoc, soluong, dongia, thanhtien);
            dscthd.setDs(arrCT);

            Thuoc.capNhatSLSP(dst, mathuoc, -soluong);
            tongtien += thanhtien;
        }

        kh.congChiTieu(tongtien);
        HoaDon[] arrHD = Arrays.copyOf(dshd.getDs(), dshd.getDs().length + 1);
        arrHD[arrHD.length - 1] = new HoaDon(mahd, manv, makh, ngaylap, tongtien);
        dshd.setDs(arrHD);

        System.out.println("=> Đã thêm hóa đơn thành công.");
        System.out.printf("TỔNG TIỀN: %,.2f VNĐ\n", tongtien);
    }

    // Các hàm tính doanh thu phục vụ thống kê
    public static double tinhTongThuTheoNgay(DanhSachHoaDon dshd, String ngayStr) {
        double tong = 0;
        try {
            LocalDate target = LocalDate.parse(ngayStr, FMT);
            for (HoaDon h : dshd.getDs()) {
                if (LocalDate.parse(h.getNgaylap(), FMT).equals(target)) tong += h.getTongtien();
            }
        } catch (Exception e) {}
        return tong;
    }

    public static double tinhTongThuTheoTuan(DanhSachHoaDon dshd, String ngayTrongTuanStr) {
        double tong = 0;
        try {
            LocalDate date = LocalDate.parse(ngayTrongTuanStr, FMT);
            LocalDate dauTuan = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate cuoiTuan = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            for (HoaDon h : dshd.getDs()) {
                LocalDate d = LocalDate.parse(h.getNgaylap(), FMT);
                if (!d.isBefore(dauTuan) && !d.isAfter(cuoiTuan)) tong += h.getTongtien();
            }
        } catch (Exception e) {}
        return tong;
    }

    public static double tinhTongThuTheoThang(DanhSachHoaDon dshd, int thang, int nam) {
        double tong = 0;
        for (HoaDon h : dshd.getDs()) {
            try {
                LocalDate d = LocalDate.parse(h.getNgaylap(), FMT);
                if (d.getMonthValue() == thang && d.getYear() == nam) tong += h.getTongtien();
            } catch (Exception e) {}
        }
        return tong;
    }

    public static double tinhTongThuTheoQuy(DanhSachHoaDon dshd, int quy, int nam) {
        double tong = 0;
        for (HoaDon h : dshd.getDs()) {
            try {
                LocalDate d = LocalDate.parse(h.getNgaylap(), FMT);
                int q = (d.getMonthValue() - 1) / 3 + 1;
                if (d.getYear() == nam && q == quy) tong += h.getTongtien();
            } catch (Exception e) {}
        }
        return tong;
    }

    public static double tinhTongThuTheoNam(DanhSachHoaDon dshd, int nam) {
        double tong = 0;
        for (HoaDon h : dshd.getDs()) {
            try {
                LocalDate d = LocalDate.parse(h.getNgaylap(), FMT);
                if (d.getYear() == nam) tong += h.getTongtien();
            } catch (Exception e) {}
        }
        return tong;
    }
}