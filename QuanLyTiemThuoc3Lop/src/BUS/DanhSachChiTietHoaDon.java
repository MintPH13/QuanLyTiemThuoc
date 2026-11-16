package BUS;
import DTO.ChiTietHoaDon;
import DTO.IFileIO;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DanhSachChiTietHoaDon implements IFileIO {
    private ChiTietHoaDon[] ds = new ChiTietHoaDon[0];
    private Scanner sc = new Scanner(System.in);

    public ChiTietHoaDon[] getDs() { return ds; }
    public int getSoLuong() { return ds.length; }

    public void them(ChiTietHoaDon ct) {
        ds = Arrays.copyOf(ds, ds.length + 1);
        ds[ds.length - 1] = ct;
    }

    public void xuat() {
        if (ds.length == 0) {
            System.out.println("Danh sách chi tiết HĐ trống!");
            return;
        }
        System.out.println("\n========================= CHI TIẾT HÓA ĐƠN (" + ds.length + " dòng) =========================");
        for (ChiTietHoaDon ct : ds) {
            ct.xuat();
        }
    }

    public void timTheoMaHD() {
        System.out.print("Nhập mã HĐ cần xem chi tiết: ");
        String ma = sc.nextLine();
        int count = 0;
        for (ChiTietHoaDon ct : ds) {
            if (ct.getMahd().equalsIgnoreCase(ma)) {
                ct.xuat();
                count++;
            }
        }
        if (count == 0) System.out.println("Không tìm thấy chi tiết cho mã HĐ: " + ma);
    }

    @Override
    public void docFile() {
        File f = new File("chitiethoadon.txt");
        if (!f.exists()) f = new File("data/chitiethoadon.txt");
        if (!f.exists()) return;
        ds = new ChiTietHoaDon[0];
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(";");
                them(new ChiTietHoaDon(p[0], p[1], Integer.parseInt(p[2]),
                        Double.parseDouble(p[3]), Double.parseDouble(p[4])));
            }
        } catch (Exception e) {
            System.out.println("Lỗi đọc chitiethoadon.txt: " + e.getMessage());
        }
    }

    @Override
    public void ghiFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("chitiethoadon.txt"))) {
            for (ChiTietHoaDon ct : ds) {
                bw.write(ct.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi chitiethoadon.txt: " + e.getMessage());
        }
    }
}
