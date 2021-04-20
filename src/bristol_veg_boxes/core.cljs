(ns mydemocracy.core
  (:require [uix.dom.alpha :as uix.dom]
            [uix.core.alpha :as uix]
            [xframe.core.alpha :as xf :refer [<sub]]
            [ajax.core :as ajax]
            ["react" :as react]

            [mydemocracy.fx]
            [mydemocracy.subs]
            [mydemocracy.util :as util]
            [mydemocracy.firebase :as firebase]
            [mydemocracy.firebase-auth-ui :as auth-ui]))

(defn ! [& evt]
  (apply xf/dispatch evt))

(defn <- [k]
  (<sub [:get k]))

(defn header []
  [:div
   [:img {:src "images/logo.png"}]
   [:h3 "Bristol Veg Boxes"]
   [:h2 "BOXES & PACKAGING"]
   [:p "We use an assortment of recycled boxes from various fruit and veg wholesalers around Bristol. Boxes can be collected if left outside the following week.
Plastic bags can also be returned to us for recycling!"]])

(defn products []
  (uix/with-effect []
    (firebase/collection-on-snapshot ["products"] #(xf/dispatch [:set [:products] %])))
  (util/for-idx i [product (<sub [:get :products])]
                [:div.product (pr-str product)]))

(defn logged-in []
  [:<>
   [products]
   [:button {:on-click #(xf/dispatch [:firebase/sign-out])}
    "log out"]])

(defn main []
  [:<>
   [header]
   [:div#content
    (if (<- :firebase/user)
      [logged-in]
      [auth-ui/login-create-account])]])

(defn ^:dev/after-load start []
  (uix.dom/render [main] (.getElementById js/document "app"))
  (firebase/init))

(defn init []
  (xf/dispatch [:db/init])
  (start))