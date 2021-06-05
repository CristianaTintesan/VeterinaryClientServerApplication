package Client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.Instant;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Controllers.*;
import Raports.PDFRaport;
import Raports.TextRaport;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import model.*;
import start.Launcher;
import utils.Response;
import utils.Request;


public class Client
{


    public static class Connection extends Thread
    {
        private final Socket socket;
        private static ObjectOutputStream output;
        private final ObjectInputStream input;

        public Connection(Socket socket) throws IOException
        {
            this.socket = socket;
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

        }

        @Override
        public void run()
        {
            try
            {
                while (socket.isConnected())
                {
                    // Seems that input.available() is not reliable?
                    boolean serverHasData = socket.getInputStream().available() > 0;
                    if (serverHasData) {

                        Response response = (Response) input.readObject();

                        System.out.println(Instant.now() + " Got from server: " + response);
                        handleResponse(response);
                    }

                    try
                    {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e)
                    {
                        Logger logger = Logger.getLogger(Client.class.getName());
                        logger.log(Level.INFO,"exception message");
                    }
                }

            }
            catch (IOException | ClassNotFoundException e)
            {
                Logger logger = Logger.getLogger(Client.class.getName());
                logger.log(Level.INFO,"exception message");
            }
        }

        public void handleResponse(Response response) throws IOException {
            System.out.println(response.getAction());
            if("handleUserLoginResponse".equals(response.getAction())) {
                handleUserLoginResponse(response);
            }
            if("handleAddConsultationResponse".equals(response.getAction())) {
                handleAddConsultationResponse(response);
            }
            if("handleAddAnimalResponse".equals(response.getAction())) {
                handleAddAnimalResponse(response);
            }
            if("handleAddUserResponse".equals(response.getAction())) {
                handleAddUserResponse(response);
            }
            if("handleUpdateAnimalResponse".equals(response.getAction())) {
                handleUpdateAnimalResponse(response);
            }
            if("handleUpdateUserResponse".equals(response.getAction())) {
                handleUpdateUserResponse(response);
            }
            if("handleUpdateConsultationResponse".equals(response.getAction())) {
                handleUpdateConsultationResponse(response);
            }
            if("handleDeleteUserResponse".equals(response.getAction())) {
                handleDeleteUserResponse(response);
            }
            if("handleViewUserResponse".equals(response.getAction())) {
                handleViewUserResponse(response);
            }
            if("handleDeleteAnimalResponse".equals(response.getAction())) {
                handleDeleteAnimalResponse(response);
            }
            if("handleDeleteConsultationResponse".equals(response.getAction())) {
                handleDeleteConsultationResponse(response);
            }
            if("handleViewAnimalResponse".equals(response.getAction())) {
                handleViewAnimalResponse(response);
            }
            if("handleViewConsultationResponse".equals(response.getAction())) {
                handleViewConsultationResponse(response);
            }

            if("handleReportResponse".equals(response.getAction())) {
                handleReportResponse(response);
            }

            if("handleAddProductResponse".equals(response.getAction())) {
                handleAddProductResponse(response);
            }
            if("handleDeleteProductResponse".equals(response.getAction())) {
                handleDeleteProductResponse(response);
            }
            if("handleViewProductResponse".equals(response.getAction())) {
                handleViewProductResponse(response);
            }
            if("handleUpdateProductResponse".equals(response.getAction())) {
                handleUpdateProductResponse(response);
            }


        }

        private void handleUserLoginResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("LOGIN SUCCESS");
                try{
                    User user = new ObjectMapper().readValue(responseData.getJsonData(), User.class);
                    LoginPage.setCurrentUser(user);

                }catch (JsonProcessingException e){
                    Logger logger = Logger.getLogger(Client.class.getName());
                    logger.log(Level.INFO,"exception message");
                }


                Request request = new Request ("handleViewUser", "");
                request.setSenderType("user");
                System.out.println("request to print all users");
                output.writeObject(request);


