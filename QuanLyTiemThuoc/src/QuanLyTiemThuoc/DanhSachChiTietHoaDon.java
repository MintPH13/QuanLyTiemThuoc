// DanhSachChiTietHoaDon.java
package QuanLyTiemThuoc;

import java.io.*;
import java.util.Arrays;

public class DanhSachChiTietHoaDon implements IFileIO {
    private ChiTietHoaDon[] ds = new ChiTietHoaDon[0];

    public ChiTietHoaDon[] getDs() { return ds; }
    public void setDs(ChiTietHoaDon[] ds) { this.ds = ds; }
    public int getSoLuong() { return ds.length; }

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
                ds = Arrays.copyOf(ds, ds.length + 1);
                ds[ds.length - 1] = new ChiTietHoaDon(p[0], p[1], Integer.parseInt(p[2]), Double.parseDouble(p[3]), Double.parseDouble(p[4]));
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