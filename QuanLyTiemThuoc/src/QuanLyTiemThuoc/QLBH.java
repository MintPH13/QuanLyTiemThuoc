package QuanLyTiemThuoc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Scanner;

public class QLBH {
    public static void thongKeDoanhThu(Scanner sc, DanhSachHoaDon dshd, DanhSachPhieuNhap dspn) {
        System.out.println("\n===== THỐNG KÊ THU - CHI - LỢI NHUẬN =====");
        System.out.println("1. Thống kê theo Ngày");
        System.out.println("2. Thống kê theo Tuần");
        System.out.println("3. Thống kê theo Tháng");
        System.out.println("4. Thống kê theo Quý");
        System.out.println("5. Thống kê theo Năm");
        System.out.print("Chọn hình thức thống kê: ");
        String opt = sc.nextLine();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        switch (opt) {
            case "1" -> {
                System.out.print("Nhập ngày cần thống kê (dd/MM/yyyy): ");
                String ngay = sc.nextLine();
                if (!KtraTinhHopLe.isDate(ngay)) { System.out.println("Ngày không hợp lệ!"); return; }
                double thu = HoaDon.tinhTongThuTheoNgay(dshd, ngay);
                double chi = PhieuNhap.tinhTongChiTheoNgay(dspn, ngay);
                System.out.printf("=> Ngày %s: Tổng Thu = %,14.2f | Tổng Chi = %,14.2f | Lợi nhuận = %,14.2f VNĐ\n",
                        ngay, thu, chi, thu - chi);
            }
            case "2" -> {
                System.out.print("Nhập 1 ngày bất kỳ trong tuần cần xem (dd/MM/yyyy): ");
                String ngay = sc.nextLine();
                if (!KtraTinhHopLe.isDate(ngay)) { System.out.println("Ngày không hợp lệ!"); return; }
                LocalDate d = LocalDate.parse(ngay, fmt);
                LocalDate dauTuan = d.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                LocalDate cuoiTuan = d.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                double thu = HoaDon.tinhTongThuTheoTuan(dshd, ngay);
                double chi = PhieuNhap.tinhTongChiTheoTuan(dspn, ngay);
                System.out.printf("=> Tuần (%s - %s): Tổng Thu = %,14.2f | Tổng Chi = %,14.2f | Lợi nhuận = %,14.2f VNĐ\n",
                        dauTuan.format(fmt), cuoiTuan.format(fmt), thu, chi, thu - chi);
            }
            case "3" -> {
                System.out.print("Nhập năm cần thống kê (VD: 2025 hoặc 2026): ");
                int nam = Integer.parseInt(sc.nextLine());
                System.out.println("--- BẢNG THỐNG KÊ 12 THÁNG NĂM " + nam + " ---");
                for (int m = 1; m <= 12; m++) {
                    double thu = HoaDon.tinhTongThuTheoThang(dshd, m, nam);
                    double chi = PhieuNhap.tinhTongChiTheoThang(dspn, m, nam);
                    System.out.printf(" - Tháng %02d/%d: Tổng Thu = %,14.2f | Tổng Chi = %,14.2f | Lợi nhuận = %,14.2f VNĐ\n",
                            m, nam, thu, chi, thu - chi);
                }
            }
            case "4" -> {
                System.out.print("Nhập năm cần thống kê (VD: 2025 hoặc 2026): ");
                int nam = Integer.parseInt(sc.nextLine());
                System.out.println("--- BẢNG THỐNG KÊ 4 QUÝ NĂM " + nam + " ---");
                for (int q = 1; q <= 4; q++) {
                    double thu = HoaDon.tinhTongThuTheoQuy(dshd, q, nam);
                    double chi = PhieuNhap.tinhTongChiTheoQuy(dspn, q, nam);
                    System.out.printf(" - Quý %d/%d: Tổng Thu = %,14.2f | Tổng Chi = %,14.2f | Lợi nhuận = %,14.2f VNĐ\n",
                            q, nam, thu, chi, thu - chi);
                }
            }
            case "5" -> {
                System.out.print("Nhập năm cần thống kê (VD: 2025 hoặc 2026): ");
                int nam = Integer.parseInt(sc.nextLine());
                double thu = HoaDon.tinhTongThuTheoNam(dshd, nam);
                double chi = PhieuNhap.tinhTongChiTheoNam(dspn, nam);
                System.out.printf("=> Cả năm %d: Tổng Thu = %,14.2f | Tổng Chi = %,14.2f | Lợi nhuận = %,14.2f VNĐ\n",
                        nam, thu, chi, thu - chi);
            }
            default -> System.out.println("Lựa chọn không hợp lệ!");
        }
    }
}