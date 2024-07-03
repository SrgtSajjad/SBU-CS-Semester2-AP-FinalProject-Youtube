package sbu.cs.youtube.Server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import sbu.cs.youtube.Server.Database.DatabaseManager;
import sbu.cs.youtube.Shared.POJO.*;
import sbu.cs.youtube.Shared.Request;
import sbu.cs.youtube.Shared.Response;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    //region [ - Fields - ]
    private final Socket client;
    private BufferedReader bufferedReader;
    private DatabaseManager databaseManager;
    private String request;
    private final Gson gson;
    //endregion

    //region [ - Constructor - ]
    public ClientHandler(Socket client) {
        this.gson = new Gson();
        this.client = client;
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader((client.getInputStream())));
            this.databaseManager = new DatabaseManager();
        } catch (IOException ioe) {
            System.out.println("!!Exception : " + ioe.getMessage());
        }
    }
    //endregion

    //region [ - Methods - ]

    //region [ - run() - ]
    @Override
    public void run() {
        try {
            try {
                while (!client.isClosed()) {
                    receiveRequest();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedReader.close();
                    client.close();
                } catch (IOException ioe) {
                    System.out.println("!!Exception : " + ioe.getMessage());
                }
            }
        } finally {
            System.out.println("Client with IP :  " + client.getRemoteSocketAddress() + " disconnected");
        }
    }
    //endregion

    //region [ - receiveRequest() - ]
    public void receiveRequest() {
        try {
            String request = bufferedReader.readLine();
            handleRequest(request);
        } catch (IOException ioe) {
            try {
                if (client != null) {
                    client.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("!!Exception : " + e.getMessage());
            }
        }
    }
    //endregion

    //region [ - handleRequest(String request) - ]
    public void handleRequest(String request) {
        this.request = request;
        TypeToken<Request<Object>> responseTypeToken = new TypeToken<>() {
        };
        Request<Object> objectRequest = gson.fromJson(request, responseTypeToken.getType());
        switch (objectRequest.getType()) {
            case "CheckExistingUser":
                CheckExistingUser();
                break;
            case "SignUp":
                signUp();
                break;
            case "SignIn":
                signIn();
                break;
            case "GetRecommendedVideos":
                GetRecommendedVideos();
                break;
            case "ViewVideo":
                viewVideo();
                break;
            case "LikeVideo":
                likeVideo();
                break;
            case "DislikeVideo":
                dislikeVideo();
                break;
            case "LikeComment":
                likeComment();
                break;
            case "DislikeComment":
                dislikeComment();
                break;
            case "Comment":
                comment();
                break;
        }
    }
    //endregion

    //region [ - signUp() - ]
    private void signUp() {
        TypeToken<Request<User>> responseTypeToken = new TypeToken<>() {
        };
        Request<User> userRequest = gson.fromJson(request, responseTypeToken.getType());
        Response<User> response;

        User user = userRequest.getBody();
        databaseManager.insertUser(user);

        response = new Response<>(client, userRequest.getType(), true, "Signed up successfully");
        response.send(user);
    }
    //endregion

    //region [ - signIn() - ]
    private void signIn() {
        TypeToken<Request<User>> responseTypeToken = new TypeToken<>() {
        };
        Request<User> userRequest = gson.fromJson(request, responseTypeToken.getType());
        Response<User> response;


        User requestedUser = userRequest.getBody();
        User user;
        if (requestedUser.getEmail().isEmpty()) {
            user = databaseManager.selectUserByUsername(requestedUser.getUsername());
        } else {
            user = databaseManager.selectUserByEmail(requestedUser.getEmail());
        }

        if (user != null) {
            if (requestedUser.getPassword().equals(user.getPassword())) {
                response = new Response<>(client, userRequest.getType(), true, "Signed in successfully");
            } else {
                response = new Response<>(client, userRequest.getType(), true, "Password is incorrect");
                user = null;
            }
        } else {
            response = new Response<>(client, userRequest.getType(), true, "User not found");
        }
        response.send(user);
    }
    //endregion

    //region [ - CheckExistingUser() - ]
    private void CheckExistingUser() {
        TypeToken<Request<User>> responseTypeToken = new TypeToken<>() {
        };
        Request<User> userRequest = gson.fromJson(request, responseTypeToken.getType());
        Response<User> response;

        User requestedUser = userRequest.getBody();
        User user;
        if (requestedUser.getEmail().isEmpty()) {
            user = databaseManager.selectUserByUsername(requestedUser.getUsername());
        } else {
            user = databaseManager.selectUserByEmail(requestedUser.getEmail());
        }

        if (user != null) {
            response = new Response<>(client, userRequest.getType(), true, "There is already a user with this email");
        } else {
            response = new Response<>(client, userRequest.getType(), true, "User not found");
        }

        response.send(user);
    }
    //endregion

    //region [ - GetRecommendedVideos() - ]
    private void GetRecommendedVideos() {
        TypeToken<Request<ArrayList<Video>>> responseTypeToken = new TypeToken<>() {
        };
        Request<ArrayList<Video>> videosRequest = gson.fromJson(request, responseTypeToken.getType());
        Response<ArrayList<Video>> response;

        ArrayList<Video> videos = databaseManager.selectVideosBriefly();

        response = new Response<>(client, videosRequest.getType(), true, "Signed up successfully");
        response.send(videos);
    }
    //endregion

    //region [ - viewVideo() - ]
    private void viewVideo() {
        TypeToken<Request<UserVideo>> responseTypeToken = new TypeToken<>() {
        };
        Request<UserVideo> userVideoRequest = gson.fromJson(request, responseTypeToken.getType());
        UserVideo requestedUserVideo = userVideoRequest.getBody();

        Response<UserVideo> response;
        UserVideo userVideo = databaseManager.selectUserVideo(requestedUserVideo.getUserId(), requestedUserVideo.getVideoId());
        if (userVideo == null) {
            response = new Response<>(client, userVideoRequest.getType(), true, "Video has already viewed");
        } else {
            databaseManager.insertUserVideo(requestedUserVideo);
            response = new Response<>(client, userVideoRequest.getType(), true, "Video has just viewed");
        }
        response.send(userVideo);
    }
    //endregion

    //region [ - likeVideo() - ]
    private void likeVideo() {
        TypeToken<Request<UserVideo>> responseTypeToken = new TypeToken<>() {
        };
        Request<UserVideo> userVideoRequest = gson.fromJson(request, responseTypeToken.getType());
        UserVideo requestedUserVideo = userVideoRequest.getBody();

        Response<UserVideo> response;
        UserVideo userVideo = databaseManager.selectUserVideo(requestedUserVideo.getUserId(), requestedUserVideo.getVideoId());

        if (userVideo.getLike()) {
            requestedUserVideo.setLike(null);
            response = new Response<>(client, userVideoRequest.getType(), true, "Video unliked");
        } else {
            response = new Response<>(client, userVideoRequest.getType(), true, "Video liked");
        }
        databaseManager.insertUserVideo(requestedUserVideo);

        response.send();
    }
    //endregion

    //region [ - dislikeVideo() - ]
    private void dislikeVideo() {
        TypeToken<Request<UserVideo>> responseTypeToken = new TypeToken<>() {
        };
        Request<UserVideo> userVideoRequest = gson.fromJson(request, responseTypeToken.getType());
        UserVideo requestedUserVideo = userVideoRequest.getBody();

        Response<UserVideo> response;
        UserVideo userVideo = databaseManager.selectUserVideo(requestedUserVideo.getUserId(), requestedUserVideo.getVideoId());

        if (!userVideo.getLike() && userVideo.getLike() != null) {
            requestedUserVideo.setLike(null);
            response = new Response<>(client, userVideoRequest.getType(), true, "Video undisliked");
        } else {
            response = new Response<>(client, userVideoRequest.getType(), true, "Video disliked");
        }
        databaseManager.insertUserVideo(requestedUserVideo);

        response.send();
    }
    //endregion

    //region [ - likeComment() - ]
    private void likeComment() {
        TypeToken<Request<UserComment>> responseTypeToken = new TypeToken<>() {
        };
        Request<UserComment> userCommentRequest = gson.fromJson(request, responseTypeToken.getType());
        UserComment requestedUserComment = userCommentRequest.getBody();

        Response<UserComment> response;
        UserComment userComment = databaseManager.selectUserComment(requestedUserComment.getUserId(), requestedUserComment.getCommentId());

        if (userComment.getLike()) {
            databaseManager.deleteUserComment(userComment.getUserId(), userComment.getCommentId());
            response = new Response<>(client, userCommentRequest.getType(), true, "Comment unliked");
        } else {
            databaseManager.insertUserComment(requestedUserComment);
            response = new Response<>(client, userCommentRequest.getType(), true, "Comment liked");
        }

        response.send();
    }
    //endregion

    //region [ - dislikeComment() - ]
    private void dislikeComment() {
        TypeToken<Request<UserComment>> responseTypeToken = new TypeToken<>() {
        };
        Request<UserComment> userCommentRequest = gson.fromJson(request, responseTypeToken.getType());
        UserComment requestedUserComment = userCommentRequest.getBody();

        Response<UserComment> response;
        UserComment userComment = databaseManager.selectUserComment(requestedUserComment.getUserId(), requestedUserComment.getCommentId());

        if (!userComment.getLike() && userComment.getLike() != null) {
            databaseManager.deleteUserComment(userComment.getUserId(), userComment.getCommentId());
            response = new Response<>(client, userCommentRequest.getType(), true, "Comment undisliked");
        } else {
            databaseManager.insertUserComment(requestedUserComment);
            response = new Response<>(client, userCommentRequest.getType(), true, "Comment disliked");
        }

        response.send();
    }
    //endregion

    //region [ - comment() - ]
    private void comment() {
        TypeToken<Request<Comment>> responseTypeToken = new TypeToken<>() {
        };
        Request<Comment> commentRequest = gson.fromJson(request, responseTypeToken.getType());
        Comment comment = commentRequest.getBody();

        databaseManager.insertComment(comment);
        Response<Comment> response = new Response<>(client, commentRequest.getType(), true, "Comment posted");
        response.send();
    }
    //endregion

    //endregion

}
