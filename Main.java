import java.util.*;

/**
 * Kelas utama yang mengatur alur program untuk aplikasi toko.
 * @author Bintang
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerDriver customerDriver = new CustomerDriver();
       
        // Loop utama program
        while (true) {
            try {
                // Tampilan menu utama
                System.out.println("|===  Selamat Datang di Toko Kami   ===|");
                System.out.println("|**************************************|");
                System.out.println("| 1. Login                             |");
                System.out.println("| 2. Daftar                            |");
                System.out.println("| 3. Keluar                            |");
                System.out.println("+--------------------------------------+");
                System.out.print("      Silahkan Pilih Menu (1/2/3): ");
                String choice = scanner.nextLine();
                
                AdminDriver adminDriver = new AdminDriver();
                
                adminDriver.setCustomerDriver(customerDriver);

                // Pemilihan aksi berdasarkan input pengguna
                switch (choice) {
                    case "1":
                        loginMenu(adminDriver, customerDriver);
                        break;
                    case "2":
                        signUp(adminDriver, customerDriver);
                        break;
                    case "3":
                        System.out.println("+--------------------------------------+");
                        System.out.println("|     Terima kasih! Sampai jumpa       |");
                        System.out.println("+--------------------------------------+");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
        }
    }

    /**
     * Metode untuk menampilkan menu login dan memproses input pengguna.
     *
     * @param adminDriver    Objek AdminDriver untuk mengelola operasi Admin.
     * @param customerDriver Objek CustomerDriver untuk mengelola operasi Customer.
     */
    public static void loginMenu(AdminDriver adminDriver, CustomerDriver customerDriver) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Tampilan menu login
            System.out.println("+--------------------------------------+");
            System.out.println("|     Pilih jenis akun untuk login     |");
            System.out.println("| 1. Admin                             |");
            System.out.println("| 2. Customer                          |");
            System.out.println("+--------------------------------------+");
            System.out.print("         Pilih opsi (1/2): ");
            String loginChoice = scanner.nextLine();

             // Pemilihan jenis akun untuk login
            switch (loginChoice) {
                case "1":
                    login(adminDriver, customerDriver, "admin");
                    break;
                case "2":
                    login(adminDriver, customerDriver, "customer");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    /**
     * Metode untuk menangani proses login berdasarkan jenis akun.
     *
     * @param adminDriver    Objek AdminDriver untuk mengelola operasi Admin.
     * @param customerDriver Objek CustomerDriver untuk mengelola operasi Customer.
     * @param userType       Jenis akun yang akan melakukan login ("admin" atau "customer").
     */
    public static void login(AdminDriver adminDriver, CustomerDriver customerDriver, String userType) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Tampilan input login
            System.out.println("+--------------------------------------+");
            System.out.print(" Masukkan username: ");
            String username = scanner.nextLine();
            System.out.print(" Masukkan password: ");
            String password = scanner.nextLine();

            // Verifikasi login berdasarkan jenis akun
            if (userType.equals("admin")) {
                Admin admin = adminDriver.verifikasiLoginAdmin(username, password);
                if (admin != null) {
                    adminDriver.showMenu();
                } else {
                    System.out.println(" Username atau password salah.");
                }
            } else if (userType.equals("customer")) {
                Customer customer = customerDriver.verifikasiLoginCustomer(username, password);
                if (customer != null) {
                    customerDriver.showMenu();
                } else {
                    System.out.println(" Username atau password salah.");
                }
            } else {
                System.out.println(" Tipe pengguna tidak valid.");
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    /**
     * Metode untuk menangani proses pendaftaran akun.
     *
     * @param adminDriver    Objek AdminDriver untuk mengelola operasi Admin.
     * @param customerDriver Objek CustomerDriver untuk mengelola operasi Customer.
     */
    public static void signUp(AdminDriver adminDriver, CustomerDriver customerDriver) {
        Scanner scanner = new Scanner(System.in);

        try {
            //Tampilan pilihan jenis akun
            System.out.println("+--------------------------------------+");
            System.out.println("            Pilih jenis akun            ");
            System.out.print("            (admin/customer)            \n ");
            String jenisAkun = scanner.nextLine();

            // Loop untuk mendapatkan input username dan password yang valid
            while (true) {
                System.out.print(" Masukkan username: ");
                String username = scanner.nextLine();

                // Pengecekan apakah username sudah terdaftar
                if (jenisAkun.equalsIgnoreCase("admin") && adminDriver.cekUsername(username)) {
                    System.out.println(" Username sudah digunakan. Silakan pilih username lain.");
                } else if (jenisAkun.equalsIgnoreCase("customer") && customerDriver.cekUsername(username)) {
                    System.out.println(" Username sudah digunakan. Silakan pilih username lain.");
                } else {
                    System.out.print(" Masukkan password: ");
                    String password = scanner.nextLine();

                    if (jenisAkun.equalsIgnoreCase("admin")) {
                        adminDriver.signUpAdmin(username, password);
                    } else if (jenisAkun.equalsIgnoreCase("customer")) {
                        customerDriver.signUpCustomer(username, password);
                    } else {
                        System.out.println(" Pilihan tidak valid.");
                    }
                    break; // Keluar dari loop setelah berhasil mendaftar
                }
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }
}

