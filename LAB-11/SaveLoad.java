import java.io.*;

class Serializable implements java.io.Serializable {
    int id;
    String name;
    transient String password;

    Serializable(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String toString() {
        return id + " " + name + " " + password;
    }
}

public class SaveLoad {
    public static void main(String[] args) {
        Serializable[] objects = {
            new Serializable(1, "Rudri", "1234"),
            new Serializable(2, "Trusha", "5678")
        };

        try {
            ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("data.txt")
            );

            out.writeObject(objects);
            out.close();

            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("data.txt")
            );

            Serializable[] newObjects = (Serializable[]) in.readObject();
            in.close();

            for (Serializable obj : newObjects) {
                System.out.println(obj);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}