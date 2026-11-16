package QuanLyTiemThuoc;

import java.util.Arrays;
import java.util.Scanner;

public abstract class Thuoc implements INhapXuat {
    protected String mathuoc;
    protected String tenthuoc;
    protected String maloai;
    protected String mahsx;
    protected String dvt;
    protected double giagoc;
    protected int soluong;
    protected String hansudung;
    public static int tongSoLoaiThuoc = 0;

    public Thuoc() {
        tongSoLoaiThuoc++;
    }

    public Thuoc(String mathuoc, String tenthuoc, String maloai, String mahsx,
                 String dvt, double giagoc, int soluong, String hansudung) {
        this.mathuoc = mathuoc;
        this.tenthuoc = tenthuoc;
        this.maloai = maloai;
        this.mahsx = mahsx;
        this.dvt = dvt;
        this.giagoc = giagoc;
        this.soluong = soluong;
        this.hansudung = hansudung;
        tongSoLoaiThuoc++;
    }

    public Thuoc(Thuoc other) {
        this(other.mathuoc, other.tenthuoc, other.maloai, other.mahsx,
             other.dvt, other.giagoc, other.soluong, other.hansudung);
    }

    public String getMathuoc() { return mathuoc; }
    public void setMathuoc(String mathuoc) { this.mathuoc = mathuoc; }
    public String getTenthuoc() { return tenthuoc; }
    public void setTenthuoc(String tenthuoc) { this.tenthuoc = tenthuoc; }
    public String getMaloai() { return maloai; }
    public void setMaloai(String maloai) { this.maloai = maloai; }
    public String getMahsx() { return mahsx; }
    public void setMahsx(String mahsx) { this.mahsx = mahsx; }
    public String getDvt() { return dvt; }
    public void setDvt(String dvt) { this.dvt = dvt; }
    public double getGiagoc() { return giagoc; }
    public void setGiagoc(double giagoc) { this.giagoc = giagoc; }
    public int getSoluong() { return soluong; }
    public void setSoluong(int soluong) { this.soluong = soluong; }
    public String getHansudung() { return hansudung; }
    public void setHansudung(String hansudung) { this.hansudung = hansudung; }

    public double getDongia() { return tinhGiaBan(); }
    public abstract double tinhGiaBan();
    public abstract String getPhanNhom();
    public abstract String toFileString();

    @Override
    public void nhap(Scanner sc) {
        do {
            System.out.print("Nhập mã thuốc: ");
            mathuoc = sc.nextLine();
        } while (!KtraTinhHopLe.isNotEmpty(mathuoc));
        nhapCapNhat(sc);
    }

    public void nhapCapNhat(Scanner sc) {
        do {
            System.out.print("Nhập tên thuốc: ");
            tenthuoc = sc.nextLine();
        } while (!KtraTinhHopLe.isNotEmpty(tenthuoc));
        System.out.print("Nhập mã loại hàng (VD: LH01): ");
        maloai = sc.nextLine();
        System.out.print("Nhập mã hãng SX (VD: HSX01): ");
        mahsx = sc.nextLine();
        System.out.print("Nhập đơn vị tính (Hộp/Tuýp/Vỉ): ");
        dvt = sc.nextLine();
        System.out.print("Nhập giá gốc: ");
        giagoc = Double.parseDouble(sc.nextLine());
        System.out.print("Nhập số lượng tồn: ");
        soluong = Integer.parseInt(sc.nextLine());
        do {
            System.out.print("Nhập hạn sử dụng (dd/MM/yyyy): ");
            hansudung = sc.nextLine();
        } while (!KtraTinhHopLe.isDate(hansudung));
    }

    @Override
    public void xuat() {
        System.out.printf("Mã: %-5s | Tên: %-20s | Loại: %-5s | HSX: %-5s | ĐVT: %-4s | Tồn: %-4d | HSD: %-10s | Đơn giá: %,10.0f",
                mathuoc, tenthuoc, maloai, mahsx, dvt, soluong, hansudung, getDongia());
    }

    // ================= CÁC THAO TÁC QUẢN LÝ THUỐC =================

