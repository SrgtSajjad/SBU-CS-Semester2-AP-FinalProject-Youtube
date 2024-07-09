package sbu.cs.youtube.Client.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sbu.cs.youtube.Shared.POJO.Channel;
import sbu.cs.youtube.Shared.POJO.Playlist;
import sbu.cs.youtube.Shared.POJO.Video;
import sbu.cs.youtube.Shared.Request;
import sbu.cs.youtube.Shared.Response;
import sbu.cs.youtube.YouTubeApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {

    //region [ - Fields - ]
    private String searchText;
    @FXML
    private FlowPane flowPanePlaylists;

    @FXML
    private FlowPane flowPaneVideos;

    @FXML
    private VBox vbxSearchPage;
    //endregion

    //region [ - Methods - ]

    //region [ - initialize(URL location, ResourceBundle resources) - ]
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(this::displayVideos).start();
        new Thread(this::displayPlaylists).start();
        new Thread(this::displayChannels).start();
    }
    //endregion

    //region [ - displayVideos() - ]
    private void displayVideos() {
        Gson gson = new Gson();
        new Request<>(YouTubeApplication.socket, "searchVideo").send(searchText);
        TypeToken<Response<ArrayList<Video>>> responseTypeToken = new TypeToken<>() {
        };
        Response<ArrayList<Video>> videoResponse = gson.fromJson(YouTubeApplication.receiveResponse(), responseTypeToken.getType());

        ArrayList<Video> videos = videoResponse.getBody();
        if (videos == null) {
            return;
        }
        Platform.runLater(() -> {
            for (var video : videos) {
                FXMLLoader videoPreviewLoader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/video-preview.fxml"));
                VBox videoPreview;

                try {
                    videoPreview = videoPreviewLoader.load();
                    VideoPreviewController videoPreviewController = videoPreviewLoader.getController();
                    if (videoPreviewController != null) {
                        videoPreviewController.setVideo(video);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Button button = new Button();
                button.getStyleClass().add("btn-video");
                button.setGraphic(videoPreview);

                button.setOnAction(event -> getVideo(event, video));

                flowPaneVideos.getChildren().add(button);
            }
        });
    }
    //endregion

    //region [ - getVideo(ActionEvent event, Video video) - ]
    private void getVideo(ActionEvent event, Video video) {
        Request<Video> videoRequest = new Request<>(YouTubeApplication.socket, "GetVideo");
        videoRequest.send(new Video(video.getId()));

        getVideoPage(event);
    }
    //endregion

    //region [ - getVideoPage(ActionEvent event) - ]
    private void getVideoPage(ActionEvent event) {
        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/video-section.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, vbxSearchPage.getScene().getWidth(), vbxSearchPage.getScene().getHeight());
        stage.setScene(scene);
        stage.show();
    }
    //endregion


    //region [ - displayPlaylists() - ]
    private void displayPlaylists() {
        Gson gson = new Gson();
        new Request<>(YouTubeApplication.socket, "searchPlaylist").send(searchText);
        TypeToken<Response<ArrayList<Playlist>>> responseTypeToken = new TypeToken<>() {
        };
        Response<ArrayList<Playlist>> playlistResponse = gson.fromJson(YouTubeApplication.receiveResponse(), responseTypeToken.getType());

        ArrayList<Playlist> playlists = playlistResponse.getBody();
        if (playlists == null) {
            return;
        }
        Platform.runLater(() -> {
            for (var playlist : playlists) {
                FXMLLoader playlistPreviewLoader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/playlist-preview.fxml"));
                VBox playlistPreview;

                try {
                    playlistPreview = playlistPreviewLoader.load();
                    PlaylistPreviewController playlistPreviewController = playlistPreviewLoader.getController();
                    if (playlistPreviewController != null) {
                        playlistPreviewController.setPlaylist(playlist);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                flowPanePlaylists.getChildren().add(playlistPreview);
            }
        });
    }
    //endregion

    //region [ - displayChannels() - ]
    private void displayChannels() {
        Gson gson = new Gson();
        new Request<>(YouTubeApplication.socket, "searchChannel").send(searchText);
        TypeToken<Response<ArrayList<Channel>>> responseTypeToken = new TypeToken<>() {
        };
        Response<ArrayList<Channel>> channelResponse = gson.fromJson(YouTubeApplication.receiveResponse(), responseTypeToken.getType());

        ArrayList<Channel> channels = channelResponse.getBody();
        if (channels == null) {
            return;
        }
    }
    //endregion

    //endregion

}