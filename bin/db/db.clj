(ns db.db
  (:require [noir.session :as session]
            [clj-time.format :as time-format]
            [clj-time.core :as time])
  (:use [somnium.congomongo]))

(def connection
  (make-connection "sc_db"
                   :host "127.0.0.1"
                   :port 27017))

(set-connection! connection)

(defn- generate-id [collection]
  "generate entity id" 
  (:seq (fetch-and-modify :sequences {:_id collection} {:$inc {:seq 1}}
                          :return-new? true :upsert? true)))

(defn- insert-entity [collection values]
   "insert an entity into database"
  (insert! collection (assoc values :_id (generate-id collection))))

(defn insert-user
  [name email username password]
  "insert user into database" 
  (insert-entity :users 
                  {:name name
                   :email email
                   :username username
                   :password password}))

(defn get-user-by-username [username]
  "Find user by username."  
  (fetch-one :users :where {:username username}))

(defn get-user-by-email [email]
  "Find user by email."  
  (fetch-one :users :where {:email email}))

(defn get-logged-users []
  "Return logged users."
  (let [user (session/get :user)]
    (fetch-one :users :where {:username (:username user)})))

(defn get-all-users []
  "Return all users from the database." 
  (fetch :users :sort {:date-added -1}))

(defn delete-user [id]
  "Delete user from the database."
  (destroy! :users {:_id id}))

(defn update-account
  [name email password]
  "Update user account into database."
  (let [id (:_id (session/get :user)), username (:username (session/get :user))]
   (fetch-and-modify :users {:_id id} {:name name :email email :username username :password password})))