    public static void menuQuanLy(Scanner sc, DanhSachThuoc dst) {
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
            try { chon = Integer.parseInt(sc.nextLine()); }
            catch (Exception e) { chon = -1; }

            switch (chon) {
                case 1 -> {
                    System.out.print("Chọn (1. Thêm 1 thuốc, 2. Thêm k thuốc): ");
                    String sub = sc.nextLine();
                    if (sub.equals("1")) them1PhanTu(sc, dst);
                    else if (sub.equals("2")) themKPhanTu(sc, dst);
                    else System.out.println("Lựa chọn không hợp lệ!");
                }
                case 2 -> xuatDanhSach(dst);
                case 3 -> timKiemNhieuKhoa(sc, dst);
                case 4 -> suaTheoMa(sc, dst);
                case 5 -> xoaTheoMa(sc, dst);
                case 6 -> {
                    sapXepTheoTen(dst);
                    xuatDanhSach(dst);
                }
                case 0 -> System.out.println("Trở về menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    public static Thuoc timTheoMa(DanhSachThuoc dst, String ma) {
        for (Thuoc t : dst.getDs()) {
            if (t.getMathuoc().equalsIgnoreCase(ma)) return t;
        }
        return null;
    }

    public static void capNhatSLSP(DanhSachThuoc dst, String mathuoc, int deltaSoLuong) {
        Thuoc t = timTheoMa(dst, mathuoc);
        if (t != null) {
            int slMoi = t.getSoluong() + deltaSoLuong;
            if (slMoi < 0) slMoi = 0;
            t.setSoluong(slMoi);
        }
    }

    public static void xuatDanhSach(DanhSachThuoc dst) {
        Thuoc[] ds = dst.getDs();
        if (ds.length == 0) {
            System.out.println("Danh sách thuốc trống!");
            return;
        }
        System.out.println("\n================================ DANH SÁCH THUỐC Y TẾ (" + ds.length + " sản phẩm) ================================");
        for (Thuoc t : ds) t.xuat();
    }

    public static void them1PhanTu(Scanner sc, DanhSachThuoc dst) {
        System.out.print("Chọn phân nhóm (1. Thuốc kê đơn, 2. Thuốc không kê đơn): ");
        String c = sc.nextLine();
        Thuoc t = c.equals("1") ? new ThuocKeDon() : new ThuocKhongKeDon();
        t.nhap(sc);
        while (timTheoMa(dst, t.getMathuoc()) != null) {
            System.out.print("Mã thuốc đã tồn tại! Nhập lại mã khác: ");
            t.setMathuoc(sc.nextLine());
        }
        Thuoc[] ds = Arrays.copyOf(dst.getDs(), dst.getDs().length + 1);
        ds[ds.length - 1] = t;
        dst.setDs(ds);
        System.out.println("=> Đã thêm thuốc thành công.");
    }

    public static void themKPhanTu(Scanner sc, DanhSachThuoc dst) {
        System.out.print("Nhập số lượng thuốc muốn thêm (k): ");
        int k = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < k; i++) {
            System.out.println("-- Thêm thuốc thứ " + (i + 1) + "/" + k + " --");
            them1PhanTu(sc, dst);
        }
    }

    public static void suaTheoMa(Scanner sc, DanhSachThuoc dst) {
        System.out.print("Nhập mã thuốc cần sửa: ");
        String ma = sc.nextLine();
        Thuoc t = timTheoMa(dst, ma);
        if (t == null) {
            System.out.println("Không tìm thấy mã thuốc: " + ma);
            return;
        }
        System.out.println("Thông tin hiện tại:");
        t.xuat();
        t.nhapCapNhat(sc);
        System.out.println("=> Đã cập nhật thuốc thành công.");
    }

    public static void xoaTheoMa(Scanner sc, DanhSachThuoc dst) {
        System.out.print("Nhập mã thuốc cần xóa: ");
        String ma = sc.nextLine();
        Thuoc[] ds = dst.getDs();
        int idx = -1;
        for (int i = 0; i < ds.length; i++) {
            if (ds[i].getMathuoc().equalsIgnoreCase(ma)) {
                idx = i;
                break;
            }
        }
        if (idx != -1) {
            for (int i = idx; i < ds.length - 1; i++) ds[i] = ds[i + 1];
            dst.setDs(Arrays.copyOf(ds, ds.length - 1));
            System.out.println("=> Đã xóa thuốc mã: " + ma);
        } else {
            System.out.println("Không tìm thấy mã thuốc!");
        }
    }

    public static void timKiemNhieuKhoa(Scanner sc, DanhSachThuoc dst) {
        System.out.println("1. Tìm chính xác theo Mã thuốc | 2. Tìm gần đúng theo Tên thuốc | 3. Tìm theo khoảng Đơn giá");
        System.out.print("Chọn: ");
        String c = sc.nextLine();
        int count = 0;
        if (c.equals("1")) {
            System.out.print("Nhập mã thuốc: ");
            Thuoc t = timTheoMa(dst, sc.nextLine());
            if (t != null) { t.xuat(); count++; }
        } else if (c.equals("2")) {
            System.out.print("Nhập từ khóa tên thuốc: ");
            String kw = sc.nextLine().toLowerCase();
            for (Thuoc t : dst.getDs()) {
                if (t.getTenthuoc().toLowerCase().contains(kw)) { t.xuat(); count++; }
            }
        } else if (c.equals("3")) {
            System.out.print("Giá tối thiểu: ");
            double min = Double.parseDouble(sc.nextLine());
            System.out.print("Giá tối đa: ");
            double max = Double.parseDouble(sc.nextLine());
            for (Thuoc t : dst.getDs()) {
                if (t.getDongia() >= min && t.getDongia() <= max) { t.xuat(); count++; }
            }
        }
        if (count == 0) System.out.println("Không tìm thấy thuốc thỏa điều kiện.");
    }

    public static void sapXepTheoTen(DanhSachThuoc dst) {
        Arrays.sort(dst.getDs(), (a, b) -> a.getTenthuoc().compareToIgnoreCase(b.getTenthuoc()));
        System.out.println("=> Đã sắp xếp danh sách thuốc theo tên!");
    }

    public static void thongKeTonKho(DanhSachThuoc dst) {
        int slKeDon = 0, slKhongKeDon = 0;
        double gtKeDon = 0, gtKhongKeDon = 0;
        for (Thuoc t : dst.getDs()) {
            if (t instanceof ThuocKeDon) {
                slKeDon += t.getSoluong();
                gtKeDon += t.getSoluong() * t.getDongia();
            } else {
                slKhongKeDon += t.getSoluong();
                gtKhongKeDon += t.getSoluong() * t.getDongia();
            }
        }
        System.out.println("===== THỐNG KÊ TỒN KHO THUỐC THEO PHÂN NHÓM =====");
        System.out.printf(" - Thuốc Kê Đơn      : Tổng tồn = %-5d | Tổng giá trị = %,15.2f VNĐ\n", slKeDon, gtKeDon);
        System.out.printf(" - Thuốc Không Kê Đơn: Tổng tồn = %-5d | Tổng giá trị = %,15.2f VNĐ\n", slKhongKeDon, gtKhongKeDon);
    }
}