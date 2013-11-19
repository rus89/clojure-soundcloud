(ns run.run
  (:require [compojure.route :as route]
            [noir.session :as session]
				    [ring.util.response :as response])
  (:use [compojure.core :only [defroutes GET POST DELETE PUT]]
        [ring.adapter.jetty :only [run-jetty]]
        [run.main :only [index do-login do-logout]]
        [run.register :only [register-page do-register]]
        [db.db :only [insert-user get-user-by-username get-logged-users get-all-users ]]
        [pages.home]
        [ring.middleware.reload :only [wrap-reload]]
        [ring.middleware.stacktrace :only [wrap-stacktrace]]
        [ring.middleware.params :only [wrap-params]]))

(defroutes handler
  (GET "/" [] (index))
  (POST "/" [username password] (do-login username password))
  (GET "/home" [] (let [user (session/get :user)] (if user (home) (index))))
  (POST "/home" [] (let [user (session/get :user)] (if user (home) (index))))
  (GET "/logout" [] (do (do-logout) (response/redirect "/")))
  (GET "/register" [] (let [user (session/get :user)] (if-not user (register-page) (index))))
  (POST "/register" [first-name last-name email username password confirm-password] (do-register first-name last-name email username password confirm-password))
  (route/resources "/")
  (route/not-found "Sorry, page not found!"))

(def app
  (-> #'handler
    (wrap-reload)
    (wrap-params)
    (session/wrap-noir-flash)
    (session/wrap-noir-session)
    (wrap-stacktrace)))

(defn start-server
  "starting server"
  []
   (run-jetty #'app {:port 8080 :join? false})
   (println "\nType http://localhost:8080 in your browser."))

(defn insert-test-user [] 
  (insert-user "Milan Rusimov" "rusmil89@gmail.com" "rus" "rus")
  (insert-user "Igor Gogic" "igac26@gmail.com" "igac" "igac")
  (insert-user "Mitko Rusimov" "mitko@gmail.com" "mitko" "mitko"))
 
(defn -main 
  [& args]
  (start-server)
  (insert-test-user))