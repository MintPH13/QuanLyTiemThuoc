package QuanLyTiemThuoc;

import java.io.*;
import java.util.Arrays;

public class DanhSachNhanVien implements IFileIO {
    private NhanVien[] ds = new NhanVien[0];

    public NhanVien[] getDs() { return ds; }
    public void setDs(NhanVien[] ds) { this.ds = ds; }
    public int getSoLuong() { return ds.length; }

    @Override
    public void docFile() {
        File f = new File("nhanvien.txt");
        if (!f.exists()) f = new File("data/nhanvien.txt");
        if (!f.exists()) return;
        ds = new NhanVien[0];
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(";");
                NhanVien nv = (p.length >= 12 && (p[0].equalsIgnoreCase("DUOCSI") || p[0].equalsIgnoreCase("THUNGAN")))
                        ? new NhanVien(p[1], p[2], p[3], Integer.parseInt(p[4]), p[5], p[6], p[7], p[8], Double.parseDouble(p[9]), Double.parseDouble(p[11]))
                        : new NhanVien(p[0], p[1], p[2], Integer.parseInt(p[3]), p[4], p[5], p[6], p[7], Double.parseDouble(p[8]), Double.parseDouble(p[9]));
                ds = Arrays.copyOf(ds, ds.length + 1);
                ds[ds.length - 1] = nv;
            }
        } catch (Exception e) {
            System.out.println("Lỗi đọc nhanvien.txt: " + e.getMessage());
        }
    }

    @Override
    public void ghiFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("nhanvien.txt"))) {
            for (NhanVien nv : ds) {
                bw.write(nv.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi nhanvien.txt: " + e.getMessage());
        }
    }
}