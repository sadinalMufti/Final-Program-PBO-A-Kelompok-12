import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Kelas yang merepresentasikan invoice untuk transaksi pembelian barang.
 * Invoice mencakup metode untuk mencetak invoice, menyimpan transaksi ke database,
 * dan memproses metode pembayaran.
 * @author Bintang
 */
public class Invoice {
    private Transaksi transaksi = new Transaksi();
    Keranjang keranjang = new Keranjang();
    Pembayaran pembayaran;
    private String idTransaksi;
    private final Date tanggalTransaksi;
    private int totalHarga =0;

    /**
     * @param totalHarga Total harga dari transaksi.
     */
    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    /**
     * @return Total harga dari transaksi.
     */
    public int getTotalHarga() {
        return totalHarga;
    }

    /**
     * @return Total harga dari transaksi.
     */
    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    /**
     * Konstruktor untuk membuat objek Invoice.
     * ID transaksi di-generate dan tanggal transaksi di-set saat objek dibuat.
     */
    public Invoice() {
        this.idTransaksi = generateTransactionID();
        this.tanggalTransaksi = new Date();
    }

    /**
     * @return ID transaksi.
     */
    public String getIdTransaksi() {
        return idTransaksi;
    }

    /**
     * @return Tanggal transaksi.
     */
    public Date getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    /**
     * @return ID transaksi yang dihasilkan.
     */
    private String generateTransactionID() {
        // Gunakan 6 karakter terakhir dari timestamp dan 2 karakter pertama dari UUID
        String timestamp = new SimpleDateFormat("HHmmss").format(new Date());
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 2);
    
        return timestamp + uuid;
    }

     /**
     * Menyimpan detail transaksi yang belum dikonfirmasi ke file.
     *
     * @param idTransaksi ID transaksi yang belum dikonfirmasi.
     * @param totalHarga Total harga dari transaksi.
     */
    public void simpanTransaksiBelumKonfirmasi(String idTransaksi, int totalHarga) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transaksi_belum_dikonfirmasi.txt", true))) {
            // Append the current transaction to the file
            writer.write("== DETAIL TRANSAKSI BELUM DIKONFIRMASI ==\n");
            writer.write(" ID Transaksi: " + idTransaksi + "\n");
            writer.write(" Total Harga adalah " + totalHarga + "\n");
            writer.write("+--------------------------------------+\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     /**
     * @return true jika keranjang belanja tidak kosong, false jika kosong.
     */
    public boolean cek_keranjang(){
        if(!keranjang.loadKeranjangFromFile().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @return true jika keranjang tidak kosong, false jika kosong.
     */
    public boolean isi_Keranjang() {
        if (!keranjang.loadKeranjangFromFile().isEmpty()) {
            System.out.println(" ---------- Daftar Pembelian ---------- ");
            int totalharga = 0;
    
            // Generate ID transaksi sekali saat transaksi dibuat
            String idTransaksi = generateTransactionID();
    
            for (Map.Entry<String, Integer> entry : keranjang.loadKeranjangFromFile().entrySet()) {
                String nama = entry.getKey();
                int jumlah = entry.getValue();
    
                // Tampilkan total harga per barang
                int totalHargaPerBarang = transaksi.hitungTotalHargaPerBarang(nama, jumlah);
                System.out.println(nama + " sebanyak " + jumlah + " item, harga Rp " + totalHargaPerBarang);
                totalharga += totalHargaPerBarang;
    
                // Tambahkan barang ke transaksi
                transaksi.tambahBarang(nama, jumlah);
            }
    
            // Set ID transaksi ke objek transaksi
            setIdTransaksi(idTransaksi);
            setTotalHarga(totalharga);
            System.out.println("Total Belanja : " + totalharga);
            return true;  // Keranjang tidak kosong
        } else {
            System.out.println("Keranjang kosong. Tidak dapat melakukan checkout.");
            return false;  // Keranjang kosong
        }
    }
    
    /**
     * Mencetak invoice dengan menampilkan detail transaksi dan total harga.
     */
    public void cetakInvoice() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String tanggalTransaksi = dateFormat.format(new Date());
        System.out.println("+=============  INVOICE  ==============+");
        System.out.println(" Tanggal Transaksi: " + tanggalTransaksi);
        System.out.println("+======================================+\n");
        //System.out.println("+========== DETAIL TRANSAKSI ==========+");
        isi_Keranjang();
        System.out.println("+======================================+");
        System.out.println("        INVOICE BERHASIL DICETAK");
    }
    
     /**
     * Menyimpan detail transaksi ke file database.
     */
    public void simpanTransaksiKeDatabase() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transaksi_database.txt", true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String tanggalTransaksi = dateFormat.format(new Date());
            writer.write("+========== DETAIL TRANSAKSI ==========+\n");
            writer.write(" Tanggal Transaksi: " + tanggalTransaksi + "\n");
            int totalharga = 0;
            for (Map.Entry<String, Integer> entry : keranjang.loadKeranjangFromFile().entrySet()) {
                String namaBarang = entry.getKey();
                int jumlah = entry.getValue();
                writer.write(" " + namaBarang + " sebanyak " + jumlah + " item\n");
                int totalHargaPerBarang = transaksi.hitungTotalHargaPerBarang(namaBarang, jumlah);
                totalharga += totalHargaPerBarang;
                
            }
            setTotalHarga(totalharga);
            writer.write(" Total Harga adalah " + totalharga + "\n");
            writer.write("+--------------------------------------+\n\n");
            keranjang.setIsiKeranjang(keranjang.loadKeranjangFromFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Meminta pengguna untuk memilih metode pembayaran dan memproses pembayaran.
    * Jika keranjang belanja tidak kosong, pengguna diminta untuk memilih antara metode pembayaran Qris, Bank, atau COD.
    * Metode pembayaran yang dipilih akan diproses, dan transaksi yang belum dikonfirmasi akan disimpan.
    */
    public void pilihMetodePembayaran() {
        Scanner scanner = new Scanner(System.in);
        boolean keranjangIsi = isi_Keranjang();

        // Cek apakah keranjang kosong
        if (keranjangIsi) {
            System.out.println("+--------------------------------------+");
            System.out.println("        Pilih metode pembayaran");
            System.out.println("| 1. Qris                              |");
            System.out.println("| 2. Bank                              |");
            System.out.println("| 3. COD                               |");
            System.out.print("               Pilihan: ");
            int metodePembayaran = scanner.nextInt();

            switch (metodePembayaran) {
                case 1:
                    pembayaran = new Qris();
                    break;
                case 2:
                    pembayaran = new Bank();
                    break;
                case 3:
                    pembayaran = new Cod();
                    break;
                default:
                    System.out.println("Metode pembayaran tidak valid.");
                    return;
            }
            pembayaran.prosesPembayaran();
            simpanTransaksiBelumKonfirmasi(getIdTransaksi(), getTotalHarga() );
            
        }
    }
}

