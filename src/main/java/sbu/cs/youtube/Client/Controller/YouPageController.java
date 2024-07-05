package sbu.cs.youtube.Client.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sbu.cs.youtube.Shared.POJO.Playlist;
import sbu.cs.youtube.Shared.POJO.User;
import sbu.cs.youtube.Shared.POJO.Video;
import sbu.cs.youtube.Shared.Request;
import sbu.cs.youtube.Shared.Response;
import sbu.cs.youtube.YouTubeApplication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class YouPageController implements Initializable {

    //region [ - Fields - ]

    private User user;
    private Gson gson;

    @FXML
    private Button btnViewAllHistory;

    @FXML
    private Button btnViewAllLikedVideos;

    @FXML
    private Button btnViewAllPlaylists;

    @FXML
    private Button btnViewAllWatchLater;

    @FXML
    private Button btnViewAllYourClips;

    @FXML
    private Button btnViewChannel;

    @FXML
    private HBox hbxHistoryHeader;

    @FXML
    private HBox hbxHistoryVideos;

    @FXML
    private HBox hbxLikedVideos;

    @FXML
    private HBox hbxLikedVideosHeader;

    @FXML
    private HBox hbxPlaylistsHeader;

    @FXML
    private HBox hbxPlaylistsVideos;

    @FXML
    private HBox hbxUserDetails;

    @FXML
    private HBox hbxWatchLaterHeader;

    @FXML
    private HBox hbxWatchLaterVideos;

    @FXML
    private HBox hbxYourClips;

    @FXML
    private HBox hbxYourClipsHeader;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private ScrollPane scrlbrHistory;

    @FXML
    private ScrollPane scrlbrLikedVideos;

    @FXML
    private ScrollPane scrlbrPlaylists;

    @FXML
    private ScrollPane scrlbrWatchLater;

    @FXML
    private ScrollPane scrlbrYourClips;

    @FXML
    private Text txtFullName;

    @FXML
    private Text txtHistoryTitle;

    @FXML
    private Text txtLikedVideosTitle;

    @FXML
    private Text txtPlaylistsTitle;

    @FXML
    private Text txtWatchLaterTitle;

    @FXML
    private Text txtYourClipsTitle;

    @FXML
    private VBox vbxDashboard;

    @FXML
    private VBox vbxHistory;

    @FXML
    private VBox vbxLikedVideos;

    @FXML
    private VBox vbxPlaylists;

    @FXML
    private VBox vbxUserDetails;

    @FXML
    private VBox vbxWatchLater;

    @FXML
    private VBox vbxYourClips;

    //endregion

    //region [ - Methods - ]

    //region [ - initialize(URL location, ResourceBundle resources) - ]
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gson = new Gson();
        this.user = YouTubeApplication.user;
        setUser();
        displayHistory();
        displayPlaylists();
        displayWatchLater();
        displayLikedVideos();
        displayYourClips();
        bindItems();
    }
    //endregion

    //region [ - setUser() - ]
    public void setUser() {
        txtFullName.setText(user.getFullName());
        btnViewChannel.setText("@" + user.getUsername() + " • " + "View Channel");

        ByteArrayInputStream bis;
        bis = new ByteArrayInputStream(user.getAvatarBytes());
        Image avatar = new Image(bis);
        imgAvatar.setImage(avatar);
    }
    //endregion

    //region [ - displayHistory() - ]
    private void displayHistory() {
        Request<User> userRequest = new Request<>(YouTubeApplication.socket, "GetHistory");
        userRequest.send(new User(user.getId()));

        String response = YouTubeApplication.receiveResponse();
        TypeToken<Response<ArrayList<Video>>> responseTypeToken = new TypeToken<>() {
        };
        Response<ArrayList<Video>> videoResponse = gson.fromJson(response, responseTypeToken.getType());

        ArrayList<Video> videos = videoResponse.getBody();
         videos.sort(Comparator.comparing(d -> LocalDateTime.parse(d.getUploadDate())));
        if (videos != null) {
                for (var v : videos) {

                    FXMLLoader videoPreviewLoader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/video-preview.fxml"));
                    VBox videoPreview;
                    try {
                        videoPreview = videoPreviewLoader.load();
                        VideoPreviewController videoPreviewController = videoPreviewLoader.getController();
                        if (videoPreviewController != null) {
                            videoPreviewController.setVideo(v);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Button button = new Button();
                    button.getStyleClass().add("btn-video");
                    button.setGraphic(videoPreview);

                    button.setOnAction(event -> getVideo(event, v));
                    hbxHistoryVideos.getChildren().add(button);
                    VBox.setVgrow(videoPreview, Priority.ALWAYS);
                }
        }
    }
    //endregion

    //region [ - displayPlaylists() - ]
    private void displayPlaylists() {
        Request<User> userRequest = new Request<>(YouTubeApplication.socket, "GetUserPlaylists");
        userRequest.send(new User(user.getId()));

        String response = YouTubeApplication.receiveResponse();
        TypeToken<Response<ArrayList<Playlist>>> responseTypeToken = new TypeToken<>() {
        };
        Response<ArrayList<Playlist>> videoResponse = gson.fromJson(response, responseTypeToken.getType());

        ArrayList<Playlist> playlists = videoResponse.getBody();
        if (playlists != null) {
            for (var p : playlists) {

                FXMLLoader playlistPreviewLoader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/playlist-preview.fxml"));
                VBox playlistPreview;
                try {
                    playlistPreview = playlistPreviewLoader.load();
                    PlaylistPreviewController playlistPreviewController = playlistPreviewLoader.getController();
                    if (playlistPreviewController != null) {
                        playlistPreviewController.setPlaylist(p);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Button button = new Button();
                button.getStyleClass().add("btn-video");
                button.setGraphic(playlistPreview);

                button.setOnAction(event -> getPlaylist(event, p));
                hbxPlaylistsVideos.getChildren().add(button);
                VBox.setVgrow(playlistPreview, Priority.ALWAYS);
            }
        }
    }
    //endregion

    //region [ - displayWatchLater() - ]
    private void displayWatchLater() {
        Request<User> userRequest = new Request<>(YouTubeApplication.socket, "GetHistory");
        userRequest.send(new User(user.getId()));

        String response = YouTubeApplication.receiveResponse();
        TypeToken<Response<ArrayList<Video>>> responseTypeToken = new TypeToken<>() {
        };
        Response<ArrayList<Video>> videoResponse = gson.fromJson(response, responseTypeToken.getType());

        ArrayList<Video> videos = videoResponse.getBody();
        if (videos != null) {
            for (var v : videos) {

                FXMLLoader videoPreviewLoader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/video-preview.fxml"));
                VBox videoPreview;
                try {
                    videoPreview = videoPreviewLoader.load();
                    VideoPreviewController videoPreviewController = videoPreviewLoader.getController();
                    if (videoPreviewController != null) {
                        videoPreviewController.setVideo(v);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Button button = new Button();
                button.getStyleClass().add("btn-video");
                button.setGraphic(videoPreview);

                button.setOnAction(event -> getVideo(event, v));
                hbxWatchLaterVideos.getChildren().add(button);
                VBox.setVgrow(videoPreview, Priority.ALWAYS);
            }
        }
    }
    //endregion

    //region [ - displayLikedVideos() - ]
    private void displayLikedVideos() {
        Request<User> userRequest = new Request<>(YouTubeApplication.socket, "GetLikedVideos");
        userRequest.send(new User(user.getId()));

        String response = YouTubeApplication.receiveResponse();
        TypeToken<Response<ArrayList<Video>>> responseTypeToken = new TypeToken<>() {
        };
        Response<ArrayList<Video>> videoResponse = gson.fromJson(response, responseTypeToken.getType());

        ArrayList<Video> videos = videoResponse.getBody();
        if (videos != null) {
            for (var v : videos) {

                FXMLLoader videoPreviewLoader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/video-preview.fxml"));
                VBox videoPreview;
                try {
                    videoPreview = videoPreviewLoader.load();
                    VideoPreviewController videoPreviewController = videoPreviewLoader.getController();
                    if (videoPreviewController != null) {
                        videoPreviewController.setVideo(v);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Button button = new Button();
                button.getStyleClass().add("btn-video");
                button.setGraphic(videoPreview);

                button.setOnAction(event -> getVideo(event, v));
                hbxLikedVideos.getChildren().add(button);
                VBox.setVgrow(videoPreview, Priority.ALWAYS);
            }
        }
    }
    //endregion

    //region [ - displayYourClips() - ]
    private void displayYourClips() {
        Request<User> userRequest = new Request<>(YouTubeApplication.socket, "GetUserVideos");
        userRequest.send(new User(user.getId()));

        String response = YouTubeApplication.receiveResponse();
        TypeToken<Response<ArrayList<Video>>> responseTypeToken = new TypeToken<>() {
        };
        Response<ArrayList<Video>> videoResponse = gson.fromJson(response, responseTypeToken.getType());

        ArrayList<Video> videos = videoResponse.getBody();
        if (videos != null) {
            for (var v : videos) {

                FXMLLoader videoPreviewLoader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/video-preview.fxml"));
                VBox videoPreview;
                try {
                    videoPreview = videoPreviewLoader.load();
                    VideoPreviewController videoPreviewController = videoPreviewLoader.getController();
                    if (videoPreviewController != null) {
                        videoPreviewController.setVideo(v);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Button button = new Button();
                button.getStyleClass().add("btn-video");
                button.setGraphic(videoPreview);

                button.setOnAction(event -> getVideo(event, v));
                hbxYourClips.getChildren().add(button);
                VBox.setVgrow(videoPreview, Priority.ALWAYS);
            }
        }
    }
    //endregion

    //region [ - getVideo(ActionEvent event, Video video) - ]
    private void getVideo(ActionEvent event, Video video) {
        Request<Video> videoRequest = new Request<>(YouTubeApplication.socket, "GetVideo");
        videoRequest.send(new Video(video.getId()));

        getVideoPage(event);
    }
    //endregion

    //region [ - getPlaylist(ActionEvent event, Playlist video) - ]
    private void getPlaylist(ActionEvent event, Playlist playlist) {
        Request<Playlist> playlistRequest = new Request<>(YouTubeApplication.socket, "GetPlaylist");
        playlistRequest.send(new Playlist(playlist.getId()));
        getVideoPage(event);
    }
    //endregion

    //region [ - bindItems() - ]
    private void bindItems() {
        hbxHistoryHeader.prefWidthProperty().bind(scrlbrHistory.widthProperty());
        hbxHistoryVideos.prefWidthProperty().bind(scrlbrHistory.widthProperty());
//        hbxHistoryHeader.prefHeightProperty().bind(scrlbrHistory.heightProperty());
//        hbxHistoryVideos.prefHeightProperty().bind(scrlbrHistory.heightProperty());

        hbxPlaylistsHeader.prefWidthProperty().bind(scrlbrPlaylists.widthProperty());
        hbxPlaylistsVideos.prefWidthProperty().bind(scrlbrPlaylists.widthProperty());
//        hbxPlaylistsHeader.prefHeightProperty().bind(scrlbrPlaylists.heightProperty());
//        hbxPlaylistsVideos.prefHeightProperty().bind(scrlbrPlaylists.heightProperty());

        hbxWatchLaterHeader.prefWidthProperty().bind(scrlbrWatchLater.widthProperty());
        hbxWatchLaterVideos.prefWidthProperty().bind(scrlbrWatchLater.widthProperty());
//        hbxWatchLaterHeader.prefHeightProperty().bind(scrlbrWatchLater.heightProperty());
//        hbxWatchLaterVideos.prefHeightProperty().bind(scrlbrWatchLater.heightProperty());

        hbxLikedVideosHeader.prefWidthProperty().bind(scrlbrLikedVideos.widthProperty());
        hbxLikedVideos.prefWidthProperty().bind(scrlbrLikedVideos.widthProperty());
//        hbxLikedVideosHeader.prefHeightProperty().bind(scrlbrLikedVideos.heightProperty());
//        hbxLikedVideos.prefHeightProperty().bind(scrlbrLikedVideos.heightProperty());

        hbxYourClipsHeader.prefWidthProperty().bind(scrlbrYourClips.widthProperty());
        hbxYourClips.prefWidthProperty().bind(scrlbrYourClips.widthProperty());
//        hbxYourClipsHeader.prefHeightProperty().bind(scrlbrYourClips.heightProperty());
//        hbxYourClips.prefHeightProperty().bind(scrlbrYourClips.heightProperty());
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
        scene = new Scene(root, vbxDashboard.getScene().getWidth(), vbxDashboard.getScene().getHeight());
        stage.setScene(scene);
        stage.show();
    }
    //endregion

    //endregion

}