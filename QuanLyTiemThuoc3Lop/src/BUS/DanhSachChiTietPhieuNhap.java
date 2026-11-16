package BUS;
import DTO.ChiTietPhieuNhap;
import DTO.IFileIO;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DanhSachChiTietPhieuNhap implements IFileIO {
    private ChiTietPhieuNhap[] ds = new ChiTietPhieuNhap[0];
    private Scanner sc = new Scanner(System.in);

    public ChiTietPhieuNhap[] getDs() { return ds; }
    public int getSoLuong() { return ds.length; }

    public void them(ChiTietPhieuNhap ct) {
        ds = Arrays.copyOf(ds, ds.length + 1);
        ds[ds.length - 1] = ct;
    }

    public void xuat() {
        if (ds.length == 0) {
            System.out.println("Danh sách chi tiết phiếu nhập trống!");
            return;
        }
        System.out.println("\n========================= CHI TIẾT PHIẾU NHẬP (" + ds.length + " dòng) =========================");
        for (ChiTietPhieuNhap ct : ds) {
            ct.xuat();
        }
    }

    public void timTheoMaPN() {
        System.out.print("Nhập mã PN cần xem chi tiết: ");
        String ma = sc.nextLine();
        for (ChiTietPhieuNhap ct : ds) {
            if (ct.getMapn().equalsIgnoreCase(ma)) ct.xuat();
        }
    }

    @Override
    public void docFile() {
        File f = new File("chitietphieunhap.txt");
        if (!f.exists()) f = new File("data/chitietphieunhap.txt");
        if (!f.exists()) return;
        ds = new ChiTietPhieuNhap[0];
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(";");
                them(new ChiTietPhieuNhap(p[0], p[1], Integer.parseInt(p[2]),
                        Double.parseDouble(p[3]), Double.parseDouble(p[4])));
            }
        } catch (Exception e) {
            System.out.println("Lỗi đọc chitietphieunhap.txt: " + e.getMessage());
        }
    }

    @Override
    public void ghiFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("chitietphieunhap.txt"))) {
            for (ChiTietPhieuNhap ct : ds) {
                bw.write(ct.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi chitietphieunhap.txt: " + e.getMessage());
        }
    }
}
