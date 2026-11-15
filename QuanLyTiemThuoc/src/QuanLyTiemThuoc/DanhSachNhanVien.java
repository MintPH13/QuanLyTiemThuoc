package QuanLyTiemThuoc;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DanhSachNhanVien implements IFileIO {
    private NhanVien[] ds = new NhanVien[0];
    private Scanner sc = new Scanner(System.in);

    public NhanVien[] getDs() { return ds; }
    public int getSoLuong() { return ds.length; }

    public void them(NhanVien nv) {
        ds = Arrays.copyOf(ds, ds.length + 1);
        ds[ds.length - 1] = nv;
    }

    public NhanVien timTheoMa(String ma) {
        for (NhanVien nv : ds) {
            if (nv.getManv().equalsIgnoreCase(ma)) return nv;
        }
        return null;
    }

    public void xuat() {
        System.out.println("\n========================= DANH SÁCH NHÂN VIÊN (" + ds.length + " NV) =========================");
        for (NhanVien nv : ds) nv.xuat();
    }

    public void themMoi() {
        NhanVien nv = new NhanVien();
        nv.nhap(sc);
        while (timTheoMa(nv.getManv()) != null) {
            System.out.print("Mã NV đã tồn tại! Nhập lại: ");
            nv.setManv(sc.nextLine());
        }
        them(nv);
        System.out.println("=> Đã thêm nhân viên thành công.");
    }

    public void sua() {
        System.out.print("Nhập mã NV cần sửa: ");
        String ma = sc.nextLine();
        NhanVien nv = timTheoMa(ma);
        if (nv == null) { System.out.println("Không tìm thấy!"); return; }
        nv.nhapCapNhat(sc);
        System.out.println("=> Đã cập nhật nhân viên.");
    }

    public void xoa() {
        System.out.print("Nhập mã NV cần xóa: ");
        String ma = sc.nextLine();
        int idx = -1;
        for (int i = 0; i < ds.length; i++) {
            if (ds[i].getManv().equalsIgnoreCase(ma)) { idx = i; break; }
        }
        if (idx != -1) {
            for (int i = idx; i < ds.length - 1; i++) ds[i] = ds[i + 1];
            ds = Arrays.copyOf(ds, ds.length - 1);
            System.out.println("=> Đã xóa nhân viên " + ma);
        } else System.out.println("Không tìm thấy mã NV!");
    }

    public void timKiem() {
        System.out.print("Nhập mã hoặc tên NV cần tìm: ");
        String tk = sc.nextLine().toLowerCase();
        for (NhanVien nv : ds) {
            if (nv.getManv().toLowerCase().contains(tk) || nv.getHoTen().toLowerCase().contains(tk)) {
                nv.xuat();
            }
        }
    }

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
                if (p.length >= 12 && (p[0].equalsIgnoreCase("DUOCSI") || p[0].equalsIgnoreCase("THUNGAN"))) {
                    them(new NhanVien(p[1], p[2], p[3], Integer.parseInt(p[4]), p[5], p[6],
                            p[7], p[8], Double.parseDouble(p[9]), Double.parseDouble(p[11])));
                } else {
                    them(new NhanVien(p[0], p[1], p[2], Integer.parseInt(p[3]), p[4], p[5],
                            p[6], p[7], Double.parseDouble(p[8]), Double.parseDouble(p[9])));
                }
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
        } catch (IOException e) {}
    }

    public void sapXepTheoTen() {
        Arrays.sort(ds, (a, b) -> a.getHoTen().compareToIgnoreCase(b.getHoTen()));
        System.out.println("=> Đã sắp xếp danh sách nhân viên theo tên!");
    }
}