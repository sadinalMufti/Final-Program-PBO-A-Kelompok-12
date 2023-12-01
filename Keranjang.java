import java.io.*;
import java.util.*;

/**
 * Kelas ini merepresentasikan keranjang belanja pelanggan.
 * Keranjang ini dapat digunakan untuk menambahkan barang, membersihkan isi keranjang, dan menyimpan serta memuat keranjang dari file.
 * @author Dinal
 */
public class Keranjang extends ListBarang implements Serializable {
    private Map<String, Integer> isiKeranjang;
    private static final String FILE_NAME = "keranjang.txt";
    Transaksi transaksi = new Transaksi();

    /**
     * Konstruktor untuk kelas Keranjang.
     * Inisialisasi isi keranjang dari file jika file tersedia, jika tidak, membuat keranjang baru.
     */
    public Keranjang() {
        // Jangan buat instance Transaksi di sini
        // Transaksi transaksi = new Transaksi();
        this.isiKeranjang = loadKeranjangFromFile();
        if (isiKeranjang == null) {
            isiKeranjang = new HashMap<>();
        }
    }

     /**
     * @return Map yang berisi barang dan jumlahnya dalam keranjang.
     */
    public Map<String, Integer> getIsiKeranjang() {
        return isiKeranjang;
    }

    /**
     * Mengatur isi keranjang.
     *
     * @param isiKeranjang Map yang berisi barang dan jumlahnya dalam keranjang.
     */
    public void setIsiKeranjang(Map<String, Integer> isiKeranjang) {
        this.isiKeranjang = isiKeranjang;
        isiKeranjang.clear();
        // Save the updated shopping cart to the file
        saveKeranjangToFile();
    }

    /**
     * Menambahkan barang ke dalam keranjang berdasarkan input pengguna.
     *
     * @param barangDaftar ListBarang yang berisi daftar barang yang tersedia.
     */
    public void tambahKeranjang(ArrayList<Barang> barangDaftar) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nama barang yang ingin ditambahkan ke keranjang\t: ");
        String namaBarang = scanner.nextLine();

        Barang barang = cariBarang(namaBarang, barangDaftar);
        if (barang != null) {
            System.out.print("Masukkan jumlah barang yang ingin ditambahkan ke keranjang\t: ");
            int jumlah = scanner.nextInt();
            if (jumlah <= barang.getStok()) {
                if (isiKeranjang.containsKey(namaBarang)) {
                    int jumlahSaatIni = isiKeranjang.get(namaBarang);
                    isiKeranjang.put(namaBarang, jumlahSaatIni + jumlah);
                } else {
                    isiKeranjang.put(namaBarang, jumlah);
                }
                int stok = barang.getStok() - jumlah;
                barang.setStok(stok);
                System.out.println("Barang berhasil ditambahkan ke keranjang!");
                ubahDaftarBarang(barang);

                // Save the updated shopping cart to the file
                saveKeranjangToFile();
            } else {
                System.out.println("Stok tidak cukup");
            }
        } else {
            System.out.println("Barang tidak ditemukan.");
        }
    }

    /**
     * Membersihkan isi keranjang dan menyimpan perubahan ke file.
     */
    public void clearKeranjangFile() {
        isiKeranjang.clear(); // Menghapus isi keranjang di dalam program
        saveKeranjangToFile(); // Menyimpan keranjang yang kosong ke file
    }

    /**
     * Menyimpan isi keranjang ke dalam file.
     */
    public void saveKeranjangToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(isiKeranjang);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
     /**
     * Memuat isi keranjang dari file.
     *
     * @return Map yang berisi barang dan jumlahnya dalam keranjang.
     */
    public Map<String, Integer> loadKeranjangFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = ois.readObject();
            if (obj instanceof Map<?, ?>) {
                Map<?, ?> map = (Map<?, ?>) obj;
                if (map.isEmpty() || (map.keySet().iterator().next() instanceof String
                        && map.values().iterator().next() instanceof Integer)) {
                    // Safe to cast
                    @SuppressWarnings("unchecked")
                    Map<String, Integer> result = (Map<String, Integer>) obj;
                    return result;
                }
            }
            throw new IOException("Unexpected object type in file");
        } catch (IOException | ClassNotFoundException e) {
            // Return null if there is an exception (e.g., file not found or corrupted)
            return null;
        }
    }
}
