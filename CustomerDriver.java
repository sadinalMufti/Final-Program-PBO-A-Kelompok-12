import java.util.*;
import java.io.*;

/**
 * Kelas ini merepresentasikan pengelolaan data dan aktivitas yang berkaitan dengan pelanggan.
 * Melibatkan pendaftaran pelanggan, login, pengelolaan keranjang belanja, transaksi, dan lainnya.
 * @author Bintang
 */
class CustomerDriver {
    private ArrayList<Customer> listCustomer = new ArrayList<>();
    private ListBarang listBarang;
    private Keranjang keranjang;
    Invoice invoice = new Invoice();
    private Transaksi transaksi = new Transaksi();
    public boolean StatusDikonfirmasi = false;

    /**
     * @param statusDikonfirmasi Status konfirmasi transaksi yang akan diatur.
     */
    public void setStatusDikonfirmasi(boolean statusDikonfirmasi) {
        StatusDikonfirmasi = statusDikonfirmasi;
    }

    /**
     * @return true jika transaksi sudah dikonfirmasi, false jika belum.
     */
    public boolean isStatusDikonfirmasi() {
        return StatusDikonfirmasi;
    }

    /**
     * Konstruktor untuk objek CustomerDriver. Inisialisasi objek ListBarang dan Keranjang.
     */
    public CustomerDriver() {
        listBarang = new ListBarang();
        keranjang = new Keranjang();
    }

    /**
     * @param username Username yang akan diperiksa.
     * @return true jika username sudah terdaftar, false jika belum.
     */
    public boolean cekUsername(String username) {
        loadCustomerData(); // Load customer data from the database

        for (Customer customer : listCustomer) {
            if (customer.getUsername().equals(username)) {
                return true; // Username sudah terdaftar
            }
        }
        return false; // Username belum terdaftar
    }

    /**
     * @param username Username pelanggan.
     * @param password Password pelanggan.
     * @return Objek Customer jika login berhasil, null jika gagal.
     */
    public Customer verifikasiLoginCustomer(String username, String password) {
        loadCustomerData(); // Load customer data from the database

        for (Customer customer : listCustomer) {
            if (customer.checkLogin(username, password)) {
                return customer;
            }
        }

        return null;
    }

    /**
     * Memuat data pelanggan dari file database.
     * Jika file tidak ditemukan, membuat file baru.
     */
    private void loadCustomerData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("customer_database.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    listCustomer.add(new Customer(parts[0], parts[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: customer_database.txt not found. Creating the file...");
            createCustomerDatabaseFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Membuat file database untuk menyimpan data pelanggan jika belum ada.
     * Jika file sudah ada, akan dibuat ulang menjadi kosong.
     */
    private void createCustomerDatabaseFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("customer_database.txt"))) {
            // Creating an empty file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param username Username pelanggan yang baru.
     * @param password Password pelanggan yang baru.
     */
    public void signUpCustomer(String username, String password) {
        Customer newCustomer = new Customer(username, password);
        saveCustomerData(newCustomer);
        System.out.println("\n   Akun Customer berhasil terdaftar!");
        System.out.println("+--------------------------------------+");
    }

    /**
     * Menyimpan data pelanggan ke file database.
     *
     * @param customer Objek Customer yang akan disimpan.
     */
    private void saveCustomerData(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("customer_database.txt", true))) {
            writer.write(customer.getUsername() + "," + customer.getPassword() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     /**
     * Menampilkan menu utama untuk pelanggan dan menangani operasi pilihan menu.
     * Pilihan menu mencakup melihat, menambahkan barang ke keranjang, checkout, mencetak invoice,
     * melihat daftar transaksi, dan logout.
     */
    public void showMenu() {
        int choice;
        Scanner scanner = new Scanner(System.in);

        System.out.println("+--------------------------------------+");
        System.out.println("  Anda Berhasil Masuk Sebagai Customer");
        do {
            System.out.println("+==========  MENU  CUSTOMER  ==========+");
            System.out.println("| 1. Lihat List Barang                 |");
            System.out.println("| 2. Tambah Barang ke Keranjang        |");
            System.out.println("| 3. Checkout                          |");
            System.out.println("| 4. Cetak Invoice                     |");
            System.out.println("| 5. Lihat Daftar Transaksi            |");
            System.out.println("| 0. Logout                            |");
            System.out.println("+--------------------------------------+");
            System.out.print("              Pilih menu: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    listBarang.updateDaftarBarang();
                    listBarang.lihatListBarang(listBarang.getDaftarBarang());
                    listBarang.saveDaftarBarangToFile();
                    break;
                case 2:
                    keranjang.tambahKeranjang(listBarang.getDaftarBarang());
                    listBarang.saveDaftarBarangToFile();
                    break;
                case 3:
                    // Update objek Transaksi sebelum menggunakan keranjang.checkout
                    invoice.pilihMetodePembayaran();
                    listBarang.saveDaftarBarangToFile();
                    break;
                case 4:
                    boolean keranjangisi = invoice.cek_keranjang();
                    if (keranjangisi) {
                        if (isStatusDikonfirmasi()) {
                            invoice.cetakInvoice();
                            invoice.simpanTransaksiKeDatabase();
                            setStatusDikonfirmasi(false);
                            keranjang.clearKeranjangFile();
                        } else {
                            System.out.println("+--------------------------------------+");
                            System.out.println("   Menunggu Transaksi diterima Admin");
                            System.out.println("+--------------------------------------+");
                        }
                    } else {
                        System.out.println("Belum ada checkout yang dilakukan");
                    }
                    listBarang.saveDaftarBarangToFile();
                    break;

                case 5:
                    transaksi.lihatListTransaksi();
                    listBarang.saveDaftarBarangToFile();
                    break;
                case 0:
                    System.out.println("             Logout berhasil");
                    break;
                default:
                    System.out.println("    Pilihan tidak tersedia, coba lagi");
            }
        } while (choice != 0);
        listBarang.saveDaftarBarangToFile();
    }
}
