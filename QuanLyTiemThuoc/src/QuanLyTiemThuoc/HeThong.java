package QuanLyTiemThuoc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HeThong {
    private DanhSachThuoc dst = new DanhSachThuoc();
    private DanhSachNhanVien dsnv = new DanhSachNhanVien();
    private DanhSachKhachHang dskh = new DanhSachKhachHang();
    private DanhSachHoaDon dshd = new DanhSachHoaDon();
    private DanhSachChiTietHoaDon dscthd = new DanhSachChiTietHoaDon();
    private DanhSachPhieuNhap dspn = new DanhSachPhieuNhap();
    private DanhSachChiTietPhieuNhap dsctpn = new DanhSachChiTietPhieuNhap();

    private Scanner sc = new Scanner(System.in);

    public void menuChinh() {
        // Tự động nạp dữ liệu từ các file khi hệ thống khởi động
        dst.docFile();
        dsnv.docFile();
        dskh.docFile();
        dshd.docFile();
        dscthd.docFile();
        dspn.docFile();
        dsctpn.docFile();

        int chon;
        do {
            System.out.println("\n================ HỆ THỐNG QUẢN LÝ TIỆM THUỐC ================");
            System.out.println("1. Quản lý Thuốc");
            System.out.println("2. Quản lý Nhân viên");
            System.out.println("3. Quản lý Khách hàng");
            System.out.println("4. Quản lý Nhập hàng");
            System.out.println("5. Quản lý Hóa đơn & Bán hàng");
            System.out.println("6. Thống kê & Báo cáo");
            System.out.println("7. Xuất dữ liệu ra 4 file riêng biệt (.txt)");
            System.out.println("0. Lưu dữ liệu & Thoát chương trình");
            System.out.print("Chọn chức năng: ");

            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                chon = -1;
            }

            switch (chon) {
                case 1 -> menuQuanLyThuoc();
                case 2 -> menuQuanLyNhanVien();
                case 3 -> menuQuanLyKhachHang();
                case 4 -> menuQuanLyNhapHang();
                case 5 -> menuQuanLyHoaDon();
                case 6 -> {
                    dst.thongKeNhieuKhoa();
                    QLBH.thongKeDoanhThu(sc, dshd, dspn);
                }
                case 7 -> xuat4FileRiengBiet();
                case 0 -> {
                    // Lưu dữ liệu toàn bộ hệ thống trước khi thoát
                    luuToanBoDuLieu();
                    System.out.println("Đã lưu toàn bộ dữ liệu. Thoát chương trình thành công!");
                }
                default -> System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
            }
        } while (chon != 0);
    }

    // ================= SUB-MENUS CHI TIẾT =================

    private void menuQuanLyThuoc() {
        int chon;
        do {
            System.out.println("\n--- QUẢN LÝ THUỐC ---");
            System.out.println("1. Nhập thêm thuốc mới");
            System.out.println("2. Xuất danh sách thuốc");
            System.out.println("3. Tìm kiếm thuốc (theo Mã / Tên / Khoảng giá)");
            System.out.println("4. Sửa thông tin thuốc");
            System.out.println("5. Xóa thuốc");
            System.out.println("6. Sắp xếp danh sách thuốc theo tên");
            System.out.println("0. Quay lại menu chính");
            System.out.print("Chọn: ");
            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                chon = -1;
            }

            switch (chon) {
                case 1 -> {
                    System.out.print("Chọn (1. Thêm 1 thuốc, 2. Thêm k thuốc): ");
                    String sub = sc.nextLine();
                    if (sub.equals("1")) dst.them1PhanTu();
                    else if (sub.equals("2")) dst.themKPhanTu();
                    else System.out.println("Lựa chọn không hợp lệ!");
                }
                case 2 -> dst.xuat();
                case 3 -> dst.timKiemNhieuKhoa();
                case 4 -> dst.suaTheoMa();
                case 5 -> dst.xoaTheoMa();
                case 6 -> {
                    dst.sapXepTheoTen();
                    dst.xuat();
                }
                case 0 -> System.out.println("Trở về menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    private void menuQuanLyNhanVien() {
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
            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                chon = -1;
            }

            switch (chon) {
                case 1 -> dsnv.themMoi();
                case 2 -> dsnv.xuat();
                case 3 -> dsnv.timKiem();
                case 4 -> dsnv.sua();
                case 5 -> dsnv.xoa();
                case 6 -> {
                    dsnv.sapXepTheoTen();
                    dsnv.xuat();
                }
                case 0 -> System.out.println("Trở về menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    private void menuQuanLyKhachHang() {
        int chon;
        do {
            System.out.println("\n--- QUẢN LÝ KHÁCH HÀNG ---");
            System.out.println("1. Thêm khách hàng mới");
            System.out.println("2. Xuất danh sách khách hàng");
            System.out.println("3. Tìm kiếm khách hàng");
            System.out.println("4. Sửa thông tin khách hàng");
            System.out.println("5. Xóa khách hàng");
            System.out.println("6. Sắp xếp danh sách khách hàng theo tên");
            System.out.println("0. Quay lại menu chính");
            System.out.print("Chọn: ");
            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                chon = -1;
            }

            switch (chon) {
                case 1 -> dskh.themMoi();
                case 2 -> dskh.xuat();
                case 3 -> dskh.timKiem();
                case 4 -> dskh.sua();
                case 5 -> dskh.xoa();
                case 6 -> {
                    dskh.sapXepTheoTen();
                    dskh.xuat();
                }
                case 0 -> System.out.println("Trở về menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    private void menuQuanLyNhapHang() {
        int chon;
        do {
            System.out.println("\n--- QUẢN LÝ NHẬP HÀNG ---");
            System.out.println("1. Lập phiếu nhập thuốc (Tự động cộng tồn kho)");
            System.out.println("2. Xem danh sách phiếu nhập & Chi tiết phiếu nhập");
            System.out.println("0. Quay lại menu chính");
            System.out.print("Chọn: ");
            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                chon = -1;
            }

            switch (chon) {
                case 1 -> QLBH.themPhieuNhapMoi(sc, dspn, dsctpn, dst, dsnv);
                case 2 -> {
                    dspn.xuat();
                    System.out.print("Bạn có muốn xem Chi tiết phiếu nhập không? (1. Xem tất cả CTPN, 2. Tìm theo mã PN, 0. Bỏ qua): ");
                    String sub = sc.nextLine();
                    if (sub.equals("1")) dsctpn.xuat();
                    else if (sub.equals("2")) dsctpn.timTheoMaPN();
                }
                case 0 -> System.out.println("Trở về menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    private void menuQuanLyHoaDon() {
        int chon;
        do {
            System.out.println("\n--- QUẢN LÝ HÓA ĐƠN & BÁN HÀNG ---");
            System.out.println("1. Lập hóa đơn bán thuốc (Tự động trừ tồn kho)");
            System.out.println("2. Xem danh sách hóa đơn & Chi tiết hóa đơn");
            System.out.println("0. Quay lại menu chính");
            System.out.print("Chọn: ");
            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                chon = -1;
            }

            switch (chon) {
                case 1 -> QLBH.themHoaDonMoi(sc, dshd, dscthd, dst, dsnv, dskh);
                case 2 -> {
                    dshd.xuat();
                    System.out.print("Bạn có muốn xem Chi tiết hóa đơn không? (1. Xem tất cả CTHD, 2. Tìm theo mã HĐ, 0. Bỏ qua): ");
                    String sub = sc.nextLine();
                    if (sub.equals("1")) dscthd.xuat();
                    else if (sub.equals("2")) dscthd.timTheoMaHD();
                }
                case 0 -> System.out.println("Trở về menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    // ================= XUẤT VÀ LƯU DỮ LIỆU =================

    private void luuToanBoDuLieu() {
        dst.ghiFile();
        dsnv.ghiFile();
        dskh.ghiFile();
        dshd.ghiFile();
        dscthd.ghiFile();
        dspn.ghiFile();
        dsctpn.ghiFile();
    }

    private void xuat4FileRiengBiet() {
        // Lưu dữ liệu cập nhật hiện tại
        luuToanBoDuLieu();

        // 1. File nhanvien.txt
        dsnv.ghiFile();

        // 2. File khachhang.txt
        dskh.ghiFile();

        // 3. File nhaphang.txt (Gộp Phếu nhập và Chi tiết phiếu nhập)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("nhaphang.txt"))) {
            bw.write("=== PHIẾU NHẬP ===\n");
            for (int i = 0; i < dspn.getSoLuong(); i++) {
                bw.write(dspn.getDs()[i].toFileString());
                bw.newLine();
            }
            bw.write("=== CHI TIẾT PHIẾU NHẬP ===\n");
            for (int i = 0; i < dsctpn.getSoLuong(); i++) {
                bw.write(dsctpn.getDs()[i].toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi xuất file nhaphang.txt: " + e.getMessage());
        }

        // 4. File hoadon.txt (Gộp Hóa đơn và Chi tiết hóa đơn)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("hoadon.txt"))) {
            bw.write("=== HÓA ĐƠN ===\n");
            for (int i = 0; i < dshd.getN(); i++) {
                bw.write(dshd.getHd()[i].toFileString());
                bw.newLine();
            }
            bw.write("=== CHI TIẾT HÓA ĐƠN ===\n");
            for (int i = 0; i < dscthd.getSoLuong(); i++) {
                bw.write(dscthd.getDs()[i].toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi xuất file hoadon.txt: " + e.getMessage());
        }

        System.out.println("=> Đã xuất thành công 4 file: nhanvien.txt, khachhang.txt, nhaphang.txt, hoadon.txt!");
    }
}