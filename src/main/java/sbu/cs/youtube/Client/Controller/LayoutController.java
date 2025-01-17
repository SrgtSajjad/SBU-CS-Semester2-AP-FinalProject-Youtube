package sbu.cs.youtube.Client.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import sbu.cs.youtube.Shared.POJO.*;
import sbu.cs.youtube.Shared.Request;
import sbu.cs.youtube.Shared.Response;
import sbu.cs.youtube.YouTubeApplication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;

public class LayoutController implements Initializable {

    //region [ - Fields - ]

    protected boolean isExpanded = false;

    protected boolean isSignedIn;

    protected boolean isDarkMode = true;

    @FXML
    protected HBox hbxNavBar;

    @FXML
    protected HBox searchHbx;

    @FXML
    protected Button btnSearch;

    @FXML
    protected TextField searchField;

    @FXML
    protected SVGPath svgHome;

    @FXML
    protected SVGPath svgYou;

    @FXML
    protected SVGPath svgSubs;

    @FXML
    protected VBox vbxSideBar;

    @FXML
    protected HBox hbxRightNavItem;

    @FXML
    protected Button btnYou;

    @FXML
    protected Button btnBurger;

    @FXML
    protected HBox hbxContent;

    @FXML
    protected ScrollPane scrollPane;

    @FXML
    protected VBox vbxLayout;

    @FXML
    protected FlowPane flowPane;

    @FXML
    protected SVGPath modeSvg;

    @FXML
    protected ToggleButton btnMode;

    private Button notificationsBtn;

    private Popup popup = new Popup();

    //endregion

    //region [ - Methods - ]

    //region [ - initialize(URL location, ResourceBundle resources) - ]
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setMode();
        if (YouTubeApplication.theme.equals("Light")) {
            btnMode.setSelected(true);
            modeSvg.setContent("M12 22C10.93 22 9.86998 21.83 8.83998 21.48L7.41998 21.01L8.83998 20.54C12.53 19.31 15 15.88 15 12C15 8.12 12.53 4.69 8.83998 3.47L7.41998 2.99L8.83998 2.52C9.86998 2.17 10.93 2 12 2C17.51 2 22 6.49 22 12C22 17.51 17.51 22 12 22ZM10.58 20.89C11.05 20.96 11.53 21 12 21C16.96 21 21 16.96 21 12C21 7.04 16.96 3 12 3C11.53 3 11.05 3.04 10.58 3.11C13.88 4.81 16 8.21 16 12C16 15.79 13.88 19.19 10.58 20.89Z");
        }

        if (YouTubeApplication.user == null) {
            isSignedIn = false;
        } else {
            isSignedIn = true;
        }
        setRightNavBarHBox();
        flowPane.prefWidthProperty().bind(scrollPane.widthProperty().subtract(15));
        flowPane.prefHeightProperty().bind(scrollPane.heightProperty());

