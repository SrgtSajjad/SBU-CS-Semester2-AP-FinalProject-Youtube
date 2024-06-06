package sbu.cs.youtube.Database;

import sbu.cs.youtube.Shared.POJO.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class DatabaseManager {

    //region [ - Fields - ]
    private final String URL = "jdbc:postgresql://localhost:5432/Youtube-Development";
    private final String USER = "postgres";
    private final String PASSWORD = "musketeers";
    //endregion

    //region [ - Methods - ]

    //region [ - User - ]

    //region [ - insertUser(User user) - ]
    public void insertUser(User user) {
        Connection c;
        PreparedStatement stmt;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (insertUser)");

            stmt = c.prepareStatement("INSERT INTO UserManagement.User(\"Id\", FullName, \"Email\", \"DateOfBirth\", \"Username\",\"Password\", \"JoinDate\") VALUES (?, ?, ?, ?, ?, ?,?);");
            stmt.setObject(1, user.getId());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getEmail());
            stmt.setObject(4, user.getDateOfBirth());
            stmt.setString(5, user.getUsername());
            stmt.setString(6, user.getPassword());
            stmt.setObject(7, user.getJoinDate());

            stmt.executeUpdate();
            c.commit();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (insertUser)");
    }
    //endregion

    //region [ - ArrayList<User> selectUsers() - ]
    public ArrayList<User> selectUsers() {
        Connection c;
        Statement stmt;
        ArrayList<User> users = null;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (selectUsers)");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM UserManagement.User;");
            users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(UUID.fromString(rs.getString("Id")));
                user.setFullName(rs.getString("FullName"));
                user.setEmail(rs.getString("Email"));
                user.setDateOfBirth(LocalDateTime.parse(rs.getString("DateOfBirth")));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setDateOfBirth(LocalDateTime.parse(rs.getString("JoinDate")));
                users.add(user);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (selectUsers)");
        return users;
    }
    //endregion

    //region [ - User selectUser(UUID Id) - ]
    public User selectUser(UUID Id) {
        Connection c;
        PreparedStatement stmt;
        User user = null;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (selectUser)");

            stmt = c.prepareStatement("SELECT * FROM UserManagement.User WHERE \"Id\" = ?");
            stmt.setObject(1, Id);
            ResultSet rs = stmt.executeQuery();
            user = new User();

            user.setId(UUID.fromString(rs.getString("Id")));
            user.setFullName(rs.getString("FullName"));
            user.setEmail(rs.getString("Email"));
            user.setDateOfBirth(LocalDateTime.parse(rs.getString("DateOfBirth")));
            user.setUsername(rs.getString("Username"));
            user.setPassword(rs.getString("Password"));
            user.setDateOfBirth(LocalDateTime.parse(rs.getString("JoinDate")));

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (selectUser)");
        return user;
    }
    //endregion

    //region [ - updateUser(User user) - ]
    public void updateUser(User user) {
        Connection c;
        PreparedStatement stmt;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (updateUser)");

            stmt = c.prepareStatement("UPDATE UserManagement.User SET \"FullName\" = ?, \"Email\" = ?, \"DateOfBirth\" = ?, \"Username\" = ?, \"Password\" = ? WHERE \"Id\" = ?;");

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setObject(3, user.getDateOfBirth());
            stmt.setString(4, user.getUsername());
            stmt.setString(5, user.getPassword());
            stmt.setObject(6, user.getId());
            stmt.executeUpdate();
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (updateUser)");
    }
    //endregion

    //region [ - deleteUser(UUID Id) - ]
    public void deleteUser(UUID Id) {
        Connection c;
        PreparedStatement stmt;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (deleteUser)");

            stmt = c.prepareStatement("DELETE FROM UserManagement.User WHERE \"Id\" = ?;");
            stmt.setObject(1, Id);
            stmt.executeUpdate();
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully (deleteUser)");
    }
    //endregion

    //endregion

    //region [ - Channel - ]

    //region [ - insertChannel(Channel user) - ]
    public void insertChannel(Channel user) {
        Connection c;
        PreparedStatement stmt;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (insertChannel)");

            stmt = c.prepareStatement("INSERT INTO UserManagement.Channel(\"Id\", CreatedId, \"Title\", \"Description\", \"DateCreated\") VALUES (?, ?, ?, ?, ?);");
            stmt.setObject(1, user.getId());
            stmt.setObject(2, user.getCreatorId());
            stmt.setString(3, user.getTitle());
            stmt.setString(4, user.getDescription());
            stmt.setObject(5, user.getDateCreated());

            stmt.executeUpdate();
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (insertChannel)");
    }
    //endregion

    //region [ - ArrayList<Channel> selectChannels() - ]
    public ArrayList<Channel> selectChannels() {
        Connection c;
        Statement stmt;
        ArrayList<Channel> users = null;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (selectChannels)");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM UserManagement.Channel;");
            users = new ArrayList<>();
            while (rs.next()) {
                Channel user = new Channel();
                user.setId(UUID.fromString(rs.getString("Id")));
                user.setCreatorId(UUID.fromString(rs.getString("Id")));
                user.setCreator(selectUser(user.getCreatorId()));
                user.setTitle(rs.getString("Title"));
                user.setDescription(rs.getString("Description"));
                user.setDateCreated(LocalDateTime.parse(rs.getString("DateOfBirth")));
                users.add(user);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (selectChannels)");
        return users;
    }
    //endregion

    //region [ - Channel selectChannel(UUID Id) - ]
    public Channel selectChannel(UUID Id) {
        Connection c;
        PreparedStatement stmt;
        Channel user = null;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (selectChannel)");

            stmt = c.prepareStatement("SELECT * FROM UserManagement.Channel WHERE \"Id\" = ?");
            stmt.setObject(1, Id);
            ResultSet rs = stmt.executeQuery();
            user = new Channel();

            user.setId(UUID.fromString(rs.getString("Id")));
            user.setCreatorId(UUID.fromString(rs.getString("Id")));
            user.setCreator(selectUser(user.getCreatorId()));
            user.setTitle(rs.getString("Title"));
            user.setDescription(rs.getString("Description"));
            user.setDateCreated(LocalDateTime.parse(rs.getString("DateOfBirth")));

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (selectChannel)");
        return user;
    }
    //endregion

    //region [ - updateChannel(Channel user) - ]
    public void updateChannel(Channel user) {
        Connection c;
        PreparedStatement stmt;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (updateChannel)");

            stmt = c.prepareStatement("UPDATE UserManagement.Channel SET \"CreatedId\" = ?, \"Title\" = ?, \"Description\" = ?, \"DateCreated\" = ?  WHERE \"Id\" = ?;");

            stmt.setObject(1, user.getCreatorId());
            stmt.setString(2, user.getTitle());
            stmt.setString(3, user.getDescription());
            stmt.setObject(4, user.getDateCreated());
            stmt.setObject(5, user.getId());
            stmt.executeUpdate();
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (updateChannel)");
    }
    //endregion

    //region [ - deleteChannel(UUID Id) - ]
    public void deleteChannel(UUID Id) {
        Connection c;
        PreparedStatement stmt;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (deleteChannel)");

            stmt = c.prepareStatement("DELETE FROM UserManagement.Channel WHERE \"Id\" = ?;");
            stmt.setObject(1, Id);
            stmt.executeUpdate();
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully (deleteChannel)");
    }
    //endregion

    //endregion

    //region [ - Subscription - ]

    //region [ - insertSubscription(Subscription subscription) - ]
    public void insertSubscription(Subscription subscription) {
        Connection c;
        PreparedStatement stmt;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (insertSubscription)");

            stmt = c.prepareStatement("INSERT INTO UserManagement.Subscription(SubscriberId, channelId, joinDate) VALUES (?, ?, ?);");
            stmt.setObject(1, subscription.getChannel());
            stmt.setObject(2, subscription.getSubscriberId());
            stmt.setObject(3, subscription.getJoinDate());
            stmt.executeUpdate();
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (insertSubscription)");
    }
    //endregion

    //region [ - ArrayList<Subscription> selectSubscriptions() - ]
    public ArrayList<Subscription> selectSubscriptions() {
        Connection c;
        Statement stmt;
        ArrayList<Subscription> subscriptions = null;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (selectSubscriptions)");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM UserManagement.Subscription;");
            subscriptions = new ArrayList<>();
            while (rs.next()) {
                Subscription subscription = new Subscription();
                subscription.setSubscriberId(UUID.fromString(rs.getString("Id")));
                subscription.setChannelId(UUID.fromString(rs.getString("Id")));
                subscription.setSubscriber(selectUser(subscription.getSubscriberId()));
                subscription.setChannel(selectChannel(subscription.getChannelId()));
                subscription.setJoinDate(LocalDateTime.parse(rs.getString("JoinDate")));
                subscriptions.add(subscription);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (selectSubscriptions)");
        return subscriptions;
    }
    //endregion

    //region [ - Subscription selectSubscription(UUID Id) - ]
    public Subscription selectSubscription(UUID Id) {
        Connection c;
        PreparedStatement stmt;
        Subscription subscription = null;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (selectSubscription)");

            stmt = c.prepareStatement("SELECT * FROM UserManagement.Subscription WHERE \"Id\" = ?");
            stmt.setObject(1, Id);
            ResultSet rs = stmt.executeQuery();
            subscription = new Subscription();

            subscription.setSubscriberId(UUID.fromString(rs.getString("Id")));
            subscription.setChannelId(UUID.fromString(rs.getString("Id")));
            subscription.setSubscriber(selectUser(subscription.getSubscriberId()));
            subscription.setChannel(selectChannel(subscription.getChannelId()));
            subscription.setJoinDate(LocalDateTime.parse(rs.getString("JoinDate")));

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (selectSubscription)");
        return subscription;
    }
    //endregion

    //region [ - deleteSubscription(UUID SubscriberId, UUID channelId) - ]
    public void deleteSubscription(UUID SubscriberId, UUID channelId) {
        Connection c;
        PreparedStatement stmt;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (deleteSubscription)");

            stmt = c.prepareStatement("DELETE FROM UserManagement.Subscription WHERE SubscriberId = ? AND channelId = ?;");
            stmt.setObject(1, SubscriberId);
            stmt.setObject(2, channelId);
            stmt.executeUpdate();
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully (deleteSubscription)");
    }
    //endregion

    //endregion


    //region [ - Category - ]

    //region [ - insertUser(Category category) - ]
    public void insertUser(Category category) {
        Connection c;
        PreparedStatement stmt;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (insertCategory)");

            stmt = c.prepareStatement("INSERT INTO ContentManagement.Category(\"Id\", \"Title\") VALUES (?, ?);");
            stmt.setObject(1, category.getId());
            stmt.setString(2, category.getTitle());

            stmt.executeUpdate();
            c.commit();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (insertCategory)");
    }
    //endregion

    //region [ - selectCategories() - ]
    public ArrayList<Category> selectCategories() {
        Connection c;
        Statement stmt;
        ArrayList<Category> categories = null;
        try {
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (selectCategories)");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ContentManagement.Category;");
            categories = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category();
                category.setId(UUID.fromString(rs.getString("Id")));
                category.setTitle(rs.getString("Title"));
                categories.add(category);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (selectCategories)");
        return categories;
    }
    //endregion

    //region [ - selectCategory(UUID Id) - ]
    public Category selectCategory(UUID Id) {
        Connection c;
        PreparedStatement stmt;
        Category category = null;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (selectCategory)");

            stmt = c.prepareStatement("SELECT * FROM ContentManagement.Category WHERE \"Id\" = ?");
            stmt.setObject(1, Id);
            ResultSet rs = stmt.executeQuery();
            category = new Category();

            category.setId(UUID.fromString(rs.getString("Id")));
            category.setTitle(rs.getString("Title"));

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (selectCategory)");
        return category;
    }
    //endregion

    //region [ - updateCategory(Category category) - ]
    public void updateCategory(Category category) {
        Connection c;
        PreparedStatement stmt;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (updateCategory)");

            stmt = c.prepareStatement("UPDATE ContentManagement.Category SET \"Title\" = ? WHERE \"Id\" = ?;");

            stmt.setString(1, category.getTitle());
            stmt.setObject(2, category.getId());

            stmt.executeUpdate();
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully (updateCategory)");
    }
    //endregion

    //region [ - deleteCategory(UUID Id) - ]
    public void deleteCategory(UUID Id) {
        Connection c;
        PreparedStatement stmt;
        try {
//            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully (deleteCategory)");

            stmt = c.prepareStatement("DELETE FROM ContentManagement.Category WHERE \"Id\" = ?;");
            stmt.setObject(1, Id);

            stmt.executeUpdate();
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully (deleteCategory)");
    }
    //endregion

    //endregion


    //endregion
}
