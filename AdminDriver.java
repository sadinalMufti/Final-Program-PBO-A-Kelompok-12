import java.util.*;
import java.io.*;
/**
 * Kelas {@code AdminDriver} adalah pengelola interaksi sistem untuk pengguna dengan peran admin.
 * Kelas ini mencakup fungsionalitas seperti manajemen barang, konfirmasi transaksi, dan navigasi menu admin.
 * @author Bintang
 */
class AdminDriver {
    private ListBarang listBarang;
    private Transaksi transaksi = new Transaksi();
    private ArrayList<Admin> listAdmin = new ArrayList<>();
    CustomerDriver customerDriver;

    /**
     * Konstruktor untuk membuat objek {@code AdminDriver}.
     * Inisialisasi daftar barang dan objek transaksi.
     */
    public AdminDriver() {
        listBarang = new ListBarang();
        new ArrayList<>();

    }

    /**
     * Menetapkan objek {@code CustomerDriver} untuk memfasilitasi interaksi dengan pelanggan.
     *
     * @param customerDriver objek {@code CustomerDriver} yang ditetapkan
     */
    public void setCustomerDriver(CustomerDriver customerDriver) {
        this.customerDriver = customerDriver;
    }

    /**
     * Mendapatkan objek {@code CustomerDriver} yang digunakan untuk interaksi dengan pelanggan.
     *
     * @return objek {@code CustomerDriver}
     */
    public CustomerDriver getCustomerDriver() {
        return customerDriver;
    }

    /**
     * Menampilkan daftar transaksi dari file database.
     */
    public void lihatListTransaksi() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transaksi_database.txt"))) {
            System.out.println("+--------------------------------------+");
            System.out.println("+=========== LIST TRANSAKSI ===========+");
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
     * Memeriksa apakah username admin sudah terdaftar.
     *
     * @param username username yang akan diperiksa
     * @return {@code true} jika username sudah terdaftar, {@code false} jika belum
     */
    public boolean cekUsername(String username) {
        loadAdminData(); // Load admin data from the database

        for (Admin admin : listAdmin) {
            if (admin.getUsername().equals(username)) {
                return true; // Username sudah terdaftar
            }
        }
        return false; // Username belum terdaftar
    }

     /**
     * Memverifikasi login admin berdasarkan username dan password.
     *
     * @param username username admin
     * @param password password admin
     * @return objek {@code Admin} jika login berhasil, {@code null} jika gagal
     */
    public Admin verifikasiLoginAdmin(String username, String password) {
        loadAdminData(); // Load admin data from the database
    
        for (Admin admin : listAdmin) {
            if (admin.checkLogin(username, password)) {
                return admin;
            }
        }
    
        return null;
    }
    
