
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.*;
import entity.Animal;
import entity.Consulation;
import entity.Stock;
import entity.User;
import utils.Json;
import utils.Request;
import utils.Response;

public class ClientManager extends Thread{
    private final Socket clientSocket;
    private long lastSentMessageMillis;
    private boolean isEmployee;
    private User loggedUser;
    private ObjectOutputStream output;

    public ClientManager(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {
            this.output = output;
            while (clientSocket.isConnected()) {
                if (System.currentTimeMillis() - lastSentMessageMillis > 10) {
                    lastSentMessageMillis = System.currentTimeMillis();
                }

                boolean clientHasData = clientSocket.getInputStream().available() > 0;
                if (clientHasData) {
                    Request request = (Request) input.readObject();
                    Logger log = Logger.getLogger(ClientManager.class.getName());
                    log.fine(Instant.now() + " Got from client: " + clientSocket.getPort() + " message: " + request);
                    handleRequest(request, output); //
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Logger logger = Logger.getLogger(ClientManager.class.getName());
                    logger.log(Level.INFO,"exception message");
                }
            }
            Logger log = Logger.getLogger(ClientManager.class.getName());
            log.fine(Instant.now() + " Client disconnected?");
        } catch (IOException e) {
            Logger logger = Logger.getLogger(ClientManager.class.getName());
            logger.log(Level.INFO,"exception message");
        } catch (ClassNotFoundException e) {
            Logger logger = Logger.getLogger(ClientManager.class.getName());
            logger.log(Level.INFO,"exception message");
        } catch (SQLException throwables) {
            Logger logger = Logger.getLogger(ClientManager.class.getName());
            logger.log(Level.INFO,"exception message");
        }

        // cleanup
        try {
            clientSocket.close();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(ClientManager.class.getName());
            logger.log(Level.INFO,"exception message");
        }
    }

    private void handleRequest(Request request, ObjectOutputStream output) throws IOException, SQLException {
        switch(request.getAction()){
            case "handleUserLogin":
                handleUserLogin(request.getJsonData(), output);
                break;
            case "handleAddConsultation":
                handleAddConsultation(request.getJsonData(), output);
                break;
            case "handleAddAnimal":
                handleAddAnimal(request.getJsonData(), output);
                break;
            case "handleAddProduct":
                handleAddProduct(request.getJsonData(), output);
                break;
            case "handleAddUser":
                handleAddUser(request.getJsonData(), output);
                break;
            case "handleUpdateAnimal":
                handleUpdateAnimal(request.getJsonData(), output);
                break;
            case "handleUpdateUser":
                handleUpdateUser(request.getJsonData(), output);
                break;
            case "handleUpdateProduct":
                handleUpdateProduct(request.getJsonData(), output);
                break;
            case "handleUpdateConsultation":
                handleUpdateConsultation(request.getJsonData(), output);
                break;
            case "handleDeleteUser":
                handleDeleteUser(request.getJsonData(), output);
                break;
            case "handleViewUser":
                handleViewUser(request.getJsonData(), output);
                break;
            case "handleDeleteAnimal":
                handleDeleteAnimal(request.getJsonData(), output);
                break;
            case "handleDeleteProduct":
                handleDeleteProduct(request.getJsonData(), output);
                break;
            case "handleDeleteConsultation":
                handleDeleteConsultation(request.getJsonData(), output);
                break;
            case "handleViewAnimal":
                handleViewAnimal(request.getJsonData(), output);
                break;
            case "handleViewConsultation":
                handleViewConsultation(request.getJsonData(), output);
                break;
            case "handleReport":
                handleReport(request.getJsonData(), output);
                break;
            case "handleViewProduct":
                handleViewProduct(request.getJsonData(), output);
                break;
            default :
                break;

        }

    }

