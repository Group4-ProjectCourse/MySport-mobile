package com.mysport.mysport_mobile.models;
import at.favre.lib.crypto.bcrypt.BCrypt;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import eu.dozd.mongo.MongoMapper;
import eu.dozd.mongo.annotation.Entity;
import eu.dozd.mongo.annotation.Id;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoManager {

    static {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
    }

    public boolean addActivity(LocalDate date, MongoActivity activity){
        try(MongoClient client = getClient()) {
            MongoCollection<MongoDay> collection = getCollection(client, date);
            if (collection.find(Filters.eq("_id", date.getDayOfMonth())).first() == null)
                insertDay(collection, date.getDayOfMonth(), date.getDayOfYear());
            return collection.updateOne(
                    Filters.eq("_id", date.getDayOfMonth()),
                    Updates.addToSet("activities", activity)
            ).getModifiedCount() != 0;
        }
    }

    public boolean addUser(MongoUser user){
        try(MongoClient client = getClient()) {
            MongoCollection<MongoUser> collection = getCollection(client);
            if (collection.find(Filters.eq("email", user.getEmail())).first() == null)
                return insertUser(collection, user);
            return false;//Error: User exists
        }
    }

    public boolean addParticipant(LocalDate date, String sport, int id, boolean isLeader){
        String table = isLeader ? "leaders" : "members";
        try(MongoClient client = getClient()) {
            return getCollection(client, date).updateOne(
                    BasicDBObject.parse("{ _id: "+date.getDayOfMonth()+", " +
                            "\"activities._id\": \"" + sport + "\" }"),
                    BasicDBObject.parse("{ $push: {\"activities.$." + table + "\": " + id + "}}")
            ).getModifiedCount() != 0;
        }
    }

    public boolean changeTime(LocalDate date, String sport, int startTime, int newStart, int newEnd){
        try(MongoClient client = getClient()) {
            return getCollection(client, date).updateOne(
                    BasicDBObject.parse("{ _id: " + date.getDayOfMonth() + ", " +
                            "\"activities._id\": \"" + sport + "\", " +
                            "\"activities.start\": " + startTime + " }"),
                    BasicDBObject.parse("{ $set: { \"activities.$.start\": " + newStart + ", \"activities.$.end\": " + newEnd + " } }")
            ).getModifiedCount() != 0;
        }
    }

    public boolean removeParticipant(LocalDate date, String sport, int id, boolean isLeader){
        try(MongoClient client = getClient()) {
            return getCollection(client, date).updateOne(
                    BasicDBObject.parse("{ _id: " + date.getDayOfMonth() + ", " +
                            "\"activities._id\": \"" + sport + "\" }"),
                    BasicDBObject.parse("{ $pull: { \"activities.$." + (isLeader ? "leaders" : "members") + "\": " + id +" } }")
            ).getModifiedCount() != 0;
        }
    }

    public boolean removeActivity(LocalDate date, String sport, int startTime){
        try(MongoClient client = getClient()) {
            return getCollection(client, date).updateOne(
                    BasicDBObject.parse("{ _id: " + date.getDayOfMonth() + " }"),
                    BasicDBObject.parse("{ $pull: { \"activities.$._id\": \"" + sport + "\", " +
                            "\"activities.$.start\": \"" + startTime + "\" } }")
            ).getModifiedCount() != 0;
        }
    }

    public boolean removeDay(LocalDate date){
        try(MongoClient client = getClient()) {
            return getCollection(client, date).deleteOne(
                    BasicDBObject.parse("{ _id: " + date.getDayOfMonth() + " }")
            ).getDeletedCount() != 0;
        }
    }

    public boolean removeUser(String email){
        try(MongoClient client = getClient()) {
            return getCollection(client).deleteOne(
                    BasicDBObject.parse(String.format("{ email: '%s' }", email))//if inserting value is string then there must be quotation marks around it
            ).getDeletedCount() != 0;
        }
    }

    //vulnerability if used on Application side not server
    public boolean changePassword(String email, String oldPassword, String newPassword){
        if(oldPassword.equals(newPassword))
            return false;//Error: Same passwords
        try(MongoClient client = getClient()) {
            MongoUser user = getCollection(client).find(BasicDBObject.parse(String.format("{ email: '%s' }", email))).first();
            if (user == null)
                return false;//Error: User not found
            else if(!BCrypt.verifyer().verify(oldPassword.toCharArray(), user.getPassword()).verified)
                return false;//Error: Passwords do not match

            return getCollection(client).updateOne(
                    BasicDBObject.parse(String.format("{ email: '%s' }", email)),
                    BasicDBObject.parse(String.format("{ $set: { password: '%s' } }", BCrypt.withDefaults().hashToString(12, newPassword.toCharArray())))
            ).getModifiedCount() != 0;
        }
    }

    private void insertDay(MongoCollection<MongoDay> collection, int dayOfMonth, int dayOfYear){
        collection.insertOne(new MongoDay(dayOfMonth, dayOfYear, new ArrayList<>(5)));
    }

    private boolean insertUser(MongoCollection<MongoUser> collection, MongoUser mongoUser){
        return collection.insertOne(mongoUser).wasAcknowledged();
    }

    //Legacy
    public int getParticipantsCount(LocalDate date, String sport){
        int sum = 0;
        try(MongoClient client = getClient()){
            MongoCollection<MongoDay> collection = getCollection(client, date);
            MongoDay day = collection.find(
                    BasicDBObject.parse("{ _id: "+ date.getDayOfMonth()+", " +
                            "\"activities._id\": \"" + sport + "\" }")
            ).first();

            for(Object o : day.getActivities()){
                Document activity = (Document) o;
                if(activity.getString("_id").equals(sport)){
                    sum = activity.getList("members", Integer.class).size() + activity.getList("leaders", Integer.class).size();
                    break;
                }
            }
        }

        return sum;
    }

    public MongoDay getDay(){
        return getDay(LocalDate.now());
    }

    public MongoDay getDay(LocalDate date){
        ArrayList<MongoActivity> activities = new ArrayList<>(5);
        try(MongoClient client = getClient()){
            MongoCollection<MongoDay> collection = getCollection(client, date);
            FindIterable<MongoDay> day = collection.find(
                    BasicDBObject.parse("{ _id: "+ date.getDayOfMonth()+" }")
            );
            if(day.first() == null)
                return null;
            for(Object a : day.first().getActivities()){
                Document document = (Document) a;
                activities.add(new MongoActivity(
                        document.getString("_id"),
                        document.getString("location"),
                        document.getInteger("startHour"),
                        document.getInteger("startMinutes"),
                        document.getInteger("endHour"),
                        document.getInteger("endMinutes"),
                        new ArrayList<>(document.getList("leaders", Integer.class)),
                        new ArrayList<>(document.getList("members", Integer.class)), document.getInteger("rating")
                ));
            }
        }
        return new MongoDay(date.getDayOfMonth(), date.getDayOfYear(), activities);
    }

    private MongoCollection<MongoDay> getCollection(MongoClient client, LocalDate date){
        return getDatabase(client, String.valueOf(date.getYear())).getCollection(date.getMonth().name(), MongoDay.class);
    }

    private MongoCollection<MongoUser> getCollection(MongoClient client){
        return getDatabase(client, "user_db").getCollection("users", MongoUser.class);
    }

    private MongoDatabase getDatabase(MongoClient client, String name){
        return client.getDatabase(name);
    }

    private static MongoClient getClient(){
        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(MongoMapper.getProviders()),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(new ConnectionString("mongodb+srv://admin:azino777@mysport-4hkzu.mongodb.net/test?retryWrites=true&w=majority"))
                .build();

        return MongoClients.create(settings);
    }

    @Entity
    public static final class MongoDay {
        @Id
        private int day;
        private int dayOfYear;
        private ArrayList<MongoActivity> activities;

        public MongoDay(){

        }

        public MongoDay(int day, int dayOfYear, ArrayList<MongoActivity> activities){
            this.dayOfYear = dayOfYear;
            this.day = day;
            this.activities = activities;
        }

        public void removeActivity(String name){
            for(MongoActivity a : activities){
                if(a.name.equals(name)){
                    activities.remove(a);
                    break;
                }
            }
        }

        public ArrayList<MongoActivity> getActivities() {
            return activities;
        }

        public void setActivities(ArrayList<MongoActivity> activities) {
            this.activities = activities;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        @Override
        public String toString() {
            return "day: " + day + " activities count: " + activities.size();
        }

        public int getDayOfYear() {
            return dayOfYear;
        }

        public void setDayOfYear(int dayOfYear) {
            this.dayOfYear = dayOfYear;
        }
    }

    @Entity
    public static final class MongoUser {
        @Id
        private String userId;
        private String firstname;
        private String surname;
        private String personalNumber;
        private String position;
        private String email;
        private String password;
        private double balance;

        public MongoUser(){

        }

        public MongoUser(String email, String password, String firstname, String surname, String personalNumber, String position, double balance){
            this(UUID.randomUUID().toString(), email, password, firstname, surname, personalNumber, position, balance);
        }

        public MongoUser(String userId, String email, String password, String firstname, String surname, String personalNumber, String position, double balance){
            this.userId = userId;
            this.email = email;
            this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            this.firstname = firstname;
            this.surname = surname;
            this.personalNumber = personalNumber;
            this.position = position;
            this.balance = balance;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getPersonalNumber() {
            return personalNumber;
        }

        public void setPersonalNumber(String personalNumber) {
            this.personalNumber = personalNumber;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @Entity
    public static final class MongoActivity {
        @Id
        private String name;
        private String location;
        private int rating;
        private int startHour; //in minutes (e.g: 15:30 is (15 * 60 + 30) = 930, then to convert back 930 div 60 + 930 mod 60)
        private int startMinutes;
        private int endHour; //in minutes (ee.g: 16:45 is (16 * 60 + 45) = 1005, then to convert back 1005 div 60 + 1005 mod 60)
        private int endMinutes;
        private ArrayList<Integer> leaders;
        private ArrayList<Integer> members;

        public MongoActivity(){

        }

        public MongoActivity(String name, String location, int[] startHour, int[] endHour){
            this(name, location, startHour[0], startHour[1], endHour[0], endHour[1]);
        }

        public MongoActivity(String name, String location, int startHour, int startMinutes, int endHour, int endMinutes){
            this(name, location, startHour, startMinutes, endHour, endMinutes, null, null, 0);
        }

        public MongoActivity(String name, String location, int startHour, int startMinutes, int endHour, int endMinutes, ArrayList<Integer> leaders, ArrayList<Integer> members, int rating){
            this.name = name;
            this.location = location;
            this.rating = rating;
            this.startHour = startHour;
            this.startMinutes = startMinutes;
            this.endHour = endHour;
            this.endMinutes = endMinutes;
            this.leaders = leaders;
            this.members = members;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getRating() {
            return rating;
        }

        public ArrayList<Integer> getLeaders() {
            return leaders;
        }

        public void setLeaders(ArrayList<Integer> leaders) {
            this.leaders = leaders;
        }

        public ArrayList<Integer> getMembers() {
            return members;
        }

        public void setMembers(ArrayList<Integer> members) {
            this.members = members;
        }

        @Override
        public String toString() {
            return "Location: " + location + " Rating: " + rating + " Starts: " + startHour + ":" + startMinutes +
                    " Ends: " + endHour + ":" + endMinutes % 60 + " Leaders count: " + leaders.size() + " Members count: " + members.size();
        }

        public String getTimeSpan(){
            return String.format(Locale.ENGLISH, "%s:%s - %s:%s",
                    startHour == 0 ? "00" : startHour,
                    startMinutes == 0 ? "00" : startMinutes,
                    endHour == 0 ? "00" : endHour,
                    endMinutes == 0 ? "00" : endMinutes);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStartHour() {
            return startHour;
        }

        public void setStartHour(int startHour) {
            this.startHour = startHour;
        }

        public int getEndHour() {
            return endHour;
        }

        public void setEndHour(int endHour) {
            this.endHour = endHour;
        }

        public int getStartMinutes() {
            return startMinutes;
        }

        public void setStartMinutes(int startMinutes) {
            this.startMinutes = startMinutes;
        }

        public int getEndMinutes() {
            return endMinutes;
        }

        public void setEndMinutes(int endMinutes) {
            this.endMinutes = endMinutes;
        }
    }
}