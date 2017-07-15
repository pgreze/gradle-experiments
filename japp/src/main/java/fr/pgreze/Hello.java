package fr.pgreze;

public class Hello {

    public static void main(String[] args) {
        Library lib = new Library();
        if (lib.someLibraryMethod()) {
            System.out.println("Hello world");
        }
    }
}
