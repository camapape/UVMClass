import java.util.ArrayList;
import java.util.List;

// clase contacto para guardar nombre, email y telefono
class Contact {
    private String name;
    private String email;
    private String phone;

    public Contact(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

// Clase Singleton para administrar la lista de contactos
class ContactManager {
    private static ContactManager instance;
    private final List<Contact> contacts;

    // constructor de Singleton
    private ContactManager() {
        contacts = new ArrayList<>();
    }

    // Obtener la instancia singleton
    public static synchronized ContactManager getInstance() {
        if (instance == null) {
            instance = new ContactManager();
        }
        return instance;
    }

    // Agraga un contacto (sincronizado por el thread)
    public synchronized void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("Added: " + contact);
    }

    // Actualizar contacto (sincronizado por el thread)
    public synchronized void updateContact(String name, String newEmail, String newPhone) {
        for (Contact contact : contacts) {
            if (contact.getName().equals(name)) {
                contact.setEmail(newEmail);
                contact.setPhone(newPhone);
                System.out.println("Updated: " + contact);
                return;
            }
        }
        System.out.println("Contact not found: " + name);
    }

    // Lista los contactos (sincronizados por thread)
    public synchronized void listContacts() {
        System.out.println("Listing all contacts:");
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }
}

// Thread para el uso de los recursos
class ContactWorker implements Runnable {
    private final ContactManager contactManager;
    private final String operation;
    private final Contact contact;

    public ContactWorker(String operation, Contact contact) {
        this.contactManager = ContactManager.getInstance();
        this.operation = operation;
        this.contact = contact;
    }

    @Override
    public void run() {
        switch (operation) {
            case "add":
                contactManager.addContact(contact);
                break;
            case "update":
                contactManager.updateContact(contact.getName(), contact.getEmail(), contact.getPhone());
                break;
            case "list":
                contactManager.listContacts();
                break;
            default:
                System.out.println("Invalid operation");
        }
    }
}

public class ContactManagerApp {
    public static void main(String[] args) {
        // Creating some contacts
        Contact contact1 = new Contact("Alice", "alice@example.com", "1234567890");
        Contact contact2 = new Contact("Bob", "bob@example.com", "0987654321");

        // Creando threads para administrar contactos
        Thread thread1 = new Thread(new ContactWorker("add", contact1));
        Thread thread2 = new Thread(new ContactWorker("add", contact2));
        Thread thread3 = new Thread(new ContactWorker("list", null));

        // Iniciando threads
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Actualizando contact
        Contact contactUpdate = new Contact("Alice", "alice.new@example.com", "1112223333");
        Thread thread4 = new Thread(new ContactWorker("update", contactUpdate));
        thread4.start();

        try {
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Listando los contactos
        thread3.start();
    }
}
