import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Kelas ini merepresentasikan daftar barang yang tersedia.
 * Daftar barang dapat diakses, diubah, dan disimpan serta dimuat dari file.
 * @author Bintang
 */
public class ListBarang implements Serializable {
    private ArrayList<Barang> daftarBarang;
    private static final String FILE_NAME = "daftarBarang.txt";

      /**
     * Konstruktor untuk kelas ListBarang.
     * Menginisialisasi daftar barang dari file jika file tersedia, jika tidak, membuat daftar baru.
     */
    public ListBarang() {
        this.daftarBarang = loadDaftarBarangFromFile();
        if (daftarBarang == null) {
            // Jika gagal membaca dari file, inisialisasi dengan data default
            initializeDefaultData();
        }
    }

    /**
     * Metode untuk menginisialisasi data default jika file tidak dapat dibaca atau tidak ada.
     */
    private void initializeDefaultData() {
        // Inisialisasi data default
        if (daftarBarang == null) {
            daftarBarang = new ArrayList<>();
        }
    
        Barang barang1 = new Barang("Barang1", 10000, 50);
        Barang barang2 = new Barang("Barang2", 15000, 30);
        Barang barang3 = new Barang("Barang3", 20000, 20);
    
        daftarBarang.add(barang1);
        daftarBarang.add(barang2);
        daftarBarang.add(barang3);
    
        // Simpan daftar barang ke file setelah inisialisasi
        saveDaftarBarangToFile();
    }
    
     /**
     * Metode untuk memperbarui daftar barang dengan data dari file.
     */
    public void updateDaftarBarang() {
        this.daftarBarang = loadDaftarBarangFromFile();
    }

    /**
     * Metode untuk memuat daftar barang dari file.
     *
     * @return ArrayList yang berisi daftar barang.
     */
    public ArrayList<Barang> loadDaftarBarangFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?> && !((ArrayList<?>) obj).isEmpty()) {
                Object firstElement = ((ArrayList<?>) obj).get(0);
                if (firstElement instanceof Barang) {
                    @SuppressWarnings("unchecked")
                    ArrayList<Barang> result = (ArrayList<Barang>) obj;
                    return result;
                }
            }
            throw new IOException("Unexpected object type in file");
        } catch (IOException | ClassNotFoundException e) {
            // Return null if there is an exception (e.g., file not found or corrupted)
            return null;
        }
    }
    
    /**
     * Metode untuk menyimpan daftar barang ke file.
     */
    public void saveDaftarBarangToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(daftarBarang);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mendapatkan daftar barang.
     *
     * @return ArrayList yang berisi daftar barang.
     */
    public ArrayList<Barang> getDaftarBarang() {
        return daftarBarang;
    }

    /**
     * Mengatur daftar barang.
     *
     * @param barangDaftar ArrayList yang berisi daftar barang.
     */
    public void setDaftarBarang(ArrayList<Barang> barangDaftar) {
        this.daftarBarang = barangDaftar;
    }

    /**
     * Metode untuk mengubah data barang dalam daftar.
     *
     * @param barang Barang yang akan diubah.
     */
    public void ubahDaftarBarang(Barang barang) {
        for (Barang existingBarang : daftarBarang) {
            if (existingBarang.getNama().equalsIgnoreCase(barang.getNama())) {
                // Found a matching Barang by name
                existingBarang.setStok(barang.getStok());

            }
        }
        
    }

    /**
     * Metode untuk menambahkan barang ke daftar.
     */
    public void tambahBarang() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("+--------------------------------------+");
        System.out.print("Masukkan nama barang: ");
        String nama = scanner.nextLine();

        System.out.print("Masukkan harga barang: ");
        int harga = scanner.nextInt();

        System.out.print("Masukkan stok barang: ");
        int stok = scanner.nextInt();

        Barang barang = new Barang(nama, harga, stok);
        daftarBarang.add(barang);

        System.out.println("+--------------------------------------+");
        System.out.println("      Barang berhasil ditambahkan!");

        // Save the updated list of items to the file
        saveDaftarBarangToFile();
    }

    /**
     * Metode untuk menghapus barang dari daftar.
     *
     * @param barangDaftar ArrayList yang berisi daftar barang.
     */
    public void hapusBarang(ArrayList<Barang> barangDaftar) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("+--------------------------------------+");
        System.out.print("Masukkan nama barang untuk dihapus: ");
        String namaBarang = scanner.nextLine();

        Barang barangDitemukan = cariBarang(namaBarang, barangDaftar);

        if (barangDitemukan != null) {
            daftarBarang.remove(barangDitemukan);
            System.out.println("        Barang berhasil dihapus!");
            System.out.println("+--------------------------------------+");

            // Save the updated list of items to the file
            saveDaftarBarangToFile();
        } else {
            System.out.println("         Barang tidak ditemukan.");
            System.out.println("+--------------------------------------+");
        }
    }

     /**
     * Metode untuk mengedit data barang dalam daftar.
     */
    public void editBarang() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("+--------------------------------------+");
        System.out.print("Masukkan nama barang untuk diedit: ");
        String namaBarang = scanner.nextLine();

        for (Barang barang : daftarBarang) {
            if (barang.getNama().equalsIgnoreCase(namaBarang)) {
                System.out.print("Masukkan harga baru: ");
                int hargaBaru = scanner.nextInt();
                barang.setHarga(hargaBaru);

                System.out.print("Masukkan stok baru: ");
                int stokBaru = scanner.nextInt();
                barang.setStok(stokBaru);

                System.out.println("        Barang berhasil diedit!");
                System.out.println("+--------------------------------------+");

                // Save the updated list of items to the file
                saveDaftarBarangToFile();

                return;
            }
        }

        System.out.println("        Barang tidak ditemukan.");
        System.out.println("+--------------------------------------+");
    }

    /**
     * @param barangDaftar ArrayList yang berisi daftar barang.
     */
    public void lihatListBarang(ArrayList<Barang> barangDaftar) {
            System.out.println("+=========== DAFTAR  BARANG ===========+");
        int nomor = 1;
        for (Barang barang : barangDaftar) {
            System.out.println(nomor + ". Nama\t\t: " + barang.getNama());
            System.out.println("   Harga\t: " + barang.getHarga());
            System.out.println("   Stok\t\t: " + barang.getStok());
            System.out.println("+======================================+");
            nomor++;
        }
    }

    /**
     * @param barang Barang yang akan diperbarui stoknya.
     * @param newStok Stok baru.
     */
    public void updateStok(Barang barang, int newStok) {
        // Cari index Barang yang sesuai dalam daftarBarang
        int index = daftarBarang.indexOf(barang);
        if (index != -1) {
            // Update stok Barang
            Barang barangToUpdate = daftarBarang.get(index);
            barangToUpdate.setStok(newStok);
            
        }
    }
    
    /**
     * @param nama Nama barang yang dicari.
     * @param barangDaftar ArrayList yang berisi daftar barang.
     * @return Barang yang sesuai dengan nama atau null jika tidak ditemukan.
     */
    public Barang cariBarang(String nama, ArrayList<Barang> barangDaftar) {
        for (Barang barang : barangDaftar) {
            if (barang.getNama().equalsIgnoreCase(nama)) {
                return barang;
            }
        }
        return null;
    }
}

