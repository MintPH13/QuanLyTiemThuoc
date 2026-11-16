// DanhSachPhieuNhap.java
package QuanLyTiemThuoc;

import java.io.*;
import java.util.Arrays;

public class DanhSachPhieuNhap implements IFileIO {
    private PhieuNhap[] ds = new PhieuNhap[0];

    public PhieuNhap[] getDs() { return ds; }
    public void setDs(PhieuNhap[] ds) { this.ds = ds; }
    public int getSoLuong() { return ds.length; }

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
                ds = Arrays.copyOf(ds, ds.length + 1);
                ds[ds.length - 1] = new PhieuNhap(p[0], p[1], p[2], p[3], Double.parseDouble(p[4]));
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