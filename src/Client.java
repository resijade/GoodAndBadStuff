import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            String host = IO.readln("Enter host name:\n");
            Socket socket = new Socket(host, 1999);
            try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