    /**
     * Memuat data admin dari file database.
     */
    private void loadAdminData() {  // Metode untuk memuat data admin dari file database
        try (BufferedReader reader = new BufferedReader(new FileReader("admin_database.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    listAdmin.add(new Admin(parts[0], parts[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: admin_database.txt not found. Creating the file...");
            createAdminDatabaseFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     /**
     * Membuat file database admin jika belum ada.
     */
    private void createAdminDatabaseFile() {  // Metode untuk membuat file database admin jika belum ada
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("admin_database.txt"))) {
            // Creating an empty file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Mendaftarkan admin baru dan menyimpannya ke file database.
     *
     * @param username username admin baru
     * @param password password admin baru
     */
    public void signUpAdmin(String username, String password) {  // Metode untuk mendaftarkan admin baru
        Admin newAdmin = new Admin(username, password);
        saveAdminData(newAdmin);
        System.out.println("\n     Akun Admin berhasil terdaftar!");
        System.out.println("+--------------------------------------+");
    }

    /**
    * @param admin Admin yang akan disimpan ke dalam file database
    */
    private void saveAdminData(Admin admin) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("admin_database.txt", true))) {
            writer.write(admin.getUsername() + "," + admin.getPassword() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Metode {@code terimaTransaksi} menampilkan daftar transaksi belum dikonfirmasi,
    * meminta input ID transaksi admin, dan memanggil {@code konfirmasiTransaksi}.
    * Jika tidak ada transaksi belum dikonfirmasi, tampilkan pesan tidak ada transaksi.
    */
    public void terimaTransaksi() {
        // Menampilkan daftar transaksi yang belum dikonfirmasi
        transaksi.lihatListTransaksiBelumDikonfirmasi();
    
        // Pemanggilan loadTransaksiBelumDikonfirmasi di sini
        Map<String, Invoice> daftarInvoices = transaksi.loadTransaksiBelumDikonfirmasi();
    
        if (daftarInvoices.isEmpty()) {
            System.out.println("Tidak ada transaksi yang belum dikonfirmasi.");
            return; // Keluar dari metode jika tidak ada transaksi yang belum dikonfirmasi
        }
    
        Scanner scanner = new Scanner(System.in);
        System.out.println("+--------------------------------------+");
        System.out.print("Masukkan ID Transaksi yang ingin Anda konfirmasi: ");
        String idTransaksi = scanner.nextLine();
    
        // Melakukan konfirmasi transaksi
        konfirmasiTransaksi(idTransaksi);
    }
    
    /**
    * Konfirmasi transaksi berdasarkan ID. Jika ID ditemukan, transaksi dikonfirmasi,
    * status pelanggan diubah, dan transaksi dihapus. Jika tidak ditemukan, tampilkan pesan kesalahan.
    *
    * @param idTransaksi ID transaksi yang akan dikonfirmasi
    */
    public void konfirmasiTransaksi(String idTransaksi) {
        Map<String, Invoice> daftarInvoices = transaksi.loadTransaksiBelumDikonfirmasi();

        if (daftarInvoices.containsKey(idTransaksi)) {
            //Invoice invoice = daftarInvoices.get(idTransaksi);
            customerDriver.setStatusDikonfirmasi(true);
            System.out.println("Pembelian dengan transaksi Id "+idTransaksi+ " berhasil dikonfirmasi");
            System.out.println("Customer sudah bisa mencetak INVOICE");

            transaksi.hapusTransaksiDikonfirmasi(idTransaksi);
        } else {
            System.out.println("Transaksi dengan ID " + idTransaksi + " tidak ditemukan.");
        }
    }

     /**
     * Menampilkan menu admin dan menangani pilihan pengguna.
     */
    public void showMenu() {
        int choice;
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("+--------------------------------------+");
        System.out.println("   Anda Berhasil Masuk Sebagai Admin");
        do {
            System.out.println("+============= MENU ADMIN =============+");
            System.out.println("| 1. Tambah Barang                     |");
            System.out.println("| 2. Hapus Barang                      |");
            System.out.println("| 3. Edit Barang                       |");
            System.out.println("| 4. Lihat Daftar Barang               |");
            System.out.println("| 5. Terima Transaksi                  |");
            System.out.println("| 6. Lihat Daftar transaksi            |");
            System.out.println("| 0. Logout                            |");
            System.out.println("+--------------------------------------+");
            System.out.print("              Pilih menu: ");
            choice = scanner.nextInt();
    
            switch (choice) {
                case 1:
                    listBarang.tambahBarang();
                    break;
                case 2:
                    listBarang.hapusBarang(listBarang.getDaftarBarang());
                    break;
                case 3:
                    listBarang.editBarang();
                    break;
                case 4:
                    // Memastikan data barang yang ditampilkan selalu yang terbaru
                    listBarang.updateDaftarBarang();
                    listBarang.lihatListBarang(listBarang.getDaftarBarang());
                    break;
                case 5:
                    terimaTransaksi();
                    break;
                case 6:
                    // Tampilkan transaksi yang sedang berlangsung
                    transaksi.lihatListTransaksi(); // Menampilkan list transaksi yang sedang berlangsung
                    break;
                case 0:
                    System.out.println("             Logout berhasil");
                    break;
                default:
                    System.out.println("    Pilihan tidak tersedia, coba lagi");
            }
        } while (choice != 0);
        //scanner.close();
        // Save the final list of items to the file before exiting
        listBarang.saveDaftarBarangToFile();
    }
}
