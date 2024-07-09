package sbu.cs.youtube.Client.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sbu.cs.youtube.Shared.POJO.Notification;
import sbu.cs.youtube.Shared.POJO.UserVideo;
import sbu.cs.youtube.Shared.POJO.Video;
import sbu.cs.youtube.Shared.Request;
import sbu.cs.youtube.Shared.Response;
import sbu.cs.youtube.YouTubeApplication;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class CreateDetailsController  {

    private File videoFile;

    private File thumbnailFile;

    @FXML
    private Button btnThumb;

    @FXML
    private TextArea fieldDescription;

    @FXML
    private TextField fieldTitle;

    @FXML
    private Text lblVideoTitle;

    @FXML
    private RadioButton radioFashion;

    @FXML
    private RadioButton radioGaming;

    @FXML
    private RadioButton radioLearning;

    @FXML
    private RadioButton radioMisc;

    @FXML
    private RadioButton radioMovies;

    @FXML
    private RadioButton radioMusic;

    @FXML
    private RadioButton radioNews;

    @FXML
    private RadioButton radioPodcasts;

    @FXML
    private RadioButton radioSports;

    @FXML
    private VBox vbxDetails;

    @FXML
    private VBox vbxRight, vbxLeft;

    @FXML
    private HBox hbxDetails;

    @FXML
    private Text txtError;

    @FXML
    private HBox hbxError;

    LayoutController parentController;

    public void initialize(File file, LayoutController parentController) {
        this.parentController = parentController;
        setParentController();
        videoFile = file;
        hbxError.setVisible(false);
        vbxDetails.getStylesheets().clear();
        vbxDetails.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/" + YouTubeApplication.theme + "/create-section.css")).toExternalForm());
        setPage();
    }

    private void setPage() {
        lblVideoTitle.setText(videoFile.getName());
        fieldTitle.setText(videoFile.getName());
    }

    @FXML
    private void getThumbnail(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select jpg file");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        thumbnailFile = fileChooser.showOpenDialog(vbxDetails.getScene().getWindow());

        if (thumbnailFile != null) {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(thumbnailFile.toURI().toString()));

            imageView.setFitWidth(315);
            imageView.setFitHeight(177);

//            imageView.setFitWidth(686);
//            imageView.setFitHeight(385);

//            vbxRight.prefWidthProperty().bind(Bindings.multiply(hbxDetails.widthProperty(), 2.0 / 5.0));
//            vbxLeft.prefWidthProperty().bind(Bindings.multiply(hbxDetails.widthProperty(), 3.0 / 5.0));



            btnThumb.setText("");
            btnThumb.setGraphic(imageView);
        }
    }

    @FXML
    private void verifyUpload(ActionEvent event) {
        String title = fieldTitle.getText();
        String description = fieldDescription.getText();

        //todo check radio buttons

        if (title.isEmpty()) {
            txtError.setText("Please enter a title");
            hbxError.setVisible(true);
            return;
        }

        Request<Video> videoRequest = new Request<>(YouTubeApplication.socket, "CreateVideo");
        videoRequest.send(new Video(title, convertImageToByteArray(thumbnailFile.getAbsolutePath()), convertVideoToByteArray(videoFile.getAbsolutePath()), description, YouTubeApplication.user.getId(), new ArrayList<>(), videoFile.getName()));
        Gson gson = new Gson();

        String response = YouTubeApplication.receiveResponse();
        TypeToken<Response<Video>> responseTypeToken = new TypeToken<>() {
        };
        Response<Video> userVideoResponse = gson.fromJson(response, responseTypeToken.getType());
        System.out.println(userVideoResponse.getMessage());
        parentController.sendNotification(userVideoResponse.getMessage());

        new Request<>(YouTubeApplication.socket, "CreateNotificationForSubscribers").send(new Notification(YouTubeApplication.user.getId(), YouTubeApplication.user.getUsername() + " post a video"));

        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/home-section.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //region [ - convertVideoToByteArray - ]

    public byte[] convertVideoToByteArray(String videoPath) {
        System.out.println("In ConvertImage Method");
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(videoPath))) {
            int videoSize = bis.available();
            byte[] videoBytes = new byte[videoSize];
            int byteRead;
            int i = 0;
            while ((byteRead = bis.read()) != -1) {
                videoBytes[i++] = (byte) byteRead;
            }
            System.out.println("End of ConvertImage Method");
            return videoBytes;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //endregion

    //region [ - convertImageToByteArray(String imagePath, String type) - ]
    private byte[] convertImageToByteArray(String imagePath) {
        System.out.println("In ConvertImage Method");
        byte[] imageBytes = null;
        try {
            // Load the image
            File file = new File(imagePath);
            BufferedImage bufferedImage = ImageIO.read(file);

            // Convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            imageBytes = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("End of ConvertImage Method");
        return imageBytes;
    }
    //endregion

    //region [ - setParentController(LayoutController parentController) - ]
    public void setParentController() {
        EventHandler<ActionEvent> existingHandler = parentController.btnMode.getOnAction();

        parentController.btnMode.setOnAction(event -> {
            if (existingHandler != null) {
                existingHandler.handle(event);
            }
            vbxDetails.getStylesheets().clear();
            vbxDetails.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/" + YouTubeApplication.theme + "/create-section.css")).toExternalForm());
        });
    }
    //endregion

}
