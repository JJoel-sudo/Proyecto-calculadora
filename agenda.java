import java.io.*;
import java.util.ArrayList;


class Contacto implements Serializable {
    private String nombre;
    private String telefono;

    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Teléfono: " + telefono;
    }
}

class Agenda {
    private ArrayList<Contacto> contactos;
    private final String archivo = "agenda.dat";

    public Agenda() {
        contactos = cargarContactos();
    }

    public void agregarContacto(String nombre, String telefono) {
        contactos.add(new Contacto(nombre, telefono));
        guardarContactos();
    }

    public void eliminarContacto(String nombre) {
        contactos.removeIf(contacto -> contacto.getNombre().equalsIgnoreCase(nombre));
        guardarContactos();
    }

    public void mostrarContactos() {
        if (contactos.isEmpty()) {
            System.out.println("La agenda está vacía.");
        } else {
            for (Contacto contacto : contactos) {
                System.out.println(contacto);
            }
        }
    }

    private void guardarContactos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(contactos);
        } catch (IOException e) {
            System.out.println("Error al guardar los contactos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Contacto> cargarContactos() {
        File file = new File(archivo);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Contacto>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar los contactos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