                Request request2 = new Request ("handleViewAnimal", "");
                request2.setSenderType("user");
                System.out.println("request to print all animals");
                output.writeObject(request2);


                Request request3 = new Request ("handleViewConsultation", "");
                request3.setSenderType("user");
                System.out.println("request to print all consultations");
                output.writeObject(request3);

                Request request4 = new Request("handleReport", "");
                request4.setSenderType("user");
                Client.Connection.sendMessageToServer(request4);

                Request request5 = new Request ("handleViewProduct", "");
                request5.setSenderType("user");
                System.out.println("request to print all products");
                output.writeObject(request5);


            } else {
                System.out.println("LOGIN FAILED");
            }
        }

        private static void handleAddConsultationResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
             System.out.println("ADD CONSULTATION SUCCESS");
               System.out.println("send request");
               Request request = new Request ("handleViewConsultation", "");
               request.setSenderType("user");
               System.out.println("request to print all consultations");
               output.writeObject(request);
           } else {
             System.out.println("ADD CONSULTATION FAILED");
           }
        }

        private static void handleAddAnimalResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("ADD ANIMAL SUCCESS");
                System.out.println("send request");
                Request request = new Request ("handleViewAnimal", "");
                request.setSenderType("user");
                System.out.println("request to print all animals");
                output.writeObject(request);
            } else {
                System.out.println("ADD ANIMAL FAILED");
            }
        }

        private static void handleAddUserResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("ADD EMPLOYEE SUCCESS");
                System.out.println("send request");
                Request request = new Request ("handleViewUser", "");
                request.setSenderType("user");
                System.out.println("request to print all users");
                output.writeObject(request);
            } else {
                System.out.println("ADD EMPLOYEE FAILED");
            }
        }

        private static void handleAddProductResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("ADD PRODUCT SUCCESS");
                System.out.println("send request");
                Request request = new Request ("handleViewProduct", "");
                request.setSenderType("user");
                System.out.println("request to print all products in stock");
                output.writeObject(request);
            } else {
                System.out.println("ADD PRODUCT FAILED");
            }
        }

        private static void handleUpdateAnimalResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("UPDATE ANIMAL SUCCESS");
                System.out.println("ADD ANIMAL SUCCESS");
                System.out.println("send request");
                Request request = new Request ("handleViewAnimal", "");
                request.setSenderType("user");
                System.out.println("request to print all animals");
                output.writeObject(request);
            } else {
                System.out.println("UPDATE ANIMAL FAILED");
            }
        }

        private static void handleUpdateProductResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("UPDATE PRODUCT SUCCESS");
                System.out.println("send request");
                Request request = new Request ("handleViewProduct", "");
                request.setSenderType("user");
                System.out.println("request to print all products");
                output.writeObject(request);
            } else {
                System.out.println("UPDATE PRODUCT FAILED");
            }
        }

        private static void handleUpdateUserResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("UPDATE USER SUCCESS");
            } else {
                System.out.println("UPDATE USER FAILED");
            }
        }

        private static void handleUpdateConsultationResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("UPDATE CONSULTATION SUCCESS");
                System.out.println("ADD CONSULTATION SUCCESS");
                System.out.println("send request");
                Request request = new Request ("handleViewConsultation", "");
                request.setSenderType("user");
                System.out.println("request to print all consultations");
                output.writeObject(request);
            } else {
                System.out.println("UPDATE CONSULTATION FAILED");
            }
        }

        private static void handleDeleteUserResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("DELETE EMPLOYEE SUCCESS");
            } else {
                System.out.println("DELETE EMPLOYEE FAILED");
            }
        }

        private static void handleViewUserResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("VIEW USERS SUCCESS");
                ArrayList<User> users ;
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                    users = mapper.readValue(responseData.getJsonData(), mapper.getTypeFactory().constructCollectionType(ArrayList.class, User.class));

                    UsersPage.viewUsers(users);

                } catch (JsonProcessingException e) {
                    Logger logger = Logger.getLogger(Client.class.getName());
                    logger.log(Level.INFO,"exception message");
                }
            } else {
                System.out.println("VIEW EMPLOYEE FAILED");
            }
        }


        private static void handleViewAnimalResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("VIEW ANIMALS SUCCESS");
                ArrayList<Animal> animals ;
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                    animals = mapper.readValue(responseData.getJsonData(), mapper.getTypeFactory().constructCollectionType(ArrayList.class, Animal.class));

                    AnimalsPage.viewAnimals(animals);

                } catch (JsonProcessingException e) {
                    Logger logger = Logger.getLogger(Client.class.getName());
                    logger.log(Level.INFO,"exception message");
                }
            } else {
                System.out.println("VIEW ANIMALS FAILED");
            }
        }

        private static void handleViewProductResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("VIEW PRODUCTS SUCCESS");
                ArrayList<Stock> products  ;
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                    products = mapper.readValue(responseData.getJsonData(), mapper.getTypeFactory().constructCollectionType(ArrayList.class, Stock.class));
                    StockPage.viewProducts(products);

                } catch (JsonProcessingException e) {
                    Logger logger = Logger.getLogger(Client.class.getName());
                    logger.log(Level.INFO,"exception message");
                }
            } else {
                System.out.println("VIEW PRODUCTS FAILED");
            }
        }

        private static void handleViewConsultationResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("VIEW CONSULTATIONS SUCCESS");
                ArrayList<Consulation> consultations ;
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
                    consultations = mapper.readValue(responseData.getJsonData(), mapper.getTypeFactory().constructCollectionType(ArrayList.class, Consulation.class));
                    ConsultationsPage.viewConsultation(consultations);
                    //System.out.println(consultations);
                    //System.out.println("consultationsssss");

                } catch (JsonProcessingException e) {
                    Logger logger = Logger.getLogger(Client.class.getName());
                    logger.log(Level.INFO,"exception message");
                }
            } else {
                System.out.println("VIEW CONSULTATIONS FAILED");
            }
        }

        private static void handleDeleteAnimalResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("DELETE ANIMAL SUCCESS");
            } else {
                System.out.println("DELETE ANIMAL FAILED");
            }
        }

        private static void handleDeleteProductResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("DELETE PRODUCT SUCCESS");
            } else {
                System.out.println("DELETE PRODUCT FAILED");
            }
        }

        private static void handleDeleteConsultationResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("DELETE CONSULTATION SUCCESS");
            } else {
                System.out.println("DELETE CONSULTATION FAILED");
            }
        }

        private static void handleReportResponse(Response responseData) throws IOException {

            if ("success".equals(responseData.getStatus())) {
                System.out.println("GENERATE PDF REPORT SUCCESS");
                ArrayList<Consulation> consultations ;
                try {
                    Request request = new Request ("handleViewConsultation", "");
                    request.setSenderType("user");

                    output.writeObject(request);
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                    consultations = mapper.readValue(responseData.getJsonData(), mapper.getTypeFactory().constructCollectionType(ArrayList.class, Consulation.class));

                    PDFRaport.viewConsultation(consultations);
                    TextRaport.viewConsultation(consultations);


                } catch (JsonProcessingException e) {
                    Logger logger = Logger.getLogger(Client.class.getName());
                    logger.log(Level.INFO,"exception message");
                }
            } else {
                System.out.println("GENERATE PDF REPORT FAIL");
            }
        }

        public static void sendMessageToServer(Request request) throws IOException
        {
            output.writeObject(request);
        }


    }

    public static void main(String[] args) {

        Connection connection ;
        try {
            connection = new Connection(new Socket("localhost", 3000));
            connection.start();
            Launcher.main(args);

        } catch (IOException e) {
            Logger logger = Logger.getLogger(Client.class.getName());
            logger.log(Level.INFO,"exception message");
        }









    }
}