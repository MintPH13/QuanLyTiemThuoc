package QuanLyTiemThuoc;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DanhSachThuoc dst = new DanhSachThuoc();
        DanhSachNhanVien dsnv = new DanhSachNhanVien();
        DanhSachKhachHang dskh = new DanhSachKhachHang();
        DanhSachHoaDon dshd = new DanhSachHoaDon();
        DanhSachChiTietHoaDon dscthd = new DanhSachChiTietHoaDon();
        DanhSachPhieuNhap dspn = new DanhSachPhieuNhap();
        DanhSachChiTietPhieuNhap dsctpn = new DanhSachChiTietPhieuNhap();

        // Tự động đọc dữ liệu từ file khi khởi động (10+ SP/NV/KH, 20+ HD/PN, 30+ CTHD/CTPN)
        dst.docFile();
        dsnv.docFile();
        dskh.docFile();
        dshd.docFile();
        dscthd.docFile();
        dspn.docFile();
        dsctpn.docFile();

        Scanner s = new Scanner(System.in);
        while (true) {
            int choise = 0;
            System.out.println("-------------Chương trình quản lý tiệm bán thuốc---------------");
            System.out.println();
            System.out.println("1.Nhập thêm thuốc mới (1 hoặc k thuốc)");
            System.out.println("2.Xuất danh sách thuốc");
            System.out.println("3.Tìm thuốc (theo mã, tên hoặc khoảng giá)");
            System.out.println("4.Sửa thông tin thuốc (theo mã thuốc)");
            System.out.println("5.Tìm và xóa thuốc (theo mã thuốc)");
            System.out.println("6.Sắp xếp theo tên thuốc");
            System.out.println("7.Nhập thêm nhân viên");
            System.out.println("8.Xuất danh sách nhân viên");
            System.out.println("9.Tìm, sửa, xóa hoặc sắp xếp nhân viên");
            System.out.println("10.Nhập thêm khách hàng");
            System.out.println("11.Xuất danh sách khách hàng");
            System.out.println("12.Tìm, sửa, xóa hoặc sắp xếp khách hàng");
            System.out.println("13.Lập hóa đơn bán thuốc (Tự động trừ số lượng tồn kho)");
            System.out.println("14.Xuất danh sách hóa đơn & Chi tiết hóa đơn");
            System.out.println("15.Lập phiếu nhập thuốc (Tự động cộng số lượng tồn kho)");
            System.out.println("16.Xuất danh sách phiếu nhập & Chi tiết phiếu nhập");
            System.out.println("17.Thống kê tồn kho thuốc & Thống kê doanh thu theo ngày/quý");
            System.out.println("18.Ghi toàn bộ dữ liệu ra file (.txt)");
            System.out.println("0. Thoát chương trình");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choise = Integer.parseInt(s.nextLine());
            } catch (Exception e) {
                choise = -1;
            }

            if (choise == 0) {
                dst.ghiFile();
                dsnv.ghiFile();
                dskh.ghiFile();
                dshd.ghiFile();
                dscthd.ghiFile();
                dspn.ghiFile();
                dsctpn.ghiFile();
                System.out.println("Đã lưu dữ liệu và thoát chương trình!");
                System.exit(0);
            }

            switch (choise) {
                case 1:
                    System.out.print("Chọn (1. Thêm 1 thuốc, 2. Thêm k thuốc, 3. Nhập lại n thuốc ban đầu): ");
                    String c1 = s.nextLine();
                    if (c1.equals("1")) dst.them1PhanTu();
                    else if (c1.equals("2")) dst.themKPhanTu();
                    else dst.nhapNPhanTuDau();
                    break;
                case 2:
                    dst.xuat();
                    break;
                case 3:
                    dst.timKiemNhieuKhoa();
                    break;
                case 4:
                    dst.suaTheoMa();
                    break;
                case 5:
                    dst.xoaTheoMa();
                    break;
                case 6:
                    dst.sapXepTheoTen();
                    dst.xuat();
                    break;
                case 7:
                    dsnv.themMoi();
                    break;
                case 8:
                    dsnv.xuat();
                    break;
                case 9:
                    System.out.print("Chọn (1. Tìm NV, 2. Sửa NV, 3. Xóa NV, 4. Sắp xếp tên NV): ");
                    String c9 = s.nextLine();
                    if (c9.equals("1")) dsnv.timKiem();
                    else if (c9.equals("2")) dsnv.sua();
                    else if (c9.equals("3")) dsnv.xoa();
                    else if (c9.equals("4")) { dsnv.sapXepTheoTen(); dsnv.xuat(); }
                    break;
                case 10:
                    dskh.themMoi();
                    break;
                case 11:
                    dskh.xuat();
                    break;
                case 12:
                    System.out.print("Chọn (1. Tìm KH, 2. Sửa KH, 3. Xóa KH, 4. Sắp xếp tên KH): ");
                    String c12 = s.nextLine();
                    if (c12.equals("1")) dskh.timKiem();
                    else if (c12.equals("2")) dskh.sua();
                    else if (c12.equals("3")) dskh.xoa();
                    else if (c12.equals("4")) { dskh.sapXepTheoTen(); dskh.xuat(); }
                    break;
                case 13:
                    QLBH.themHoaDonMoi(s, dshd, dscthd, dst, dsnv, dskh);
                    break;
                case 14:
                    dshd.xuat();
                    System.out.print("Bạn có muốn xem Chi tiết hóa đơn không? (1. Xem tất cả CTHD, 2. Tìm theo mã HĐ, 0. Bỏ qua): ");
                    String c14 = s.nextLine();
                    if (c14.equals("1")) dscthd.xuat();
                    else if (c14.equals("2")) dscthd.timTheoMaHD();
                    break;
                case 15:
                    QLBH.themPhieuNhapMoi(s, dspn, dsctpn, dst, dsnv);
                    break;
                case 16:
                    dspn.xuat();
                    System.out.print("Bạn có muốn xem Chi tiết phiếu nhập không? (1. Xem tất cả CTPN, 2. Tìm theo mã PN, 0. Bỏ qua): ");
                    String c16 = s.nextLine();
                    if (c16.equals("1")) dsctpn.xuat();
                    else if (c16.equals("2")) dsctpn.timTheoMaPN();
                    break;
                case 17:
                    dst.thongKeNhieuKhoa();
                    QLBH.thongKeDoanhThu(s, dshd, dspn);
                    break;
                case 18:
                    dst.ghiFile();
                    dsnv.ghiFile();
                    dskh.ghiFile();
                    dshd.ghiFile();
                    dscthd.ghiFile();
                    dspn.ghiFile();
                    dsctpn.ghiFile();
                    System.out.println("=> Đã ghi toàn bộ dữ liệu ra 7 file .txt thành công!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }

            System.out.println("\nNhập phím bất kỳ và nhấn Enter để tiếp tục...");
            s.nextLine();
            try {
                Runtime.getRuntime().exec("cls");
            } catch (IOException ex) {
            }
        }
    }
}
