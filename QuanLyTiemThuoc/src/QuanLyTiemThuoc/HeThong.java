package QuanLyTiemThuoc;

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
        // Nạp dữ liệu từ file khi khởi động
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
            System.out.println("6. Thống kê tồn kho & doanh thu");
            System.out.println("0. Lưu dữ liệu & Thoát chương trình");
            System.out.print("Chọn chức năng: ");

            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                chon = -1;
            }

            switch (chon) {
                case 1 -> Thuoc.menuQuanLy(sc, dst);
                case 2 -> NhanVien.menuQuanLy(sc, dsnv);
                case 3 -> KhachHang.menuQuanLy(sc, dskh);
                case 4 -> PhieuNhap.menuQuanLy(sc, dspn, dsctpn, dst, dsnv);
                case 5 -> HoaDon.menuQuanLy(sc, dshd, dscthd, dst, dsnv, dskh);
                case 6 -> {
                    Thuoc.thongKeTonKho(dst);
                    QLBH.thongKeDoanhThu(sc, dshd, dspn);
                }
                case 0 -> {
                    dst.ghiFile();
                    dsnv.ghiFile();
                    dskh.ghiFile();
                    dshd.ghiFile();
                    dscthd.ghiFile();
                    dspn.ghiFile();
                    dsctpn.ghiFile();
                    System.out.println("Đã lưu toàn bộ dữ liệu. Thoát chương trình thành công!");
                }
                default -> System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
            }
        } while (chon != 0);
    }
}