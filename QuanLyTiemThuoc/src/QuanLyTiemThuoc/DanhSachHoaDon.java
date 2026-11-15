package QuanLyTiemThuoc;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class DanhSachHoaDon implements IFileIO {
    private HoaDon[] hd = new HoaDon[0];
    private Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public HoaDon[] getHd() { return hd; }
    public int getN() { return hd.length; }

    public void them(HoaDon h) {
        hd = Arrays.copyOf(hd, hd.length + 1);
        hd[hd.length - 1] = h;
    }

    public HoaDon timtheomahd(String mahd) {
        for (HoaDon h : hd) {
            if (h.getMahd().equalsIgnoreCase(mahd)) return h;
        }
        return null;
    }

    public void xuat() {
        if (hd.length == 0) {
            System.out.println("Danh sách hóa đơn trống!");
            return;
        }
        System.out.println("\n========================= DANH SÁCH HÓA ĐƠN (" + hd.length + " HĐ) =========================");
        for (HoaDon h : hd) {
            h.xuat();
        }
    }

    public void timtheomahd() {
        System.out.print("Nhập mã HĐ hoặc mã KH cần tìm: ");
        String tk = sc.nextLine().toLowerCase();
        int count = 0;
        for (HoaDon h : hd) {
            if (h.getMahd().toLowerCase().contains(tk) || h.getMakh().toLowerCase().contains(tk)) {
                h.xuat();
                count++;
            }
        }
        if (count == 0) System.out.println("Không tìm thấy hóa đơn!");
    }

    public void xoamahd() {
        System.out.print("Nhập mã HĐ cần xóa: ");
        String ma = sc.nextLine();
        int idx = -1;
        for (int i = 0; i < hd.length; i++) {
            if (hd[i].getMahd().equalsIgnoreCase(ma)) {
                idx = i;
                break;
            }
        }
        if (idx != -1) {
            for (int i = idx; i < hd.length - 1; i++) hd[i] = hd[i + 1];
            hd = Arrays.copyOf(hd, hd.length - 1);
            System.out.println("=> Đã xóa hóa đơn " + ma);
        } else {
            System.out.println("Không tìm thấy mã hóa đơn!");
        }
    }

    public void suatheomahd() {
        System.out.print("Nhập mã HĐ cần sửa: ");
        String ma = sc.nextLine();
        HoaDon h = timtheomahd(ma);
        if (h == null) {
            System.out.println("Không tìm thấy hóa đơn!");
            return;
        }
        System.out.print("Nhập mã NV mới (" + h.getManv() + "): ");
        String nv = sc.nextLine();
        if (!nv.trim().isEmpty()) h.setManv(nv);
        System.out.print("Nhập mã KH mới (" + h.getMakh() + "): ");
        String kh = sc.nextLine();
        if (!kh.trim().isEmpty()) h.setMakh(kh);
        System.out.print("Nhập ngày lập mới (" + h.getNgaylap() + "): ");
        String nl = sc.nextLine();
        if (KtraTinhHopLe.isDate(nl)) h.setNgaylap(nl);
        System.out.println("=> Đã cập nhật hóa đơn!");
    }

    public void thongketongtien() {
        double tong = 0;
        for (HoaDon h : hd) tong += h.getTongtien();
        System.out.printf("=> Tổng doanh thu toàn bộ %d hóa đơn: %,15.2f VNĐ\n", hd.length, tong);
    }

    public double tinhTongThuTheoKhoang(String tuNgay, String denNgay) {
        double tong = 0;
        try {
            LocalDate d1 = LocalDate.parse(tuNgay, FMT);
            LocalDate d2 = LocalDate.parse(denNgay, FMT);
            for (HoaDon h : hd) {
                LocalDate d = LocalDate.parse(h.getNgaylap(), FMT);
                if (!d.isBefore(d1) && !d.isAfter(d2)) {
                    tong += h.getTongtien();
                }
            }
        } catch (Exception e) {}
        return tong;
    }

    public double tinhTongThuTheoQuy(int quy, int nam) {
        double tong = 0;
        for (HoaDon h : hd) {
            try {
                LocalDate d = LocalDate.parse(h.getNgaylap(), FMT);
                int q = (d.getMonthValue() - 1) / 3 + 1;
                if (d.getYear() == nam && q == quy) {
                    tong += h.getTongtien();
                }
            } catch (Exception e) {}
        }
        return tong;
    }

    public double tinhTongTheoKhachHangVaNam(String makh, int nam) {
        double tong = 0;
        for (HoaDon h : hd) {
            try {
                LocalDate d = LocalDate.parse(h.getNgaylap(), FMT);
                if (h.getMakh().equalsIgnoreCase(makh) && d.getYear() == nam) {
                    tong += h.getTongtien();
                }
            } catch (Exception e) {}
        }
        return tong;
    }

    @Override
    public void docFile() {
        File f = new File("hoadon.txt");
        if (!f.exists()) f = new File("data/hoadon.txt");
        if (!f.exists()) return;
        hd = new HoaDon[0];
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(";");
                them(new HoaDon(p[0], p[1], p[2], p[3], Double.parseDouble(p[4])));
            }
        } catch (Exception e) {
            System.out.println("Lỗi đọc hoadon.txt: " + e.getMessage());
        }
    }

    @Override
    public void ghiFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("hoadon.txt"))) {
            for (HoaDon h : hd) {
                bw.write(h.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi hoadon.txt: " + e.getMessage());
        }
    }
}
