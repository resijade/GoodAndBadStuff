import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Client {
    public static void main(String[] args) {
        try {
            String host = IO.readln("Enter host name:\n");
            Socket socket = new Socket(host, 1999);
            try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                boolean done = false;
                while (!done) {
                    Song[] options = (Song[]) ois.readObject();
                    IO.println("Please vote for your favorite song:\n");
                    IO.println("1. " + options[0].getName() + ", arr. by " + options[0].getArranger() + " (" + options[0].getEloScore() + ")");
                    IO.println("2. " + options[1].getName() + ", arr. by " + options[1].getArranger() + " (" + options[1].getEloScore() + ")");
                    IO.println("3. Skip");
                    IO.println("4. Exit");
                    int choice = 0;
                    do {
                        try {
                            choice = Integer.parseInt(IO.readln("Enter choice: "));
                            if (choice < 1 || choice > 4) {
                                throw new Exception("Invalid choice");
                            }
                        } catch (Exception e) {
                            IO.println("Invalid option.");
                        }
                        oos.writeInt(choice);
                        oos.flush();
                        if (choice == 4) {
                            done = true;
                            break;
                        }
                    } while ((choice < 1) || (choice > 4));
                }
            }
        } catch (SocketException e) {
            System.err.println("Connection with server ended.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