        if (YouTubeApplication.user == null) {
            disableButtons();
        } else {
            flowPane.getChildren().removeFirst();
        }
        btnSearch.setOnAction(event -> search(event, searchField.getText()));

    }
    //endregion

    //region [ - setMode() - ]
    private void setMode() {
        vbxLayout.getStylesheets().clear();
        vbxLayout.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/" + YouTubeApplication.theme + "/nav-bar.css")).toExternalForm());
        scrollPane.getStylesheets().clear();
        scrollPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/" + YouTubeApplication.theme + "/home-section.css")).toExternalForm());
        Separator separator = (Separator) hbxContent.getChildren().get(1);
        separator.getStylesheets().clear();
        separator.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/" + YouTubeApplication.theme + "/side-bar-collapsed.css")).toExternalForm());
        vbxSideBar.getStylesheets().clear();
        if (!isExpanded) {
            vbxSideBar.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/" + YouTubeApplication.theme + "/side-bar-collapsed.css")).toExternalForm());
        } else {
            vbxSideBar.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/" + YouTubeApplication.theme + "/side-bar-expanded.css")).toExternalForm());
        }

    }
    //endregion

    //region [ - disableButtons() - ]
    private void disableButtons() {
        btnBurger.setDisable(true);
        hbxContent.getChildren().getFirst().setDisable(true);
        searchHbx.setDisable(true);
    }
    //endregion

    //region [ - openSesame() - ]
    @FXML
    protected void openSesame() {
        if (isExpanded) {
            vbxSideBar.getStylesheets().clear();
            vbxSideBar.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/" + YouTubeApplication.theme + "/side-bar-collapsed.css")).toExternalForm());
            isExpanded = false;
        } else {
            vbxSideBar.getStylesheets().clear();
            vbxSideBar.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/" + YouTubeApplication.theme + "/side-bar-expanded.css")).toExternalForm());
            isExpanded = true;
        }
        setSideBarMenus();

    }

    //endregion

    //region [ - setSideBarMenus() - ]

    private void setSideBarMenus() {
        if (isExpanded) {
            if (btnYou.getChildrenUnmodifiable().getFirst() instanceof FlowPane) {
                // add an arrow to the "You" button
                SVGPath arrowSvg = new SVGPath();
                arrowSvg.setContent("M4.97 12.65 9.62 8 4.97 3.35l.71-.71L11.03 8l-5.35 5.35-.71-.7z");
                arrowSvg.getStyleClass().add("you-arrow-svg");
                ((FlowPane) btnYou.getChildrenUnmodifiable().getFirst()).getChildren().removeFirst();
                ((FlowPane) btnYou.getChildrenUnmodifiable().getFirst()).getChildren().add(arrowSvg);
            }

            Separator separator = new Separator(Orientation.HORIZONTAL);
            separator.getStyleClass().add("separator-horizontal");
            vbxSideBar.getChildren().add(2, separator); // add the horizontal separator on top of the "You" button


            //region [ - Your Channel Button - ]
            Button yourChannelBtn = new Button();
            SVGPath yourChannelSvg = new SVGPath();
            yourChannelSvg.getStyleClass().add("you-svg");
            yourChannelSvg.setContent("M4 20h14v1H3V6h1v14zM6 3v15h15V3H6zm2.02 14c.36-2.13 1.93-4.1 5.48-4.1s5.12 1.97 5.48 4.1H8.02zM11 8.5a2.5 2.5 0 015 0 2.5 2.5 0 01-5 0zm3.21 3.43A3.507 3.507 0 0017 8.5C17 6.57 15.43 5 13.5 5S10 6.57 10 8.5c0 1.69 1.2 3.1 2.79 3.43-3.48.26-5.4 2.42-5.78 5.07H7V4h13v13h-.01c-.38-2.65-2.31-4.81-5.78-5.07z");
            Label yourChannelLbl = new Label("Your Channel");
            yourChannelLbl.getStyleClass().add("dashboard-lbl");
            FlowPane yourChannelPane = new FlowPane(yourChannelSvg, yourChannelLbl);
            yourChannelPane.getStyleClass().add("flow-pane");
            yourChannelBtn.setGraphic(yourChannelPane);
            yourChannelBtn.getStyleClass().add("side-btn");
            yourChannelBtn.setTooltip(new Tooltip("Your Channel"));
            yourChannelBtn.setOnAction(this::getDashboard);
            //endregion

            //region [ - History Button - ]
            Button historyBtn = new Button();
            SVGPath historySvg = new SVGPath();
            historySvg.getStyleClass().add("you-svg");
            historySvg.setContent("M14.97 16.95 10 13.87V7h2v5.76l4.03 2.49-1.06 1.7zM22 12c0 5.51-4.49 10-10 10S2 17.51 2 12h1c0 4.96 4.04 9 9 9s9-4.04 9-9-4.04-9-9-9C8.81 3 5.92 4.64 4.28 7.38c-.11.18-.22.37-.31.56L3.94 8H8v1H1.96V3h1v4.74c.04-.09.07-.17.11-.25.11-.22.23-.42.35-.63C5.22 3.86 8.51 2 12 2c5.51 0 10 4.49 10 10z");
            Label historyLbl = new Label("History");
            historyLbl.getStyleClass().add("dashboard-lbl");
            FlowPane historyPane = new FlowPane(historySvg, historyLbl);
            historyPane.getStyleClass().add("flow-pane");
            historyBtn.setGraphic(historyPane);
            historyBtn.getStyleClass().add("side-btn");
            historyBtn.setTooltip(new Tooltip("History"));
            historyBtn.setOnAction(this::setYouSection);
            //endregion

            //region [ - Playlists Button - ]
            Button playlistsBtn = new Button();
            SVGPath playlistSvg = new SVGPath();
            playlistSvg.getStyleClass().add("you-svg");
            playlistSvg.setContent("M22 7H2v1h20V7zm-9 5H2v-1h11v1zm0 4H2v-1h11v1zm2 3v-8l7 4-7 4z");
            Label playlistsLbl = new Label("Playlists");
            playlistsLbl.getStyleClass().add("dashboard-lbl");
            FlowPane playlistsPane = new FlowPane(playlistSvg, playlistsLbl);
            playlistsPane.getStyleClass().add("flow-pane");
            playlistsBtn.setGraphic(playlistsPane);
            playlistsBtn.getStyleClass().add("side-btn");
            playlistsBtn.setTooltip(new Tooltip("Playlists"));
            playlistsBtn.setOnAction(this::setYouSection);
            //endregion

            //region [ - WatchLater Button - ]
            SVGPath watchLaterSvg = new SVGPath();
            watchLaterSvg.getStyleClass().add("you-svg");
            Button watchLaterBtn = new Button();
            watchLaterSvg.setContent("M14.97 16.95 10 13.87V7h2v5.76l4.03 2.49-1.06 1.7zM12 3c-4.96 0-9 4.04-9 9s4.04 9 9 9 9-4.04 9-9-4.04-9-9-9m0-1c5.52 0 10 4.48 10 10s-4.48 10-10 10S2 17.52 2 12 6.48 2 12 2z");
            Label watchLaterLbl = new Label("Watch Later");
            watchLaterLbl.getStyleClass().add("dashboard-lbl");
            FlowPane watchLaterPane = new FlowPane(watchLaterSvg, watchLaterLbl);
            watchLaterPane.getStyleClass().add("flow-pane");
            watchLaterBtn.setGraphic(watchLaterPane);
            watchLaterBtn.getStyleClass().add("side-btn");
            watchLaterBtn.setTooltip(new Tooltip("Watch Later"));
            watchLaterBtn.setOnAction(this::getWatchLaterPage);
            //endregion

            //region [ - LikedVideos Button - ]
            SVGPath likedVideosSvg = new SVGPath();
            likedVideosSvg.getStyleClass().add("you-svg");
            Button likedVideosBtn = new Button();
            likedVideosSvg.setContent("M18.77,11h-4.23l1.52-4.94C16.38,5.03,15.54,4,14.38,4c-0.58,0-1.14,0.24-1.52,0.65L7,11H3v10h4h1h9.43 c1.06,0,1.98-0.67,2.19-1.61l1.34-6C21.23,12.15,20.18,11,18.77,11z M7,20H4v-8h3V20z M19.98,13.17l-1.34,6 C18.54,19.65,18.03,20,17.43,20H8v-8.61l5.6-6.06C13.79,5.12,14.08,5,14.38,5c0.26,0,0.5,0.11,0.63,0.3 c0.07,0.1,0.15,0.26,0.09,0.47l-1.52,4.94L13.18,12h1.35h4.23c0.41,0,0.8,0.17,1.03,0.46C19.92,12.61,20.05,12.86,19.98,13.17z");
            Label likedVideosLbl = new Label("Liked");
            likedVideosLbl.getStyleClass().add("dashboard-lbl");
            FlowPane likedVideosPane = new FlowPane(likedVideosSvg, likedVideosLbl);
            likedVideosPane.getStyleClass().add("flow-pane");
            likedVideosBtn.setGraphic(likedVideosPane);
            likedVideosBtn.getStyleClass().add("side-btn");
            likedVideosBtn.setTooltip(new Tooltip("Liked Videos"));
            likedVideosBtn.setOnAction(this::setYouSection);
            //endregion

            Label exploreLbl = new Label("Explore");
            exploreLbl.getStyleClass().add("dashboard-lbl");

            Separator separator2 = new Separator(Orientation.HORIZONTAL);
            separator2.getStyleClass().add("separator-horizontal");

            //region [ - Explore Scroll Pane - ]
            VBox sideVbx = new VBox();
            sideVbx.getStyleClass().add("side-scroll-pane-vbox");
            ScrollPane sideScrl = new ScrollPane(sideVbx);
            sideScrl.getStyleClass().add("side-scroll-pane");
            sideScrl.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            sideVbx.prefWidthProperty().bind(sideScrl.widthProperty().subtract(15));
            sideVbx.prefHeightProperty().bind(sideScrl.heightProperty());
            //endregion

            //region [ - Trending Button - ]
            Button trendingBtn = new Button();
            SVGPath trendingSvg = new SVGPath();
            trendingSvg.getStyleClass().add("category-svg");
            trendingSvg.setContent("M19 3.87v9.77C19 17.7 15.86 21 12 21s-7-3.3-7-7.37v-.13c0-1.06.22-2.13.62-3.09.5-1.19 1.29-2.21 2.27-2.97.85-.66 1.83-1.14 2.87-1.65.39-.19.77-.38 1.15-.58.36-.19.72-.38 1.08-.56v3.22l1.55-1.04L19 3.87M20 2l-6 4V3c-.85.44-1.7.88-2.55 1.33-1.41.74-2.9 1.34-4.17 2.32-1.13.87-2.02 2.05-2.58 3.37-.46 1.09-.7 2.29-.7 3.48v.14C4 18.26 7.58 22 12 22s8-3.74 8-8.36V2zM9.45 12.89 14 10v5.7c0 1.82-1.34 3.3-3 3.3s-3-1.47-3-3.3c0-1.19.58-2.23 1.45-2.81z");
            Label trendingLbl = new Label("Trending");
            trendingLbl.getStyleClass().add("category-lbl");
            FlowPane trendingPane = new FlowPane(trendingSvg, trendingLbl);
            trendingPane.getStyleClass().add("flow-pane");
            trendingBtn.setGraphic(trendingPane);
            trendingBtn.getStyleClass().add("side-btn");
            trendingBtn.setOnAction(event -> getCategory(event, new Category(UUID.fromString("1c577d77-1a69-4207-9642-b9d356e3082d"), trendingLbl.getText())));
            //endregion

            //region [ - Music Button - ]
            Button musicBtn = new Button();
            SVGPath musicSvg = new SVGPath();
            musicSvg.getStyleClass().add("category-svg");
            musicSvg.setContent("M12 4v9.38c-.73-.84-1.8-1.38-3-1.38-2.21 0-4 1.79-4 4s1.79 4 4 4 4-1.79 4-4V8h6V4h-7zM9 19c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3zm9-12h-5V5h5v2z");
            Label musicLbl = new Label("Music");
            musicLbl.getStyleClass().add("category-lbl");
            FlowPane musicPane = new FlowPane(musicSvg, musicLbl);
            musicPane.getStyleClass().add("flow-pane");
            musicBtn.setGraphic(musicPane);
            musicBtn.getStyleClass().add("side-btn");
            musicBtn.setOnAction(event -> getCategory(event, new Category(UUID.fromString("23278ae1-3944-44df-af8e-28ecaeffc771"), musicLbl.getText())));
            //endregion

            //region [ - Movies&TV Button - ]
            Button moviesBtn = new Button();
            SVGPath moviesSvg = new SVGPath();
            moviesSvg.getStyleClass().add("category-svg");
            moviesSvg.setContent("m22.01 4.91-.5-2.96L1.64 5.19 2 8v13h20V8H3.06l18.95-3.09zM5 9l1 3h3L8 9h2l1 3h3l-1-3h2l1 3h3l-1-3h3v11H3V9h2z");
            Label moviesLbl = new Label("Movies & TV");
            moviesLbl.getStyleClass().add("category-lbl");
            FlowPane moviesPane = new FlowPane(moviesSvg, moviesLbl);
            moviesPane.getStyleClass().add("flow-pane");
            moviesBtn.setGraphic(moviesPane);
            moviesBtn.getStyleClass().add("side-btn");
            moviesBtn.setOnAction(event -> getCategory(event, new Category(UUID.fromString("8442cd0e-7d67-497a-a172-e09910dc12c0"), moviesLbl.getText())));
            //endregion

            //region [ - Gaming Button - ]
            Button gamingBtn = new Button();
            SVGPath gamingSvg = new SVGPath();
            gamingSvg.getStyleClass().add("category-svg");
            gamingSvg.setContent("M10 12H8v2H6v-2H4v-2h2V8h2v2h2v2zm7 .5c0-.83-.67-1.5-1.5-1.5s-1.5.67-1.5 1.5.67 1.5 1.5 1.5 1.5-.67 1.5-1.5zm3-3c0-.83-.67-1.5-1.5-1.5S17 8.67 17 9.5s.67 1.5 1.5 1.5 1.5-.67 1.5-1.5zm-3.03-4.35-4.5 2.53-.49.27-.49-.27-4.5-2.53L3 7.39v6.43l8.98 5.04 8.98-5.04V7.39l-3.99-2.24m0-1.15 4.99 2.8v7.6L11.98 20 2 14.4V6.8L6.99 4l4.99 2.8L16.97 4z");
            Label gamingLbl = new Label("Gaming");
            gamingLbl.getStyleClass().add("category-lbl");
            FlowPane gamingPane = new FlowPane(gamingSvg, gamingLbl);
            gamingPane.getStyleClass().add("flow-pane");
            gamingBtn.setGraphic(gamingPane);
            gamingBtn.getStyleClass().add("side-btn");
            gamingBtn.setOnAction(event -> getCategory(event, new Category(UUID.fromString("8d68738b-e384-4681-9a48-ba4b42aaf2e7"), gamingLbl.getText())));
            //endregion

            //region [ - News Button - ]
            Button newsBtn = new Button();
            SVGPath newsSvg = new SVGPath();
            newsSvg.getStyleClass().add("category-svg");
            newsSvg.setContent("M11 11v6H7v-6h4m1-1H6v8h6v-8zM3 3.03V21h14l4-4V3.03M20 4v11.99l-.01.01H16v3.99l-.01.01H4V4h16zm-2 4H6V6h12v2zm0 7h-5v-2h5v2zm0-3h-5v-2h5v2z");
            Label newsLbl = new Label("News");
            newsLbl.getStyleClass().add("category-lbl");
            FlowPane newsPane = new FlowPane(newsSvg, newsLbl);
            newsPane.getStyleClass().add("flow-pane");
            newsBtn.setGraphic(newsPane);
            newsBtn.getStyleClass().add("side-btn");
            newsBtn.setOnAction(event -> getCategory(event, new Category(UUID.fromString("dcc365a3-1671-49c7-a3ea-ae61f52ac629"), newsLbl.getText())));
            //endregion

            //region [ - Sports Button - ]
            Button sportsBtn = new Button();
            SVGPath sportsSvg = new SVGPath();
            sportsSvg.getStyleClass().add("category-svg");
            sportsSvg.setContent("M18 5V2H6v3H3v6l3.23 1.61c.7 2.5 2.97 4.34 5.69 4.38L8 19v3h8v-3l-3.92-2.01c2.72-.04 4.99-1.88 5.69-4.38L21 11V5h-3zM6 11.38l-2-1V6h2v5.38zM15 21H9v-1.39l3-1.54 3 1.54V21zm2-10c0 2.76-2.24 5-5 5s-5-2.24-5-5V3h10v8zm3-.62-2 1V6h2v4.38z");
            Label sportsLbl = new Label("Sports");
            sportsLbl.getStyleClass().add("category-lbl");
            FlowPane sportsPane = new FlowPane(sportsSvg, sportsLbl);
            sportsPane.getStyleClass().add("flow-pane");
            sportsBtn.setGraphic(sportsPane);
            sportsBtn.getStyleClass().add("side-btn");
            sportsBtn.setOnAction(event -> getCategory(event, new Category(UUID.fromString("c1dddd73-ecca-4897-aaf4-f584bf3e26fa"), sportsLbl.getText())));
            //endregion

            //region [ - Learning Button - ]
            Button learningBtn = new Button();
            SVGPath learningSvg = new SVGPath();
            learningSvg.getStyleClass().add("category-svg");
            learningSvg.setContent("M16 21h-2.28c-.35.6-.98 1-1.72 1s-1.38-.4-1.72-1H8v-1h8v1zm4-11c0 2.96-1.61 5.54-4 6.92V19H8v-2.08C5.61 15.54 4 12.96 4 10c0-4.42 3.58-8 8-8s8 3.58 8 8zm-5 8v-1.66l.5-.29C17.66 14.8 19 12.48 19 10c0-3.86-3.14-7-7-7s-7 3.14-7 7c0 2.48 1.34 4.8 3.5 6.06l.5.28V18h6z");
            Label learningLbl = new Label("Learning");
            learningLbl.getStyleClass().add("category-lbl");
            FlowPane learningPane = new FlowPane(learningSvg, learningLbl);
            learningPane.getStyleClass().add("flow-pane");
            learningBtn.setGraphic(learningPane);
            learningBtn.getStyleClass().add("side-btn");
            learningBtn.setOnAction(event -> getCategory(event, new Category(UUID.fromString("518cfb98-b4a3-46c7-9533-e7cbb68e16f3"), learningLbl.getText())));
            //endregion

            //region [ - Fashion&Beauty Button - ]
            Button fashionBtn = new Button();
            SVGPath fashionSvg = new SVGPath();
            fashionSvg.getStyleClass().add("category-svg");
            fashionSvg.setContent("M12.5 6.44v-.5C13.36 5.71 14 4.93 14 4c0-1.1-.9-2-2-2s-2 .9-2 2h1c0-.55.45-1 1-1s1 .45 1 1-.45 1-1 1h-.5v1.44L4 13h2v6h1v2h1v-2h2v3h1v-3h2v2h1v-2h1v-3h3v-3h2l-7.5-6.56zM6.66 12 12 7.33 17.34 12H6.66zM14 18H7v-5h7v5zm1-3v-2h2v2h-2z");
            Label fashionLbl = new Label("Fashion & Beauty");
            fashionLbl.getStyleClass().add("category-lbl");
            FlowPane fashionPane = new FlowPane(fashionSvg, fashionLbl);
            fashionPane.getStyleClass().add("flow-pane");
            fashionBtn.setGraphic(fashionPane);
            fashionBtn.getStyleClass().add("side-btn");
            fashionBtn.setOnAction(event -> getCategory(event, new Category(UUID.fromString("493a8465-40b6-4379-ae02-e302b797b6f9"), fashionLbl.getText())));
            //endregion

            //region [ - Podcasts Button - ]
            Button podcastsBtn = new Button();
            SVGPath podcastsSvg = new SVGPath();
            podcastsSvg.getStyleClass().add("category-svg");
            podcastsSvg.setContent("M6 12c0-3.31 2.69-6 6-6s6 2.69 6 6c0 1.66-.67 3.16-1.77 4.25l-.71-.71C16.44 14.63 17 13.38 17 12c0-2.76-2.24-5-5-5s-5 2.24-5 5c0 1.38.56 2.63 1.47 3.54l-.71.71C6.67 15.16 6 13.66 6 12zm8 0c0-1.1-.9-2-2-2s-2 .9-2 2c0 .74.4 1.38 1 1.72V22h2v-8.28c.6-.34 1-.98 1-1.72zm-9.06 7.08.71-.71C4.01 16.74 3 14.49 3 12c0-4.96 4.04-9 9-9s9 4.04 9 9c0 2.49-1.01 4.74-2.65 6.37l.71.71C20.88 17.27 22 14.77 22 12c0-5.52-4.48-10-10-10S2 6.48 2 12c0 2.77 1.12 5.27 2.94 7.08z");
            Label podcastsLbl = new Label("Podcasts");
            podcastsLbl.getStyleClass().add("category-lbl");
            FlowPane podcastsPane = new FlowPane(podcastsSvg, podcastsLbl);
            podcastsPane.getStyleClass().add("flow-pane");
            podcastsBtn.setGraphic(podcastsPane);
            podcastsBtn.getStyleClass().add("side-btn");
            podcastsBtn.setOnAction(event -> getCategory(event, new Category(UUID.fromString("95e32988-1ee7-479a-9a1f-340bfc225893"), podcastsLbl.getText())));
            //endregion

            sideVbx.getChildren().addAll(trendingBtn, musicBtn, moviesBtn, gamingBtn, newsBtn, sportsBtn, learningBtn, fashionBtn, podcastsBtn);
            VBox.setVgrow(sideScrl, Priority.ALWAYS);
            vbxSideBar.getChildren().addAll(yourChannelBtn, historyBtn, playlistsBtn, watchLaterBtn, likedVideosBtn, separator2, exploreLbl, sideScrl);

        } else {
            if (btnYou.getChildrenUnmodifiable().getFirst() instanceof FlowPane) {
                ((FlowPane) btnYou.getChildrenUnmodifiable().getFirst()).getChildren().addFirst(svgYou);
                ((FlowPane) btnYou.getChildrenUnmodifiable().getFirst()).getChildren().remove(2);
            }
            vbxSideBar.getChildren().remove(2);
            if (vbxSideBar.getChildren().size() > 3) {
                vbxSideBar.getChildren().subList(3, vbxSideBar.getChildren().size()).clear(); // clear all children under the "You" button
            }
        }
    }

    //endregion

    //region [ - getCategory(ActionEvent event) - ]
    private void getCategory(ActionEvent event, Category category) {
        setDefaultSvgs();
        getCategoryPage(event, category);
    }
    //endregion

    //region [ - getPlaylist(ActionEvent event) - ]

    private void getPlaylist(ActionEvent event, String playlistName) {
        setDefaultSvgs();

    }
    //endregion

    //region [ - getHistoryPage(ActionEvent event) - ]

    private void getHistoryPage(ActionEvent event) {
        setDefaultSvgs();
    }
    //endregion

    //region [ - setRightNavBarHBox() - ]

    protected void setRightNavBarHBox() {
        if (!isSignedIn) {
            Button signInBtn = new Button();
            SVGPath signInSvg = new SVGPath();
            signInSvg.setContent("M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 1c4.96 0 9 4.04 9 9 0 1.42-.34 2.76-.93 3.96-1.53-1.72-3.98-2.89-7.38-3.03A3.996 3.996 0 0016 9c0-2.21-1.79-4-4-4S8 6.79 8 9c0 1.97 1.43 3.6 3.31 3.93-3.4.14-5.85 1.31-7.38 3.03C3.34 14.76 3 13.42 3 12c0-4.96 4.04-9 9-9zM9 9c0-1.65 1.35-3 3-3s3 1.35 3 3-1.35 3-3 3-3-1.35-3-3zm3 12c-3.16 0-5.94-1.64-7.55-4.12C6.01 14.93 8.61 13.9 12 13.9c3.39 0 5.99 1.03 7.55 2.98C17.94 19.36 15.16 21 12 21z");
            Label signInLbl = new Label();
            HBox signInHbx = new HBox();
            signInHbx.setAlignment(Pos.CENTER);

            signInBtn.getStyleClass().add("signIn-btn");
            signInSvg.getStyleClass().add("signIn-svg");
            signInLbl.getStyleClass().add("signIn-lbl");
            signInLbl.setText("Sign In");

            signInHbx.getChildren().add(signInSvg);
            signInHbx.getChildren().add(signInLbl);
            signInHbx.setSpacing(7);
            signInBtn.setGraphic(signInHbx);
            signInBtn.setTooltip(new Tooltip("Sign In"));

            signInBtn.setOnAction(this::getSignInPage);

            hbxRightNavItem.getChildren().clear();
            hbxRightNavItem.getChildren().add(signInBtn);
        } else {

            // create buttons
            Button createBtn = new Button();
            createBtn.getStyleClass().add("loggedIn-btn");

            notificationsBtn = new Button();
            notificationsBtn.getStyleClass().add("loggedIn-btn");

            Button accountBtn = new Button();
            accountBtn.getStyleClass().add("loggedIn-btn");

            // create graphics for buttons
            SVGPath createSvg = new SVGPath();
            createSvg.setContent("M14 13h-3v3H9v-3H6v-2h3V8h2v3h3v2zm3-7H3v12h14v-6.39l4 1.83V8.56l-4 1.83V6m1-1v3.83L22 7v8l-4-1.83V19H2V5h16z");
            createSvg.getStyleClass().add("create-svg");

            SVGPath notificationsSvg = new SVGPath();
            notificationsSvg.setContent("M10 20h4c0 1.1-.9 2-2 2s-2-.9-2-2zm10-2.65V19H4v-1.65l2-1.88v-5.15C6 7.4 7.56 5.1 10 4.34v-.38c0-1.42 1.49-2.5 2.99-1.76.65.32 1.01 1.03 1.01 1.76v.39c2.44.75 4 3.06 4 5.98v5.15l2 1.87zm-1 .42-2-1.88v-5.47c0-2.47-1.19-4.36-3.13-5.1-1.26-.53-2.64-.5-3.84.03C8.15 6.11 7 7.99 7 10.42v5.47l-2 1.88V18h14v-.23z");
            notificationsSvg.getStyleClass().add("notifications-svg");

            Circle clip = new Circle(17, 17, 13);
            ByteArrayInputStream bis;
            bis = new ByteArrayInputStream(YouTubeApplication.user.getAvatarBytes());
            Image avatar = new Image(bis);
            ImageView accountImg = new ImageView(avatar);

            accountImg.fitWidthProperty().set(34);
            accountImg.fitHeightProperty().set(34);

            accountImg.getStyleClass().add("account-img");
            accountImg.setClip(clip);

            createBtn.setGraphic(createSvg);
            notificationsBtn.setGraphic(notificationsSvg);
            accountBtn.setGraphic(accountImg);

            createBtn.setTooltip(new Tooltip("Create"));
            notificationsBtn.setTooltip(new Tooltip("Notifications"));
            accountBtn.setTooltip(new Tooltip("Account Management"));

            createBtn.setOnAction(this::getCreatePage);

            notificationsBtn.setOnAction(event -> {
                getNotifications();
                Bounds bounds = notificationsBtn.localToScreen(notificationsBtn.getBoundsInLocal());
                popup.setX(bounds.getMinX() - 150);
                popup.setY(bounds.getMinY() + bounds.getHeight());

                if (popup.isShowing())
                    popup.hide();
                else
                    popup.show((Stage) btnBurger.getScene().getWindow());
            });

            accountBtn.setOnAction(this::getDashboard);

            hbxRightNavItem.getChildren().clear();
            hbxRightNavItem.getChildren().add(createBtn);
            hbxRightNavItem.getChildren().add(notificationsBtn);
            hbxRightNavItem.getChildren().add(accountBtn);
        }
    }
    //endregion

    //region [ - setHomeSection - ]
    @FXML
    void setHomeSection(ActionEvent event) {
        setDefaultSvgs();
        svgHome.setContent("M4 21V10.08l8-6.96 8 6.96V21h-6v-6h-4v6H4z");
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

        scene = new Scene(root, btnYou.getScene().getWidth(), btnYou.getScene().getHeight());
        stage.setScene(scene);
        stage.show();
    }

    //endregion

    //region [ - setSubscriptionsSection(ActionEvent event) - ]
    @FXML
    void setSubscriptionsSection(ActionEvent event) {
        setDefaultSvgs();
        svgSubs.setContent("M20 7H4V6h16v1zm2 2v12H2V9h20zm-7 6-5-3v6l5-3zm2-12H7v1h10V3z");
        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/subscription-section.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root, btnYou.getScene().getWidth(), btnYou.getScene().getHeight());
        stage.setScene(scene);
        stage.show();
    }
    //endregion

    //region [ - setYouSection(ActionEvent event) - ]
    @FXML
    protected void setYouSection(ActionEvent event) {
        setDefaultSvgs();
        svgYou.setContent("M4 20h14v1H3V6h1v14zM21 3v15H6V3h15zm-4 7.5L11 7v7l6-3.5z");

        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/you-section.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, vbxLayout.getScene().getWidth(), vbxLayout.getScene().getHeight());
        stage.setScene(scene);
        stage.show();
    }
    //endregion

    //region [ - getDashboard(ActionEvent event) - ]

    protected void getDashboard(ActionEvent event) {
        setDefaultSvgs();

        Request<User> videoRequest = new Request<>(YouTubeApplication.socket, "GetUserChannel");
        videoRequest.send(new User(YouTubeApplication.user.getId()));

        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/channel-section.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, vbxLayout.getScene().getWidth(), vbxLayout.getScene().getHeight());
        stage.setScene(scene);
        stage.show();

    }

    //endregion

    //region [ - getNotificationsScene(ActionEvent event) - ]
    protected void getNotifications() {
        setDefaultSvgs();
        Gson gson = new Gson();

        new Request<>(YouTubeApplication.socket, "GetUserNotifications").send(new User(YouTubeApplication.user.getId()));
        Response<ArrayList<Notification>> notificationResponse = gson.fromJson(YouTubeApplication.receiveResponse(), new TypeToken<Response<ArrayList<Notification>>>() {
        }.getType());
        ArrayList<Notification> notifications = notificationResponse.getBody();

        VBox vbxNotifications = new VBox();
        Text text = new Text("Notifications");
        if (YouTubeApplication.theme.equals("Dark")) {
            vbxNotifications.setStyle("-fx-background-color: rgb(20, 20, 20);-fx-background-radius:20;-fx-padding: 15;-fx-spacing: 10");
            text.setStyle("-fx-fill: rgb(255,255,255); -fx-font-weight: bold; -fx-font-size: 15px;-fx-padding: 10;");
        } else {
            vbxNotifications.setStyle("-fx-background-color: rgb(240, 240, 240);-fx-background-radius:20;-fx-padding: 15;-fx-spacing: 10");
            text.setStyle("-fx-fill: rgb(0,0,0); -fx-font-weight: bold; -fx-font-size: 15px;-fx-padding: 10;");
        }
        vbxNotifications.getChildren().add(text);

        popup.getContent().clear();
        popup.getContent().add(vbxNotifications);

        for (var n : notifications) {
            Label label = new Label(n.getMessage());
            label.setId(n.getId().toString());
            label.setOnMouseClicked(mouseEvent -> {
                if (YouTubeApplication.theme.equals("Dark")) {
                    label.setStyle("-fx-background-color: rgb(70, 70, 70);-fx-background-radius:10;-fx-text-fill: rgb(255, 255, 255);-fx-alignment: center;-fx-text-alignment: center;-fx-tile-alignment: center; -fx-padding: 10; -fx-opacity: 0.5; -fx-cursor: DEFAULT;");
                } else {
                    label.setStyle("-fx-background-color: rgb(200, 200, 200);-fx-background-radius:10;-fx-text-fill: rgb(0, 0, 0);-fx-alignment: center;-fx-text-alignment: center;-fx-tile-alignment: center; -fx-padding: 10; -fx-opacity: 0.5; -fx-cursor: DEFAULT;");
                }
                new Request<Notification>(YouTubeApplication.socket, "UpdateNotification").send(new Notification(UUID.fromString(label.getId()), true));
                YouTubeApplication.receiveResponse();
            });
            if (n.isRead()) {
                if (YouTubeApplication.theme.equals("Dark")) {
                    label.setStyle("-fx-background-color: rgb(70, 70, 70);-fx-background-radius:10;-fx-text-fill: rgb(255, 255, 255);-fx-alignment: center;-fx-text-alignment: center;-fx-tile-alignment: center; -fx-padding: 10; -fx-opacity: 0.5; -fx-cursor: DEFAULT;");
                } else {
                    label.setStyle("-fx-background-color: rgb(229, 229, 229);-fx-background-radius:10;-fx-text-fill: rgb(0, 0, 0);-fx-alignment: center;-fx-text-alignment: center;-fx-tile-alignment: center; -fx-padding: 10; -fx-opacity: 0.5; -fx-cursor: DEFAULT;");
                }
            } else {
                if (YouTubeApplication.theme.equals("Dark")) {
                    label.setStyle("-fx-background-color: rgb(70, 70, 70);-fx-background-radius:10;-fx-text-fill: rgb(255, 255, 255);-fx-alignment: center;-fx-text-alignment: center;-fx-tile-alignment: center; -fx-padding: 10; -fx-cursor: HAND");
                } else {
                    label.setStyle("-fx-background-color: rgb(204, 204, 204);-fx-background-radius:10;-fx-text-fill: rgb(0, 0, 0);-fx-alignment: center;-fx-text-alignment: center;-fx-tile-alignment: center; -fx-padding: 10; -fx-cursor: HAND");
                }
            }
            vbxNotifications.getChildren().add(label);

        }
    }
    //endregion

    //region [ - changeMode(ActionEvent event) - ]
    @FXML
    protected void changeMode(ActionEvent event) {
        if (btnMode.isSelected()) {
            modeSvg.setContent("M12 22C10.93 22 9.86998 21.83 8.83998 21.48L7.41998 21.01L8.83998 20.54C12.53 19.31 15 15.88 15 12C15 8.12 12.53 4.69 8.83998 3.47L7.41998 2.99L8.83998 2.52C9.86998 2.17 10.93 2 12 2C17.51 2 22 6.49 22 12C22 17.51 17.51 22 12 22ZM10.58 20.89C11.05 20.96 11.53 21 12 21C16.96 21 21 16.96 21 12C21 7.04 16.96 3 12 3C11.53 3 11.05 3.04 10.58 3.11C13.88 4.81 16 8.21 16 12C16 15.79 13.88 19.19 10.58 20.89Z");
            YouTubeApplication.theme = "Light";
        } else {
            modeSvg.setContent("M10 13a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm0 2a5 5 0 1 1 0-10 5 5 0 0 1 0 10zm0-15a1 1 0 0 1 1 1v2a1 1 0 0 1-2 0V1a1 1 0 0 1 1-1zm0 16a1 1 0 0 1 1 1v2a1 1 0 0 1-2 0v-2a1 1 0 0 1 1-1zM1 9h2a1 1 0 1 1 0 2H1a1 1 0 0 1 0-2zm16 0h2a1 1 0 0 1 0 2h-2a1 1 0 0 1 0-2zm.071-6.071a1 1 0 0 1 0 1.414l-1.414 1.414a1 1 0 1 1-1.414-1.414l1.414-1.414a1 1 0 0 1 1.414 0zM5.757 14.243a1 1 0 0 1 0 1.414L4.343 17.07a1 1 0 1 1-1.414-1.414l1.414-1.414a1 1 0 0 1 1.414 0zM4.343 2.929l1.414 1.414a1 1 0 0 1-1.414 1.414L2.93 4.343A1 1 0 0 1 4.343 2.93zm11.314 11.314l1.414 1.414a1 1 0 0 1-1.414 1.414l-1.414-1.414a1 1 0 1 1 1.414-1.414z");
            YouTubeApplication.theme = "Dark";
        }

        setMode();

    }
    //endregion

    //region [ - getCreatePage(ActionEvent event) - ]

    protected void getCreatePage(ActionEvent event) {
        setDefaultSvgs();
        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/create-section.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root, btnYou.getScene().getWidth(), btnYou.getScene().getHeight());
        stage.setScene(scene);
        stage.show();
    }

    //endregion

    //region [ - getSignInPage(ActionEvent event) - ]
    protected void getSignInPage(ActionEvent event) {
        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/sign-in.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root, btnYou.getScene().getWidth(), btnYou.getScene().getHeight());
        stage.setScene(scene);
        stage.show();
    }
    //endregion

    //region [ - sendNotification(String text) - ]

    protected void sendNotification(String text) {
        Notifications.create().text(text).hideAfter(Duration.seconds(3)).owner(hbxRightNavItem).position(Pos.BASELINE_RIGHT).threshold(3, Notifications.create().title("Notifications")).show();
    }


    //endregion

    //region [ - addToFlowPane(Parent parent) - ]
    public void addToFlowPane(Parent parent) {
        flowPane.getChildren().add(parent);
    }
    //endregion

    //region [ - setDefaultSvgs() - ]
    private void setDefaultSvgs() {
        svgHome.setContent("m12 4.44 7 6.09V20h-4v-6H9v6H5v-9.47l7-6.09m0-1.32-8 6.96V21h6v-6h4v6h6V10.08l-8-6.96z");
        svgSubs.setContent("M10 18v-6l5 3-5 3zm7-15H7v1h10V3zm3 3H4v1h16V6zm2 3H2v12h20V9zM3 10h18v10H3V10z");
        svgYou.setContent("m11 7 6 3.5-6 3.5V7zm7 13H4V6H3v15h15v-1zm3-2H6V3h15v15zM7 17h13V4H7v13z");
    }
    //endregion

    //region [ - getPlaylistPage(ActionEvent event) - ]
    private void getWatchLaterPage(ActionEvent event) {
        Gson gson = new Gson();

        new Request<>(YouTubeApplication.socket, "GetUserPlaylistsBriefly").send(new User(YouTubeApplication.user.getId()));
        Response<ArrayList<Playlist>> playlistsResponse = gson.fromJson(YouTubeApplication.receiveResponse(), new TypeToken<Response<ArrayList<Playlist>>>() {
        }.getType());
        ArrayList<Playlist> playlists = playlistsResponse.getBody();

        new Request<Playlist>(YouTubeApplication.socket, "GetPlaylist").send(new Playlist(playlists.getFirst().getId()));

        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/playlist-section.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, vbxLayout.getScene().getWidth(), vbxLayout.getScene().getHeight());
        stage.setScene(scene);
        stage.show();
    }
    //endregion

    //region [ - getCategoryPage(ActionEvent event, Category category) - ]
    private void getCategoryPage(ActionEvent event, Category category) {
        Gson gson = new Gson();
        new Request<Category>(YouTubeApplication.socket, "GetCategoryVideos").send(category);

        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/playlist-section.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, vbxLayout.getScene().getWidth(), vbxLayout.getScene().getHeight());
        stage.setScene(scene);
        stage.show();
    }
    //endregion

    //region [ - search(ActionEvent event, String searchedText) - ]
    private void search(ActionEvent event, String searchText) {
        setDefaultSvgs();
        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sbu/cs/youtube/search-section.fxml"));
        try {
            root = loader.load();
            SearchSectionController searchSectionController = loader.getController();
            searchSectionController.initialize(searchText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root, btnYou.getScene().getWidth(), btnYou.getScene().getHeight());
        stage.setScene(scene);
        stage.show();
    }
    //endregion

    //endregion


}
