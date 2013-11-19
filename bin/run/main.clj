(ns run.main
  (:require [noir.session :as session]
            [ring.util.response :as response])
  (:use  [hiccup.form :only [form-to text-field password-field label hidden-field submit-button]]
         [hiccup.element :only [image]]
         [template.template :only [get-template-index get-template]]
         [db.db :only [get-user-by-username]]
         ))

(defn index
  "display index page"
  []
  (get-template-index ":: Clojure Soundcloud ::"
                                       
;      js za focus username
      
      [:script {:type "text/javascript"} "\n function setFocus(){ \n document.forms[0].username.focus(); } \n "]
      
                [:div {:id "wrapper"} 
                 [:div {:class "animate form", :id "login"} 
                   (form-to  {:onsubmit "return showMessage()"} [:post "/"]
                   [:h1 {} "Log in"] 
                                      
                   [:p {} 
                    (label :username "Your username")
                    (text-field :username (session/flash-get :username))]
                   [:p {} 
                    (label :username "Your password")
                    (password-field :password (session/flash-get :password))]
                   [:p {:class "login button"} 
                    (submit-button "Log in")]
                   [:p {:class "change_link"} "\n\t\t\t\t\t\t\t\t\tNot a member yet ?\n\t\t\t\t\t\t\t\t\t" 
                    [:a {:shape "rect", :class "register", :href "/register"} "Join us"]])]]))

(defn- check-user
  "Check if the user with given username and passwored is valid."
  [user password]
    (cond
      (nil? user) "User with given username does not exist."
      (not= password (:password user)) "Password is not correct."
      :else true))

(defn do-login
  "If username and password are correct, put the information into session that the admin is logged in."
  [username password]
  (let [lu (clojure.string/lower-case username)
      user (get-user-by-username lu)
      error-msg-u (check-user user password)]
    (cond
      (= true error-msg-u) (do (session/put! :user user)(response/redirect "/home"))
      :else (do (session/flash-put! :login-error "User with given username and password does not exist.") (response/redirect "/")))))

(defn do-logout []
  (session/remove! :user))
             