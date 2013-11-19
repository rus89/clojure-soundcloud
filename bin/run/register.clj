(ns run.register
  (:require [noir.session :as session]
            [ring.util.response :as response])
  (:use [hiccup.form :only [form-to label text-field password-field submit-button]]
        [template.template :only [get-template-registration]]
        [db.db :only [get-user-by-username insert-user get-user-by-email insert-user]]
        ))

(defn register-page
  "shows registration page"
  []
  (get-template-registration ":: Car repair shop :: Registration"
                                                                                   
;      js za focus first-name
      
      [:script {:type "text/javascript"} "\n function setFocus(){ \n document.getElementById(\"first-name\").focus();} \n "]
                                   
                    [:div {:id "wrapper", :style "margin-bottom: 50px;"} 
                    [:div {:class "animate form", :id "register"} 
                     (form-to {:name "register-form", :id "register-form", :onsubmit "return showMessage()"} [:post "/register"]
                       [:h1 {} " Sign up "] 
                       [:p {} 
                        (label :first-name "Your first name")
                        (text-field :first-name (session/flash-get :first-name))] 
                       [:p {} 
                        (label :last-name "Your last name")
                        (text-field :last-name (session/flash-get :last-name))] 
                       [:p {} 
                        (label :username "Your username")
                        (text-field :username (session/flash-get :username))] 
                       [:p {} 
                        (label :email "Your email")
                        (text-field :email (session/flash-get :email))] 
                       [:p {} 
                        (label :password "Your password") 
                        (password-field :password (session/flash-get :password))] 
                       [:p {} 
                        (label :confirm-password "Please confirm your password")
                        (password-field :confirm-password (session/flash-get :confirm-password))] 
                       [:p {:class "signin button"} 
                        (submit-button "Sign up")]
                       [:p {:class "change_link"} "  \n\t\t\t\t\t\t\t\t\tAlready a member ?\n\t\t\t\t\t\t\t\t\t" 
                        [:a {:shape "rect", :class "register", :href "/"} " Go and log in "]])]]))

(defn- check-user-data
  "Check enetered user data." 
  [first-name last-name email username password confirm-password]
  (cond
    (> 1 (.length first-name)) "First name must be at least 1 character long."
    (> 1 (.length last-name)) "Last name must be at least 1 character long."
    (< 35 (.length (str last-name first-name))) "First and last should not be longer than 35 characters combined."
    (not (nil? (get-user-by-email email))) "Email address already exist. Please choose another one."
    (not (nil? (get-user-by-username username))) "Username already exist. Please choose another one."
    (> 3 (.length username)) "Username must be at least 3 characters long."
    (< 15 (.length username)) "Username should not be longer than 15 characters."
    (> 3 (.length password)) "Password must be at least 3 characters long."
    (not= password confirm-password) "Password and confirmed password are not the same."
    :else true))

(defn do-register 
  "If user data is entered properly, add user to database."
  [first-name last-name email username password confirm-password]
  (let [lu (clojure.string/lower-case username)
        register-error-msg (check-user-data first-name last-name email lu password confirm-password)]
    (if-not (string? register-error-msg)
      (do
        (insert-user (str first-name " " last-name) email lu password)
        (response/redirect "/"))
      (do
        (session/flash-put! :register-error register-error-msg)
        (session/flash-put! :first-name first-name)
        (session/flash-put! :last-name last-name)
        (session/flash-put! :email email)
        (session/flash-put! :username lu)
        (response/redirect "/register")))))

