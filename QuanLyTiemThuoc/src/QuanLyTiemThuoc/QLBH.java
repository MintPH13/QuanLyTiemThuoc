package QuanLyTiemThuoc;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class QLBH {
    public static void themHoaDonMoi(Scanner sc, DanhSachHoaDon dsHoaDon, DanhSachChiTietHoaDon dsChiTietHoaDon,
                                     DanhSachThuoc dsThuoc, DanhSachNhanVien dsNhanVien, DanhSachKhachHang dsKhachHang) {
        System.out.println("\n=== THÊM HÓA ĐƠN BÁN THUỐC ===");
        System.out.print("Nhập mã hóa đơn (VD: HD23): ");
        String mahd = sc.nextLine();
        if (dsHoaDon.timtheomahd(mahd) != null) {
            System.out.println("Mã hóa đơn đã tồn tại!");
            return;
        }

        System.out.print("Nhập mã nhân viên (VD: NV01): ");
        String manv = sc.nextLine();
        if (dsNhanVien.timTheoMa(manv) == null) {
            System.out.println("Không tìm thấy nhân viên!");
            return;
        }

        System.out.print("Nhập mã khách hàng (VD: KH01): ");
        String makh = sc.nextLine();
        KhachHang kh = dsKhachHang.timtheomakh(makh);
        if (kh == null) {
            System.out.println("Không tìm thấy khách hàng!");
            return;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.print("Nhập ngày lập (dd/MM/yyyy): ");
        String ngaylap = sc.nextLine();
        try {
            LocalDate.parse(ngaylap, fmt);
        } catch (Exception e) {
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

            Thuoc sp1 = dsThuoc.timTheoMa(mathuoc);
            if (sp1 == null) {
                System.out.println("Thuốc không tồn tại!");
                i--;
                continue;
            }

            double dongia = sp1.getDongia();
            System.out.print("Nhập số lượng mua (Tồn kho hiện tại: " + sp1.getSoluong() + "): ");
            int soluong = Integer.parseInt(sc.nextLine());

            if (soluong <= 0 || soluong > sp1.getSoluong()) {
                System.out.println("Số lượng không hợp lệ hoặc vượt quá tồn kho!");
                i--;
                continue;
            }

            double thanhtien = soluong * dongia;
            ChiTietHoaDon ct1 = new ChiTietHoaDon(mahd, mathuoc, soluong, dongia, thanhtien);
            dsChiTietHoaDon.them(ct1);

            // Cập nhật trừ số lượng thuốc bán ra
            dsThuoc.capNhatSLSP(mathuoc, -soluong);
            tongtien += thanhtien;
        }

        kh.congChiTieu(tongtien);
        HoaDon hd1 = new HoaDon(mahd, manv, makh, ngaylap, tongtien);
        dsHoaDon.them(hd1);
        System.out.println("=> Đã thêm hóa đơn thành công.");

        System.out.println("\n===== HÓA ĐƠN VỪA TẠO =====");
        System.out.println("Mã HD: " + mahd);
        System.out.println("Mã NV: " + manv);
        System.out.println("Mã KH: " + makh);
        System.out.println("Ngày lập: " + ngaylap);
        System.out.println("-----------------------------");
        System.out.println("Chi tiết hàng:");
        for (int i = 0; i < dsChiTietHoaDon.getSoLuong(); i++) {
            ChiTietHoaDon ct = dsChiTietHoaDon.getDs()[i];
            if (ct.getMahd().equalsIgnoreCase(mahd)) {
                System.out.printf(" - Thuốc: %s | SL: %d | Đơn giá: %,.2f | Thành tiền: %,.2f\n",
                        ct.getMathuoc(), ct.getSoluong(), ct.getDongia(), ct.getThanhtien());
            }
        }
        System.out.println("-----------------------------");
        System.out.printf("TỔNG TIỀN: %,.2f VNĐ\n", tongtien);
        System.out.println("================================");
    }

    public static void themPhieuNhapMoi(Scanner sc, DanhSachPhieuNhap dsPhieuNhap, DanhSachChiTietPhieuNhap dsChiTietPhieuNhap,
                                        DanhSachThuoc dsThuoc, DanhSachNhanVien dsNhanVien) {
        System.out.println("\n=== THÊM PHIẾU NHẬP THUỐC ===");
        System.out.print("Nhập mã phiếu nhập (VD: PN23): ");
        String mapn = sc.nextLine();
        if (dsPhieuNhap.timTheoMa(mapn) != null) {
            System.out.println("Mã phiếu nhập đã tồn tại!");
            return;
        }
        System.out.print("Nhập mã nhân viên (VD: NV01): ");
        String manv = sc.nextLine();
        if (dsNhanVien.timTheoMa(manv) == null) {
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
            Thuoc sp = dsThuoc.timTheoMa(mat);
            if (sp == null) {
                System.out.println("Thuốc không tồn tại!");
                i--;
                continue;
            }
            System.out.print("Nhập số lượng nhập thêm: ");
            int sl = Integer.parseInt(sc.nextLine());
            double gn = sp.getGiagoc();
            double tt = sl * gn;
            dsChiTietPhieuNhap.them(new ChiTietPhieuNhap(mapn, mat, sl, gn, tt));
            // Cập nhật cộng số lượng thuốc vào kho
            dsThuoc.capNhatSLSP(mat, +sl);
            tong += tt;
        }
        dsPhieuNhap.them(new PhieuNhap(mapn, manv, mancc, ngay, tong));
        System.out.printf("=> Đã thêm phiếu nhập và cộng tồn kho thành công! Tổng tiền: %,.2f VNĐ\n", tong);
    }

    public static void thongKeDoanhThu(Scanner sc, DanhSachHoaDon dsHoaDon, DanhSachPhieuNhap dsPhieuNhap) {
        System.out.println("\n1. Thống kê Thu - Chi - Lợi nhuận từ ngày A đến ngày B");
        System.out.println("2. Thống kê Thu - Chi - Lợi nhuận theo Quý trong năm");
        System.out.print("Chọn: ");
        String opt = sc.nextLine();
        if (opt.equals("1")) {
            System.out.print("Nhập ngày bắt đầu (dd/MM/yyyy): ");
            String tu = sc.nextLine();
            System.out.print("Nhập ngày kết thúc (dd/MM/yyyy): ");
            String den = sc.nextLine();
            double thu = dsHoaDon.tinhTongThuTheoKhoang(tu, den);
            double chi = dsPhieuNhap.tinhTongChiTheoKhoang(tu, den);
            System.out.printf("=> Tổng Thu: %,.2f | Tổng Chi: %,.2f | Lợi Nhuận: %,.2f VNĐ\n", thu, chi, thu - chi);
        } else if (opt.equals("2")) {
            System.out.print("Nhập năm (VD: 2025 hoặc 2026): ");
            int nam = Integer.parseInt(sc.nextLine());
            for (int q = 1; q <= 4; q++) {
                double thu = dsHoaDon.tinhTongThuTheoQuy(q, nam);
                double chi = dsPhieuNhap.tinhTongChiTheoQuy(q, nam);
                System.out.printf(" - Quý %d/%d: Tổng Thu = %,14.2f | Tổng Chi = %,14.2f | Lợi nhuận = %,14.2f VNĐ\n",
                        q, nam, thu, chi, thu - chi);
            }
        }
    }
}
