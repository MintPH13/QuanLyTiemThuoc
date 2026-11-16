package QuanLyTiemThuoc;

import java.io.*;
import java.util.Arrays;

public class DanhSachThuoc implements IFileIO {
    private Thuoc[] ds = new Thuoc[0];

    public Thuoc[] getDs() { return ds; }
    public void setDs(Thuoc[] ds) { this.ds = ds; }
    public int getSoLuong() { return ds.length; }

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
                Thuoc t = p[0].equalsIgnoreCase("KEDON")
                        ? new ThuocKeDon(p[1], p[2], p[3], p[4], p[5], Double.parseDouble(p[6]), Integer.parseInt(p[7]), p[8], p[9], p[10])
                        : new ThuocKhongKeDon(p[1], p[2], p[3], p[4], p[5], Double.parseDouble(p[6]), Integer.parseInt(p[7]), p[8], p[9]);
                ds = Arrays.copyOf(ds, ds.length + 1);
                ds[ds.length - 1] = t;
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
}