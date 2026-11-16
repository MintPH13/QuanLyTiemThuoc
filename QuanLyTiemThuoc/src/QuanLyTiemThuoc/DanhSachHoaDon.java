// DanhSachHoaDon.java
package QuanLyTiemThuoc;

import java.io.*;
import java.util.Arrays;

public class DanhSachHoaDon implements IFileIO {
    private HoaDon[] ds = new HoaDon[0];

    public HoaDon[] getDs() { return ds; }
    public void setDs(HoaDon[] ds) { this.ds = ds; }
    public int getSoLuong() { return ds.length; }

    @Override
    public void docFile() {
        File f = new File("hoadon.txt");
        if (!f.exists()) f = new File("data/hoadon.txt");
        if (!f.exists()) return;
        ds = new HoaDon[0];
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(";");
                ds = Arrays.copyOf(ds, ds.length + 1);
                ds[ds.length - 1] = new HoaDon(p[0], p[1], p[2], p[3], Double.parseDouble(p[4]));
            }
        } catch (Exception e) {
            System.out.println("Lỗi đọc hoadon.txt: " + e.getMessage());
        }
    }

    @Override
    public void ghiFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("hoadon.txt"))) {
            for (HoaDon h : ds) {
                bw.write(h.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi hoadon.txt: " + e.getMessage());
        }
    }
}