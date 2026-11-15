package QuanLyTiemThuoc;
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

    // Đa hình: tính đơn giá bán dựa trên loại thuốc
    public double getDongia() {
        return tinhGiaBan();
    }

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
}
