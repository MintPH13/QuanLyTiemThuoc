package QuanLyTiemThuoc;

import java.io.*;
import java.util.Arrays;

public class DanhSachKhachHang implements IFileIO {
    private KhachHang[] ds = new KhachHang[0];

    public KhachHang[] getDs() { return ds; }
    public void setDs(KhachHang[] ds) { this.ds = ds; }
    public int getSoLuong() { return ds.length; }

    @Override
    public void docFile() {
        File f = new File("khachhang.txt");
        if (!f.exists()) f = new File("data/khachhang.txt");
        if (!f.exists()) return;
        ds = new KhachHang[0];
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(";");
                KhachHang kh = (p.length >= 8 && (p[0].equalsIgnoreCase("VIP") || p[0].equalsIgnoreCase("THUONG")))
                        ? new KhachHang(p[1], p[2], p[3], Integer.parseInt(p[4]), p[5], p[6], Double.parseDouble(p[7]))
                        : new KhachHang(p[0], p[1], p[2], Integer.parseInt(p[3]), p[4], p[5], Double.parseDouble(p[6]));
                ds = Arrays.copyOf(ds, ds.length + 1);
                ds[ds.length - 1] = kh;
            }
        } catch (Exception e) {
            System.out.println("Lỗi đọc khachhang.txt: " + e.getMessage());
        }
    }

    @Override
    public void ghiFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("khachhang.txt"))) {
            for (KhachHang kh : ds) {
                bw.write(kh.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi khachhang.txt: " + e.getMessage());
        }
    }
}