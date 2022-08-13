import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import bookstore.Book;
import bookstore.BookCategory;
import bookstore.Transactions;

public class Main {

    static String str;
    static Scanner readIn;
    static int pilihan;

    static ArrayList<BookCategory> bookCategory;
    static ArrayList<Book> books;
    static ArrayList<Book> booksQuery;
    static ArrayList<Transactions> pembelian;

    public static void main(String[] args) {
        readIn = new Scanner(System.in);

        // Data Initialization
        initDataBookCategory("..\\data\\book-cat.dat");
        initDataBookList("..\\data\\books.dat");
        pembelian = new ArrayList<Transactions>();

        // Show menu
        title();
        menu();

    }

    private static void initDataBookCategory(String infile) {

        try {
            FileReader fr=new FileReader(infile);
            BufferedReader br=new BufferedReader(fr);
            StringBuffer sb=new StringBuffer();
            String line;

            bookCategory = new ArrayList<BookCategory>();
            String[] rec;

            while((line=br.readLine())!=null) {
                rec = line.split("\\|");
                BookCategory categories = new BookCategory(rec[0], rec[1]);
                bookCategory.add(categories);
            }
            fr.close();

        }
        catch (IOException e) {
            // null
        }

    }


    private static void initDataBookList(String infile) {

        try {
            FileReader fr=new FileReader(infile);
            BufferedReader br=new BufferedReader(fr);
            StringBuffer sb=new StringBuffer();
            String line;

            books = new ArrayList<Book>();
            String[] rec;

            while((line=br.readLine())!=null) {
                rec = line.trim().split("\\|");
                Book bookItem = new Book(Integer.parseInt(rec[0]), rec[1]);
                bookItem.setCategory(rec[2]);
                bookItem.setAuthor(rec[3]);
                bookItem.setPublisher(rec[4]);
                bookItem.setPrice(Double.parseDouble(rec[5]));
                books.add(bookItem);
            }
            fr.close();

        }
        catch (IOException e) {
            // null
        }

    }

    public static void copyArray(int[] a, int[] b) {
        for(int i=0; i < a.length; i++) {
            b[i] = a[i];
        }
    }

    public static void title() {
        System.out.println("-----              Selamat Datang di Toko Buku \"Sejahtera\"              -----");
        System.out.println();
    }

    public static void menu() {
        System.out.println("MENU: | (1) Lihat Buku         | (2) Keranjang      | (0) Keluar");
        System.out.println();
        System.out.print("Pilih Menu : ");
        str = readIn.nextLine();
        pilihan = Integer.parseInt(str);
        System.out.println();

        submenu(pilihan);
    }

    public static void submenu(int sub) {

        switch(sub) {
            case 1:
                System.out.println(">> Toko Buku >> Daftar Katalog");
                for (int i = 0; i < bookCategory.size(); i++) {
                    System.out.println("   (" + Integer.toString(i + 1) + ") " + bookCategory.get(i).category);
                }
                System.out.println("   (0) << Kembali");
                System.out.println();

                System.out.print("   Pilih Katalog : ");
                str = readIn.nextLine();
                pilihan = Integer.parseInt(str);
                System.out.println();
                if(pilihan == 0) {
                    menu();
                } else {
                    pilihkatalog( bookCategory.get(pilihan-1).code );
                }
                break;

            case 2:
                checkout();
                break;

            default:

        }


    }


    public static void pilihkatalog(String code) {

        System.out.println(">> Toko Buku >> Daftar Buku");

        booksQuery = new ArrayList<Book>();
        for (int i = 0; i < books.size(); i++) {
            if(code.equals(books.get(i).categoryCode)) {
                booksQuery.add(books.get(i));
            }
        }

        for (int i = 0; i < booksQuery.size(); i++) {
            System.out.println("   [" + Integer.toString(booksQuery.get(i).bookId) + "] " + booksQuery.get(i).title);
            System.out.println("       Penulis   : " + booksQuery.get(i).author);
            System.out.println("       Publisher : " + booksQuery.get(i).publisher);
            System.out.println("       Harga     : Rp. " + Double.toString(booksQuery.get(i).price) );
        }
        System.out.println("   (0) << Kembali");
        System.out.println();
        pilihbuku();
    }

    public static void pilihbuku() {
        System.out.print("     - Pilih Buku yang akan dibeli : ");
        str = readIn.nextLine();
        pilihan = Integer.parseInt(str);

        if(pilihan > 0) {

            Double harga = 0.0;
            Book bookSelected = new Book(0, "");
            for (int i = 0; i < booksQuery.size(); i++) {
                if(booksQuery.get(i).bookId == pilihan) {
                    System.out.println("     - Judul Buku : " + booksQuery.get(i).title );
                    System.out.println("     - Harga : Rp. " + Double.toString(booksQuery.get(i).price) );
                    harga = booksQuery.get(i).price;
                    bookSelected = booksQuery.get(i);
                }
            }
            System.out.print("     - Jumlah Buku  : ");
            str = readIn.nextLine();
            int jml = Integer.parseInt(str);
            Double total = harga * jml;
            System.out.println("     - Total  : Rp. " + Double.toString(total));

            // Input pembelian
            if(jml > 0) {
                Transactions beliBuku = new Transactions(bookSelected, jml);
                pembelian.add(beliBuku);
            }

            System.out.print("     Beli lagi? (y/N) : ");
            str = readIn.nextLine();
            System.out.println();
            if(str.equals("Y") || str.equals("y") ) {
                submenu(1);
            } else {
                menu();
            }

        } else {
            submenu(1);
        }

    }


    public static void checkout() {
        System.out.println(">> Toko Buku >> Keranjang");
        Double grandTotal = 0.0;
        for (int i = 0; i < pembelian.size(); i++) {
            System.out.print("   " + Integer.toString(i+1) + ". ");
            System.out.print(pembelian.get(i).bookRef.title);
            System.out.println();
            System.out.print("      Rp. " + Double.toString(pembelian.get(i).bookRef.price) + "              ");
            System.out.print(" x  " + Integer.toString(pembelian.get(i).bookCount) + "              ");
            System.out.print(" = Rp. " + Double.toString(pembelian.get(i).getTotalPrice()) );
            System.out.println();

            grandTotal = grandTotal + pembelian.get(i).getTotalPrice();
        }
        System.out.println("   ----------------------------------------------------------------------------------");
        System.out.println("   Total Pembelian :                                 Rp. " + Double.toString(grandTotal));
        System.out.println("   (0) << Kembali");
        System.out.println();

        if (grandTotal > 0) {
            System.out.print("   Bayar           :  Rp. ");
            str = readIn.nextLine();
            Double bayar = Double.parseDouble(str);
            if (bayar > 0) {
                Double kembali = bayar - grandTotal;
                System.out.println("   Kembali         :  Rp. " + Double.toString(kembali));
                System.out.println();
                System.out.println();
                System.out.println("----------------------- TERIMAKASIH - PEMBAYARAN ANDA TELAH LUNAS -----------------------");
                System.out.println("                                    \"SELAMAT MEMBACA\" ");
                System.out.println("                                  Semoga harimu menyenangkan");
                System.out.println();
                System.out.println();
                System.out.println();
                pembelian.clear();
            }

        }

        menu();
    }



}

