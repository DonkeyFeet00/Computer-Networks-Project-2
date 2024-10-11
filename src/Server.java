import java.io.*;
import java.net.*;
import java.util.ArrayList;

class Server {

    public static void main(String args[]) {

        ArrayList<Integer> inputValues = new ArrayList<Integer>();

        try {

            // Create server Socket that listens/bonds to port/endpoint address 6666 (any port id of your choice, should be >=1024, as other port addresses are reserved for system use)
            ServerSocket mySocket = new ServerSocket(6666);
            System.out.println("Startup the server side over port 6666 ....");

            // use the created ServerSocket and accept() to start listening for incoming client requests targeting this server and this port
            Socket connectedClient = mySocket.accept();

            // reaching this point means that a client established a connection with your server and this particular port.
            System.out.println("Connection established");


            // BufferReader
            BufferedReader br = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));

            // PrintStream
            PrintStream ps = new PrintStream(connectedClient.getOutputStream());


            // Let's keep reading data from the client, as long as the client doesn't send "exit".
            String incomingCommand = "";
            while (incomingCommand != "exit") {
                incomingCommand = br.readLine();
                System.out.println("message received: " + incomingCommand);   //print the incoming data from the client

                if (incomingCommand.matches("Add \\d+"))
                    ps.println(add(inputValues, incomingCommand));


                else if (incomingCommand.matches("Remove \\d+"))
                    ps.println(remove(inputValues, incomingCommand));


                else if (incomingCommand.matches("Clear \\d+"))
                    ps.println(clear(inputValues));


                else if (incomingCommand.matches("Get_Summation"))
                    ps.println(getSummation(inputValues));


                else if (incomingCommand.matches("Get_Minimum")) {
                    ps.println(getMinimum(inputValues));
                }

                else if (incomingCommand.matches("Get_Maximum")) {
                    ps.println(getMaximum(inputValues));
                }

                else if (incomingCommand.matches("Display_Content")) {
                    ps.println(displayContent(inputValues));
                }


            }

            System.out.println("Closing the connection and the sockets");

            // close
            ps.close();
            br.close();
            mySocket.close();
            connectedClient.close();

        } catch (Exception exc) {
            System.out.println("Error :" + exc.toString());
        }

    }

    private static String displayContent(ArrayList<Integer> arrList) {
        String output = "";
        for (int i = 0; i < arrList.size(); i++)
            output += arrList.get(i) + ", ";
        return output;
    }

    private static String getMaximum(ArrayList<Integer> arrList) {
        int max = arrList.getFirst();
        for (int i = 1; i < arrList.size(); i++)
            if (arrList.get(i) > max)
                max = arrList.get(i);
        return "The maximum is " + max;
    }

    private static String getMinimum(ArrayList<Integer> arrList) {
        int min = arrList.getFirst();
        for (int i = 1; i < arrList.size(); i++)
            if (arrList.get(i) < min)
                min = arrList.get(i);
        return "The minimum is " + min;
    }

    private static String clear(ArrayList<Integer> arrList) {
        arrList.clear();
        return "cleared successfully";
    }

    private static String getSummation(ArrayList<Integer> arrList) {
        int sum = 0;
        for (int i = 0; i < arrList.size(); i++)
            sum += arrList.get(i);
        return "The summation is " + sum;
    }



    //Adds a number to the end of the array list. Parameters: arraylist to be added to, and the command that was run
    private static String add(ArrayList<Integer> arrList, String command) {
        //get substring of the number start, and convert to int
        int numToAdd = Integer.parseInt(command.substring(4));
        arrList.add(numToAdd);
        return "added successfully";
    }

    private static String remove(ArrayList<Integer> arrList, String command) {
        int numToRemove = Integer.parseInt(command.substring(7));
        arrList.removeIf(num -> (num == numToRemove));
        return "removed successfully";
    }
}

