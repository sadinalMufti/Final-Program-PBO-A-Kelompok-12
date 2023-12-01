import java.io.*;
import java.util.*;

/**
 * Kelas yang merepresentasikan transaksi dalam aplikasi toko.
 * @author bintang
 */
public class Transaksi {
    private Map<String, Invoice> daftarInvoices = new HashMap<>();
    private Map<String, Integer> barangCheckOut;
    ListBarang listBarang = new ListBarang();

    /**
     * Konstruktor untuk kelas Transaksi.
     * Inisialisasi objek Transaksi dan struktur data yang dibutuhkan.
     */
    public Transaksi() {
        this.barangCheckOut = new HashMap<>();
    }

    /**
     * Mengatur daftar transaksi yang belum dikonfirmasi.
     *
     * @param daftarInvoices Map yang berisi daftar transaksi yang belum dikonfirmasi.
     */
    public void setDaftarInvoices(Map<String, Invoice> daftarInvoices) {
        this.daftarInvoices = daftarInvoices;
    }
    
    /**
     * Mendapatkan daftar transaksi yang belum dikonfirmasi.
     *
     * @return Map berisi daftar transaksi yang belum dikonfirmasi.
     */
    public Map<String, Invoice> getDaftarInvoices() {
        return daftarInvoices;
    }
    
    /**
     * Menghapus transaksi yang sudah dikonfirmasi dari daftar transaksi yang belum dikonfirmasi.
     *
     * @param idTransaksi ID transaksi yang sudah dikonfirmasi.
     */
    public void hapusTransaksiDikonfirmasi(String idTransaksi) {
        try {
            // Pemanggilan loadTransaksiBelumDikonfirmasi di sini
            Map<String, Invoice> daftarInvoices = loadTransaksiBelumDikonfirmasi();
    
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("transaksi_belum_dikonfirmasi.txt"))) {
                for (Map.Entry<String, Invoice> entry : daftarInvoices.entrySet()) {
                    String currentIdTransaksi = entry.getKey();
    
                    if (!currentIdTransaksi.equals(idTransaksi)) {
                        // Tulis kembali transaksi yang belum dikonfirmasi ke file
                        writer.write("== DETAIL TRANSAKSI BELUM DIKONFIRMASI ==\n");
                        writer.write(" ID Transaksi: " + currentIdTransaksi + "\n");
                        writer.write(" Total Harga adalah " + entry.getValue().getTotalHarga() + "\n");
                        writer.write("+--------------------------------------+\n\n");
                    }
                }
            }
    
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Gagal menghapus transaksi dari database.");
        }
    }
    
    /**
     * Menampilkan daftar transaksi yang belum dikonfirmasi.
     */
    public void lihatListTransaksiBelumDikonfirmasi() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transaksi_belum_dikonfirmasi.txt"))) {
            System.out.println("+======================================+");
            System.out.println("| Daftar Transaksi Belum Dikonfirmasi  |");
            System.out.println("+======================================+");
            String line;
            boolean isInsideTransactionSection = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains("DETAIL TRANSAKSI BELUM DIKONFIRMASI")) {
                    isInsideTransactionSection = true;
                } else if (isInsideTransactionSection && line.contains("+--------------------------------------+")) {
                    isInsideTransactionSection = false;
                } else if (isInsideTransactionSection) {
                    System.out.println(line.trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + "transaksi_belum_dikonfirmasi.txt" + " not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Memuat daftar transaksi yang belum dikonfirmasi dari file.
     *
     * @return Map berisi daftar transaksi yang belum dikonfirmasi.
     */
    public Map<String, Invoice> loadTransaksiBelumDikonfirmasi() {
        Map<String, Invoice> daftarInvoices = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("transaksi_belum_dikonfirmasi.txt"))) {
            String line;
            boolean isInsideTransactionSection = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains("ID Transaksi: ")) {
                    isInsideTransactionSection = true;
                    String idTransaksi = line.replace("ID Transaksi: ", "").trim();

                    // Create a new Invoice for the transaction
                    Invoice invoice = new Invoice();
                    daftarInvoices.put(idTransaksi, invoice);
                } else if (isInsideTransactionSection && line.contains("+--------------------------------------+")) {
                    isInsideTransactionSection = false;
                } else if (isInsideTransactionSection) {
                    // Process other details of the transaction if needed
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: transaksi_belum_dikonfirmasi.txt not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return daftarInvoices;
    }
    
    /**
     * Menampilkan daftar seluruh transaksi yang telah terkonfirmasi.
     */
    public void lihatListTransaksi() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transaksi_database.txt"))) {
            System.out.println("+========== DAFTAR TRANSAKSI ==========+");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("+======================================+");
        } catch (FileNotFoundException e) {
            System.err.println("Error: transaksi_database.txt not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Menambahkan barang ke dalam daftar check-out dan membuat transaksi baru.
     *
     * @param barang  Nama barang yang ditambahkan.
     * @param jumlah  Jumlah barang yang ditambahkan.
     */
    public void tambahBarang(String barang, int jumlah) {
        barangCheckOut.put(barang, jumlah);

        // Create a new Invoice and add it to the daftarInvoices map
        Invoice invoice = new Invoice();
        daftarInvoices.put(invoice.getIdTransaksi(), invoice);
    }

    /**
     * Menghitung total harga per barang berdasarkan nama barang dan jumlah.
     *
     * @param namaBarang Nama barang.
     * @param jumlah     Jumlah barang.
     * @return Total harga per barang.
     */
    public int hitungTotalHargaPerBarang(String namaBarang, int jumlah) {
        Barang barang = listBarang.cariBarang(namaBarang, listBarang.loadDaftarBarangFromFile());
        return barang.getHarga() * jumlah;
    }
}

