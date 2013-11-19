(ns template.template
  (:require [noir.session :as session])
  (:use  [hiccup.core :only [html]]
         [hiccup.page :only [include-css doctype]]
         [hiccup.page :only [include-js doctype]]
         ))

(defn get-template
  "generating template for all pages except login/registration"
  [title js sc-connect-button content]
  (html
    (doctype :html5)
    [:html {"xml:lang" "en" :lang "en"} 
      [:head
        [:title {} title] 
        [:link {:rel "shortcut icon", :href "https://googledrive.com/host/0B9gNN6hownJNMk1OMVlEd2lyU00/clojure-sound.png"}] 
        (include-css "/css/menu/core.css")
        (include-css "/css/menu/effects/fading.css")
        (include-css "/css/menu/effects/slide.css")
        (include-css "/css/menu/styles/lcyan.css")
        (include-css "/css/skins/cyan.css")
        (include-css "/css/animate.min.css")
        (include-css "/css/bootstrap-responsive.min.css")
        (include-css "/css/bootstrap.min.css")
        (include-css "/css/font-awesome.min.css")
        (include-css "/css/fontawesome-webfont.woff")
        (include-css "/css/main.css")
        (include-css "/css/notification/stylen.css")
        (include-js "/js/vendor/jquery.min.js")
        (include-js "/js/main.js")
        (include-js "/js/vendor/bootstrap.min.js")
        (include-js "/js/vendor/modernizr.min.js")
        (include-js "/js/plugins.js")
        [:script {:src "http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"}] 
        [:script {:src "http://connect.soundcloud.com/sdk.js"}]
        [:script {:src "http://code.jquery.com/jquery-1.10.1.min.js"}]
        [:script {:src "http://code.jquery.com/jquery-migrate-1.2.1.min.js"}]
        
        js
        
;        povezivanje sa sc profilom i popunjavanje polja profila
        
[:script {} "\n        SC.initialize({\n          client_id: '69fc8587cf1f1d3efcaab8c6720c6e95',\n          redirect_uri: 'http://localhost:8080/home' \n       });\n      \n        $('#connect-to-soundcloud').live('click', function(){\n          SC.connect(function(){\n            SC.get('/me', function(me){ \n       document.getElementById('avatar-sc-account').src=me.avatar_url; \n       document.getElementById('sc-account-nav-img').src=me.avatar_url;  \n       $('#avatar-sc-account').val(me.avatar_url);  \n           $('#username-sc-account').val(me.username);\n              $('#description-sc-account').val(me.description);\n           $('#country-sc-account').val(me.country);\n          $('#city-sc-account').val(me.city);\n        document.getElementById('online-sc-account').src='https://googledrive.com/host/0B9gNN6hownJNMk1OMVlEd2lyU00/Status-user-online-icon.png';\n         $('#fullname-sc-account').val(me.full_name);\n         document.getElementById('url-sc-account').href = me.permalink_url; \n       $('#url-sc-account').text(me.permalink_url); \n       $('#website-link-sc-account').val(me.website);  \n       $('#website-title-sc-account').val(me.website_title);\n       \n       document.getElementById('description-sc-account').disabled=false;\n       document.getElementById('city-sc-account').disabled=false; \n       document.getElementById('btn-update-sc-account').disabled=false;\n       document.getElementById('website-link-sc-account').disabled=false; \n       document.getElementById('website-title-sc-account').disabled=false; \n      });\n\n       SC.get('/me/followings', function(meFollow){\n         console.log(meFollow);\n         var result = '';\n         for (var i=0;i<meFollow.length;i++){ \n           result = result + meFollow[i].username + '; ';}\n         $('#follow-sc-account').val(result);\n});\n\nSC.get('/me/followers', function(meFollowers){\n         console.log(meFollowers);\n         var result = '';\n         for (var i=0;i<meFollowers.length;i++) \n { \n           result = result + meFollowers[i].username + '; ';}\n         $('#followers-sc-account').val(result);\n});\n\n SC.get('/me/favorites', function(meFavorites){\n         console.log(meFavorites);\n         var result = '';\n         for (var i=0;i<meFavorites.length;i++){ \n           result = result + meFavorites[i].title + '; ';\n            }\n         $('#favorites-sc-account').val(result);\n});\n\nSC.get('/me/tracks', function(meTracks){\n         console.log(meTracks);\n         var result = '';\n         for (var i=0;i<meTracks.length;i++){ \n           result = result + meTracks[i].title + '; ';\n            }\n         $('#tracks-sc-account').val(result);\n});\n\nSC.get('/me/playlists', function(mePlaylists){\n         console.log(mePlaylists);\n         var result = '';\n         for (var i=0;i<mePlaylists.length;i++){ \n           result = result + mePlaylists[i].title + ';';\n            }\n         $('#playlists-sc-account').val(result);\n});\n\n        });\n        });\n       "]

;        update podataka profila
        [:script {} "\n  SC.initialize({\n    client_id: \"69fc8587cf1f1d3efcaab8c6720c6e95\",\n    redirect_uri: \"http://localhost:8080/home\" \n });\n \n 
$(\"#btn-update-sc-account\").live(\"click\", function(){SC.put(\"/me\", {user: {description: $(\"#description-sc-account\").val(), city: $(\"#city-sc-account\").val(), website: $(\"#website-link-sc-account\").val(), website_title: $(\"#website-title-sc-account\").val()}}, function(response, error){
      if(error){
        alert(\"Some error occured: \" + error.message);
      }else{
        alert(\"Profile successfully updated!\");
      }}); \n });"]

;      play-sound-button
      [:script {:type "text/javascript"} "\n SC.initialize({ \n client_id: \"69fc8587cf1f1d3efcaab8c6720c6e95\" \n });
\n $(\"#play-sound-button\").live(\"click\", function(){ \n  SC.stream(\"/tracks/293\", function(sound){\n sound.play(); \n}); }); \n"]
        
;search

[:script {} "\n function search(){\n  if(document.getElementById('search-field').value==''){\n alert('You must inser some data in search field'); \n document.getElementById('search-field').focus(); \n return false; };   SC.initialize({\n     client_id: '69fc8587cf1f1d3efcaab8c6720c6e95'\n   });\n     \n   SC.get('/tracks', { q: $('#search-field').val()}, function(tracks) {\n  document.getElementById('table-result').innerHTML = '';  \n     var table = document.createElement('table');\n   var baseRow = document.createElement('tr');\n   var baseCell = document.createElement('td');\n   var container = document.getElementById('table-result');\n     \n     for(var i = 0; i < tracks.length; i++){\n     \n       var myRow = baseRow.cloneNode(false);\n       var title = baseCell.cloneNode(false);\n       var genre = baseCell.cloneNode(false);\n       var url = baseCell.cloneNode(false);\n       baseCell.width='390px';\n       title.innerHTML = tracks[i].title;\n       genre.innerHTML = tracks[i].genre;\n       url.innerHTML = tracks[i].permalink_url;\n     myRow.appendChild(title);\n       myRow.appendChild(genre);\n       myRow.appendChild(url);\n   \n     table.appendChild(myRow);\n   }\n    \n     container.appendChild(table);\n     container.style.display='block';\n     jQuery(\"table\").find(\"tr:odd\").css(\"background-color\", \"#EBEBEB\"); \n     jQuery(\"table\").find(\"tr:even\").css(\"background-color\", \"white\");\n   });  \n   }\n              "]

;clear search

[:script {} "\n function clearSearch(){\n document.getElementById('table-result').style.display='none'; \n document.getElementById('search-field').text(''); \n document.getElementById('search-field').focus(); \n}"]

;search nav - focus search field

[:script {} "\n function focusSearch(){\n document.getElementById('search-field').focus(); \n } \n"]

        [:meta {:charset "UTF-8"}]]
      
      [:body {:class "wide", :onload "loadMusic()"}
       [:div {:class "layout"} 
        [:header {} 
         
         [:div {:class "header"} 
          [:div {:class "container"} 
           [:div {:class "row"}                           
            [:div {:class "span4"} 
             [:div {:class "logo"} 
              [:a {:shape "rect", :href "#home"} 
               [:img {:id "logo", :src "https://googledrive.com/host/0B9gNN6hownJNMk1OMVlEd2lyU00/clojure-sound.png", :style "height: 100px" :alt "Clojure soundcloud"}]] 
              [:h2 {:class "hide-text"} "Clojure soundcloud"]]]]]] 
         
         [:nav {:id "nav", :style "position: fixed; left: 0px; top: 0px;", :class "navigation lcyan"} 
          [:div {:class "container"} 
           [:div {:class "row"} 
            [:div {:class "span12"} 
             [:ul {:class "menu fading"} 
              [:li {} 
               [:a {:shape "rect", :href "#home", :id "home-nav", :class "home-nav"} 
                [:i {:class "fa fa-home fa-lg"}] " Home"]] 
             [:li {} 
               [:a {:class "search-nav", :id "search-nav", :shape "rect", :href "#search", :onclick "focusSearch()"} 
                [:i {:class "fa fa-search fa-lg"}] " Search"]]
             
             [:li {} 
               [:a {:shape "rect", :href "/logout"} 
                [:i {:class "fa fa-sign-out fa-lg"}] " Log out"]]
             [:li {:style "float: right; "} 
               [:a {:class "profile-nav", :id "profile-nav", :shape "rect", :href "#"} 
                [:i {:class "fa fa-users fa-lg"}] " Profiles"]
               [:ul {} 
                [:li {} 
                 [:a {:shape "rect", :href "#sc-account", :id "sc-account-nav", :class "sc-account-nav"}
                  [:img {:class "sc-account-nav-img", :id "sc-account-nav-img", :src "https://googledrive.com/host/0B9gNN6hownJNMk1OMVlEd2lyU00/user-avatar.png", :style "height: 19px; width: 19px;"}] " Soundcloud account"]]]]
             ]]]]]]
        
            sc-connect-button
            
            [:div {:class "main"} 
             content] 
            
            [:div {:class "ticker"} 
             [:div {:class "container"} 
              [:div {:class "row"}
               [:div {:class "span12"} 
                [:div {:class "tweet", :id "ticker"} ]]]]] 
            
            [:footer {}
             [:div {:class "copyright"}
              [:div {:class "container"} 
               [:div {:class "row"} 
                [:div {:class "span5"} 
                 [:p {} "Copyright © Rusimov Milan, FON 2013."]] 
                [:div {:class "span7"}
                 [:ul {:class "unstyled"}]]]]]]]
       [:a {:shape "rect", :class "scroll-top", :href "#home"} 
            [:i {:class "fa fa-chevron-up fa-lg"}]]]]))

(defn get-template-index
  "generating template for login page"
  [title js content]
  (html
    (doctype :html5)
    [:html {"xml:lang" "en" :lang "en"} 
      [:head
        [:title {} title] 
        [:link {:rel "shortcut icon", :href "https://googledrive.com/host/0B9gNN6hownJNMk1OMVlEd2lyU00/clojure-sound.png"}] 
        (include-css "/css/login/demo.css")
        (include-css "/css/login/style.css")
        (include-css "/css/notification/stylen.css")
        [:meta {:charset "UTF-8"}]
        
        js
        
        ;ucitavanje soundcloud js fajla
        
        [:script {:src "http://connect.soundcloud.com/sdk.js"}]
        [:script {:src "http://code.jquery.com/jquery-1.10.1.min.js"}]
        [:script {:src "http://code.jquery.com/jquery-migrate-1.2.1.min.js"}]
        
;        povezivanje sa SC profilom
        
        [:script {} "\n  SC.initialize({\n    client_id: \"69fc8587cf1f1d3efcaab8c6720c6e95\",\n    redirect_uri: \"http://localhost:8080/home\", \n scope: 'non-expiring', \n client_secret: '317ae835a33f3dbcd76afeba957063c6' \n });\n\n  $(\"#connect-to-soundcloud\").live(\"click\", function(){\n    SC.connect(function(){\n      SC.get(\"/me\", function(me){\n        $(\".sc-login\").text(me.username);\n        $(\"#description\").val(me.description);\n   window.location.replace(\"http://localhost:8080/home\");   });\n    });\n  });\n "]
   
;      js za prazna login polja
      
      [:script {:type "text/javascript"} "\n function showMessage(){\n  if (document.forms[0].username.value=='' || document.forms[0].password.value=='') {\n alert(\"You must insert username and password!\"); \n document.forms[0].username.focus(); \nreturn false; \n}  \n return true; \n }\n"]]
  
      
      [:body {:onload "setFocus()"}
       [:div {:class "container"}
        [:p.error {:style "color: red; margin-top: 20px;"} (session/flash-get :login-error)]
                  [:header {}] 
                  [:section {} 
                   [:div {:id "container_demo"} 
                    content]]]]]))


(defn get-template-registration
  "generating template for registration page"
  [title js content]
  (html
    (doctype :html5)
    [:html {"xml:lang" "en" :lang "en"} 
      [:head
        [:title {} title] 
        [:link {:rel "shortcut icon", :href "https://googledrive.com/host/0B9gNN6hownJNMk1OMVlEd2lyU00/clojure-sound.png"}] 
        (include-css "/css/login/demo.css")
        (include-css "/css/login/style.css")
        (include-css "/css/notification/stylen.css")
        [:meta {:charset "UTF-8"}]
        
        js
 
        [:script {:src "http://code.jquery.com/jquery-1.10.1.min.js"}]
        [:script {:src "http://code.jquery.com/jquery-migrate-1.2.1.min.js"}]
      
      
;      js za prazna login polja
      
      [:script {:type "text/javascript"} "\n function showMessage(){\n  if (document.forms[0].first-name.value=='' || document.forms[0].last-name.value=='' || document.forms[0].email.value=='' || document.forms[0].username.value=='' || document.forms[0].password.value=='' || document.forms[0].confirm-password.value=='') {\n alert(\"You must insert all data!\"); \n document.getElementById(\"first-name\").focus(); \n return false; \n   \n} \n var x=document.forms[\"register-form\"][\"email\"].value; \n var atposition=x.indexOf(\"@\"); \n var dotposition=x.lastIndexOf(\".\"); \n if (atposition<>1 || dotposition<atposition+2 || dotposition+2>=x.length){\n alert(\"Not a valid e-mail address!\"); return false; \n} \n return true; \n };\n"]]
  
      
      [:body {:onload "setFocus()"}
       [:div {:class "container"}
        [:p.error {:style "color: red; margin-top: 20px;"} (session/flash-get :register-error)]
                  [:header {}] 
                  [:section {} 
                   [:div {:id "container_demo"} 
                    content]]]]]))