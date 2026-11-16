package QuanLyTiemThuoc;

import java.util.Arrays;
import java.util.Scanner;

public class KhachHang implements INhapXuat {
    private String makh;
    private String hoTen;
    private String diaChi;
    private int gioiTinh; // 0: Nam, 1: Nữ
    private String ngaySinh;
    private String sdt;
    private double tongChiTieu;

    public KhachHang() {}

    public KhachHang(String makh, String hoTen, String diaChi, int gioiTinh, String ngaySinh,
                     String sdt, double tongChiTieu) {
        this.makh = makh;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.tongChiTieu = tongChiTieu;
    }

    public String getMakh() { return makh; }
    public void setMakh(String makh) { this.makh = makh; }
    public String getHoTen() { return hoTen; }
    public String getDiaChi() { return diaChi; }
    public String getGioiTinh() { return gioiTinh == 1 ? "Nữ" : "Nam"; }
    public String getNgaySinh() { return ngaySinh; }
    public String getSdt() { return sdt; }
    public double getTongChiTieu() { return tongChiTieu; }
    public void congChiTieu(double tien) { this.tongChiTieu += tien; }

    @Override
    public void nhap(Scanner sc) {
        do {
            System.out.print("Mã khách hàng: ");
            makh = sc.nextLine();
        } while (!KtraTinhHopLe.isNotEmpty(makh));
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

        System.out.print("Tổng chi tiêu tích lũy: ");
        tongChiTieu = Double.parseDouble(sc.nextLine());
    }

    @Override
    public void xuat() {
        System.out.printf("Mã KH: %-5s | Họ tên: %-20s | GT: %-3s | NS: %-10s | SĐT: %-10s | ĐC: %-16s | Chi tiêu: %,10.0f VNĐ\n",
                makh, hoTen, getGioiTinh(), ngaySinh, sdt, diaChi, tongChiTieu);
    }

    public String toFileString() {
        return makh + ";" + hoTen + ";" + diaChi + ";" + gioiTinh + ";" + ngaySinh + ";" + sdt + ";" + tongChiTieu;
    }

    // ================= CÁC THAO TÁC QUẢN LÝ KHÁCH HÀNG =================

    public static void menuQuanLy(Scanner sc, DanhSachKhachHang dskh) {
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
            try { chon = Integer.parseInt(sc.nextLine()); }
            catch (Exception e) { chon = -1; }

            switch (chon) {
                case 1 -> themMoi(sc, dskh);
                case 2 -> xuatDanhSach(dskh);
                case 3 -> timKiem(sc, dskh);
                case 4 -> sua(sc, dskh);
                case 5 -> xoa(sc, dskh);
                case 6 -> {
                    sapXepTheoTen(dskh);
                    xuatDanhSach(dskh);
                }
                case 0 -> System.out.println("Trở về menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    public static KhachHang timTheoMa(DanhSachKhachHang dskh, String makh) {
        for (KhachHang kh : dskh.getDs()) {
            if (kh.getMakh().equalsIgnoreCase(makh)) return kh;
        }
        return null;
    }

    public static void xuatDanhSach(DanhSachKhachHang dskh) {
        KhachHang[] ds = dskh.getDs();
        System.out.println("\n========================= DANH SÁCH KHÁCH HÀNG (" + ds.length + " KH) =========================");
        for (KhachHang kh : ds) kh.xuat();
    }

    public static void themMoi(Scanner sc, DanhSachKhachHang dskh) {
        KhachHang kh = new KhachHang();
        kh.nhap(sc);
        while (timTheoMa(dskh, kh.getMakh()) != null) {
            System.out.print("Mã KH đã tồn tại! Nhập lại: ");
            kh.setMakh(sc.nextLine());
        }
        KhachHang[] ds = Arrays.copyOf(dskh.getDs(), dskh.getDs().length + 1);
        ds[ds.length - 1] = kh;
        dskh.setDs(ds);
        System.out.println("=> Đã thêm khách hàng thành công.");
    }

    public static void sua(Scanner sc, DanhSachKhachHang dskh) {
        System.out.print("Nhập mã KH cần sửa: ");
        String ma = sc.nextLine();
        KhachHang kh = timTheoMa(dskh, ma);
        if (kh == null) { System.out.println("Không tìm thấy!"); return; }
        kh.nhapCapNhat(sc);
        System.out.println("=> Đã cập nhật khách hàng.");
    }

    public static void xoa(Scanner sc, DanhSachKhachHang dskh) {
        System.out.print("Nhập mã KH cần xóa: ");
        String ma = sc.nextLine();
        KhachHang[] ds = dskh.getDs();
        int idx = -1;
        for (int i = 0; i < ds.length; i++) {
            if (ds[i].getMakh().equalsIgnoreCase(ma)) { idx = i; break; }
        }
        if (idx != -1) {
            for (int i = idx; i < ds.length - 1; i++) ds[i] = ds[i + 1];
            dskh.setDs(Arrays.copyOf(ds, ds.length - 1));
            System.out.println("=> Đã xóa khách hàng " + ma);
        } else {
            System.out.println("Không tìm thấy mã KH!");
        }
    }

    public static void timKiem(Scanner sc, DanhSachKhachHang dskh) {
        System.out.print("Nhập mã, tên hoặc SĐT khách hàng: ");
        String tk = sc.nextLine().toLowerCase();
        for (KhachHang kh : dskh.getDs()) {
            if (kh.getMakh().toLowerCase().contains(tk) || kh.getHoTen().toLowerCase().contains(tk) || kh.getSdt().contains(tk)) {
                kh.xuat();
            }
        }
    }

    public static void sapXepTheoTen(DanhSachKhachHang dskh) {
        Arrays.sort(dskh.getDs(), (a, b) -> a.getHoTen().compareToIgnoreCase(b.getHoTen()));
        System.out.println("=> Đã sắp xếp danh sách khách hàng theo tên!");
    }
}