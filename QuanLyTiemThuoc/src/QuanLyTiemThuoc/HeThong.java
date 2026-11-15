package QuanLyTiemThuoc;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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
            System.out.println("7. Xuất báo cáo ra trang Web (.html) & Mở trình duyệt");
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
                case 7 -> xuatBaoCaoHTML();
                case 0 -> {
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

    private void xuatBaoCaoHTML() {
        luuToanBoDuLieu();

        double tongDoanhThu = 0;
        for (int i = 0; i < dshd.getN(); i++) {
            tongDoanhThu += dshd.getHd()[i].getTongtien();
        }
        double tongChiNhap = 0;
        for (int i = 0; i < dspn.getSoLuong(); i++) {
            tongChiNhap += dspn.getDs()[i].getTongtien();
        }

        String fileName = "BaoCao_TiemThuoc.html";
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>\n<html lang='vi'>\n<head>\n")
                .append("<meta charset='UTF-8'>\n")
                .append("<title>Báo Cáo Hệ Thống Quản Lý Tiệm Thuốc</title>\n")
                .append("<style>\n")
                .append("  body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f7f6; color: #333; margin: 0; padding: 20px; }\n")
                .append("  .container { max-width: 1100px; margin: auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.08); }\n")
                .append("  h1 { text-align: center; color: #0f766e; margin-bottom: 5px; }\n")
                .append("  .subtitle { text-align: center; color: #64748b; margin-bottom: 30px; font-size: 14px; }\n")
                .append("  .summary { display: flex; gap: 20px; margin-bottom: 30px; justify-content: space-between; }\n")
                .append("  .card { flex: 1; background: #f0fdfa; border-left: 5px solid #0d9488; padding: 15px 20px; border-radius: 6px; }\n")
                .append("  .card h3 { margin: 0 0 8px 0; font-size: 14px; color: #475569; text-transform: uppercase; }\n")
                .append("  .card p { margin: 0; font-size: 22px; font-weight: bold; color: #0f766e; }\n")
                .append("  h2 { color: #0f766e; border-bottom: 2px solid #ccfbf1; padding-bottom: 8px; margin-top: 35px; }\n")
                .append("  table { width: 100%; border-collapse: collapse; margin-top: 15px; font-size: 14px; }\n")
                .append("  th, td { padding: 10px 12px; border: 1px solid #e2e8f0; text-align: left; }\n")
                .append("  th { background-color: #0d9488; color: white; font-weight: 600; }\n")
                .append("  tr:nth-child(even) { background-color: #f8fafc; }\n")
                .append("  tr:hover { background-color: #f1f5f9; }\n")
                .append("  .num { text-align: right; font-variant-numeric: tabular-nums; }\n")
                .append("  .badge { background: #e0f2fe; color: #0369a1; padding: 3px 8px; border-radius: 12px; font-size: 12px; font-weight: 600; }\n")
                .append("</style>\n</head>\n<body>\n<div class='container'>\n");

            html.append("<h1>BÁO CÁO QUẢN LÝ TIỆM THUỐC</h1>\n")
                .append("<p class='subtitle'>Dữ liệu tổng hợp tự động từ hệ thống</p>\n")
                .append("<div class='summary'>\n")
                .append(String.format("  <div class='card'><h3>Tổng Doanh Thu</h3><p>%,.0f VNĐ</p></div>\n", tongDoanhThu))
                .append(String.format("  <div class='card'><h3>Tổng Chi Nhập Hàng</h3><p>%,.0f VNĐ</p></div>\n", tongChiNhap))
                .append(String.format("  <div class='card'><h3>Nhân Sự / Khách Hàng</h3><p>%d NV / %d KH</p></div>\n", dsnv.getSoLuong(), dskh.getSoLuong()))
                .append("</div>\n");

            // 1. Bảng Thuốc
            html.append("<h2>1. Danh Sách Thuốc Trong Kho (" + dst.getSoLuong() + " sản phẩm)</h2>\n")
                .append("<table><tr><th>Mã</th><th>Tên Thuốc</th><th>Phân Nhóm</th><th>ĐVT</th><th class='num'>Tồn Kho</th><th>Hạn SD</th><th class='num'>Đơn Giá Bán</th></tr>\n");
            for (Thuoc t : dst.getDs()) {
                html.append(String.format("<tr><td>%s</td><td><b>%s</b></td><td><span class='badge'>%s</span></td><td>%s</td><td class='num'>%d</td><td>%s</td><td class='num'>%,.0f đ</td></tr>\n",
                        t.getMathuoc(), t.getTenthuoc(), t.getPhanNhom(), t.getDvt(), t.getSoluong(), t.getHansudung(), t.getDongia()));
            }
            html.append("</table>\n");

            // 2. Bảng Nhân Viên
            html.append("<h2>2. Danh Sách Nhân Viên (" + dsnv.getSoLuong() + " nhân viên)</h2>\n")
                .append("<table><tr><th>Mã NV</th><th>Họ Tên</th><th>Giới Tính</th><th>Ngày Sinh</th><th>SĐT</th><th>Email</th><th class='num'>Tổng Lương</th></tr>\n");
            for (NhanVien nv : dsnv.getDs()) {
                html.append(String.format("<tr><td>%s</td><td><b>%s</b></td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td class='num'>%,.0f đ</td></tr>\n",
                        nv.getManv(), nv.getHoTen(), nv.getGioiTinh(), nv.getNgaySinh(), nv.getSdt(), nv.getEmail(), nv.tinhLuong()));
            }
            html.append("</table>\n");

            // 3. Bảng Khách Hàng
            html.append("<h2>3. Danh Sách Khách Hàng (" + dskh.getSoLuong() + " khách hàng)</h2>\n")
                .append("<table><tr><th>Mã KH</th><th>Họ Tên</th><th>Giới Tính</th><th>Ngày Sinh</th><th>SĐT</th><th>Địa Chỉ</th><th class='num'>Tổng Chi Tiêu</th></tr>\n");
            for (KhachHang kh : dskh.getDs()) {
                html.append(String.format("<tr><td>%s</td><td><b>%s</b></td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td class='num'>%,.0f đ</td></tr>\n",
                        kh.getMakh(), kh.getHoTen(), kh.getGioiTinh(), kh.getNgaySinh(), kh.getSdt(), kh.getDiaChi(), kh.getTongChiTieu()));
            }
            html.append("</table>\n");

            // 4. Bảng Hóa Đơn
            html.append("<h2>4. Lịch Sử Hóa Đơn Bán Hàng (" + dshd.getN() + " hóa đơn)</h2>\n")
                .append("<table><tr><th>Mã HĐ</th><th>Mã NV</th><th>Mã KH</th><th>Ngày Lập</th><th class='num'>Tổng Tiền</th></tr>\n");
            for (int i = 0; i < dshd.getN(); i++) {
                HoaDon hd = dshd.getHd()[i];
                html.append(String.format("<tr><td><b>%s</b></td><td>%s</td><td>%s</td><td>%s</td><td class='num'>%,.0f đ</td></tr>\n",
                        hd.getMahd(), hd.getManv(), hd.getMakh(), hd.getNgaylap(), hd.getTongtien()));
            }
            html.append("</table>\n");

            // 5. Bảng Phiếu Nhập
            html.append("<h2>5. Lịch Sử Phiếu Nhập Hàng (" + dspn.getSoLuong() + " phiếu nhập)</h2>\n")
                .append("<table><tr><th>Mã PN</th><th>Mã NV</th><th>Mã NCC</th><th>Ngày Nhập</th><th class='num'>Tổng Tiền Nhập</th></tr>\n");
            for (int i = 0; i < dspn.getSoLuong(); i++) {
                PhieuNhap pn = dspn.getDs()[i];
                html.append(String.format("<tr><td><b>%s</b></td><td>%s</td><td>%s</td><td>%s</td><td class='num'>%,.0f đ</td></tr>\n",
                        pn.getMapn(), pn.getManv(), pn.getMancc(), pn.getNgaynhap(), pn.getTongtien()));
            }
            html.append("</table>\n");

            html.append("</div>\n</body>\n</html>");

            bw.write(html.toString());
            System.out.println("=> Đã xuất báo cáo thành công ra file: " + fileName);

            File htmlFile = new File(fileName);
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(htmlFile.toURI());
                System.out.println("=> Đang mở báo cáo trên trình duyệt...");
            } else {
                System.out.println("=> Đường dẫn file: " + htmlFile.getAbsolutePath());
            }

        } catch (IOException e) {
            System.out.println("Lỗi khi xuất báo cáo HTML: " + e.getMessage());
        }
    }
}