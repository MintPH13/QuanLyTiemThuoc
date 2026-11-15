package QuanLyTiemThuoc;
import java.util.Scanner;

public class KhachHang extends ConNguoi {
    private String makh;
    private double tongChiTieu;
    private String hangKhach; // VIP / Thường

    public KhachHang() { super(); }

    public KhachHang(String makh, String hoTen, String diaChi, int gioiTinh, String ngaySinh,
                     String sdt, double tongChiTieu, String hangKhach) {
        super(hoTen, diaChi, gioiTinh, ngaySinh, sdt);
        this.makh = makh;
        this.tongChiTieu = tongChiTieu;
        this.hangKhach = hangKhach;
    }

    public String getMakh() { return makh; }
    public void setMakh(String makh) { this.makh = makh; }
    public double getTongChiTieu() { return tongChiTieu; }
    public void congChiTieu(double tien) { this.tongChiTieu += tien; }
    public String getHangKhach() { return hangKhach; }

    @Override
    public String getVaiTro() {
        return "Khách " + hangKhach;
    }

    @Override
    public void nhap(Scanner sc) {
        do {
            System.out.print("Mã khách hàng: ");
            makh = sc.nextLine();
        } while (!KtraTinhHopLe.isNotEmpty(makh));
        nhapCapNhat(sc);
    }

    public void nhapCapNhat(Scanner sc) {
        super.nhap(sc);
        System.out.print("Tổng chi tiêu tích lũy: ");
        tongChiTieu = Double.parseDouble(sc.nextLine());
        System.out.print("Hạng khách (VIP / Thường): ");
        hangKhach = sc.nextLine();
    }

    @Override
    public void xuat() {
        System.out.printf("Mã KH: %-5s | ", makh);
        super.xuat();
        System.out.printf(" | Chi tiêu: %,10.0f | Hạng: %s\n", tongChiTieu, hangKhach);
    }

    public String toFileString() {
        return "VIP;" + makh + ";" + hoTen + ";" + diaChi + ";" + gioiTinh + ";" + ngaySinh + ";" + sdt + ";" + tongChiTieu + ";" + hangKhach;
    }
}
