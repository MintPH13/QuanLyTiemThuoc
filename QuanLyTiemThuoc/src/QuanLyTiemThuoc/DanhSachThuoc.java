package QuanLyTiemThuoc;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DanhSachThuoc implements IFileIO {
    private Thuoc[] ds = new Thuoc[0];
    private Scanner sc = new Scanner(System.in);

    public Thuoc[] getDs() { return ds; }
    public int getSoLuong() { return ds.length; }

    public void them(Thuoc t) {
        ds = Arrays.copyOf(ds, ds.length + 1);
        ds[ds.length - 1] = t;
    }

    // Hàm quan trọng: Cập nhật số lượng sản phẩm khi Bán hàng (-soluong) hoặc Nhập hàng (+soluong)
    public void capNhatSLSP(String mathuoc, int deltaSoLuong) {
        Thuoc t = timTheoMa(mathuoc);
        if (t != null) {
            int slMoi = t.getSoluong() + deltaSoLuong;
            if (slMoi < 0) slMoi = 0;
            t.setSoluong(slMoi);
        }
    }

    public Thuoc timTheoMa(String ma) {
        for (Thuoc t : ds) {
            if (t.getMathuoc().equalsIgnoreCase(ma)) return t;
        }
        return null;
    }

    public void nhapNPhanTuDau() {
        System.out.print("Nhập số lượng thuốc ban đầu (n): ");
        int n = Integer.parseInt(sc.nextLine());
        ds = new Thuoc[0];
        for (int i = 0; i < n; i++) {
            System.out.println("-- Nhập thuốc thứ " + (i + 1) + " --");
            them1PhanTu();
        }
    }

    public void xuat() {
        if (ds.length == 0) {
            System.out.println("Danh sách thuốc trống!");
            return;
        }
        System.out.println("\n================================ DANH SÁCH THUỐC Y TẾ (" + ds.length + " sản phẩm) ================================");
        for (Thuoc t : ds) {
            t.xuat();
        }
    }

    public void them1PhanTu() {
        System.out.print("Chọn phân nhóm (1. Thuốc kê đơn, 2. Thuốc không kê đơn): ");
        String c = sc.nextLine();
        Thuoc t = c.equals("1") ? new ThuocKeDon() : new ThuocKhongKeDon();
        t.nhap(sc);
        while (timTheoMa(t.getMathuoc()) != null) {
            System.out.print("Mã thuốc đã tồn tại! Nhập lại mã khác: ");
            t.setMathuoc(sc.nextLine());
        }
        them(t);
        System.out.println("=> Đã thêm thuốc thành công.");
    }

    public void themKPhanTu() {
        System.out.print("Nhập số lượng thuốc muốn thêm (k): ");
        int k = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < k; i++) {
            System.out.println("-- Thêm thuốc thứ " + (i + 1) + "/" + k + " --");
            them1PhanTu();
        }
    }

    public void suaTheoMa() {
        System.out.print("Nhập mã thuốc cần sửa: ");
        String ma = sc.nextLine();
        Thuoc t = timTheoMa(ma);
        if (t == null) {
            System.out.println("Không tìm thấy mã thuốc: " + ma);
            return;
        }
        System.out.println("Thông tin hiện tại:");
        t.xuat();
        t.nhapCapNhat(sc);
        System.out.println("=> Đã cập nhật thuốc thành công.");
    }

    public void xoaTheoMa() {
        System.out.print("Nhập mã thuốc cần xóa: ");
        String ma = sc.nextLine();
        int idx = -1;
        for (int i = 0; i < ds.length; i++) {
            if (ds[i].getMathuoc().equalsIgnoreCase(ma)) {
                idx = i;
                break;
            }
        }
        if (idx != -1) {
            for (int i = idx; i < ds.length - 1; i++) ds[i] = ds[i + 1];
            ds = Arrays.copyOf(ds, ds.length - 1);
            System.out.println("=> Đã xóa thuốc mã: " + ma);
        } else {
            System.out.println("Không tìm thấy mã thuốc!");
        }
    }

    public void timKiemNhieuKhoa() {
        System.out.println("1. Tìm chính xác theo Mã thuốc | 2. Tìm gần đúng theo Tên thuốc | 3. Tìm theo khoảng Đơn giá");
        System.out.print("Chọn: ");
        String c = sc.nextLine();
        int count = 0;
        if (c.equals("1")) {
            System.out.print("Nhập mã thuốc: ");
            Thuoc t = timTheoMa(sc.nextLine());
            if (t != null) { t.xuat(); count++; }
        } else if (c.equals("2")) {
            System.out.print("Nhập từ khóa tên thuốc: ");
            String kw = sc.nextLine().toLowerCase();
            for (Thuoc t : ds) {
                if (t.getTenthuoc().toLowerCase().contains(kw)) { t.xuat(); count++; }
            }
        } else if (c.equals("3")) {
            System.out.print("Giá tối thiểu: ");
            double min = Double.parseDouble(sc.nextLine());
            System.out.print("Giá tối đa: ");
            double max = Double.parseDouble(sc.nextLine());
            for (Thuoc t : ds) {
                if (t.getDongia() >= min && t.getDongia() <= max) { t.xuat(); count++; }
            }
        }
        if (count == 0) System.out.println("Không tìm thấy thuốc thỏa điều kiện.");
    }

    public void thongKeNhieuKhoa() {
        int slKeDon = 0, slKhongKeDon = 0;
        double gtKeDon = 0, gtKhongKeDon = 0;
        for (Thuoc t : ds) {
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

    @Override
    public void docFile() {
        File f = new File("thuoc.txt");
        if (!f.exists()) f = new File("data/thuoc.txt");
        if (!f.exists()) return;
        ds = new Thuoc[0];
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(";");
                if (p[0].equalsIgnoreCase("KEDON")) {
                    them(new ThuocKeDon(p[1], p[2], p[3], p[4], p[5], Double.parseDouble(p[6]),
                            Integer.parseInt(p[7]), p[8], p[9], p[10]));
                } else {
                    them(new ThuocKhongKeDon(p[1], p[2], p[3], p[4], p[5], Double.parseDouble(p[6]),
                            Integer.parseInt(p[7]), p[8], p[9]));
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi đọc file thuoc.txt: " + e.getMessage());
        }
    }

    @Override
    public void ghiFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("thuoc.txt"))) {
            for (Thuoc t : ds) {
                bw.write(t.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file thuoc.txt: " + e.getMessage());
        }
    }

    public void sapXepTheoTen() {
        Arrays.sort(ds, (a, b) -> a.getTenthuoc().compareToIgnoreCase(b.getTenthuoc()));
        System.out.println("=> Đã sắp xếp danh sách thuốc theo tên!");
    }
}
