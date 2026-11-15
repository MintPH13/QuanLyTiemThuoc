package QuanLyTiemThuoc;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DanhSachKhachHang implements IFileIO {
    private KhachHang[] ds = new KhachHang[0];
    private Scanner sc = new Scanner(System.in);

    public KhachHang[] getDs() { return ds; }
    public int getSoLuong() { return ds.length; }

    public void them(KhachHang kh) {
        ds = Arrays.copyOf(ds, ds.length + 1);
        ds[ds.length - 1] = kh;
    }

    public KhachHang timtheomakh(String makh) {
        for (KhachHang kh : ds) {
            if (kh.getMakh().equalsIgnoreCase(makh)) return kh;
        }
        return null;
    }

    public void xuat() {
        System.out.println("\n========================= DANH SÁCH KHÁCH HÀNG (" + ds.length + " KH) =========================");
        for (KhachHang kh : ds) kh.xuat();
    }

    public void themMoi() {
        KhachHang kh = new KhachHang();
        kh.nhap(sc);
        while (timtheomakh(kh.getMakh()) != null) {
            System.out.print("Mã KH đã tồn tại! Nhập lại: ");
            kh.setMakh(sc.nextLine());
        }
        them(kh);
        System.out.println("=> Đã thêm khách hàng thành công.");
    }

    public void sua() {
        System.out.print("Nhập mã KH cần sửa: ");
        String ma = sc.nextLine();
        KhachHang kh = timtheomakh(ma);
        if (kh == null) { System.out.println("Không tìm thấy!"); return; }
        kh.nhapCapNhat(sc);
        System.out.println("=> Đã cập nhật khách hàng.");
    }

    public void xoa() {
        System.out.print("Nhập mã KH cần xóa: ");
        String ma = sc.nextLine();
        int idx = -1;
        for (int i = 0; i < ds.length; i++) {
            if (ds[i].getMakh().equalsIgnoreCase(ma)) { idx = i; break; }
        }
        if (idx != -1) {
            for (int i = idx; i < ds.length - 1; i++) ds[i] = ds[i + 1];
            ds = Arrays.copyOf(ds, ds.length - 1);
            System.out.println("=> Đã xóa khách hàng " + ma);
        } else System.out.println("Không tìm thấy mã KH!");
    }

    public void timKiem() {
        System.out.print("Nhập mã, tên hoặc SĐT khách hàng: ");
        String tk = sc.nextLine().toLowerCase();
        for (KhachHang kh : ds) {
            if (kh.getMakh().toLowerCase().contains(tk) || kh.getHoTen().toLowerCase().contains(tk) || kh.getSdt().contains(tk)) {
                kh.xuat();
            }
        }
    }

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
                String hang = p[0].equalsIgnoreCase("VIP") ? "VIP (" + p[8] + ")" : "Thường (" + p[8] + "đ)";
                them(new KhachHang(p[1], p[2], p[3], Integer.parseInt(p[4]), p[5], p[6], Double.parseDouble(p[7]), hang));
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
        } catch (IOException e) {}
    }

    public void sapXepTheoTen() {
        Arrays.sort(ds, (a, b) -> a.getHoTen().compareToIgnoreCase(b.getHoTen()));
        System.out.println("=> Đã sắp xếp danh sách khách hàng theo tên!");
    }
}