    private void handleUserLogin(String usernameAndPasssword, ObjectOutputStream output) throws IOException, SQLException {
        String[] split = usernameAndPasssword.split("\\s+");
        String username = split[0];
        String password = split[1];

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getByUsername(username,password);
        Response response;

        if(user != null){
            Logger log = Logger.getLogger(ClientManager.class.getName());
            log.fine("user found in database");
            this.setLoggedUser(user);
            this.setIsEmployee(true);
            Server.addUserManager(this);
            response = new Response("handleUserLoginResponse", "success", Json.convertToJSON(user));
        }else {
            response = new Response("handleUserLoginResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }

    private void handleAddConsultation(String animalIdAndDate, ObjectOutputStream output) throws IOException, SQLException {

        String[] split = animalIdAndDate.split("\\s+");
        String animalId = split[0];
        String consultationStatus = split[1];
        String consultationDate = split[2];

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        ConsultationDAO consultationDAO = new ConsultationDAO();
        Consulation consulation = new Consulation(0, Integer.parseInt(animalId), Date.valueOf(consultationDate), consultationStatus);

        Response response;

        if(consulation != null ){

            consultationDAO.insert(consulation);
            Server.addUserManager(this);
            response = new Response("handleAddConsultationResponse", "success", Json.convertToJSON(consulation));
        }else {
            response = new Response("handleAddConsultationResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }


    private void handleAddAnimal(String ownerIdAndName, ObjectOutputStream output) throws IOException, SQLException {
        String[] split = ownerIdAndName.split("\\s+");
        String idOwner = split[0];
        String animalName = split[1];

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        AnimalDAO animalDAO = new AnimalDAO();
        Animal animal = new Animal(animalName, Integer.parseInt(idOwner));
        Response response;

        if(animal != null && animalName.length()>=3){
            animalDAO.insert(animal);

            Server.addUserManager(this);
            response = new Response("handleAddAnimalResponse", "success", Json.convertToJSON(animal));
        }else {
            response = new Response("handleAddAnimalResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }

    private void handleAddProduct(String typeAndQuantity, ObjectOutputStream output) throws IOException, SQLException {
        String[] split = typeAndQuantity.split("\\s+");
        String quantityProduct = split[0];
        String typeProduct = split[1];

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        StockDAO stockDAO = new StockDAO();
        Stock stock = new Stock(typeProduct, Integer.parseInt(quantityProduct));
        Response response;

        if(stock != null){
            stockDAO.insert(stock);

            Server.addUserManager(this);
            response = new Response("handleAddProductResponse", "success", Json.convertToJSON(stock));
        }else {
            response = new Response("handleAddProductResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }

    private void handleAddUser(String nameAndPass, ObjectOutputStream output) throws IOException, SQLException {
        String[] split = nameAndPass.split("\\s+");
        String username = split[0];
        String password = split[1];

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();

        Response response;

        if((username.length()>=4 | password.length()>=4)){
            UserDAO userDAO = new UserDAO();
            User user = new User(username,password);
            userDAO.insert(user);

            Server.addUserManager(this);
            response = new Response("handleAddUserResponse", "success", Json.convertToJSON(user));
        }else {
            response = new Response("handleAddUserResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }

    private void handleUpdateAnimal(String ownerIdAndName, ObjectOutputStream output) throws IOException, SQLException {
        String[] split = ownerIdAndName.split("\\s+");
        String idOwner = split[0];
        String animalName = split[1];
        String animalId = split[2];

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        AnimalDAO animalDAO = new AnimalDAO();
        UserDAO userDAO = new UserDAO();
        Animal animal = animalDAO.getById(Integer.parseInt(animalId));
        User user = userDAO.getById(Integer.parseInt(idOwner));

        Response response;

        if(animal != null && user != null && animalName.length() >=3){
            animalDAO.update(Integer.parseInt(animalId), Arrays.asList(idOwner, animalName));

            Server.addUserManager(this);
            response = new Response("handleUpdateAnimalResponse", "success", Json.convertToJSON(animal));
        }else {
            response = new Response("handleUpdateAnimalResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }

    private void handleUpdateProduct(String typeAndQuantity, ObjectOutputStream output) throws IOException, SQLException {
        String[] split = typeAndQuantity.split("\\s+");
        String typeProduct = split[0];
        String quantityProduct = split[1];
        String idProduct = split[2];

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        StockDAO stockDAO = new StockDAO();
        Stock stock = stockDAO.getById(Integer.parseInt(idProduct));

        Response response;

        if(stock != null){
            stockDAO.update(Integer.parseInt(idProduct), Arrays.asList(quantityProduct, typeProduct));

            Server.addUserManager(this);
            response = new Response("handleUpdateProductResponse", "success", Json.convertToJSON(stock));
        }else {
            response = new Response("handleUpdateProductResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }

    private void handleUpdateUser(String userNameAndId, ObjectOutputStream output) throws IOException, SQLException {
        String[] split = userNameAndId.split("\\s+");
        String newName = split[0];
        String userID =  split[1];

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        UserDAO userDAO = new UserDAO();;
        User user = userDAO.getById(Integer.parseInt(userID));

        Response response;

        if(user != null && newName.length()>=4){
            userDAO.update(Integer.parseInt(userID), Arrays.asList(newName));

            Server.addUserManager(this);
            response = new Response("handleUpdateUserResponse", "success", Json.convertToJSON(user));
        }else {
            response = new Response("handleUpdateUserResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }

    private void handleUpdateConsultation(String animalIdDateConsultationId, ObjectOutputStream output) throws IOException, SQLException {
        String[] split = animalIdDateConsultationId.split("\\s+");
        String animalId = split[0];
        String newDate =  split[1];
        String consultationId =  split[2];
        String consultationState = split[3];

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        ConsultationDAO consultationDAO = new ConsultationDAO();
        AnimalDAO animalDAO = new AnimalDAO();
        Animal animal = animalDAO.getById(Integer.parseInt(animalId));
        Consulation consulation = consultationDAO.getById(Integer.parseInt(consultationId));
        StockDAO stockDAO = new StockDAO();
        List<Stock>products = stockDAO.getAll();
        int ok = 1;

        if ("Progress".equals(consultationState)){
            for (Stock p : products){
                if (p.getQuantity()==0){
                    ok = 0;
                }
            }
        }

        Response response;

        if(animal != null && ok==1){
            consultationDAO.update(Integer.parseInt(consultationId), Arrays.asList(animalId, newDate, consultationState));

            if ("Progress".equals(consultationState)){
                products=stockDAO.getAll();
                for (Stock p : products){
                    StockDAO stockDAO2 = new StockDAO();
                    Stock stock = stockDAO2.getById(p.getId());
                    stockDAO2.update(p.getId(), Arrays.asList(String.valueOf(p.getQuantity()-1), p.getType()));

                }
            }

            Server.addUserManager(this);
            response = new Response("handleUpdateConsultationResponse", "success", Json.convertToJSON(consulation));
        }else {
            response = new Response("handleUpdateConsultationResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }

    private void handleDeleteUser(String idUser, ObjectOutputStream output) throws IOException, SQLException {
        String idEmployee = idUser;

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getById(Integer.parseInt(idEmployee));
        Response response;

        if(user != null){
            userDAO.delete(Integer.parseInt(idEmployee));

            Server.addUserManager(this);
            response = new Response("handleDeleteUserResponse", "success", Json.convertToJSON(user));
        }else {
            response = new Response("handleDeleteUserResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }

    private void handleViewUser(String userInf, ObjectOutputStream output) throws IOException, SQLException {

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAll();
        Response response;
        //System.out.println(users);
        if(users != null){

            Server.addUserManager(this);
            response = new Response("handleViewUserResponse", "success", Json.convertToJSON(users));
        }else {
            response = new Response("handleViewUserResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }

    private void handleViewAnimal(String userInf, ObjectOutputStream output) throws IOException, SQLException {

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        AnimalDAO animalDAO = new AnimalDAO();
        List<Animal> animals = animalDAO.getAll();
        Response response;
        //System.out.println(animals);
        if(animals != null){

            Server.addUserManager(this);
            response = new Response("handleViewAnimalResponse", "success", Json.convertToJSON(animals));
        }else {
            response = new Response("handleViewAnimalResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }

    private void handleViewProduct(String productsInf, ObjectOutputStream output) throws IOException, SQLException {

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        StockDAO stockDAO = new StockDAO();
        List<Stock> products = stockDAO.getAll();
        Response response;

        if(products != null){
            Server.addUserManager(this);
            response = new Response("handleViewProductResponse", "success", Json.convertToJSON(products));
        }else {
            response = new Response("handleViewProductResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }

    private void handleViewConsultation(String consultationInf, ObjectOutputStream output) throws IOException, SQLException {

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        ConsultationDAO consultationDAO = new ConsultationDAO();
        List<Consulation> consultations = consultationDAO.getAll();
        Response response;
        //System.out.println(consultations);
        if(consultations != null){

            Server.addUserManager(this);
            response = new Response("handleViewConsultationResponse", "success", Json.convertToJSON(consultations));
        }else {
            response = new Response("handleViewConsultationResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }

    private void handleDeleteAnimal(String idAnimal, ObjectOutputStream output) throws IOException, SQLException {
        String animalId = idAnimal;

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        AnimalDAO animalDAO = new AnimalDAO();
        Animal animal = animalDAO.getById(Integer.parseInt(animalId));
        Response response;

        if(animal != null){
            animalDAO.delete(Integer.parseInt(idAnimal));

            Server.addUserManager(this);
            response = new Response("handleDeleteAnimalResponse", "success", Json.convertToJSON(animal));
        }else {
            response = new Response("handleDeleteAnimalResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }

    private void handleDeleteProduct(String idStock, ObjectOutputStream output) throws IOException, SQLException {
        String stockId = idStock;

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        StockDAO stockDAO = new StockDAO();
        Stock stock = stockDAO.getById(Integer.parseInt(stockId));
        Response response;

        if(stock != null){
            stockDAO.delete(Integer.parseInt(idStock));

            Server.addUserManager(this);
            response = new Response("handleDeleteProductResponse", "success", Json.convertToJSON(stock));
        }else {
            response = new Response("handleDeleteProductResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }



    private void handleDeleteConsultation(String idConsultation, ObjectOutputStream output) throws IOException, SQLException {
        String consultationId = idConsultation;

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        ConsultationDAO consultationDAO = new ConsultationDAO();
        Consulation consulation = consultationDAO.getById(Integer.parseInt(consultationId));
        Response response;

        consultationDAO.delete(Integer.parseInt(idConsultation));

        Server.addUserManager(this);
        response = new Response("handleDeleteConsultationResponse", "success", Json.convertToJSON(consulation));
        output.writeObject(response);
    }

    private void handleReport(String consultationInf, ObjectOutputStream output) throws IOException, SQLException {

        DBConnection dbConnection;
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();
        ConsultationDAO consultationDAO = new ConsultationDAO();
        List<Consulation> consultations = consultationDAO.getAll();
        Response response;
        //System.out.println(consultations);
        if(consultations != null){

            Server.addUserManager(this);
            response = new Response("handleReportResponse", "success", Json.convertToJSON(consultations));
        }else {
            response = new Response("handleReportResponse", "error", Json.convertToJSON(""));
        }
        output.writeObject(response);
    }


    public void setIsEmployee(boolean employee) {
        isEmployee = employee;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
