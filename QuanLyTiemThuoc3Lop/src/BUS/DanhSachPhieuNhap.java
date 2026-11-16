package BUS;
import DTO.IFileIO;
import DTO.PhieuNhap;
import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Scanner;

public class DanhSachPhieuNhap implements IFileIO {
    private PhieuNhap[] ds = new PhieuNhap[0];
    private Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PhieuNhap[] getDs() { return ds; }
    public int getSoLuong() { return ds.length; }

    public void them(PhieuNhap pn) {
        ds = Arrays.copyOf(ds, ds.length + 1);
        ds[ds.length - 1] = pn;
    }

    public PhieuNhap timTheoMa(String mapn) {
        for (PhieuNhap pn : ds) {
            if (pn.getMapn().equalsIgnoreCase(mapn)) return pn;
        }
        return null;
    }

    public void xuat() {
        if (ds.length == 0) {
            System.out.println("Danh sách phiếu nhập trống!");
            return;
        }
        System.out.println("\n========================= DANH SÁCH PHIẾU NHẬP (" + ds.length + " PN) =========================");
        for (PhieuNhap pn : ds) {
            pn.xuat();
        }
    }

    public void xoaTheoMa() {
        System.out.print("Nhập mã PN cần xóa: ");
        String ma = sc.nextLine();
        int idx = -1;
        for (int i = 0; i < ds.length; i++) {
            if (ds[i].getMapn().equalsIgnoreCase(ma)) { idx = i; break; }
        }
        if (idx != -1) {
            for (int i = idx; i < ds.length - 1; i++) ds[i] = ds[i + 1];
            ds = Arrays.copyOf(ds, ds.length - 1);
            System.out.println("=> Đã xóa phiếu nhập " + ma);
        } else {
            System.out.println("Không tìm thấy phiếu nhập!");
        }
    }

    public void timKiem() {
        System.out.print("Nhập mã PN hoặc mã NCC cần tìm: ");
        String tk = sc.nextLine().toLowerCase();
        for (PhieuNhap pn : ds) {
            if (pn.getMapn().toLowerCase().contains(tk) || pn.getMancc().toLowerCase().contains(tk)) {
                pn.xuat();
            }
        }
    }

    public double tinhTongChiTheoNgay(String ngayStr) {
        double tong = 0;
        try {
            LocalDate target = LocalDate.parse(ngayStr, FMT);
            for (PhieuNhap pn : ds) {
                LocalDate d = LocalDate.parse(pn.getNgaynhap(), FMT);
                if (d.equals(target)) tong += pn.getTongtien();
            }
        } catch (Exception e) {}
        return tong;
    }

    public double tinhTongChiTheoTuan(String ngayTrongTuanStr) {
        double tong = 0;
        try {
            LocalDate date = LocalDate.parse(ngayTrongTuanStr, FMT);
            LocalDate dauTuan = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate cuoiTuan = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            for (PhieuNhap pn : ds) {
                LocalDate d = LocalDate.parse(pn.getNgaynhap(), FMT);
                if (!d.isBefore(dauTuan) && !d.isAfter(cuoiTuan)) {
                    tong += pn.getTongtien();
                }
            }
        } catch (Exception e) {}
        return tong;
    }

    public double tinhTongChiTheoThang(int thang, int nam) {
        double tong = 0;
        for (PhieuNhap pn : ds) {
            try {
                LocalDate d = LocalDate.parse(pn.getNgaynhap(), FMT);
                if (d.getMonthValue() == thang && d.getYear() == nam) {
                    tong += pn.getTongtien();
                }
            } catch (Exception e) {}
        }
        return tong;
    }

    public double tinhTongChiTheoQuy(int quy, int nam) {
        double tong = 0;
        for (PhieuNhap pn : ds) {
            try {
                LocalDate d = LocalDate.parse(pn.getNgaynhap(), FMT);
                int q = (d.getMonthValue() - 1) / 3 + 1;
                if (d.getYear() == nam && q == quy) {
                    tong += pn.getTongtien();
                }
            } catch (Exception e) {}
        }
        return tong;
    }

    public double tinhTongChiTheoNam(int nam) {
        double tong = 0;
        for (PhieuNhap pn : ds) {
            try {
                LocalDate d = LocalDate.parse(pn.getNgaynhap(), FMT);
                if (d.getYear() == nam) {
                    tong += pn.getTongtien();
                }
            } catch (Exception e) {}
        }
        return tong;
    }

    @Override
    public void docFile() {
        File f = new File("phieunhap.txt");
        if (!f.exists()) f = new File("data/phieunhap.txt");
        if (!f.exists()) return;
        ds = new PhieuNhap[0];
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(";");
                them(new PhieuNhap(p[0], p[1], p[2], p[3], Double.parseDouble(p[4])));
            }
        } catch (Exception e) {
            System.out.println("Lỗi đọc phieunhap.txt: " + e.getMessage());
        }
    }

    @Override
    public void ghiFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("phieunhap.txt"))) {
            for (PhieuNhap pn : ds) {
                bw.write(pn.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi phieunhap.txt: " + e.getMessage());
        }
    }
}
